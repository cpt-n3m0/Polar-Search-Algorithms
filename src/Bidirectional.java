import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

public class Bidirectional {
    private PriorityQueue<Node> sFrontier; //frontier from start side
    private PriorityQueue<Node> gFrontier; // frontier form goal side
    protected ArrayList<Node> explored;
    private int parallels;
    public boolean DEBUG = true;



    public Bidirectional(int parallels)
    {
        sFrontier= new PriorityQueue<Node>(new AstarComparator());
        gFrontier= new PriorityQueue<Node>(new AstarComparator());
        explored = new ArrayList<Node>();
        this.parallels = parallels;
    }


    void reset() {
        this.frontier.clear();
        super.explored.clear();
    }

    private double hCost(State s1, State goal) {
        if(s1.equals(goal))
            return 0;
        return Math.sqrt(Math.pow(s1.getD(), 2) + Math.pow(goal.getD(), 2) - 2 * s1.getD() * goal.getD() * Math.cos(goal.getAngle() - s1.getAngle()));
    }
    private double pathCost(State s1, State s2) {
        if(s1.equals(s2))
            return 0;
        if(s1.getAngle() == s2.getAngle())
            return 1;
        else if(s1.getD() == s2.getD())
            return (2 * Math.PI * s1.getD())/8;
        else
            return -1;
    }
    Node makeNode(Node current, State nu, String action, State goal) {
        double pCost = current.getPathCost() + this.pathCost(current.getState(), nu);
        double hCost =  this.hCost(nu, goal);
        return new Node(current, nu, action, current.getDepth() + 1, pCost, hCost, pCost + hCost );
    }

    boolean isFrontierEmpty()
    {
        return this.frontier.isEmpty();
    }
    ArrayList<Node> removeFromFrontier(int n) {
        ArrayList<Node> explored = new ArrayList<Node>();
        for(int i=0; i < n; i++)
            explored.add(frontier.poll());

        return explored;
    }
    Node getNextNode()
    {
        return this.frontier.peek();
    }
    
    void addToFrontier(Node n)
    {
        this.frontier.add(n);

    }
    private void addToFrontier(Collection<Node> nodes)
    {
        for (Node n: nodes) {
            this.addToFrontier(n);
        }
    }
    protected String collectionString(Collection<Node> ns)
    {
        String out = "";
        for (Node n: ns) {
            out += n.getState().toString() + ",";
        }
        return out;
    }

    private boolean inCollection(State s , Collection<Node> c) {
        //System.out.println("size : " + String.valueOf(c.size()) );
        for (Node n: c)
        {
            if(n.getState().equals(s))
            {
                //System.out.println(s.toString() + " exists ");
                return true;
            }
        }
        return false;
    }
    public ArrayList<State> successor(Node node)
    {
        ArrayList<State> nextStates= new ArrayList<State>();
        for(int i = 0; i < 4; i++)
        {
            State childState = null;
            switch(i)
            {
                case 0: // H360
                    if(node.getState().getD() - 1 > 0) {
                        childState= new State(node.getState().getD() - 1, node.getState().getAngle());
                    }
                    break;
                case 1:// H180
                    if (node.getState().getD() + 1 < this.parallels) {
                        childState= new State(node.getState().getD() + 1, node.getState().getAngle());
                    }
                    break;
                case 2: //H90
                    int t1 = node.getState().getAngle() + 45;
                    int childAngle90 = ( t1 == 360)? 0 : t1;
                    childState= new State(node.getState().getD(), childAngle90);
                    break;
                case 3: // H270
                    int t2 = node.getState().getAngle() - 45;
                    int childAngle270 = ( t2 < 0)? 315 : t2;
                    childState= new State(node.getState().getD(), childAngle270);
                    break;

            }
            if(childState != null)
                nextStates.add(childState);
        }
        return nextStates;
    }
    private String getAction(Node current, State child)
    {
        if(current.equals(child))
            return "Goal reached";
        int angleDiff = current.getState().getAngle() - child.getAngle();
        int dDiff = current.getState().getD() - child.getD();
        if( angleDiff == -45 || angleDiff == 315)
            return "H90";
        else if (angleDiff == 45 || angleDiff == -315)
            return "H270";
        else if( dDiff == 1)
            return "H360";
        else if(dDiff == -1)
            return "H180";

        return "Illegal move";

    }
    public ArrayList<Node> expand(Node node, State goal, ArrayList<Node> noGoNodes) {
        if(this.DEBUG)
            System.out.println("Expanding : " + node.getState().toString() + ":");
        ArrayList<Node> children = new ArrayList<Node>();
        ArrayList<State> childrenStates = successor(node);

        for(State childState: childrenStates)
        {

            if(this.inCollection(childState, this.explored))
                continue;
            if(this.inCollection(childState, this.frontier))
                continue;
            if(this.inCollection(childState, noGoNodes))
                continue;
            children.add(makeNode(node, childState, this.getAction(node, childState),  goal));
        }



        //System.out.println(this.collectionString(children));
        return children;
    }




    public Node search(State start, State goal, ArrayList<Node> noGoNodes) {
        if (start.getD() == 0 || goal.getD() == 0
                || start.getD() > this.parallels - 1 || goal.getD() > this.parallels - 1
                || start.getAngle() % 45 != 0 || goal.getAngle() % 45 != 0 )
        {
            System.out.println("Invalid coordinates");
            return null;
        }
        Node root = new Node(null, start, "Start", 0, 0, 0, 0);
        this.addToFrontier(root);

        while(true)
        {

            if (this.isFrontierEmpty())
            {
                return null;
            }

            Node current =  this.getNextNode();

            if(this.inCollection(current.getState(), this.explored))
            {
                this.removeFromFrontier(1);
                continue;
            }

            if (current.getState().equals(goal))
            {
                return current;
            }
            if(DEBUG) {
                System.out.println("current node : " + current.getState().toString());
            }


            this.explored.add(current);
            this.removeFromFrontier(1);
            if(DEBUG) {
                System.out.println("Frontier : " + this.collectionString(this.frontier));
                System.out.println("Explored : " + this.collectionString(this.explored));
            }
            this.addToFrontier(this.expand(current, goal, noGoNodes));

        }
}

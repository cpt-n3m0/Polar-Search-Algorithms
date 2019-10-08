import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;
import java.lang.Math.*;

public abstract class GeneralSearch {
	protected ArrayDeque<Node> frontier;
	protected ArrayList<Node> explored;
	private int parallels;
	public boolean DEBUG = true;
	
	
	
	public GeneralSearch(int parallels)
	{
		frontier = new ArrayDeque<Node>(); 
		explored = new ArrayList<Node>();
		this.parallels = parallels;
	}
	
	abstract Node makeNode(Node current, State nu, String action, State goal);
	abstract boolean isFrontierEmpty();
	abstract ArrayList<Node> removeFromFrontier(int n);	
	abstract Node getNextNode();
	abstract void addToFrontier(Node n);
	abstract void reset();
	
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
	
	
	
}

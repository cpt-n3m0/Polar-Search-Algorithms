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
	private ArrayList<Node> explored;
	private int parallels;
	public boolean DEBUG = false;
	
	
	
	public GeneralSearch(int parallels)
	{
		frontier = new ArrayDeque<Node>(); 
		explored = new ArrayList<Node>();
		this.parallels = parallels;
	}
	
	abstract double cost(State s1, State s2);
	abstract boolean isFrontierEmpty();
	abstract ArrayList<Node> removeFromFrontier(int n);	
	abstract void addToFrontier(Node n);
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
				System.out.print(s.toString() + " exists ");
				return true;
			}	
		}
		return false;
	}
	public ArrayList<Node> expand(Node node) {
		
		System.out.println("Expanding : " + node.getState().toString() + ":");
		ArrayList<Node> children = new ArrayList<Node>();
		double pathCost = 0;
		String action = "";
		for(int i = 0; i < 4; i++)
		{
			State childState = null;
			
			switch(i)
			{
				case 0: // H360
					if(node.getState().getD() - 1 > 0) {
						childState= new State(node.getState().getD() - 1, node.getState().getAngle());	
						action = "H360";
					}
					break;
				case 1:// H180
					if (node.getState().getD() + 1 < this.parallels) {
						childState= new State(node.getState().getD() + 1, node.getState().getAngle());	
						action = "H180";
					}
					break;
				case 2: //H90
					int t1 = node.getState().getAngle() + 45;
					int childAngle90 = ( t1 == 360)? 0 : t1;
					childState= new State(node.getState().getD(), childAngle90);
					action = "H90";
					break;
				case 3: // H270
					int t2 = node.getState().getAngle() - 45;
					int childAngle270 = ( t2 == -45)? 315 : t2;
					childState= new State(node.getState().getD(), childAngle270);
					action = "H270";
					break;				
			}

			if (childState != null ) {
				pathCost = this.cost(node.getState(), childState);
				Node childNode = new Node(node, childState, action, node.getDepth() + 1, node.getPathCost() + pathCost);
				children.add(childNode);
				System.out.println(this.collectionString(children));

			}
		}
		
		//System.out.println(this.collectionString(children));
		return children;
	}
	

	private void printPath(Node goal)
	{
		ArrayList<String> path = new ArrayList<String>();
		double pathCost = 0;
		Node current = goal;
		do
		{
			path.add(current.getAction());
			pathCost += current.getPathCost();
			current = current.getParent();
		}
		while(current != null);
		Collections.reverse(path);
		for(String action: path)
		{
			System.out.println(action);
		}
		System.out.println("Path Cost: " + String.valueOf(pathCost) );
	}
	
	public Node search(State start, State goal) {
		if (start.getD() == 0 || goal.getD() == 0 || start.getD() > this.parallels - 1 || goal.getD() > this.parallels - 1 )
		{
			System.out.println("Invalid coordinates");
			return null;
		}
		Node root = new Node(null, start, "Start", 0, 0 );
		this.addToFrontier(root);
		
		while(true)
		{
			ArrayList<Node> expanded = new ArrayList<Node>();
			int remove = 0;
			if(DEBUG) {
				System.out.println("Frontier : " + this.collectionString(this.frontier));
				System.out.println("Explored : " + this.collectionString(this.explored));
			}
			for (Node current: frontier) {
				if(this.inCollection(current.getState(), this.explored))
				{
					remove++;
					break;
				}
				if (frontier.isEmpty())
				{
					System.out.println("Search finished. Goal not found.");
					return null;
				}
				
				if (current.getState().equals(goal))
				{
					System.out.println("Goal reached!");
					this.printPath(current);
					return current;
				}
				expanded.addAll(this.expand(current));
				if(DEBUG) {
					System.out.println("current node : " + current.getState().toString());
				}
				remove++;
			}
			if (DEBUG) {
				System.out.println("Expanded : " + this.collectionString(expanded));
			}
			this.explored.addAll(this.removeFromFrontier(remove));
			this.addToFrontier(expanded);
		}
	}
	
	
	
}

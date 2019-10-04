import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;
import java.lang.Math.*;

public class GeneralSearch {
	private PriorityQueue<Node> frontier;
	private ArrayList<Node> explored;
	private int parallels;
	
	
	
	public GeneralSearch(int parallels)
	{
		frontier = new PriorityQueue<Node>(new CostComparator()); 
		explored = new ArrayList<Node>();
		this.parallels = parallels;
	}
	public boolean isFrontEmpty() {
		return this.frontier.isEmpty();
	}
	private double cost(State s1, State s2) {
		if (s1.equals(s2))
			return 0;
		
		if(s1.getAngle() == s2.getAngle())
		{
			return 1;
		}
		else if (s1.getD() == s2.getD())
		{
			double c = (2 * Math.PI * s1.getD())/8;
			System.out.println(c);
			return c;
		}
		else
			System.out.println("Illegal move");
			return -1;
	}
	private double radToDeg(float rad)
	{
		return (rad * 180)/Math.PI;
	}

	public ArrayList<Node> expand(Node node) {
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
					if (node.getState().getD() + 1 <= this.parallels) {
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

			if (childState != null) {
				pathCost = this.cost(node.getState(), childState);
				Node childNode = new Node(node, childState, action, node.getDepth() + 1, node.getPathCost() + pathCost);
				children.add(childNode);
			}
		}
		return children;
	}
	private boolean isExplored(Node node)
	{
		for(Node n: this.explored)
		{
			if(n.equals(node))
				return true;
		}
		return false;
	}
	private Node removeFromFrontier()
	{
		return this.frontier.poll();
	}
	
	private void addToFrontier(Node n)
	{
		this.frontier.add(n);
	}
	private void addToFrontier(Collection<Node> nodes)
	{
		for(Node n: nodes)
		{
			this.frontier.add(n);

		}
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
		Node root = new Node(null, start, "Start", 0, 0 );
		this.addToFrontier(root);

		while(true)
		{
			ArrayList<Node> expanded = new ArrayList<Node>();
			int remove = 0;
			
			for (Node current: frontier) {
				if (frontier.isEmpty())
				{
					System.out.println("Search finished. Goal not found.");
					return null;
				}
				if(this.isExplored(current))
				{
					System.out.println("explored.");
					continue;
				}
				
				if (current.getState().equals(goal))
				{
					System.out.println("Goal reached!");
					this.printPath(current);
					return current;
				}
				expanded.addAll(this.expand(current));
				remove++;
			}
			for(int i = 0 ; i < remove; i++)
				this.removeFromFrontier();
			this.addToFrontier(expanded);
		}
	}
	
	
	
}

import java.util.ArrayList;
import java.util.PriorityQueue;

public class BestFirst extends GeneralSearch{
	private PriorityQueue<Node> frontier;
	
	public BestFirst(int parallels) {
		super(parallels);
		frontier = new PriorityQueue<Node>(new BestFirstCostComparator());
	}
	
	@Override
	void reset() {
		this.frontier.clear();
		super.explored.clear();
	}
	boolean isFrontierEmpty()
	{
		return this.frontier.isEmpty();
	}
	@Override
	ArrayList<Node> removeFromFrontier(int n) {
		ArrayList<Node> explored = new ArrayList<Node>();
		for(int i=0; i < n; i++)
			explored.add(frontier.poll());

		return explored;
	}
	@Override 
	Node getNextNode()
	{
		return this.frontier.peek();
	}
	@Override
	void addToFrontier(Node n)
	{
		this.frontier.add(n);

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
	@Override
	Node makeNode(Node current, State nu, String action, State goal) {
		return new Node(current, nu, action, current.getDepth() + 1, current.getPathCost() + this.pathCost(current.getState(), nu), this.hCost(nu, goal), 0);
	}

}

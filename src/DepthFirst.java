import java.util.ArrayList;

public class DepthFirst extends GeneralSearch{
	public DepthFirst(int parallels ) {
		super(parallels);
	}
	@Override
	void reset() {
		super.frontier.clear();
		super.explored.clear();
	}
	@Override
	boolean isFrontierEmpty()
	{
		return super.frontier.isEmpty();
	}
	@Override
	ArrayList<Node> removeFromFrontier(int n) {
		ArrayList<Node> explored = new ArrayList<Node>();
		for(int i=0; i < n; i++)
			explored.add(super.frontier.poll());

		return explored;
	}
	@Override 
	Node getNextNode()
	{
		return super.frontier.peek();
	}
	@Override
	void addToFrontier(Node n)
	{
		super.frontier.addFirst(n);
	}
	@Override
	Node makeNode(Node current, State nu, String action, State goal) {
		return new Node(current, nu, action, current.getDepth() + 1, 0 , 0, 0);
	}
	
}

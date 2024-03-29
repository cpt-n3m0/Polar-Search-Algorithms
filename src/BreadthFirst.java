import java.util.ArrayList;
import java.util.Deque;

public class BreadthFirst extends GeneralSearch{
	
	public BreadthFirst(int parallels) {
		super(parallels);
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
		super.frontier.offer(n);
	}

	@Override
	Node makeNode(Node current, State nu, String action, State goal) {
		return new Node(current, nu, action, current.getDepth() + 1, 0, 0, 0);
	}

	@Override
	void reset() {
		super.frontier.clear();
		super.explored.clear();
	}
	

}

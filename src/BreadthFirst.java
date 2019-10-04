import java.util.ArrayList;
import java.util.Deque;

public class BreadthFirst extends GeneralSearch{
	
	public BreadthFirst(int parallels) {
		super(parallels);
	}
	@Override
	double cost(State s1, State s2) { // path cost information irrelevant since it's an uninformed search
		return 0;
	}
	@Override
	boolean isFrontierEmpty()
	{
		return super.frontier.isEmpty();
	}
	@Override
	ArrayList<Node> removeFromFrontier(int n) {
		System.out.println(super.collectionString(super.frontier));
		ArrayList<Node> explored = new ArrayList<Node>();
		for(int i=0; i < n; i++)
			explored.add(super.frontier.poll());
		System.out.println(super.collectionString(super.frontier));

		return explored;
	}
	@Override
	void addToFrontier(Node n)
	{
		super.frontier.offer(n);
	}
	

}

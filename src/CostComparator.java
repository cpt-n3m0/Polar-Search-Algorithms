import java.util.Comparator;

public class CostComparator implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		if (o1.getPathCost() == o2.getPathCost())
			return 0;
		else if (o1.getPathCost() > o2.getPathCost())
			return 1;
		else
			return -1;
	}

}

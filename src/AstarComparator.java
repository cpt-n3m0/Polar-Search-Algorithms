import java.util.Comparator;

public class AstarComparator implements Comparator<Node>{
	@Override
	public int compare( Node o1, Node o2) {
		if (o1.getfCost() == o2.getfCost())
			return 0;
		else if (o1.getfCost() > o2.getfCost())
			return 1;
		else
			return -1;
	}
}

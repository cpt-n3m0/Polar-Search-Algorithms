import java.util.Comparator;

public class BestFirstCostComparator implements Comparator<Node>{


		@Override
		public int compare( Node o1, Node o2) {
			if (o1.gethCost() == o2.gethCost())
				return 0;
			else if (o1.gethCost() > o2.gethCost())
				return 1;
			else
				return -1;
		}

	
}

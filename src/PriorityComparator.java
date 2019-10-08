import java.util.Comparator;

public class PriorityComparator implements Comparator<Flight> {

	@Override
	public int compare(Flight o1, Flight o2) {
		if (o1.getPriority() == o2.getPriority())
			return 0;
		else if (o1.getPriority() > o2.getPriority())
			return 1;
		else
			return -1;
	}

}

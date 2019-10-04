
public class FlightControl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GeneralSearch s = new BreadthFirst(3);
		State start = new State(2, 45);
		State goal = new State(1, 225);
		
		s.search(start, goal);
		
	}

}

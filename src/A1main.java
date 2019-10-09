import java.util.ArrayList;
import java.util.PriorityQueue;

public class A1main {

	private static Flight getFlightInfo(int pos, String[] args)
	{
		Flight flight = null;
		
			if (pos + 2 >= args.length)
				return null;
			String[] startCoords = args[pos].split(",");
			String[] goalCoords = args[pos + 1].split(",");
			State start = new State(Integer.valueOf(startCoords[0]), Integer.valueOf(startCoords[1]));
			State goal = new State(Integer.valueOf(goalCoords[0]), Integer.valueOf(goalCoords[1]));

			flight = new Flight(start, goal, Integer.valueOf(args[pos + 2]));
		
		return flight;
	}

	public static void main(String[] args) {
		System.out.println("Starting ..");
		String method = "";
		int parallels = 0;
		int pos = 0;
		PriorityQueue<Flight> flights = new PriorityQueue<Flight>(new PriorityComparator());

		if (args.length < 4) {
			System.out.println(args.length);
			System.out.println("Error: Insufficient number of arguments");
			System.out.println(
					"Usage : java A1main <DFS|BFS|AStar|BestF|...> <N> <d_s,angle_s> <d_g,angle_g> [anyparam]");
			return;
		}
		for (String arg : args) {
			switch (pos) {
			case 0:
				method = arg;
				pos++;
				break;
			case 1:
				parallels = Integer.valueOf(arg);
				pos++;
				break;
			default:
				Flight f = getFlightInfo(pos, args);
				
			    if (f == null) break;
		
				flights.add(f);
				pos += 3;
			}

		}

		
		GeneralSearch s = null;
		Bidirectional bs = null;
		boolean isBs = false;
		switch (method) {
		case "DFS":
			s = new DepthFirst(parallels);
			break;
		case "BFS":
			s = new BreadthFirst(parallels);
			break;
		case "AStar":
			s = new Astar(parallels);
			break;
		case "BestFirst":
			s = new BestFirst(parallels);
			break;
		case  "Bidirectional":
			bs = new Bidirectional(parallels);
			isBs = true;
			break;
		default:
			System.out.println("Unrecognised Method");
			return;
		}

		//s.DEBUG = false;
		ArrayList<Node> noGoNodes = new ArrayList<Node>();
		do
		{
			Flight flight = flights.poll();

			System.out.println(flight.toString() );
			if(isBs) {
				flight.setFlightPath(bs.search(flight.getStart(), flight.getGoal(), noGoNodes));
				bs.reset();
			}
			else {
				flight.setFlightPath(s.search(flight.getStart(), flight.getGoal(), noGoNodes));
				s.reset();
			}
			if(flight.getFlightPath() == null)
			{
				System.out.println("No route found.");
				continue;
			}
			
			flight.printPath();
			noGoNodes.addAll(flight.getFlightPath());

			System.out.println("---------------------------");
		}
		while(flights.isEmpty() == false);


	}

}

import java.util.ArrayList;
import java.util.Date;
import java.util.PriorityQueue;

public class A1main {

	private static Flight getFlightInfo(int pos, String[] args)
	{
		Flight flight = null;
		boolean singleFlight = false;
			if(pos >= args.length)
				return null;
			if (pos + 2 >= args.length && args.length != 4)
				return null;
			else
				singleFlight = true;
			String[] startCoords = args[pos].split(",");
			String[] goalCoords = args[pos + 1].split(",");
			State start = new State(Integer.valueOf(startCoords[0]), Integer.valueOf(startCoords[1]));
			State goal = new State(Integer.valueOf(goalCoords[0]), Integer.valueOf(goalCoords[1]));

			if(singleFlight)
				flight = new Flight(start, goal, 0);
			else 
				flight = new Flight(start, goal, Integer.valueOf(args[pos + 2]));
			
		return flight;
	}

	public static void main(String[] args) {
		boolean DEBUG = true;
		Date date = new Date();
		
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
				//if(args.length == 4) f.setPriority(Integer.ValueOf(0)) = 0;
		
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

		ArrayList<Node> noGoNodes = new ArrayList<Node>();
		do
		{
			long strtTime = date.getTime();
			Flight flight = flights.poll();
			if(DEBUG)
				System.out.println(flight.toString());
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
			if(DEBUG)
			{
	
				System.out.println("Search duration: " + String.valueOf((new Date()).getTime() - strtTime));
				System.out.println("Flight path cost: " + String.valueOf(flight.getPathCost()));
				System.out.println("path length: "  + flight.getPathLength());
			}
			noGoNodes.addAll(flight.getFlightPath());

		}
		while(flights.isEmpty() == false);


	}

}

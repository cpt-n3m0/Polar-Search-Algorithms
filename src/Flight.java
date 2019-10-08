import java.util.ArrayList;
import java.util.Collections;

public class Flight {
	private State start, goal;
	private ArrayList<Node> flightPath;
	private int priority;

	public Flight(State start, State goal, int priority) {
		this.flightPath = new ArrayList<Node>();
		this.start = start;
		this.goal = goal;
		this.priority = priority;
	}

	public State getStart() {
		return start;
	}

	public void setStart(State start) {
		this.start = start;
	}

	public State getGoal() {
		return goal;
	}

	public void setGoal(State goal) {
		this.goal = goal;
	}

	public ArrayList<Node> getFlightPath() {
		return flightPath;
	}

	public ArrayList<Node> getFlightPathNoEndpoints() {

		return (ArrayList<Node>) this.flightPath.subList(1, this.flightPath.size() - 1);
	}

	public void printPath() {
		for (Node n : this.flightPath)
			System.out.println(n.getAction() + " : " + n.getState().toString());
		System.out.println("Goal");
	}

	public void setFlightPath(Node goal) {
		Node current = goal;
		do {
			this.flightPath.add(current);
			current = current.getParent();
		} while (current != null);

		Collections.reverse(this.flightPath);
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String toString() {
		return "Start: " + start.toString() + "\nGoal: " + goal.toString() + "\nPriority: " + String.valueOf(priority);
	}

}

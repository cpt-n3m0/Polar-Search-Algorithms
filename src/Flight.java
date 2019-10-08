
public class Flight {
	private State start, goal;
	private Node[] flightPath;
	private int priority;
	
	public Flight(State start, State goal, int priority)
	{
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
	public Node[] getFlightPath() {
		return flightPath;
	}
	public void setFlightPath(Node[] flightPath) {
		this.flightPath = flightPath;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public String toString()
	{
		return "Start: " + start.toString() + "\nGoal: " + goal.toString() + "\nPriority: " + String.valueOf(priority);
	}
	
	
}

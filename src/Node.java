
public class Node {
	private Node parent;
	private State state;
	private int depth;
	private String action;
	private double pathCost;
	
	public Node(Node parent, State state, String action, int depth,double pathCost) {
		this.parent = parent;
		this.state = state;
		this.depth = depth;
		this.action = action;
		this.pathCost = pathCost;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public void setPathCost(double pathCost) {
		this.pathCost = pathCost;
	}
	public double getPathCost() {
		return this.pathCost;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}

	
	public boolean equals(Node n) {
		return this.state.equals(n.getState());
	}
}

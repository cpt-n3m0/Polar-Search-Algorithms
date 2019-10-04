
public class State {

	private int d, angle;

	public State()
	{
		
	}
	public State(int d, int angle)
	{
		this.d = d;
		this.angle = angle;
	}
	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}
	public boolean equals(State n) {
		if (this.d == n.getD() 
			&& this.angle == n.getAngle())
			return true;
		return false;
	}
	
}

package serialize;

import mainstuff.Waytype;

public class Field {

	private int x;
	private int y;
	
	private Waytype type = null;
	
	public Field(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setType(Waytype type) {
		this.type = type;
	}
	
	public Waytype getType() {
		return type;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
}

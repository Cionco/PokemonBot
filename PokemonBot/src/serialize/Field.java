package serialize;

import mainstuff.Tool;

public class Field {

	private int x;
	private int y;
	
	private Link link = null;
	private Location loc = null;
	
	private Tool type = null;
	
	public Field(int x, int y, Location loc) {
		this.x = x;
		this.y = y;
		this.loc = loc;
	}
	
	public void setType(Tool type) {
		this.type = type;
	}
	
	public Tool getType() {
		return (type == null)?Tool.ERASE:type;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void addLink(String loc, int x, int y) {
		link = new Link(loc, x, y);
	}
	
	public void removeLink() {
		link = null;
	}
	
	public Location getLoc() {
		return loc;
	}
	
	public Link getLink() {
		return link;
	}
	
	
	public class Link {
		private String loc;
		private int x;
		private int y;
		
		public Link(String loc, int x, int y) {
			this.loc = loc;
			this.x = x;
			this.y = y;
		}
		
		public String getLocName() {
			return loc;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
	}
	
}

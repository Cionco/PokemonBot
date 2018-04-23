import java.awt.Color;

public enum Waytype {
    NONE {
    	public Color getColor() { return Color.WHITE; }
    },
    FORREST{
    	public Color getColor() { return Color.GREEN; }
    },
    CITY{
    	public Color getColor() { return Color.GRAY; }
    },
    CAVE{
    	public Color getColor() { return Color.darkGray; }
    },
    WATER{
    	public Color getColor() { return Color.BLUE; }
    },
    VICTORYROAD{
    	public Color getColor() { return Color.PINK; }
    },
    MANSION{
    	public Color getColor() { return Color.LIGHT_GRAY; }
    },
    POKETOWER{
    	public Color getColor() { return Color.LIGHT_GRAY; }
    },
    POWERPLANT{
    	public Color getColor() { return Color.DARK_GRAY; }
    },
    SAFARIZONE{
    	public Color getColor() { return Color.green; }
    };
    
    
    public Color getColor() {
    	throw new AbstractMethodError();
    }
}

package mainstuff;
import java.awt.Color;

import javax.swing.ImageIcon;

public enum Waytype {
    NONE {
    	public Color getColor() { return Color.WHITE; }
    },
    FORREST{
    	public Color getColor() { return Color.GREEN; }
    	public String getIconPath() { return "/Users/nicolaskepper/Desktop/tree.png"; }
    	public ImageIcon getIcon() { return new ImageIcon(getClass().getResource("/resources/tree.png")); }
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
    
    public String getIconPath() {
    	throw new AbstractMethodError();
    }
    
    public ImageIcon getIcon() {
    	throw new AbstractMethodError();
    }
}

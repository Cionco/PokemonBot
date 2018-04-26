package mainstuff;
import java.awt.Color;

import javax.swing.ImageIcon;

public enum Tool {
    ERASE {
    	public Color getColor() { return Color.WHITE; }
    },
    FOREST{
    	public Color getColor() { return Color.GREEN; }
    	public ImageIcon getIcon() { return new ImageIcon(getClass().getResource("/resources/forest.png")); }
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
    },
    LINK{
    	public ImageIcon getIcon(Tool t) { return new ImageIcon(getClass().getResource("/resources/" + t.toString().toLowerCase() + "L.png")); }
    	public ImageIcon getIcon() { return new ImageIcon(getClass().getResource("/resources/link.png")); }
    };
    
    
    public Color getColor() {
    	throw new AbstractMethodError();
    }
   
    public ImageIcon getIcon() {
    	return null;
    }
    
    public ImageIcon getIcon(Tool t) {
    	throw new AbstractMethodError();
    }
}

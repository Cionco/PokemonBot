package serialize;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JTable;

public class Location {
	
	public static void main(String[] args) {
		Location l = new Location("Test", new JTable(3, 3));
		for(Field f : l.fields) System.out.println(f.getX() + "/" + f.getY());
		System.out.println();
		l.updateSize(new JTable(2, 2));
		for(Field f : l.fields) System.out.println(f.getX() + "/" + f.getY());
		
	}

	private String name;
	private String filename;
	private String description;
	private Dimension size;
	public ArrayList<Field> fields = new ArrayList<Field>();
	
	public Location(String name) {
		this.name = name;
		this.filename = name.replaceAll(" ", "_") + ".json";
	}
	
	public Location(String name, JTable table) {
		this.name = name;
		this.filename = name.replaceAll(" ", "_") + ".json";
		this.size = new Dimension(table.getModel().getColumnCount(), table.getModel().getRowCount());
		for(int i = 0; i < size.height; i++) 
			for(int j = 0; j < size.width; j++) 
				fields.add(new Field(j, i));
	}
	
	public Field getField(int x, int y) {
		return fields.get(y * size.width + x);
	}
	
	public void updateSize(JTable table) {
		Dimension newSize = new Dimension(table.getModel().getColumnCount(), table.getModel().getRowCount());
		if(newSize.width < size.width) 
			for(int i = fields.size() - 1; i >= 0; i--) {
				if(fields.get(i).getX() >= newSize.width) fields.remove(i);
			}
		else if(newSize.width > size.width) {
			int difference = newSize.width - size.width;
			
			for(int i = size.height - 1; i >= 0; i--)
				for(int j = 0; j < difference; j++) {
					fields.add((i + 1) * size.width + j, new Field(size.width + j, i));
				}
		}
		
		
		if(newSize.height < size.height)
			for(int i = fields.size() - 1; i >= 0; i--) {
				if(fields.get(i).getY() >= newSize.height) fields.remove(i);
			}
		else if(newSize.height > size.height) {
			int difference = newSize.height - size.height;
			for(int i = 0; i < difference; i++)
				for(int j = 0; j < newSize.width; j++) {
					fields.add(size.height * newSize.width + i * newSize.width + j, new Field(j, size.height + i));
				}
		}
		
	}

}

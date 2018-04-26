package serialize;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JTable;

import mainstuff.Tool;

public class Location {
	
	private String name;
	private String filename;
	private String description;
	private Dimension size;
	private ArrayList<Field> fields = new ArrayList<Field>();
	
	public Location(String name) {
		this.name = name;
		this.filename = name.replaceAll(" ", "_") + ".json";
		this.size = new Dimension(20, 20);
		for(int i = 0; i < size.height; i++) 
			for(int j = 0; j < size.width; j++) 
				fields.add(new Field(j, i, this));
	}
	
	public Location(String name, JTable table) {
		this.name = name;
		this.filename = name.replaceAll(" ", "_") + ".json";
		this.size = new Dimension(table.getModel().getColumnCount(), table.getModel().getRowCount());
		for(int i = 0; i < size.height; i++) 
			for(int j = 0; j < size.width; j++) 
				fields.add(new Field(j, i, this));
	}
	
	public int getWidth() {
		return size.width;
	}
	public int getHeight() {
		return size.height;
	}
	
	public Field getField(int x, int y) {
		return fields.get(y * size.width + x);
	}
	
	public void setFieldType(int x, int y, Tool type) {
		getField(x, y).setType(type);
	}
	
	public ArrayList<Field> getFields() {
		return fields;
	}
	
	public String getFileName() {
		return filename;
	}
	
	public String getName() {
		return name;
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
					fields.add((i + 1) * size.width + j, new Field(size.width + j, i, this));
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
					fields.add(size.height * newSize.width + i * newSize.width + j, new Field(j, size.height + i, this));
				}
		}
		
		this.size = newSize;
	}
}

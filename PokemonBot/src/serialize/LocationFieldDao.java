package serialize;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JFileChooser;

import org.json.JSONArray;
import org.json.JSONObject;

public class LocationFieldDao {

	static String filename = "_map.json";
	
	public static void main(String[] args) {
		load();
	}
	
	public static void saveAll(ArrayList<Location> locations) throws IOException {
		JSONObject json = new JSONObject();
		JSONArray locs = new JSONArray();
		
		for(Location l : locations) {
			JSONObject location = new JSONObject();
			
			location.put("name", l.getName())
					.put("description", l.getDescription())
					.put("width", l.getWidth())
					.put("height", l.getHeight());
			
			JSONArray fields = new JSONArray();
			for(Field f : l.getFields()) {
				JSONObject field = new JSONObject();
				field.put("x", f.getX())
						.put("y", f.getY())
						.put("type", f.getRealType());
				if(f.getLink() != null)
					field
						.put("linkedTo", 
								new JSONObject()
									.put("spawn_map", f.getLink().getLocName())
									.put("spawn_x", f.getLink().getX())
									.put("spawn_y", f.getLink().getY()));
				
				fields.put(field);	
			}
			location.put("fields", fields);
			locs.put(location);
		}
		
		
		
		json.put("locations", locs); //add array of locations
		
		System.out.println(json.toString());
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		FileWriter fw = new FileWriter(new File(timeStamp + filename));
		
		fw.write(json.toString());
	}
	
	
	public static void load() {
		File folder = new File(".");
		
		File[] files = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				String lowercase = name.toLowerCase();
				if(lowercase.endsWith(".json"))
					return true;
				else
					return false;
			}
		});
		
		String[] filenames = new String[files.length];
		for(int i = 0; i < files.length; i++) filenames[i] = files[i].getName();
		
		Arrays.sort(filenames);
		
		String filename = filenames[filenames.length - 1];
		
		File file = new File(filename);
		
		
		
	}
	
}

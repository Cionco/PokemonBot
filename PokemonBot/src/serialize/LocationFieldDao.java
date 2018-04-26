package serialize;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

public class LocationFieldDao {

	static String filename = "map.json";
	
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
	
}

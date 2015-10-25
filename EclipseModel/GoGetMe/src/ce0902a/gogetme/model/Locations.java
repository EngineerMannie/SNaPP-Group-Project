package ce0902a.gogetme.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class Locations {

	// Lists to store locations
	private final ArrayList<Location> locations = new ArrayList<Location>();
	
	private final ArrayList<String> images = new ArrayList<String>();

	// List to get the stored locations
	public List<Location> getLocations() {
		return locations;
	}
	
	public List<String> getImages(){
		return images;
	}

	// Add Location Method
	public void addLocation(int id, String firstLine, String secondLine,
			String city, String postcode, String description, String image) {
		locations.add(new Location(id, firstLine, secondLine, city, postcode,
				description, image));

	}
	
	public void addImage(String image){
		images.add(image);
	}

	public void deleteLocation(int id, String firstLine, String secondLine,
			String city, String postcode, String description, String image) {
		locations.remove(new Location(id, firstLine, secondLine, city,
				postcode, description, image));
	}

	public List<Location> LoadLocations(Activity currentActivity)
			throws IOException {

		String FILENAME = "locations_file";

		FileInputStream fis = currentActivity.openFileInput(FILENAME);
		BufferedReader myReader = new BufferedReader(new InputStreamReader(fis));

		String aDataRow = "";

		while ((aDataRow = myReader.readLine()) != null) {
			String s = aDataRow;
			String[] split = s.split(",");

			int id = Integer.parseInt(split[0].toString());
			addLocation(id, split[1], split[2], split[3], split[4], split[5], split[6]);

		}
		Log.d("File Loaded", "file loaded");
		Log.d("locations size", "" + getLocations().size());
		myReader.close();
		fis.close();

		return getLocations();

	}

	public void SaveLocations(Activity currentActivity) throws IOException {
		String FILENAME = "locations_file";

		FileOutputStream fos = currentActivity.openFileOutput(FILENAME,
				Context.MODE_PRIVATE);

		OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);

		for (int i = 0; i < getLocations().size(); i++) {
			myOutWriter.write("" + getLocations().get(i).getId());
			myOutWriter.write(",");
			myOutWriter.write(getLocations().get(i).getFirstLine());
			myOutWriter.write(",");
			myOutWriter.write(getLocations().get(i).getSecondLine());
			myOutWriter.write(",");
			myOutWriter.write(getLocations().get(i).getCity());
			myOutWriter.write(",");
			myOutWriter.write(getLocations().get(i).getPostcode());
			myOutWriter.write(",");
			myOutWriter.write(getLocations().get(i).getDescription());
			myOutWriter.write(",");
			myOutWriter.write(getLocations().get(i).getImage());
			myOutWriter.write(",");
			myOutWriter.write(System.getProperty("line.separator"));
		}
		Log.d("File saved", "file saved");
		myOutWriter.close();
		fos.close();

	}
	
	
	public void SaveImage(Activity currentActivity) throws IOException {
		String FILENAME = "image_file";

		FileOutputStream fos = currentActivity.openFileOutput(FILENAME,
				Context.MODE_PRIVATE);

		OutputStreamWriter myOutWriter = new OutputStreamWriter(fos);

		for (int i = 0; i < getLocations().size(); i++) {
		
			myOutWriter.write(getLocations().get(i).getImage());
			myOutWriter.write(",");
			myOutWriter.write(System.getProperty("line.separator"));
		}
		Log.d("File saved", "file saved");
		myOutWriter.close();
		fos.close();

	}
	
	public List<String> LoadImage(Activity currentActivity)throws IOException {

		String FILENAME = "image_file";

		FileInputStream fis = currentActivity.openFileInput(FILENAME);
		BufferedReader myReader = new BufferedReader(new InputStreamReader(fis));

		String aDataRow = "";

		while ((aDataRow = myReader.readLine()) != null) {
			String s = aDataRow;
			String[] split = s.split(",");

			addImage(split[0]);

		}
		Log.d("File Loaded", "file loaded");
		Log.d("locations size", "" + getImages().size());
		myReader.close();
		fis.close();

		return getImages();

	}

}

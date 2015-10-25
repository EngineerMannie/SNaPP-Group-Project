package ce0902a.SNaPP.gogetme;


import java.io.File;

import java.io.IOException;


import ce0902a.gogetme.model.*;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

public class GoGetMeAddLocation extends Activity {

	Locations locModel = new Locations();

	private EditText firstLine, secondLine, city, postcode, description;
	
	String mFirstLine;
	
	SharedPreferences addLocationShared;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_go_get_me_add_location);
		

		firstLine = (EditText) findViewById(R.id.edtFirstLine);
		secondLine = (EditText) findViewById(R.id.edtSecondLine);
		city = (EditText) findViewById(R.id.edtTown);
		postcode = (EditText) findViewById(R.id.edtPostCode);
		description = (EditText) findViewById(R.id.edtTextDescription);

		// Button to go back to Main Screen
		((Button) findViewById(R.id.btnBack))
				.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent backScreen = new Intent(GoGetMeAddLocation.this,
								GoGetMeMain.class);
						startActivity(backScreen);

						try {
							saveToFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		
		
		
		// Button to go back to Main Screen
		((Button) findViewById(R.id.btnAddLocation))
				.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						Bundle bundle=getIntent().getExtras();   	    	
						final String imageId=bundle.getString("myImageId");
						
						Toast.makeText(GoGetMeAddLocation.this, "position["+imageId+"]", Toast.LENGTH_SHORT).show();
						
						locModel.addLocation(1, firstLine.getText().toString(),
								secondLine.getText().toString(), 
								city.getText().toString(), 
								postcode.getText().toString(), 
								description.getText().toString(),
								imageId);
						
						locModel.addImage(imageId);
								
						Log.d("Locations", "" + locModel.getLocations().size() + "" + imageId);
					}
				});

		// Button to go back to Main Screen
		((Button) findViewById(R.id.btnTakePicture))
				.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						
						SaveSharedPref();

						Intent takePicture = new Intent(
								GoGetMeAddLocation.this, GoGetMeSelectImage.class);
						startActivity(takePicture);
						//***********Use this please
						//*****START ACTIVITY FOR RESULT CODE
						//http://stackoverflow.com/questions/2497205/how-to-return-a-result-startactivityforresult-from-a-tabhost-activity

					}
				});

		try {
			File file = getBaseContext().getFileStreamPath("locations_file");
			if (file.exists()) {
				loadToFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d("Locations", "" + locModel.getLocations().size());
		
		LoadSharedPref();
		
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		locModel.getLocations();
		super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {

	}
	
	
	public void SaveSharedPref()
	{
		//get the values from the view
		mFirstLine = firstLine.getText().toString();
		
		addLocationShared = getSharedPreferences("locInfo", MODE_PRIVATE);
		SharedPreferences.Editor editor = addLocationShared.edit();
		
		//store user entered data
		editor.putString("firstline", mFirstLine);
		editor.commit();
		
		Toast.makeText(this, "details are saved", Toast.LENGTH_SHORT).show();
	}
	
	public void LoadSharedPref(){
		//retrieve the saved values
		addLocationShared = getSharedPreferences("locInfo", MODE_PRIVATE);
		mFirstLine = addLocationShared.getString("firstline", mFirstLine);
		
		firstLine.setText(mFirstLine);
		
	}

	public void onPause(Bundle savedInstanceState) {
		super.onPause();
		
	}
	
	public void onResume(Bundle savedInstanceState) {
		super.onResume();
		
		
	}

	public void onDestroy(Bundle savedInstanceState) {
		super.onDestroy();

		try {
			saveToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveToFile() throws IOException {
		locModel.SaveLocations(this);
		locModel.SaveImage(this);

	}

	public void loadToFile() throws IOException {
		locModel.LoadLocations(this);
		locModel.LoadImage(this);
	}

}

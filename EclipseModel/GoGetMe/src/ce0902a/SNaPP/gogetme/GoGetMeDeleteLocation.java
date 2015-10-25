package ce0902a.SNaPP.gogetme;


import java.io.File;
import java.io.IOException;

import ce0902a.gogetme.model.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class GoGetMeDeleteLocation extends Activity {
	
	Locations locModel = new Locations();
	//GoGetMeAddLocation addLoc = new GoGetMeAddLocation();
	
	private Spinner spinner;
	private TextView location;
	private int x;
	 
	 
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_go_get_me_delete_location);
	        
	       try {
	    	   locModel.LoadLocations(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	       location = (TextView)findViewById(R.id.txtLocation);
	       
	       
	        //Button to go back to Main Screen
	        ((Button) findViewById(R.id.btnBack)).setOnClickListener(
	    			new Button.OnClickListener(){

						@Override
						public void onClick(View v) {
							Intent backScreen = new Intent(GoGetMeDeleteLocation.this, GoGetMeMain.class);
							startActivity(backScreen);
						}
	    	
	    	});
	        
	        spinner = (Spinner)findViewById(R.id.spnLocationDropdown);
	        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
			spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			spinner.setAdapter(spinnerAdapter);
			for(int i=0; i<locModel.getLocations().size(); i++)
			{
				spinnerAdapter.add(locModel.getLocations().get(i).toString());
			}
			
			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			        // your code here
			    	for(int i=0; i<locModel.getLocations().size(); i++)
					{
			    		location.setText(locModel.getLocations().get(i).toString());
			    		x = i;
					}
			    }

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
}
}

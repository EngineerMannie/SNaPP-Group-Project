package ce0902a.SNaPP.gogetme;

import org.json.JSONObject;


import ce0902a.gogetme.model.DatabaseConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class GoGetMeMain extends Activity {
	private String srvcName, imei, result;
	private boolean con;
	private Handler handlerConnection;
	private Button btnRegister, btnUserMode, btnAddLocation, btnDeleteLocation, btnSetSafeZone, btnSetupHelp, btnCarer;
	private int userId;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_get_me_main);
        
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnUserMode = (Button)findViewById(R.id.btnUsermode);
        btnAddLocation = (Button)findViewById(R.id.btnAddLocation);
        btnDeleteLocation = (Button)findViewById(R.id.btnDeleteLocation);
        btnSetSafeZone = (Button)findViewById(R.id.btnSetSafeZone);
        btnSetupHelp = (Button)findViewById(R.id.btnSetupHelp);
        btnCarer = (Button)findViewById(R.id.btnCarer);
        
        // Get imei
     	srvcName = Context.TELEPHONY_SERVICE;
     	TelephonyManager telephonyManager = (TelephonyManager)getSystemService(srvcName);
     	imei = telephonyManager.getDeviceId();
     	// IMEI EXIST IN THE DB? 
     	new ThreadConnection().execute(new DatabaseConnection());				
		handlerConnection = new Handler() {
			public void handleMessage(Message myMessage) {
				result = myMessage.getData().getString("result");
				if (result.equals("False")) {
					con = false;
				}
				else {
					con = true;
					userId = Integer.parseInt(result);
            	}
				 if(!con) {
			        	btnUserMode.setVisibility(View.INVISIBLE);
			        	btnAddLocation.setVisibility(View.INVISIBLE);
			        	btnDeleteLocation.setVisibility(View.INVISIBLE);
			        	btnSetSafeZone.setVisibility(View.INVISIBLE);
			        	btnSetupHelp.setVisibility(View.INVISIBLE);
			        	btnCarer.setVisibility(View.VISIBLE);
			        	btnRegister.setVisibility(View.VISIBLE);
					}
					else {
						btnRegister.setVisibility(View.INVISIBLE);
						btnUserMode.setVisibility(View.VISIBLE);
			        	btnAddLocation.setVisibility(View.VISIBLE);
			        	btnDeleteLocation.setVisibility(View.VISIBLE);
			        	btnSetSafeZone.setVisibility(View.VISIBLE);
			        	btnSetupHelp.setVisibility(View.VISIBLE);
			        	btnCarer.setVisibility(View.VISIBLE);
					}
			}
		};
		
        //Buttons on Main Screen Linking to designated screen
        //Button to User Mode Screen
        ((Button) findViewById(R.id.btnUsermode)).setOnClickListener(
        			new Button.OnClickListener(){

						@Override
						public void onClick(View v) {
							Intent pickLocation = new Intent(GoGetMeMain.this, GoGetMePickLocation.class);
							pickLocation.putExtra("UserId", userId);
							startActivity(pickLocation);
						}
        	
        });
        
        //Button to Add Location Screen
        ((Button) findViewById(R.id.btnAddLocation)).setOnClickListener(
    			new Button.OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent addLocation = new Intent(GoGetMeMain.this, GoGetMeAddLocation.class);
						startActivity(addLocation);
					}
    	
    	});
        
        //Button to Delete Location Screen
        ((Button) findViewById(R.id.btnDeleteLocation)).setOnClickListener(
    			new Button.OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent deleteLocation = new Intent(GoGetMeMain.this, GoGetMeDeleteLocation.class);
						startActivity(deleteLocation);
						
					}
    	
    	});
        
        //Button to Set Safe Zone Screen
        ((Button) findViewById(R.id.btnSetSafeZone)).setOnClickListener(
    			new Button.OnClickListener(){

					@Override
					public void onClick(View v) {
						
						
					}
    	
    	});
        
        ((Button) findViewById(R.id.btnRegister)).setOnClickListener(
    			new Button.OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent register = new Intent(GoGetMeMain.this, Register.class);
						startActivity(register);
						
					}
    	
    	});
        
        ((Button) findViewById(R.id.btnCarer)).setOnClickListener(
    			new Button.OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent carer = new Intent(GoGetMeMain.this, CarerMain.class);
						startActivity(carer);
						
					}
    	
    	});
        
        //Button to Setup Help Screen
        ((Button) findViewById(R.id.btnSetupHelp)).setOnClickListener(
    			new Button.OnClickListener(){

					@Override
					public void onClick(View v) {
						
						
					}
    	
    	});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.go_get_me_main, menu);
        menu.findItem(R.id.selectDestination).setIntent(new Intent(this, GoGetMePickLocation.class));
        menu.findItem(R.id.addLocation).setIntent(new Intent(this, GoGetMeAddLocation.class));
        menu.findItem(R.id.deleteLocation).setIntent(new Intent(this, GoGetMeDeleteLocation.class));
        
        //HELP Menu item to be added
        //menu.findItem(R.id.help).setIntent(new Intent(this, GoGetMeHelp.class));
        
        return true;
    }
    /*
	 * THREAD Connection 
	 */
	
	public class ThreadConnection extends AsyncTask<DatabaseConnection, Long, JSONObject> {
		@Override
		protected JSONObject doInBackground(DatabaseConnection... params) {
			// It's executed on background thread
			return params[0].Connection(imei);
		}
		@Override 
		protected void onPostExecute(JSONObject joConnection) {
			try {
				result = (joConnection.getString("result"));				
				// Creates a message to send to our UI thread
				Message myMessage = new Message();
				// Creates the data for the message
				Bundle databundle = new Bundle();
				// Adds a string to the data bundle
				databundle.putString("result", result);
				myMessage.setData(databundle);
				// Sends the message to the handler
				handlerConnection.sendMessage(myMessage);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}
}

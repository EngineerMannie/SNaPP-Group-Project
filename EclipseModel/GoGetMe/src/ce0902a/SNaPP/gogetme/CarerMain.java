package ce0902a.SNaPP.gogetme;

import org.json.JSONObject;

import ce0902a.gogetme.model.DatabaseConnection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CarerMain extends Activity {
	boolean con;
	int userId = 0;
	String imei, result, srvcName;
	Handler handlerConnectionCarer;
	Button btnComeGetMe, btnRegistration;
	TextView txtMainMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Delete the black line on the top of the application
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Link to layout activity_main.xml
		setContentView(R.layout.carermain);
		
		// Get imei
		srvcName = Context.TELEPHONY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(srvcName);
		imei = telephonyManager.getDeviceId();
		
		/*
		 * TextView
		 */
		
		txtMainMenu=(TextView)findViewById(R.id.txtMainMenu);
		
		/*
		 * Get Connection with the database - Test if imei exist on the DB if yes send the userId, if false = null.
		 */
		
		new ThreadConnectionCarer().execute(new DatabaseConnection());				
		handlerConnectionCarer = new Handler() {
			public void handleMessage(Message myMessage) {
				result = myMessage.getData().getString("result");
				if (result.equals("False")) {
					con = false;
				}
				else {
					con = true;
					userId = Integer.parseInt(result);
            	}
				// Hide or display some button depending on the connection status.
				if(!con) {
					btnRegistration.setVisibility(View.VISIBLE);
					btnComeGetMe.setVisibility(View.INVISIBLE);
					txtMainMenu.setText("Your phone is not registered as a carer in the database.\nPlease, register.");
				}
				else {
					btnRegistration.setVisibility(View.INVISIBLE);
					btnComeGetMe.setVisibility(View.VISIBLE);
					txtMainMenu.setText("You are connected.");
				}
			}
		};
		
		/*
		 * BUTTON 
		 */
		
		// Button Come Get Me
		btnComeGetMe=(Button)findViewById(R.id.btnComeGetMe);
		btnComeGetMe.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				Intent carerLocation = new Intent(CarerMain.this, CarerLocation.class);
				carerLocation.putExtra("userId", userId);
				startActivity(carerLocation);	
			}
		});

		// Button Register
		btnRegistration=(Button)findViewById(R.id.btnRegistration);
		btnRegistration.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
		        Intent register = new Intent(CarerMain.this, CarerRegister.class);
				startActivityForResult(register, 1);
			}
		});
	}
	
	/*
	 * THREAD ConnectionCarer
	 */
	
	public class ThreadConnectionCarer extends AsyncTask<DatabaseConnection, Long, JSONObject> {
		@Override
		protected JSONObject doInBackground(DatabaseConnection... params) {
			// It's executed on background thread
			return params[0].Connection(imei);
		}
		@Override 
		protected void onPostExecute(JSONObject joConnectionCarer) {
			try {
				result = (joConnectionCarer.getString("result"));				
				// Creates a message to send to our UI thread
				Message myMessage = new Message();
				// Creates the data for the message
				Bundle databundle = new Bundle();
				// Adds a string to the data bundle
				databundle.putString("result", result);
				myMessage.setData(databundle);
				// Sends the message to the handler
				handlerConnectionCarer.sendMessage(myMessage);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}
}

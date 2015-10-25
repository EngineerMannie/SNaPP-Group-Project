package ce0902a.SNaPP.gogetme;

import org.json.JSONObject;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import ce0902a.SNaPP.gogetme.CarerRegister.ThreadRegisterCarer;
import ce0902a.gogetme.model.DatabaseConnection;


public class Register extends Activity{
	private static Handler handler;
	private Button buttonRegister;
	private EditText textFirstName, textLastName, textPhone, textLinkedPhone;
	private TextView textError;
	private boolean valid, carerCheck;
	private String firstName, lastName, phone, linkedPhone, result, idUser, imei, srvcName;
	private CheckBox checkBoxCarer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Delete the black line on the top of the application
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Link to layout activity_main.xml
		setContentView(R.layout.register);
		

		/*
		 * EDITTEXT 
		 */
		
		// EditText textFirstName
		textFirstName=(EditText)findViewById(R.id.textFirstName);
		// EditText textLastName
		textLastName=(EditText)findViewById(R.id.textLastName);
		// EditText textPhone
		textPhone=(EditText)findViewById(R.id.textPhone);
		// EditText textLinkedPhone
		textLinkedPhone =(EditText)findViewById(R.id.textLinkedPhone);
		
		// Get imei, phoneNumber
		srvcName = Context.TELEPHONY_SERVICE;
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(srvcName);
		imei = telephonyManager.getDeviceId();
		
		/*
		 * TEXTVIEW 
		 */
		
		// TextView textError
		textError=(TextView)findViewById(R.id.textError);
		
		/*
		 * CHECKBOX
		 */
		
		checkBoxCarer=(CheckBox)findViewById(R.id.checkBoxCarer);
		checkBoxCarer.setOnClickListener(new OnClickListener () {
			@Override
			public void onClick(View v) {
				if(checkBoxCarer.isChecked()) {
					textLinkedPhone.setVisibility(View.VISIBLE);
					carerCheck = true;
				}
				else {
					textLinkedPhone.setVisibility(View.INVISIBLE);
					carerCheck = false;
				}
			}
		});
		
	
		/*
		 * BUTTON 
		 */
	
		// Button Register
		buttonRegister=(Button)findViewById(R.id.buttonRegister);
		buttonRegister.setOnClickListener(new OnClickListener () {
		@Override
		public void onClick(View v) {

			firstName = textFirstName.getText().toString();
			lastName = textLastName.getText().toString();
			phone = textPhone.getText().toString();
			if(carerCheck == false) {
				new ThreadNewUser().execute(new DatabaseConnection());				
					handler = new Handler() {
						public void handleMessage(Message myMessage) {
							result = myMessage.getData().getString("result");
							if (result.equals("Linked phone doesn't exist") || result.equals("Error in the registration process") || result.equals("Missing data!")) {
								valid = false;
								textError.setText(result);
							}
							else {
								idUser = result;
								valid = true;
		            		}
							if (valid == true) {
								Intent menu = new Intent(Register.this, GoGetMeMain.class);	            		
								startActivity(menu);
							}
						}
					};
				}
			else{
				linkedPhone = textLinkedPhone.getText().toString();
				new ThreadRegisterCarer().execute(new DatabaseConnection());				
				handler = new Handler() {
					public void handleMessage(Message myMessage) {
						result = myMessage.getData().getString("result");
						if (result.equals("Linked phone doesn't exist") || result.equals("Error in the registration process") || result.equals("Missing data!")) {
							valid = false;
							textError.setText(result);
						}
						else {
							idUser = result;
							valid = true;
		            	}
		            	if (valid == true) {
		            		Intent menu = new Intent(Register.this, GoGetMeMain.class);	            		
							startActivity(menu);
						}
					}
				};
			}
		}
		});
	}
	
	/*
	 * THREAD REGISTER 
	 */
	
	public class ThreadNewUser extends AsyncTask<DatabaseConnection, Long, JSONObject> {
		@Override
		protected JSONObject doInBackground(DatabaseConnection... params) {
			// It's executed on background thread
			return params[0].RegisterNewUser(firstName, lastName, phone, imei);
		}
		@Override 
		protected void onPostExecute(JSONObject joRegisterNewUser) {
			try {
				result = (joRegisterNewUser.getString("result"));
				// Creates a message to send to our UI thread
				Message myMessage = new Message();
				// Creates the data for the message
				Bundle databundle = new Bundle();
				// Adds a string to the data bundle
				databundle.putString("result", result);
				myMessage.setData(databundle);
				// Sends the message to the handler
				handler.sendMessage(myMessage);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	/*
	 * THREAD REGISTER 
	 */
	
	public class ThreadRegisterCarer extends AsyncTask<DatabaseConnection, Long, JSONObject> {
		@Override
		protected JSONObject doInBackground(DatabaseConnection... params) {
			// It's executed on background thread
			return params[0].RegisterCarer(firstName, lastName, phone, linkedPhone, imei);
		}
		@Override 
		protected void onPostExecute(JSONObject joCarer) {
			try {
				result = (joCarer.getString("result"));
				// Creates a message to send to our UI thread
				Message myMessage = new Message();
				// Creates the data for the message
				Bundle databundle = new Bundle();
				// Adds a string to the data bundle
				databundle.putString("result", result);
				myMessage.setData(databundle);
				// Sends the message to the handler
				handler.sendMessage(myMessage);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}
}

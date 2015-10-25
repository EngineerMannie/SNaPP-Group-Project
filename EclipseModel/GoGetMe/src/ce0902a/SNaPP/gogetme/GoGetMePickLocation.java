package ce0902a.SNaPP.gogetme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import ce0902a.gogetme.model.DatabaseConnection;
import ce0902a.gogetme.model.Location;
import ce0902a.gogetme.model.Locations;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

public class GoGetMePickLocation extends Activity {

	Locations locModel = new Locations();
	Button btnNext;
	Button btnPrevious;
	Button btnGo;
	int i, userId;
	int l;
	Handler handlerRegister;
	String result, IMEI, srvc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_go_get_me_pick_location);
		
		Bundle bundle = getIntent().getExtras();
		userId = bundle.getInt("UserId");
		Log.d("User Id", ""+userId);
		
		srvc = Context.TELEPHONY_SERVICE;
		TelephonyManager manager = (TelephonyManager)getSystemService(srvc);
		IMEI = manager.getDeviceId();
		
		/*new ThreadRegister().execute(new DatabaseConnection());				
		handlerRegister = new Handler() {
			public void handleMessage(Message myMessage) {
				result = myMessage.getData().getString("result");
				if (result.equals("False")) {
					// CONNECTION PROBLEM
					//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
				}
				else {
					// CONNECTION IS OK
					userId = Integer.parseInt(result);
					
					//Log.d("user ID",""+userId);
					//Toast.makeText(getApplicationContext(), userId, Toast.LENGTH_SHORT).show();
            	}
			}
		};*/
		
		try {
	    	   locModel.LoadLocations(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
	    	   locModel.LoadImage(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

		displayImage(0);
		
		btnNext = (Button)findViewById(R.id.btnNext);
		btnPrevious = (Button)findViewById(R.id.btnPrevious);
		btnGo = (Button)findViewById(R.id.btnGo);
		
		btnNext.setOnClickListener(new OnClickListener (){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(i==locModel.getImages().size()-1)
				{
					i=0;
					l=0;
					displayImage(i);
				}
				else if(i<locModel.getImages().size()){
					i++;
					l++;
					displayImage(i);
				}

			}
			
		});	
		
		btnPrevious.setOnClickListener(new OnClickListener (){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(i==0)
				{
					i=locModel.getImages().size()-1;
					l=locModel.getImages().size()-1;
					displayImage(i);
				}
				else if(i<locModel.getImages().size()){
					i--;
					l--;
					displayImage(i);
				}
			}
			
		});	
		
		btnGo.setOnClickListener(new OnClickListener (){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
			if(i == l){
				
				String address = locModel.getLocations().get(i).toString();
				
				Intent mapStart = new Intent(GoGetMePickLocation.this, GoGetMeDirections.class);
				mapStart.putExtra("DESTINATION", address);
				mapStart.putExtra("UserId", userId);
				startActivity(mapStart);
				Log.d("address firstLine", address);
				
				//Make intent to google screen, pass values of first line and postcode through
				//pass values of first line and postcode through as extras
				
				//Toast.makeText(GoGetMePickLocation.this, "firstLine["+firstLine+"]", Toast.LENGTH_SHORT).show();
			}
			}
			
		});	
	}
	
	public void displayImage(int i)
	{
		
		String imgPath = locModel.getImages().get(i).toString();
		
		ImageView iView = (ImageView)findViewById(R.id.imgImageView);
		
		File imgFile = new File(imgPath);
		if(imgFile.exists()){
			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			iView.setImageBitmap(myBitmap);
			
		}
	}
	public class ThreadRegister extends AsyncTask<DatabaseConnection, Long, JSONObject> {
		@Override
		protected JSONObject doInBackground(DatabaseConnection... params) {
			// It's executed on background thread
			return params[0].Register(IMEI);
		}
		@Override 
		protected void onPostExecute(JSONObject joRegister) {
			try {
				result = (joRegister.getString("result"));
				// Creates a message to send to our UI thread
				Message myMessage = new Message();
				// Creates the data for the message
				Bundle databundle = new Bundle();
				// Adds a string to the data bundle
				databundle.putString("result", result);
				myMessage.setData(databundle);
				// Sends the message to the handler
				handlerRegister.sendMessage(myMessage);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}
}

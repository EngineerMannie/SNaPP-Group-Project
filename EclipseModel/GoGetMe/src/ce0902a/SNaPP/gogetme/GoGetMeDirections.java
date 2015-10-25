package ce0902a.SNaPP.gogetme;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
	
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import ce0902a.gogetme.model.DatabaseConnection;
//import controller.Main;
//import controller.Register;
//import controller.Register.ThreadRegister;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GoGetMeDirections extends FragmentActivity {

		private LatLng CURRENT;
		private LatLng DESTINATION; //= new LatLng(56.483738,-2.838850);
		private int userId;
		private double lat;
		private double lng;
		private GoogleMap map;
		private GoogleMap mMap;
		private SupportMapFragment fragment;
		private LatLngBounds latlngBounds;
		private Polyline newPolyline;
		private int width, height;
		private String result;
		private Button btnNavigate;
		private Location location;
		private RemovePolyAsyncTask polyline;
		private Handler handler, handlerLocation, handlerRegister;
		private Runnable runnable;
		
		private Timer t;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_go_get_me_directions);
			
			Bundle bundle = getIntent().getExtras();
			final String destination = bundle.getString("DESTINATION");
			userId = bundle.getInt("UserId");
			Log.d("UserId",""+userId);
			
			//List of locations user has picked
			//helps with setting DESTINATION to user picked location
			List<Address> foundGeocode = null;
			try {
				foundGeocode = new Geocoder(this).getFromLocationName(destination, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			getSreenDimanstions();
		    fragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
			map = fragment.getMap(); 
			mMap = fragment.getMap();
			
			map.setMyLocationEnabled(true);
			
			//This sets destination lat and long to whatever location user has picked
			DESTINATION = new LatLng(foundGeocode.get(0).getLatitude(), foundGeocode.get(0).getLongitude());
			
			LocationManager locationManager;
        	String svcName = Context.LOCATION_SERVICE;
        	locationManager = (LocationManager)getSystemService(svcName);

        	Criteria criteria = new Criteria();
        	criteria.setAccuracy(Criteria.ACCURACY_FINE);
        	criteria.setPowerRequirement(Criteria.POWER_LOW);
        	criteria.setAltitudeRequired(false);
        	criteria.setBearingRequired(false);
        	criteria.setSpeedRequired(false);
        	criteria.setCostAllowed(true);
        	//String provider = locationManager.getBestProvider(criteria, true);

        	location = locationManager.getLastKnownLocation("gps");
        	locationManager.requestLocationUpdates("gps", 0, 1, locationListener);
        	
        	GetLocation(location);
    		
        	MarkerOptions markerOptions = new MarkerOptions().position(DESTINATION);
        	map.addMarker(markerOptions);
        	
        	
        	
        	latlngBounds = createLatLngBoundsObject(CURRENT, DESTINATION);  
        	
        	findDirections(lat, lng, DESTINATION.latitude, DESTINATION.longitude, GMapV2Direction.MODE_WALKING);
        	
        	handler = new Handler(){
        		public void handleMessage(Message msg){
        			boolean result = msg.getData().getBoolean("delete");
        			newPolyline.remove();
        			GetLocation(location);
        			findDirections(lat, lng, DESTINATION.latitude, DESTINATION.longitude, GMapV2Direction.MODE_WALKING);
        		}
        	};        		     
        	t = new Timer();
        	t.scheduleAtFixedRate(new directionsTImer(),5000,15000);
        	
        	
        	
		}
		
		
		
		@Override
		protected void onResume() {
			
			super.onResume();
			GetLocation(location);
	    	latlngBounds = createLatLngBoundsObject(CURRENT, DESTINATION);
	        //map.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));

		}

		public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
			PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);

			for(int i = 0 ; i < directionPoints.size() ; i++) 
			{          
				rectLine.add(directionPoints.get(i));
			}
			if (newPolyline != null)
			{
				newPolyline.remove();
			}
			newPolyline = map.addPolyline(rectLine);
				//latlngBounds = createLatLngBoundsObject(CURRENT, DESTINATION);
		        //map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));
		}
		
		private void getSreenDimanstions()
		{
			Display display = getWindowManager().getDefaultDisplay();
			width = display.getWidth(); 
			height = display.getHeight(); 
		}
		
		private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation)
		{
			if (firstLocation != null && secondLocation != null)
			{
				LatLngBounds.Builder builder = new LatLngBounds.Builder();    
				builder.include(firstLocation).include(secondLocation);
				
				return builder.build();
			}
			return null;
		}
		
		public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
			map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
			map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
			map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
			map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);
			
			GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
			asyncTask.execute(map);
		}
		
		public void GetLocation(Location location){		
			if(location != null)
			{
				
				lat = location.getLatitude();
	        	lng = location.getLongitude();
	        	
	        	CURRENT = new LatLng(lat, lng);
	        
			      }}
			
		
		//This is used for location Listener, instead of an intent
	private final LocationListener locationListener = new LocationListener() {
	
		public void onLocationChanged(Location location) {
			/*Toast.makeText(getApplicationContext(),
                    location.getLatitude() + " " + location.getLongitude(),
                    Toast.LENGTH_LONG).show();*/	

            /*map.addMarker(new MarkerOptions()
                    .position(
                            new LatLng(location.getLatitude(), location
                                    .getLongitude()))
                    .title("my position")
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_launcher)));*/

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                    location.getLatitude(), location.getLongitude()), 17.0f));
            
           
            GetLocation(location);
           
            new ThreadLocation().execute(new DatabaseConnection());				
			handlerLocation = new Handler() {
				public void handleMessage(Message myMessage) {
					result = myMessage.getData().getString("result");
					if (result.equals("False")) {
						// CONNECTION PROBLEM
						Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
					}
					else {
						// CONNECTION IS OK
						Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
	            	}
				}
			};
            
		}
		
		public void onProviderDisabled(String provider) {
			
		}
		
		public void onProviderEnabled(String provider){
			
		}
		
		public void onStatusChanged(String Provider, int status, Bundle extras){
			
		}
	
	};
	
	class directionsTImer extends TimerTask
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			//polyline = new RemovePolyAsyncTask(newPolyline);
			//polyline.execute("polyline removed");
			
			
			
			Log.d("location","location now"+lat+" "+lng);
			
			Message myMessage = new Message();
			Bundle databundle = new Bundle();
			databundle.putBoolean("delete", true);
			myMessage.setData(databundle);
			
			handler.sendMessage(myMessage);	
		}
		
	}
	
	/*
	 * THREAD GET LOCATION 
	 */
	
	public class ThreadLocation extends AsyncTask<DatabaseConnection, Long, JSONObject> {
		@Override
		protected JSONObject doInBackground(DatabaseConnection... params) {
			// It's executed on background thread
			return params[0].SendLocation(userId, lat, lng);
		}
		@Override 
		protected void onPostExecute(JSONObject joLocation) {
			try {
				result = (joLocation.getString("result"));
				// Creates a message to send to our UI thread
				Message myMessage = new Message();
				// Creates the data for the message
				Bundle databundle = new Bundle();
				// Adds a string to the data bundle
				databundle.putString("result", result);
				myMessage.setData(databundle);
				// Sends the message to the handler
				handlerLocation.sendMessage(myMessage);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
}
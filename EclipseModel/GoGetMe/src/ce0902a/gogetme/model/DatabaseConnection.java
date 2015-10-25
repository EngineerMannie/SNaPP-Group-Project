package ce0902a.gogetme.model;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
//import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DatabaseConnection {
	
	public JSONObject SendLocation(int userId, double lat, double lng) {
		// URL for getting all customers.
		String url = "http://mayar.abertay.ac.uk/~1304883/GoGetMeScript/sendLocation.php?userId=" + userId + "&lat=" + lat + "&lng=" + lng;
		// Get HttpResponce Object from url.
		// Get HttpEntity from Http Response Object.
		HttpEntity httpEntity = null;
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient(); // Default HttpClient.
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httResponse = httpClient.execute(httpGet);
			httpEntity = httResponse.getEntity();
		}
		catch(ClientProtocolException e) {
			// Signals error in http protocol.
			e.printStackTrace();
			// Log errors here
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		JSONObject joLocation = null;
		if(httpEntity != null) {
			try {
				String entityResponse = EntityUtils.toString(httpEntity);
				Log.e("Entity Response :", entityResponse);
				joLocation = new JSONObject(entityResponse);
			}
			catch(JSONException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return joLocation;
	}
	
	public JSONObject Register(String IMEI) {
		// URL for getting all customers.
		String url = "http://mayar.abertay.ac.uk/~1304883/GoGetMeScript/register.php?IMEI="+IMEI;
		Log.d("url",url);
		// Get HttpResponce Object from url.
		// Get HttpEntity from Http Response Object.
		HttpEntity httpEntity = null;
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient(); // Default HttpClient.
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httResponse = httpClient.execute(httpGet);
			httpEntity = httResponse.getEntity();
		}
		catch(ClientProtocolException e) {
			// Signals error in http protocol.
			e.printStackTrace();
			// Log errors here
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		JSONObject joRegister = null;
		if(httpEntity != null) {
			try {
				String entityResponse = EntityUtils.toString(httpEntity);
				Log.e("Entity Response :", entityResponse);
				joRegister = new JSONObject(entityResponse);
			}
			catch(JSONException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return joRegister;
	}
	
	public JSONObject RegisterNewUser(String firstName, String lastName, String phone, String imei) {
		// URL for getting all customers.
		String url = "http://mayar.abertay.ac.uk/~1304883/GoGetMeScript/registerNewPatient.php?firstName=" + firstName + "&lastName=" + lastName + "&phone=" + phone + "&imei=" + imei;
		Log.d("url",url);
		// Get HttpResponce Object from url.
		// Get HttpEntity from Http Response Object.
		HttpEntity httpEntity = null;
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient(); // Default HttpClient.
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httResponse = httpClient.execute(httpGet);
			httpEntity = httResponse.getEntity();
		}
		catch(ClientProtocolException e) {
			// Signals error in http protocol.
			e.printStackTrace();
			// Log errors here
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		JSONObject joRegisterNewUser = null;
		if(httpEntity != null) {
			try {
				String entityResponse = EntityUtils.toString(httpEntity);
				Log.e("Entity Response :", entityResponse);
				joRegisterNewUser = new JSONObject(entityResponse);
			}
			catch(JSONException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return joRegisterNewUser;
	}
	
	public JSONObject Connection(String imei) {
		// URL for getting all customers.
		String url = "http://mayar.abertay.ac.uk/~1304883/GoGetMeScript/connection.php?imei=" + imei;
		// Get HttpResponce Object from url.
		// Get HttpEntity from Http Response Object.
		HttpEntity httpEntity = null;
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient(); // Default HttpClient.
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httResponse = httpClient.execute(httpGet);
			httpEntity = httResponse.getEntity();
		}
		catch(ClientProtocolException e) {
			// Signals error in http protocol.
			e.printStackTrace();
			// Log errors here
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		JSONObject joConnection = null;
		if(httpEntity != null) {
			try {
				String entityResponse = EntityUtils.toString(httpEntity);
				Log.e("Entity Response :", entityResponse);
				joConnection = new JSONObject(entityResponse);
			}
			catch(JSONException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return joConnection;
	}
	
	public JSONObject ConnectionCarer(String imei) {
		// URL for getting all customers.
		String url = "http://mayar.abertay.ac.uk/~1304883/GoGetMeScript/connectionCarer.php?imei=" + imei;
		// Get HttpResponce Object from url.
		// Get HttpEntity from Http Response Object.
		HttpEntity httpEntity = null;
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient(); // Default HttpClient.
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httResponse = httpClient.execute(httpGet);
			httpEntity = httResponse.getEntity();
		}
		catch(ClientProtocolException e) {
			// Signals error in http protocol.
			e.printStackTrace();
			// Log errors here
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		JSONObject joConnectionCarer = null;
		if(httpEntity != null) {
			try {
				String entityResponse = EntityUtils.toString(httpEntity);
				joConnectionCarer = new JSONObject(entityResponse);
			}
			catch(JSONException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return joConnectionCarer;
	}
	
	public JSONObject RegisterCarer(String firstName, String lastName, String phone, String linkedPhone, String imei) {
		// URL for getting all customers.
		String url = "http://mayar.abertay.ac.uk/~1304883/GoGetMeScript/registerNewFollower.php?firstName=" + firstName + "&lastName=" + lastName + "&phone=" + phone + "&linkedPhone=" + linkedPhone + "&imei=" + imei;
		// Get HttpResponce Object from url.
		// Get HttpEntity from Http Response Object.
		HttpEntity httpEntity = null;
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient(); // Default HttpClient.
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httResponse = httpClient.execute(httpGet);
			httpEntity = httResponse.getEntity();
		}
		catch(ClientProtocolException e) {
			// Signals error in http protocol.
			e.printStackTrace();
			// Log errors here
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		JSONObject joRegisterCarer = null;
		if(httpEntity != null) {
			try {
				String entityResponse = EntityUtils.toString(httpEntity);
				Log.e("Entity Response :", entityResponse);
				joRegisterCarer = new JSONObject(entityResponse);
			}
			catch(JSONException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return joRegisterCarer;
	}
	
	public JSONObject LocationCarer(int userId) {
		// URL for getting all customers.
		String url = "http://mayar.abertay.ac.uk/~1304883/GoGetMeScript/locationCarer.php?userId=" + userId;
		// Get HttpResponce Object from url.
		// Get HttpEntity from Http Response Object.
		HttpEntity httpEntity = null;
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient(); // Default HttpClient.
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httResponse = httpClient.execute(httpGet);
			httpEntity = httResponse.getEntity();
		}
		catch(ClientProtocolException e) {
			// Signals error in http protocol.
			e.printStackTrace();
			// Log errors here
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		JSONObject joLocationCarer = null;
		if(httpEntity != null) {
			try {
				String entityResponse = EntityUtils.toString(httpEntity);
				Log.e("Entity Response :", entityResponse);
				joLocationCarer = new JSONObject(entityResponse);
			}
			catch(JSONException e) {
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		return joLocationCarer;
	}
}

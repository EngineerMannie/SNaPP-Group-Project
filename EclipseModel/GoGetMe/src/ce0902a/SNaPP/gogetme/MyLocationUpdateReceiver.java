package ce0902a.SNaPP.gogetme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

public class MyLocationUpdateReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String key = LocationManager.KEY_LOCATION_CHANGED;
		Location location =(Location)intent.getExtras().get(key);
		
	}
	}

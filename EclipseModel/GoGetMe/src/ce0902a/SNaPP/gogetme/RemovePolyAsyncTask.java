package ce0902a.SNaPP.gogetme;

import com.google.android.gms.maps.model.Polyline;

import android.os.AsyncTask;

public class RemovePolyAsyncTask extends AsyncTask<String, Void, String>{

	Polyline firstline;
	String result = "fail";
	
	public RemovePolyAsyncTask(Polyline newPolyline) {
		// TODO Auto-generated constructor stub
		super();
		firstline = newPolyline;
	}


	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		firstline.remove();
		result = "got it";
		return result;
	}

}

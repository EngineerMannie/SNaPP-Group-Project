package ce0902a.SNaPP.gogetme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class GoGetMeCameraView extends Activity{
	
	private static final int CAMERA_REQUEST = 0;
	protected static final int CAMERA_RESULT = 0;
	
	ImageView imageView;
	Button photoButton;

	static Uri capturedImageUri=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_go_get_me_picture_screen);
	    this.imageView = (ImageView)this.findViewById(R.id.CameraView);
	    photoButton = (Button) this.findViewById(R.id.myButton);
	    photoButton.setOnClickListener(new View.OnClickListener() {

	        @Override
	        public void onClick(View v) {
	        Calendar cal = Calendar.getInstance();
	        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/hello file",  (cal.getTimeInMillis()+".jpg"));
	        	if(!file.exists()){
	        		try {
	        			file.createNewFile();
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	        	}else{
	        		file.delete();
	        		try {
	        			file.createNewFile();
	        		} catch (IOException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	        	}
	        	capturedImageUri = Uri.fromFile(file);
	        	Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	        	i.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
	        	startActivityForResult(i, CAMERA_RESULT);
	        }
	    	});
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	    if (requestCode == CAMERA_REQUEST) {  
	        //Bitmap photo = (Bitmap) data.getExtras().get("data");
	        //imageView.setImageBitmap(photo);
	        try {
	    Bitmap bitmap = MediaStore.Images.Media.getBitmap( getApplicationContext().getContentResolver(),  capturedImageUri);
	    imageView.setImageBitmap(bitmap);
	    } catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    } catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    }
	    }  
	}}
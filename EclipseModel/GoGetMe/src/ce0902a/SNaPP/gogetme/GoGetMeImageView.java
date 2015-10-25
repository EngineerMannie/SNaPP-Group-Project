package ce0902a.SNaPP.gogetme;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;



public class GoGetMeImageView extends Activity{
	@Override
	protected void onCreate(Bundle savedInstance){
	super.onCreate(savedInstance);
	setContentView(R.layout.activity_go_get_me_gallery_view);  
	
    final Bundle bundle=getIntent().getExtras();   	    	
   	final String imageId=bundle.getString("myImageId");
   	Uri uri=Uri.parse(imageId);
   	ImageView imageView=(ImageView)findViewById(R.id.GalleryView);
   	imageView.setImageURI(uri);
   	
   	
   	((Button) findViewById(R.id.btnLoad)).setOnClickListener(new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent backScreen = new Intent(GoGetMeImageView.this, GoGetMeAddLocation.class);
			backScreen.putExtra("myImageId", imageId);
			startActivity(backScreen);
			
			//Make selected image save to corresponding location
			Toast.makeText(GoGetMeImageView.this, "position["+imageId+"]", Toast.LENGTH_SHORT).show();
		}
	});
   	
   	((Button) findViewById(R.id.btnGallery)).setOnClickListener(new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent backScreen = new Intent(GoGetMeImageView.this, GoGetMeSelectThumb.class);
			startActivity(backScreen);
			
		}
	});
	}
}

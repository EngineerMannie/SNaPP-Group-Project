package ce0902a.SNaPP.gogetme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GoGetMeSelectImage extends Activity {
	
	Button btnBack;
	Button btnGallery;
	Button btnSDcard;

	

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_go_get_me_gallery_select);
			
			
			
			btnBack = (Button)findViewById(R.id.Back);
			btnBack.setMaxHeight(150);
			btnBack.setMaxWidth(200);
			btnBack.setOnClickListener(new OnClickListener(){
			
			@Override
	       	public void onClick(View v){
	       			
	       		Intent openEditCat = new Intent(getApplicationContext(), GoGetMeAddLocation.class);
	       		startActivity(openEditCat);
	       	}
	       	});
		
				
			btnGallery = (Button)findViewById(R.id.Gallery);
			btnGallery.setMaxHeight(150);
			btnGallery.setMaxWidth(200);
			
			btnGallery.setOnClickListener(new OnClickListener(){
				
				@Override
		       	public void onClick(View v){
		       			
		       		Intent openEditCat = new Intent(GoGetMeSelectImage.this, GoGetMeSelectThumb.class);
		       		startActivity(openEditCat);
		       	}
		       	});

			btnSDcard = (Button)findViewById(R.id.Camera);
			btnSDcard.setMaxHeight(150);
			btnSDcard.setMaxWidth(200);
			btnSDcard.setOnClickListener(new OnClickListener(){
			
			@Override
	       	public void onClick(View v){
	       			
	       		Intent openEditCat = new Intent(GoGetMeSelectImage.this, GoGetMeCameraView.class);
	       		startActivity(openEditCat);
	       	}
	       	});
		
		
	}

	}

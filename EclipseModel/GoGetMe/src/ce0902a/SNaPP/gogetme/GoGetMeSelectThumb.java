package ce0902a.SNaPP.gogetme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class GoGetMeSelectThumb extends Activity {
	
	private Button main;
	//Variables used in the activity
	GridView gridview;
	ImageView imageview;

	
		//Class Name, and extends activity to override methods if need be
		//allows the subclass to inherit public and protected methods
		//and variables of the superclass 
		@Override
		public void onCreate(Bundle state) {
			super.onCreate(state);
			setContentView(R.layout.activity_go_get_me_gallery_thumb);
			gridview=(GridView)findViewById(R.id.ImageThumb);
			//imageview=(ImageView)findViewById(R.id.CatGallery);
			gridview.setAdapter(new GoGetMeImageAdapter(this));
		
		//Used to set up my button navigation
		//Also used to set an image button
		main = (Button)findViewById(R.id.Home);
		main.setMaxHeight(150);
		main.setMaxWidth(200);
		/*main.setOnClickListener(new OnClickListener(){
		
		
		

	//Used to make my button do something from the onClicklistener
    //Will move from Cat Gallery back to Main menu screen
			
	/*@Override
    public void onClick(View v){
       			
       	Intent openMainMenu = new Intent(Select_ImageThumb.this, GoGetMeAddLocation.class);
       	startActivity(openMainMenu);
       	}
    });
}	
		
		//Destroys Screen once activity losses foreground
		public void onPause(){
			finish();
	        System.exit(0);
		}*/

	
		};
		}

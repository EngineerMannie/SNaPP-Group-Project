package ce0902a.SNaPP.gogetme;

import java.io.File;
import java.io.FilenameFilter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GoGetMeImageAdapter extends BaseAdapter {

private Context mContext;
	
	
	public GoGetMeImageAdapter(Context context) {
		this.mContext=context;
	}
	@Override
	public int getCount() {
		/*return mThumbsId.length;*/
		return getImages().length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertview, ViewGroup arg2) {
		ImageView imageView;
		if(convertview==null){
			imageView = new ImageView(mContext);
			imageView.setPadding(8, 8, 8, 8);
			imageView.setLayoutParams(new GridView.LayoutParams(250,250));
			imageView.setOnClickListener(new OnClickListener(){
			
				@Override
				public void onClick(View arg0){
					Toast.makeText(mContext, "position["+position+"]", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(mContext, GoGetMeImageView.class);	
					//intent.putExtra("myImageId", mThumbsId[position]);
					intent.putExtra("myImageId", mFiles[position]);
					mContext.startActivity(intent);
				}
			});	
			
		}else{
			imageView=(ImageView)convertview;
			
		}
		imageView.setImageURI(getImages()[position]);
		return imageView;
	}
	
	
	Uri[] mUrls;
	public String[] mFiles=null;
	public Uri[] getImages(){
		
		File images = new File (Environment.getExternalStorageDirectory().getAbsolutePath()+ "/hello file");
		
		File[] imagelist=images.listFiles(new FilenameFilter(){
			
			@Override
			public boolean accept(File dir, String name){
				
				return((name.endsWith(".jpg")||(name.endsWith(".png"))));
			}
			
		});
		
		mFiles= new String[imagelist.length];
		for(int i=0; i<imagelist.length; i++){
			mFiles[i]=imagelist[i].getAbsolutePath();
		}
		mUrls=new Uri[mFiles.length];
		for(int i=0; i<mFiles.length; i++){
			mUrls[i]=Uri.parse(mFiles[i]);
	}
		return mUrls;
	}
}

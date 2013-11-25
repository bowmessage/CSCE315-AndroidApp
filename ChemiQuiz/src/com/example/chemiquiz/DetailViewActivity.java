package com.example.chemiquiz;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailViewActivity extends Activity {
	
	ImageView image;
	
    @SuppressWarnings("unchecked")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        
        Bundle extras = getIntent().getExtras();
        Chemical chem = (Chemical) extras.getSerializable("com.exmaple.chemiquiz.DetailChemSpiderID");
        
        image = (ImageView) findViewById(R.id.detailImage);
        
        new DrawableFromChemical().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, chem);
        
        TextView t = (TextView) findViewById(R.id.detailName);
        t.setText(chem.getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_view, menu);
        return true;
    }
    
    class DrawableFromChemical extends AsyncTask<Chemical, Void, Drawable> {

	    private Exception exception;
	    
	    @Override
	    protected Drawable doInBackground(Chemical... params) {
	        try {
	        	Bitmap x;
	    	    HttpURLConnection connection = (HttpURLConnection) new URL("http://www.chemspider.com/ImagesHandler.ashx?id="+params[0].getId()+"&w=300&h=300").openConnection();
	    	    connection.connect();
	    	    InputStream input = connection.getInputStream();
	    	    x = BitmapFactory.decodeStream(input);
	    	    return new BitmapDrawable(DetailViewActivity.this.getResources(), x);
	        } catch (Exception e) {
	            this.exception = e;
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	    @Override
	    protected void onPostExecute(Drawable res){
	    	image.setImageDrawable(res);
	    }
	}
    
}

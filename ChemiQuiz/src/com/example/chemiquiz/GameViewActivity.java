package com.example.chemiquiz;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GameViewActivity extends Activity {

	ArrayList<Integer> id_numbers;
	int this_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        id_numbers = randomNumbers(13200000, 10);
        Random r = new Random();
        this_id = id_numbers.get(r.nextInt(id_numbers.size()));
        
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        Button button1 = (android.widget.Button)findViewById(R.id.Button01);
        Button button2 = (android.widget.Button)findViewById(R.id.Button02);
        Button button3 = (android.widget.Button)findViewById(R.id.Button03);
        Button button4 = (android.widget.Button)findViewById(R.id.Button04);
        
        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        setComps(imageView1, buttons);
        
        setContentView(R.layout.game_view);
    }
    
    void setComps(ImageView view, ArrayList<android.widget.Button> buttons){
    	
    }
    
    
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu., menu);
        return true;
    }
   
   ArrayList<Integer> randomNumbers(int max, int size){
	   ArrayList<Integer> ret = new ArrayList<Integer>();
	   for(int i = 0; i < size; i++){
		   Random r = new Random();
		   ret.add(r.nextInt(max));
	   }
	   return ret;
   }
   
   class DrawableFromChemical extends AsyncTask<Pair<Chemical, ImageView>, Void, Integer> {

	    private Exception exception;
	    
	    @Override
	    protected Integer doInBackground(Pair<Chemical, ImageView>... params) {
	        try {
	        	Bitmap x;
	    	    HttpURLConnection connection = (HttpURLConnection) new URL("http://www.chemspider.com/ImagesHandler.ashx?id="+((Chemical) params[0].first).getId()+"&w=200&h=200").openConnection();
	    	    connection.connect();
	    	    InputStream input = connection.getInputStream();
	    	    x = BitmapFactory.decodeStream(input);
	    	    ((ImageView) params[0].second).setImageDrawable(new BitmapDrawable(GameViewActivity.this.getResources(), x));
	    	    return 1;
	        } catch (Exception e) {
	            this.exception = e;
	            return -1;
	        }
	    }
	}
}

package com.example.chemiquiz;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.util.vector;
import java.util.Random;

public class GameViewActivity extends Activity {

	vector<String> id_numbers;
	string this_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        id_numbers = populateIDs(randomNumbers(13200000, 10));
        Random r = new Random();
        this_id = id_number[r.nextInt(id_numbers.size())];
        
        ImageView imageView1 = findViewById(R.id.imageView1);
        android.widget.Button button1 = (android.widget.Button)findViewById(R.id.button01);
        android.widget.Button button2 = (android.widget.Button)findViewById(R.id.button02);
        android.widget.Button button3 = (android.widget.Button)findViewById(R.id.button03);
        android.widget.Button button4 = (android.widget.Button)findViewById(R.id.button04);
        
        vector<android.widget.Button> buttons;
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        setComps(imageView1, buttons);
        
        setContentView(R.layout.game_view);
    }
    
    void setComps(ImageView view, vector<android.widget.Button> buttons){
    	Random r = new Random(3);
    	view = 
    }
    
    vector<String> populateIDs(vector<int> set){
    	vector<String> ret;
    	for(int i = 0; i < set.size(); i++){
    		ret.add(set[i].toString());
    	}
    	return ret;
    }
    
   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu., menu);
        return true;
    }
   
   vector<int> randomNumbers(int max, int size){
	   Random generator = new Random(max);
	   vector<int> ret;
	   for(int i = 0; i < size; i++){
		   ret.add(generator.nextInt());
	   }
	   return ret;
   }
   
   public static Drawable drawableFromUrl(String url) throws IOException {
	    Bitmap x;

	    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	    connection.connect();
	    InputStream input = connection.getInputStream();

	    x = BitmapFactory.decodeStream(input);
	    return new BitmapDrawable(x);
	}
    
}

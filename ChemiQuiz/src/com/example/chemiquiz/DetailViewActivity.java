package com.example.chemiquiz;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DetailViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        
        Bundle extras = getIntent().getExtras();
        int chemSpiderID = extras.getInt("com.exmaple.chemiquiz.DetailChemSpiderID");
        
        //TextView t = (TextView)
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_view, menu);
        return true;
    }
    
}

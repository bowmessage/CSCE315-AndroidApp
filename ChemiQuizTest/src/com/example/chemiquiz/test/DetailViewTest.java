package com.example.chemiquiz.test;

import junit.framework.Assert;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.example.chemiquiz.Chemical;
import com.example.chemiquiz.DetailViewActivity;
import com.example.chemiquiz.R;

public class DetailViewTest extends ActivityInstrumentationTestCase2<DetailViewActivity> {
		
	public DetailViewTest(){
		super(DetailViewActivity.class);
	}
	
	Context mContext;

    public void setUp(){
        try {
            super.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mContext = this.getInstrumentation().getContext();
        
        Intent i = new Intent(mContext, DetailViewActivity.class);
        i.putExtra("com.exmaple.chemiquiz.DetailChemical", new Chemical(1, "asdf"));
        setActivityIntent(i);
        
    }   

    public void testTextViewExists(){
    	Assert.assertNotNull(getActivity().findViewById(R.id.detailName));
    }
    
    public void testDetailImageExsists(){
    	Assert.assertNotNull(getActivity().findViewById(R.id.detailImage));
    }
    
    public void testDetailNameIsCorrect(){
    	Assert.assertEquals(((Chemical)getActivity().getIntent().getExtras().getSerializable("com.exmaple.chemiquiz.DetailChemical")).getName(),
    			((TextView)getActivity().findViewById(R.id.detailName)).getText().toString());
    }
    	
	public DetailViewTest(Class<DetailViewActivity> activityClass) {
		super(activityClass);
	}

	
}

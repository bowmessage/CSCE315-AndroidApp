package com.example.chemiquiz.test;

import junit.framework.Assert;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.chemiquiz.R;
import com.example.chemiquiz.SearchViewActivity;

public class SearchViewTest extends ActivityInstrumentationTestCase2<SearchViewActivity> {
		
	public SearchViewTest(){
		super(SearchViewActivity.class);
	}
	
	Context mContext;

    public void setUp(){
        try {
            super.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mContext = this.getInstrumentation().getContext();
    	Intent searchIntent = new Intent(mContext, SearchViewActivity.class);
    	searchIntent.putExtra("com.exmaple.chemiquiz.AddingToSubsetID", 0);
    	setActivityIntent(searchIntent);
    }   

    public void testListViewExists(){
    	Assert.assertNotNull(getActivity().findViewById(R.id.searchResults));
    }
    
    public void testOneResultFromAspirinSearchAfterFiveSeconds(){
    	final EditText query = (EditText) getActivity().findViewById(R.id.searchText);
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				query.setText("aspirin");
			} 
			
		});
    	getInstrumentation().waitForIdleSync();
    	
    	final ImageButton searchButton = (ImageButton) getActivity().findViewById(R.id.searchButton);
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				searchButton.performClick();
			} 
			
		});
    	getInstrumentation().waitForIdleSync();
    	
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	ListView l = (ListView) getActivity().findViewById(R.id.searchResults);
    	Assert.assertEquals(l.getCount(), 1);
    }
	
	public SearchViewTest(Class<SearchViewActivity> activityClass) {
		super(activityClass);
	}
}

package com.example.chemiquiz.test;

import junit.framework.Assert;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.chemiquiz.R;
import com.example.chemiquiz.SearchViewActivity;

public class SearchViewTest extends ActivityUnitTestCase<SearchViewActivity> {
		
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
    }   

    public void testListViewExists(){
    	Intent searchIntent = new Intent(mContext, SearchViewActivity.class);
    	searchIntent.putExtra("com.exmaple.chemiquiz.AddingToSubsetID", 0);
    	startActivity(searchIntent, null, null);
    	Assert.assertNotNull(this.getActivity().findViewById(R.id.searchResults));
    }
    
    public void testOneResultFromAspirinSearch(){
    	Intent searchIntent = new Intent(mContext, SearchViewActivity.class);
    	searchIntent.putExtra("com.exmaple.chemiquiz.AddingToSubsetID", 0);
    	SearchViewActivity a = startActivity(searchIntent, null, null);
    	
    	EditText query = (EditText) a.findViewById(R.id.searchText);
    	query.setText("aspirin");
    	
    	ImageButton searchButton = (ImageButton) a.findViewById(R.id.searchButton);
    	searchButton.performClick();
    	
    	ListView l = (ListView) a.findViewById(R.id.searchResults);
    	Assert.assertEquals(l.getCount(), 1);
    }
	
	public SearchViewTest(Class<SearchViewActivity> activityClass) {
		super(activityClass);
	}
	
	
}

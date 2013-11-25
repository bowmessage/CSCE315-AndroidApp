package com.example.chemiquiz.test;

import junit.framework.Assert;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.chemiquiz.ChemicalSubset;
import com.example.chemiquiz.R;
import com.example.chemiquiz.SubsetViewActivity;

public class SubsetEditTest extends ActivityUnitTestCase<SubsetViewActivity> {
		
	public SubsetEditTest(){
		super(SubsetViewActivity.class);
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
    	Intent i = new Intent(mContext, SubsetViewActivity.class);
    	i.putExtra("com.exmaple.chemiquiz.EditingSubsetID", 0);
    	startActivity(i, null, null);
    	Assert.assertNotNull(this.getActivity().findViewById(R.id.subsetEditChemicalList));
    }
    
    public void testListViewTiedToChemicalArrayList(){
    	SubsetViewActivity a = startActivity(new Intent(mContext, SubsetViewActivity.class), null, null);
    	SubsetViewActivity.subsets.add(new ChemicalSubset("test1"));
    	ListView l = (ListView) a.findViewById(R.id.subsetViewsubsetList);
    	((BaseAdapter) l.getAdapter()).notifyDataSetChanged();
    	Assert.assertEquals(l.getCount(), 1);
    }
	
	public SubsetEditTest(Class<SubsetViewActivity> activityClass) {
		super(activityClass);
	}
	
	
}

package com.example.chemiquiz.test;

import junit.framework.Assert;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;

import com.example.chemiquiz.R;
import com.example.chemiquiz.SubsetViewActivity;

public class SubsetViewTest extends ActivityUnitTestCase<SubsetViewActivity> {
	
	/*@Override
	public void TestCase(){
		Activity mActivity = getActivity();
		assertTrue(mActivity.getActionBar().isShowing());
	}*/
	
	public SubsetViewTest(){
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

    public void test(){
    	startActivity(new Intent(mContext, SubsetViewActivity.class), null, null);
    	Assert.assertNotNull(this.getActivity().findViewById(R.id.subsetViewsubsetList));
    }
	
	public SubsetViewTest(Class<SubsetViewActivity> activityClass) {
		super(activityClass);
	}
	
	
}

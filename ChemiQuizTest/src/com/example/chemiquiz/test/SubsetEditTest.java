package com.example.chemiquiz.test;

import junit.framework.Assert;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.example.chemiquiz.Chemical;
import com.example.chemiquiz.ChemicalSubset;
import com.example.chemiquiz.R;
import com.example.chemiquiz.SubsetEditActivity;
import com.example.chemiquiz.SubsetViewActivity;

public class SubsetEditTest extends ActivityInstrumentationTestCase2<SubsetEditActivity> {
		
	public SubsetEditTest(){
		super(SubsetEditActivity.class);
	}
	
	Context mContext;

    public void setUp(){
        try {
            super.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ChemicalSubset CS1 = new ChemicalSubset("test1");
        CS1.add(new Chemical(0,"test_chem"));
        SubsetViewActivity.subsets.add(CS1);
        
        mContext = this.getInstrumentation().getContext();
        Intent i = new Intent(mContext, SubsetEditActivity.class);
    	i.putExtra("com.exmaple.chemiquiz.EditingSubsetID", 0);
        setActivityIntent(i);
    }   

    public void testListViewExists(){
    	Assert.assertNotNull(getActivity().findViewById(R.id.subsetEditChemicalList));
    }
    
    public void testListViewTiedToChemicalArrayList(){
    	ListView l = (ListView) getActivity().findViewById(R.id.subsetEditChemicalList);
    	Assert.assertEquals(l.getCount(), SubsetViewActivity.subsets.get(0).size());
    }
}

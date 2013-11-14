package com.example.chemiquiz;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SubsetEditActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subset_edit);
        
        Bundle extras = getIntent().getExtras();
        int curChemicalSubsetID = extras.getInt("com.exmaple.chemiquiz.EditingSubsetID");
        ChemicalSubset found = new ChemicalSubset("asdf111");
        found.add(13);
        found.add(52);
        found.add(523);
               
        final ListView listview = (ListView) findViewById(R.id.subsetEditChemicalList);
        
        final SubsetValuesAdapter adapter = new SubsetValuesAdapter(this,
        	android.R.layout.simple_list_item_1, new ArrayList<Integer>(found.chemSpiderIDs));
            //R.layout.subset_list_item, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

          @Override
          public void onItemClick(AdapterView<?> parent, final View view,
              int position, long id) {
            final ChemicalSubset item = (ChemicalSubset) parent.getItemAtPosition(position);
            SubsetEditActivity.this.startActivity(new Intent(SubsetEditActivity.this, GameViewActivity.class));
          }

        });
      }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.subset_edit, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
            	startActivity(new Intent(this, SearchViewActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
}

class SubsetValuesAdapter extends ArrayAdapter<Integer> {

	private final Context context;
	private final List<Integer> values;

    public SubsetValuesAdapter(Context context, int textViewResourceId,
        List<Integer> objects) {
      super(context, textViewResourceId, objects);
      this.context = context;
      values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View rowView = inflater.inflate(R.layout.list_item_chemical, parent, false);
      TextView nameTextView = (TextView) rowView.findViewById(R.id.idNumber);
      nameTextView.setText(values.get(position) + "");

      return rowView;
    }

  }
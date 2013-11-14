package com.example.chemiquiz;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SubsetViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subset_view);

        
        final ArrayList<ChemicalSubset> subsets = new ArrayList<ChemicalSubset>();
        ChemicalSubset test1 = new ChemicalSubset("test1");
        test1.add(324);
        test1.add(4523);
        subsets.add(test1);
        subsets.add(new ChemicalSubset("test2"));
        
                
        final ListView listview = (ListView) findViewById(R.id.subsetViewsubsetList);
        
        final SubsetArrayAdapter adapter = new SubsetArrayAdapter(this,
        	android.R.layout.simple_list_item_1, subsets);
            //R.layout.subset_list_item, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

          @Override
          public void onItemClick(AdapterView<?> parent, final View view,
              int position, long id) {
            final ChemicalSubset item = (ChemicalSubset) parent.getItemAtPosition(position);
            SubsetViewActivity.this.startActivity(new Intent(SubsetViewActivity.this, GameViewActivity.class));
          }

        });
      }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.subset_view, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
            	Intent editIntent = new Intent(this, SubsetEditActivity.class);
            	editIntent.putExtra("com.exmaple.chemiquiz.EditingSubsetID", 0);
            	startActivity(editIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
}

class SubsetArrayAdapter extends ArrayAdapter<ChemicalSubset> {
	private final Context context;
	private final List<ChemicalSubset> values;

    public SubsetArrayAdapter(Context context, int textViewResourceId,
        List<ChemicalSubset> objects) {
      super(context, textViewResourceId, objects);
      this.context = context;
      values = objects;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View rowView = inflater.inflate(R.layout.list_item_subset, parent, false);
      TextView nameTextView = (TextView) rowView.findViewById(R.id.name);
      TextView sizeTextView = (TextView) rowView.findViewById(R.id.size);
      nameTextView.setText(values.get(position).getName());
      sizeTextView.setText(values.get(position).getSize() + " items"); 

      return rowView;
    }

  }

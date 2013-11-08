package com.example.chemiquiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SubsetViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subset_view);
        
        final ArrayList<ChemicalSubset> subsets = new ArrayList<ChemicalSubset>();
        subsets.add(new ChemicalSubset("test1"));
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
            /*view.animate().setDuration(2000).alpha(0)
                .withEndAction(new Runnable() {
                  @Override
                  public void run() {
                	subsets.remove(item);
                    adapter.notifyDataSetChanged();
                    view.setAlpha(1);
                  }
                });*/
          }

        });
      }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.subset_view, menu);
        return true;
    }
    
}

class SubsetArrayAdapter extends ArrayAdapter<ChemicalSubset> {

    //HashMap<ChemicalSubset, Integer> idMap = new HashMap<ChemicalSubset, Integer>();
	
	private final Context context;
	private final List<ChemicalSubset> values;

    public SubsetArrayAdapter(Context context, int textViewResourceId,
        List<ChemicalSubset> objects) {
      super(context, textViewResourceId, objects);
      this.context = context;
      values = objects;
      /*for (int i = 0; i < objects.size(); ++i) {
        idMap.put(objects.get(i), i);
      }*/
    }

    /*@Override
    public long getItemId(int position) {
    	ChemicalSubset item = getItem(position);
      return idMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }*/
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View rowView = inflater.inflate(R.layout.subset_list_item, parent, false);
      TextView nameTextView = (TextView) rowView.findViewById(R.id.name);
      TextView sizeTextView = (TextView) rowView.findViewById(R.id.size);
      //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
      nameTextView.setText(values.get(position).getName());
      sizeTextView.setText(values.get(position).getSize() + " items"); 

      return rowView;
    }

  }

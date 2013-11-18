package com.example.chemiquiz;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SubsetViewActivity extends Activity {
	
	public static ArrayList<ChemicalSubset> subsets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subset_view);

        subsets = new ArrayList<ChemicalSubset>();
        
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
        
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        	
        	@Override
        	public boolean onItemLongClick(AdapterView<?> parent, View anchorView,
        			final int position, long id){
        	    View popupView = getLayoutInflater().inflate(R.layout.popup_subset_view_longpress, null);

        	    final PopupWindow popupWindow = new PopupWindow(popupView, 
        	                           LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        	    
        	    Button editButton = (Button) popupView.findViewById(R.id.editButton);
        	    editButton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						popupWindow.dismiss();
						Intent editIntent = new Intent(SubsetViewActivity.this, SubsetEditActivity.class);
		            	editIntent.putExtra("com.exmaple.chemiquiz.EditingSubsetID", position);
		            	startActivity(editIntent);
					}
        	    });
        	    
        	    Button deleteButton = (Button) popupView.findViewById(R.id.deleteButton);
        	    deleteButton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						popupWindow.dismiss();
						subsets.remove(position);
						adapter.notifyDataSetChanged();
					}
        	    });
        	    

        	    popupWindow.setFocusable(true);
        	    popupWindow.setBackgroundDrawable(new ColorDrawable(R.color.lightGray));
        	    popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
        		return true;
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
        	    View popupView = getLayoutInflater().inflate(R.layout.popup_subset_view_add_subset, null);

        	    final PopupWindow popupWindow = new PopupWindow(popupView, 
        	                           LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        	    
        	    final EditText newName = (EditText) popupView.findViewById(R.id.newSubsetName);
        	    final Button createButton = (Button) popupView.findViewById(R.id.createButton);
        	    createButton.setEnabled(false);
        	    createButton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						popupWindow.dismiss();
						subsets.add(new ChemicalSubset(newName.getText().toString()));
						((BaseAdapter) ((ListView) findViewById(R.id.subsetViewsubsetList)).getAdapter()).notifyDataSetChanged();
						Intent editIntent = new Intent(SubsetViewActivity.this, SubsetEditActivity.class);
		            	editIntent.putExtra("com.exmaple.chemiquiz.EditingSubsetID", subsets.size()-1);
		            	startActivity(editIntent);
					}
        	    });
        	    
        	    
        	    newName.addTextChangedListener(new TextWatcher(){
					@Override
					public void afterTextChanged(Editable arg0) {
						if(arg0.length() > 0)
							createButton.setEnabled(true);
						else
							createButton.setEnabled(false);
					}
					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {}
					@Override
					public void onTextChanged(CharSequence arg0, int arg1,
							int arg2, int arg3) {}
        	    });
        	    

        	    popupWindow.setFocusable(true);
        	    popupWindow.setBackgroundDrawable(new ColorDrawable());
        	    popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
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

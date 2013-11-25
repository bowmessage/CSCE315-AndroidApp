package com.example.chemiquiz;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Xml;
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
	
	public static ArrayList<ChemicalSubset> subsets = new ArrayList<ChemicalSubset>();
	SubsetArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        importSubsetsXMLData();

        setContentView(R.layout.activity_subset_view);
        
        final ListView listview = (ListView) findViewById(R.id.subsetViewsubsetList);
        
        adapter = new SubsetArrayAdapter(this,
        	android.R.layout.simple_list_item_1, subsets);
            //R.layout.subset_list_item, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

          @Override
          public void onItemClick(AdapterView<?> parent, final View view,
              int position, long id) {
            //final ChemicalSubset item = (ChemicalSubset) parent.getItemAtPosition(position);
        	  
        	if(subsets.get(position).size() >= 4){
        		Intent gameIntent = new Intent(SubsetViewActivity.this, GameViewActivity.class);
            	gameIntent.putExtra("com.exmaple.chemiquiz.PlayingSubsetID", position);
                SubsetViewActivity.this.startActivity(gameIntent);
        	}
        	else{
        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SubsetViewActivity.this);
         
        			alertDialogBuilder.setTitle("Subset Too Small");
         
        			alertDialogBuilder
        				.setMessage("You cannot play with a subset with less than 4 items.")
        				.setCancelable(true)
        				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
        					public void onClick(DialogInterface dialog,int id) {
        						
        					}
        				  });
         
        				// create alert dialog
        				AlertDialog alertDialog = alertDialogBuilder.create();
         
        				// show it
        				alertDialog.show();
        	}
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
    
    public ArrayList<Node> getElementNodes(NodeList nl){
    	ArrayList<Node> ret = new ArrayList<Node>();
    	for(int i = 0; i < nl.getLength(); i++){
    		if (nl.item(i).getNodeType() == Node.ELEMENT_NODE){
    			ret.add(nl.item(i));
    		}
    	}
    	return ret;
    }
    
    public void importSubsetsXMLData(){
    	try{
    		FileInputStream fis = null;
            InputStreamReader isr = null;

            fis = openFileInput("subsets.xml");
            isr = new InputStreamReader(fis);
            char[] inputBuffer = new char[fis.available()];
            isr.read(inputBuffer);
            String data = new String(inputBuffer);
            isr.close();
            fis.close();
            
            InputStream is = new ByteArrayInputStream(data.getBytes("UTF-8"));

            DocumentBuilderFactory dbf;
            DocumentBuilder db;
            NodeList items = null;
            Document dom;

            dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            dom = db.parse(is);
            dom.getDocumentElement().normalize();

            
            items = dom.getElementsByTagName("ChemicalSubset");

            for (int i = 0; i < items.getLength(); i++){
            	ChemicalSubset cur = new ChemicalSubset();
            	
            	ArrayList<Node> children = getElementNodes(items.item(i).getChildNodes());
            	cur.setName(children.get(0).getTextContent());
            	
            	ArrayList<Node> subsetChemicals = getElementNodes(children.get(1).getChildNodes());
            	
            	for(int j = 0; j < subsetChemicals.size(); j++){
            		ArrayList<Node> chemicalChildren = getElementNodes(subsetChemicals.get(j).getChildNodes());
            		cur.add(new Chemical(Integer.parseInt(chemicalChildren.get(1).getTextContent()), chemicalChildren.get(0).getTextContent()));
            	}           
                subsets.add(cur);
            }
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.subset_view, menu);
        return true;
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	if(adapter != null){
    		((BaseAdapter) adapter).notifyDataSetChanged();
    	}
    }
    
    @Override
    public void onStop(){
    	super.onStop();
    	//Save static arraylist subsets into xml storage
    	String filename = "subsets.xml";
        try {
	        FileOutputStream fos = openFileOutput(filename, 0);
	        XmlSerializer serializer = Xml.newSerializer();
	        serializer.setOutput(fos, "UTF-8");
	        serializer.startDocument(null, Boolean.valueOf(true));
	        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
	        serializer.startTag(null, "root");
	        for(int i = 0; i < subsets.size(); i++){
	        	serializer.startTag(null, "ChemicalSubset");
	        	
		        	serializer.startTag(null, "SubsetName");
		        	serializer.text(subsets.get(i).getName());
		        	serializer.endTag(null, "SubsetName");
		        	
		        	serializer.startTag(null, "ChemicalList");
		        	for(int j = 0; j < subsets.get(i).size(); j++){
		        		serializer.startTag(null, "Chemical");
		        		serializer.startTag(null, "Name");
		        		serializer.text(subsets.get(i).get(j).name);
		        		serializer.endTag(null, "Name");
		        		serializer.startTag(null, "Id");
		        		serializer.text(subsets.get(i).get(j).id + "");
		        		serializer.endTag(null, "Id");
		        		serializer.endTag(null, "Chemical");
		        	}
		        	serializer.endTag(null, "ChemicalList");
		        	
	        	serializer.endTag(null, "ChemicalSubset");
	        }
	        serializer.endTag(null, "root");
	        serializer.endDocument();
	        serializer.flush();
	        fos.close();
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block change
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
      sizeTextView.setText(values.get(position).size() + " items"); 

      return rowView;
    }

  }

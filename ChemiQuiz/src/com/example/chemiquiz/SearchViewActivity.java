package com.example.chemiquiz;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SearchViewActivity extends Activity {
	
	
	ProgressDialog pDialog;
	
	ChemicalSubset resultantChemicals;
	ListView searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        
        resultantChemicals = new ChemicalSubset();
        
        
        Bundle extras = getIntent().getExtras();
        final int curChemicalSubsetID = extras.getInt("com.exmaple.chemiquiz.AddingToSubsetID");
        
        
        final EditText searchVal = (EditText) findViewById(R.id.searchText);
        final ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        final Button addButton = (Button) findViewById(R.id.addButton);
        searchResults = (ListView) findViewById(R.id.searchResults);
        
        addButton.setEnabled(false);
        
        final ResultsArrayAdapter adapter = new ResultsArrayAdapter(this,
            	android.R.layout.simple_list_item_1, resultantChemicals.chemicals);
        
        searchVal.setOnEditorActionListener(new OnEditorActionListener(){
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				searchButton.performClick();
				return true;
			}
        });
            
        searchResults.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        searchResults.setAdapter(adapter);
        searchResults.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				adapter.setSelectedState(position, !adapter.getSelectedState(position));
				CheckBox c = (CheckBox) view.findViewById(R.id.checkBox);
				c.toggle();
				view.setSelected(!view.isSelected());
				
				if(searchResults.getCheckedItemCount() > 0){
					addButton.setEnabled(true);
				} else addButton.setEnabled(false);
			}
        	
        });
        
        searchButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String query = searchVal.getEditableText().toString();
				new IdNumberParser("http://www.chemspider.com/search.asmx/SimpleSearch?query="+query+"&token=f52ab236-347f-41dd-973d-a0e6668b7e14").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{null});
			}
        });
        
        addButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				for(int i = 0; i < adapter.isSelected.size(); i++) {
					SubsetViewActivity.subsets.get(curChemicalSubsetID).add(adapter.chemicals.get(adapter.isSelected.keyAt(i)));
				}
				SearchViewActivity.this.finish();
			}
			
        });
        
        
        
    }
    
    public class IdNumberParser extends AsyncTask<String, Void, String> {

        // variables passed in:
        String urls;
        //  constructor
        public IdNumberParser(String urls) {
            this.urls = urls;
        }

        @Override
        protected void onPreExecute() {
        	if(SearchViewActivity.this != null){
        		pDialog = ProgressDialog.show(SearchViewActivity.this, "Fetching Details..", "Please wait...", true);
        	}
        }


        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            URL url;
            try {

                url = new URL(urls);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));

                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("int");
                
                resultantChemicals.clear();

                for (int i = 0; i < nodeList.getLength(); i++) {
                	new CommonNameParser(Integer.parseInt(nodeList.item(i).getTextContent()))
                	.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{null});
                	//resultantChemSpiderIDs.add(new Chemical(Integer.parseInt(nodeList.item(i).getTextContent())));
                }

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // Now we have your JSONObject, play around with it.
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

    }
    
    public class CommonNameParser extends AsyncTask<String, Void, String> {

		// variables passed in:
		String urls;
		int id;
		NodeList nodeList;

		// constructor
		public CommonNameParser(int id) {
			this.id = id;
			urls = "http://www.chemspider.com/MassSpecAPI.asmx/GetExtendedCompoundInfo?CSID="
					+ id + "&token=f52ab236-347f-41dd-973d-a0e6668b7e14";
		}

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			URL url;
			try {

				url = new URL(urls);
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new InputSource(url.openStream()));

				doc.getDocumentElement().normalize();

				nodeList = doc.getElementsByTagName("CommonName");
				

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			resultantChemicals.add(new Chemical(id, nodeList.item(0).getTextContent()));
			((BaseAdapter) searchResults.getAdapter()).notifyDataSetChanged();
		}

	}
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_view, menu);
        return true;
    }
    
}


class ResultsArrayAdapter extends ArrayAdapter<Chemical> {
	private final Context context;
	public final List<Chemical> chemicals;
	public final SparseBooleanArray isSelected;

    public ResultsArrayAdapter(Context context, int textViewResourceId,
        List<Chemical> objects) {
      super(context, textViewResourceId, objects);
      this.context = context;
      chemicals = objects;
      isSelected = new SparseBooleanArray();
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	if(chemicals.get(position).name == null){
    		//return null;
    	}
      LayoutInflater inflater = (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View rowView = inflater.inflate(R.layout.list_item_search_result, parent, false);
      TextView nameTextView = (TextView) rowView.findViewById(R.id.resultName);
      nameTextView.setText(chemicals.get(position).getName() + "");
      TextView idTextView = (TextView) rowView.findViewById(R.id.resultId);
      idTextView.setText(chemicals.get(position).getId() + "");
      CheckBox cb = (CheckBox) rowView.findViewById(R.id.checkBox);
      cb.setClickable(false);
      if(isSelected.get(position)){
    	  cb.setChecked(true);
      }
      return rowView;
    }
    
    public Chemical getChemicalIdAtPosition(int position){
    	return chemicals.get(position);
    }
    
    public void setSelectedState(int index, boolean v){
    	isSelected.put(index, v);
    }
    
    public boolean getSelectedState(int index){
    	return isSelected.get(index);
    }
  }

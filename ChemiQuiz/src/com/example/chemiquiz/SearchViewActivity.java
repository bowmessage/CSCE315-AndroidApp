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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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

public class SearchViewActivity extends Activity {
	
	
	ProgressDialog pDialog;
	
	ArrayList<Integer> resultantChemSpiderIDs;
	ListView searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        
        resultantChemSpiderIDs = new ArrayList<Integer>();
        
        
        Bundle extras = getIntent().getExtras();
        final int curChemicalSubsetID = extras.getInt("com.exmaple.chemiquiz.AddingToSubsetID");
        
        
        final EditText searchVal = (EditText) findViewById(R.id.searchText);
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        final Button addButton = (Button) findViewById(R.id.addButton);
        searchResults = (ListView) findViewById(R.id.searchResults);
        
        addButton.setEnabled(false);
        
        final ResultsArrayAdapter adapter = new ResultsArrayAdapter(this,
            	android.R.layout.simple_list_item_1, resultantChemSpiderIDs);
            
        searchResults.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        searchResults.setAdapter(adapter);
        searchResults.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
				new XmlParsing("http://www.chemspider.com/search.asmx/SimpleSearch?query="+query+"&token=f52ab236-347f-41dd-973d-a0e6668b7e14").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{null});
			}
        });
        
        addButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				ArrayList<Integer> chemSpiderIDsSelected = new ArrayList<Integer>();
				SparseBooleanArray valuesSelected = searchResults.getCheckedItemPositions();
		        for (int i = 0; i < valuesSelected.size(); i++) {
		            if(valuesSelected.valueAt(i) == true) {
		            	SubsetViewActivity.subsets.get(curChemicalSubsetID).add(adapter.getItem(i));
		            	//finish();
		            	//TODO FIX THIS
		            }
		        }
			}
        });
        
        
        
    }
    
    public class XmlParsing extends AsyncTask<String, Void, String> {

        // variables passed in:
        String urls;
        //  constructor
        public XmlParsing(String urls) {
            this.urls = urls;
        }

        @Override
        protected void onPreExecute() {
            pDialog = ProgressDialog.show(SearchViewActivity.this, "Fetching Details..", "Please wait...", true);
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
                
                resultantChemSpiderIDs.clear();

                for (int i = 0; i < nodeList.getLength(); i++) {
                	resultantChemSpiderIDs.add(Integer.parseInt(nodeList.item(i).getTextContent()));
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


class ResultsArrayAdapter extends ArrayAdapter<Integer> {
	private final Context context;
	private final List<Integer> values;

    public ResultsArrayAdapter(Context context, int textViewResourceId,
        List<Integer> objects) {
      super(context, textViewResourceId, objects);
      this.context = context;
      values = objects;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View rowView = inflater.inflate(R.layout.list_item_search_result, parent, false);
      TextView nameTextView = (TextView) rowView.findViewById(R.id.idNumber);
      nameTextView.setText(values.get(position) + "");
      return rowView;
    }

  }

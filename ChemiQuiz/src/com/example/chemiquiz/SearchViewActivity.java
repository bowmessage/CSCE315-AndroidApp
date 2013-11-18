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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
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
        
        final EditText searchVal = (EditText) findViewById(R.id.searchText);
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        Button addButton = (Button) findViewById(R.id.addButton);
        searchResults = (ListView) findViewById(R.id.searchResults);
        
        final ResultsArrayAdapter adapter = new ResultsArrayAdapter(this,
            	android.R.layout.simple_list_item_1, resultantChemSpiderIDs);
            
        searchResults.setAdapter(adapter);
        
        searchButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String query = searchVal.getEditableText().toString();
				new XmlParsing("http://www.chemspider.com/search.asmx/SimpleSearch?query="+query+"&token=f52ab236-347f-41dd-973d-a0e6668b7e14").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{null});
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

                    /*Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;
                    NodeList idList = fstElmnt.getElementsByTagName("int");
                    Element nameElement = (Element) nameList.item(0);
                    nameList = nameElement.getChildNodes();
                    title.add(""+ ((Node) nameList.item(0)).getNodeValue());

                    System.out.println("name : "+((Node) nameList.item(0)).getNodeValue());


                    Element fstElmnt1 = (Element) node;
                    NodeList nameList1 = fstElmnt1.getElementsByTagName("id");
                    Element nameElement1 = (Element) nameList1.item(0);
                    nameList1 = nameElement1.getChildNodes();
                    description.add(""+ ((Node) nameList1.item(0)).getNodeValue());

                    System.out.println("id : "+ ((Node) nameList1.item(0)).getNodeValue());

                    Element fstElmnt2 = (Element) node;
                    NodeList nameList2 = fstElmnt2.getElementsByTagName("cost");
                    Element nameElement2 = (Element) nameList2.item(0);
                    nameList2 = nameElement2.getChildNodes();
                    id.add(""+ ((Node) nameList2.item(0)).getNodeValue());

                    System.out.println("cost : "+ ((Node) nameList2.item(0)).getNodeValue());

                    Element fstElmnt3 = (Element) node;
                    NodeList nameList3 = fstElmnt3.getElementsByTagName("description");
                    Element nameElement3 = (Element) nameList3.item(0);
                    nameList3 = nameElement3.getChildNodes();
                    cost.add(""+ ((Node) nameList3.item(0)).getNodeValue());

                    System.out.println("description : "+ ((Node) nameList3.item(0)).getNodeValue());*/

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

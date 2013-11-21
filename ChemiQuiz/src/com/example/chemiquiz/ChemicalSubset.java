package com.example.chemiquiz;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;

public class ChemicalSubset {
	public String name;
	public int size;
	public ArrayList<Chemical> chemicals;

	public ChemicalSubset() {
		chemicals = new ArrayList<Chemical>();
		name = "New Subset";
	}

	public ChemicalSubset(String n) {
		chemicals = new ArrayList<Chemical>();
		name = n;
	}

	public void add(Chemical i) {
		chemicals.add(i);
		new CommonNameParser(
				"http://www.chemspider.com/MassSpecAPI.asmx/GetExtendedCompoundInfo?CSID="
						+ i.id + "&token=f52ab236-347f-41dd-973d-a0e6668b7e14", chemicals.size()-1)
		.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{null});
	}

	public void remove(int i) {
		chemicals.remove(i);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return chemicals.size();
	}

	public class CommonNameParser extends AsyncTask<String, Void, String> {

		// variables passed in:
		String urls;
		int indexToMod;

		// constructor
		public CommonNameParser(String urls, int indexToModify) {
			this.urls = urls;
			indexToMod = indexToModify;
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

				NodeList nodeList = doc.getElementsByTagName("CommonName");
				Log.d("cq", nodeList.item(0).getTextContent());
				chemicals.get(indexToMod).setName(nodeList.item(0).getTextContent());

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

		}

	}

	public void clear() {
		chemicals.clear();
	}
}

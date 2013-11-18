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

public class ChemicalSubset {
	public String name;
	public int size;
	public ArrayList<Integer> chemSpiderIDs;
	public ArrayList<String> chemNames;

	public ChemicalSubset() {
		chemSpiderIDs = new ArrayList<Integer>();
		chemNames = new ArrayList<String>();
		name = "New Subset";
	}

	public ChemicalSubset(String n) {
		chemSpiderIDs = new ArrayList<Integer>();
		name = n;
	}

	public void add(int i) {
		chemSpiderIDs.add(i);
		new GetCommonNameParser(
				"www.chemspider.com/MassSpecAPI.asmx/GetExtendedCompoundInfo?CSID="
						+ i + "&token=f52ab236-347f-41dd-973d-a0e6668b7e14");
	}

	public void remove(int i) {
		chemSpiderIDs.remove(i);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return chemSpiderIDs.size();
	}

	public class GetCommonNameParser extends AsyncTask<String, Void, String> {

		// variables passed in:
		String urls;

		// constructor
		public GetCommonNameParser(String urls) {
			this.urls = urls;
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

				chemNames.clear();

				for (int i = 0; i < nodeList.getLength(); i++) {

					chemNames.add(nodeList.item(i).getTextContent());

					/*
					 * Node node = nodeList.item(i); Element fstElmnt =
					 * (Element) node; NodeList idList =
					 * fstElmnt.getElementsByTagName("int"); Element nameElement
					 * = (Element) nameList.item(0); nameList =
					 * nameElement.getChildNodes(); title.add(""+ ((Node)
					 * nameList.item(0)).getNodeValue());
					 * 
					 * System.out.println("name : "+((Node)
					 * nameList.item(0)).getNodeValue());
					 * 
					 * 
					 * Element fstElmnt1 = (Element) node; NodeList nameList1 =
					 * fstElmnt1.getElementsByTagName("id"); Element
					 * nameElement1 = (Element) nameList1.item(0); nameList1 =
					 * nameElement1.getChildNodes(); description.add(""+ ((Node)
					 * nameList1.item(0)).getNodeValue());
					 * 
					 * System.out.println("id : "+ ((Node)
					 * nameList1.item(0)).getNodeValue());
					 * 
					 * Element fstElmnt2 = (Element) node; NodeList nameList2 =
					 * fstElmnt2.getElementsByTagName("cost"); Element
					 * nameElement2 = (Element) nameList2.item(0); nameList2 =
					 * nameElement2.getChildNodes(); id.add(""+ ((Node)
					 * nameList2.item(0)).getNodeValue());
					 * 
					 * System.out.println("cost : "+ ((Node)
					 * nameList2.item(0)).getNodeValue());
					 * 
					 * Element fstElmnt3 = (Element) node; NodeList nameList3 =
					 * fstElmnt3.getElementsByTagName("description"); Element
					 * nameElement3 = (Element) nameList3.item(0); nameList3 =
					 * nameElement3.getChildNodes(); cost.add(""+ ((Node)
					 * nameList3.item(0)).getNodeValue());
					 * 
					 * System.out.println("description : "+ ((Node)
					 * nameList3.item(0)).getNodeValue());
					 */

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

		}

	}
}

 	package com.example.chemiquiz;

import java.util.ArrayList;

public class ChemicalSubset {
	public String name;
	public int size;
	public ArrayList<Integer> chemSpiderIDs;
	
	public ChemicalSubset(){
		chemSpiderIDs = new ArrayList<Integer>();
		name = "New Subset";
	}
	public ChemicalSubset(String n){
		chemSpiderIDs = new ArrayList<Integer>();
		name = n;
	}
	
	public void add(int i){
		chemSpiderIDs.add(i);
	}
	
	public void remove(int i){
		chemSpiderIDs.remove(i);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getSize(){
		return chemSpiderIDs.size();
	}
}

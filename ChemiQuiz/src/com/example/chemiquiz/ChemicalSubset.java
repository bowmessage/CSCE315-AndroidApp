package com.example.chemiquiz;

import java.util.HashSet;

public class ChemicalSubset {
	public String name;
	public HashSet<Integer> chemSpiderIDs;
	
	public ChemicalSubset(){
		chemSpiderIDs = new HashSet<Integer>();
		name = "New Subset";
	}
	public ChemicalSubset(String n){
		chemSpiderIDs = new HashSet<Integer>();
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
}

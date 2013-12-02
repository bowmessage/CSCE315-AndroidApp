package com.example.chemiquiz;

import java.util.ArrayList;
import java.util.Collections;

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
	}
	
	public Chemical get(int i){
		return chemicals.get(i);
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

	public int size() {
		return chemicals.size();
	}
	
	public ArrayList<Chemical> toArrayList(){
		ArrayList<Chemical> ret = new ArrayList<Chemical>();
		for(int i = 0; i < chemicals.size(); i++){
			ret.add(chemicals.get(i).clone());
		}
		return ret;
	}
	
	public void shuffle(){
		Collections.shuffle(chemicals);
	}

	public void clear() {
		chemicals.clear();
	}
}

package com.example.chemiquiz;

import java.io.Serializable;

public class Chemical implements Serializable, Cloneable {
	private static final long serialVersionUID = -7989111605836217510L;
	public String name;
	public int id;
	
	public Chemical(int i){
		id = i;
		name = "";
	}
	public Chemical(int i, String s){
		id = i;
		name = s;
	}
	
	public Chemical() {
		name = "";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public Chemical clone(){
		return new Chemical(id, name);
	}
}

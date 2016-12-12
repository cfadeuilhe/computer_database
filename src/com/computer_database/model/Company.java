package com.computer_database.model;

public class Company {

	private int ID;
	private String name;

	
	
	public Company(int ID, String name) {
		super();
		this.ID = ID;
		this.name = name;
	}
	public int getID() {
		return ID;
	}
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "\nCompany n°"+ID+" -> " + name;
	}
}

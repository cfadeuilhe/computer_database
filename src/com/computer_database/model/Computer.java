package com.computer_database.model;
import java.util.*;

public class Computer {
	
	private int ID;
	private String name;
	private int manufacturerID;
	private String manufacturer;
	private Date introductionDate;
	private Date discontinutionDate;
	
	
	public Computer(int ID,String name, int manufacturerID, String manufacturer, Date introductionDate, Date discontinutionDate) {
		super();
		this.ID = ID;
		this.name = name;
		this.manufacturer = manufacturer;
		this.manufacturerID = manufacturerID;
		this.introductionDate = introductionDate;
		this.discontinutionDate = discontinutionDate;
	}
	
	public Computer() {
		super();
	}

	public Computer(int ID, String name) {
		super();
		this.ID = ID;
		this.name = name;
	}
	
	public void setComputer(int ID, String name,int manufacturerID,Date introductionDate,Date discontinutionDate){
		this.ID = ID;
		this.name = name;
		this.manufacturerID = manufacturerID;
		this.introductionDate = introductionDate;
		this.discontinutionDate = discontinutionDate;
	}

	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getManufacturerID() {
		return manufacturerID;
	}
	public void setManufacturerID(int manufacturerID) {
		this.manufacturerID = manufacturerID;
	}/*
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}*/
	public Date getIntroductionDate() {
		return introductionDate;
	}
	public void setIntroductionDate(Date introductionDate) {
		this.introductionDate = introductionDate;
	}
	public Date getDiscontinutionDate() {
		return discontinutionDate;
	}
	public void setDiscontinutionDate(Date discontinutionDate) {
		this.discontinutionDate = discontinutionDate;
	}
	
	@Override
	public String toString() {
		return "\nComputer n°"+ ID +": [Name=" + name + ", Manufacturer=" + ((manufacturer != null)?manufacturer:"Unknown manufacturer") + ", Introduced on=" + ((introductionDate != null)?introductionDate.toString():"Date undefined")
				+ ", Discontinued on=" + ((discontinutionDate != null)?discontinutionDate.toString():"Date undefined") + "]";
	}
}

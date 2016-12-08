package com.computer_database.model;
import java.util.*;

public class Computer {
	
	private String name;
	private String manufacturer;
	Date introductionDate;
	Date discontinutionDate;
	
	
	public Computer(String name, String manufacturer, Date introductionDate, Date discontinutionDate) {
		super();
		this.name = name;
		this.manufacturer = manufacturer;
		this.introductionDate = introductionDate;
		this.discontinutionDate = discontinutionDate;
	}
	
	public Computer() {
		super();
	}

	public Computer(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
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
		return "Computer [name=" + name + ", manufacturer=" + manufacturer + ", introductionDate=" + introductionDate
				+ ", discontinutionDate=" + discontinutionDate + "]";
	}
}

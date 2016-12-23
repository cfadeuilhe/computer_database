package com.excilys.computerdatabase.validators;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.Computer;

public class ComputerValidator {

    private static List<String> list = new ArrayList<String>();
	public static void idValidator(int id) {
		if (id <= 0) {
			list.add("NegativeId");
		}
	}

	public static void idValidator(String id) {

	}

	public static void nameValidator(String name){
		if(name == null || name.length() <= 0){
			list.add("NoName");
		}else if(name.length() <= 2){
			list.add("NameTooShort");
		}
	}
	
	public static void validator(Computer c){
	    //List<String> list = new ArrayList<String>();
	    c.setErrorsList(list);
	}
}

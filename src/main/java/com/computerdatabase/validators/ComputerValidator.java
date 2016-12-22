package main.java.com.computerdatabase.validators;

import main.java.com.computerdatabase.exceptions.ValidationException;

public class ComputerValidator {

	public static void idValidator(int id) {
		if (id <= 0) {
			throw new ValidationException("id must be positive");
		}
	}

	public static void idValidator(String id) {

	}

	public static void nameValidator(String name){
		if(name == null || name.length() <= 0){
			throw new ValidationException("You must enter a name");
		}else if(name.length() <= 2){
			throw new ValidationException("The name must have more than 2 characters");
		}
	}
}

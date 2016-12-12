package com.computer_database.userinterface;
import java.io.*;
import java.text.*;
import java.util.Date;

import com.computer_database.persistence.ReadDataBase;
import com.computer_database.persistence.WriteDataBase;
import com.computer_database.service.Page;

public class interfaceMenu {

	public static void main(String[] args) throws IOException, ParseException {
		String userChoice, choiceName, choiceManuf, choiceIntroDate, choiceDiscoDate;
		Date intro=null, disco=null;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	  do{	
		System.out.println("Menu :\n\t1) List computers\n\t2) List companies\n\t3) Show computer details\n\t4) Create a computer\n\t5) Update a computer\n\t6) Delete a computer\n\t7) By Pages\n\t8) Quit");
		userChoice = reader.readLine();
		switch(userChoice){
		case "1": //List computers
			ReadDataBase.listComputers();
			break;
		case "2": //List companies
			ReadDataBase.listCompanies();
			break;
		case "3": //Show computer details (info of one specific computer)
			System.out.println("Enter computer ID [There are currently "+ReadDataBase.numberOfComputers()+" computers in the database]");
			userChoice = reader.readLine();
			ReadDataBase.showComputerDetails(Integer.parseInt(userChoice));
			break;
		case "4": //Create a computer
			do{
			System.out.println("Enter name (mandatory)");
			choiceName = reader.readLine();
			}while(choiceName.equals(""));
			System.out.println("Enter manufacturer from the following list :");
			ReadDataBase.listCompanies();
			choiceManuf = reader.readLine();
			System.out.println("Enter introduction date. [dd/mm/yyyy] t) for today's date");
			choiceIntroDate = reader.readLine();
			if(choiceIntroDate.equals("t")){
				intro = new Date();
			}else if(choiceIntroDate.equals("")){
				intro = null;
			}else{
				intro = df.parse(choiceIntroDate);
			}
			System.out.println("Enter discontinution date. [dd/mm/yyyy] t) for today's date");
			choiceDiscoDate = reader.readLine();
			if(choiceDiscoDate.equals("t")){
				disco = new Date();
			}else if(choiceDiscoDate.equals("")){
				disco = null;
			}else{
				disco = df.parse(choiceDiscoDate);
			} 
			WriteDataBase.createComputer(choiceName, Integer.parseInt(choiceManuf), intro, disco);
			
			break;
		case "5": //Update a computer
			System.out.println("Enter id of computer to update (0 to "+ReadDataBase.numberOfComputers()+")");
			userChoice = reader.readLine();
			WriteDataBase.updateComputer(Integer.parseInt(userChoice));
			break;
		case "6": //Delete a computer
			System.out.println("Enter id of the computer to delete");
			userChoice = reader.readLine();
			WriteDataBase.deleteComputer(Integer.parseInt(userChoice));
			break;
		case "7": //Pages
			System.out.println("Enter the number of elements per page wanted");
			userChoice = reader.readLine();
			System.out.println("Enter the number of the page to display");
			String choicePage = reader.readLine();
			Page.listXElements("computer", Integer.parseInt(userChoice), Integer.parseInt(choicePage));
			break;
		case "8": //Quit
			System.out.println("Bubye :)");
			break;
		default:
			System.out.println("try again, stupid");
			break;
		}
	  }while(!userChoice.equals("7"));
	}

}

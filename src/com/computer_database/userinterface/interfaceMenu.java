package com.computer_database.userinterface;
import java.io.*;
import com.computer_database.persistence.ReadDataBase;

public class interfaceMenu {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int choix;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	  do{	
		System.out.println("Menu :\n1) List computers\n2) List companies\n3) Show computer details\n4) Create a computer\n5)Update a computer\n6) Delete a computer");
		choix = reader.read();
		
		switch(choix){
		case 1: //List computers
			ReadDataBase.listComputers();
			break;
		case 2: //List companies
			ReadDataBase.listCompanies();
			break;
		case 3: //Show computer details (info of one specific computer)
			
			break;
		case 4: //Create a computer
			
			break;
		case 5: //Update a computer
			
			break;
		case 6: //Delete a computer
			
			break;
		default:
			System.out.println("try again");
			break;
		}
	  }while(choix != 7);
	}

}

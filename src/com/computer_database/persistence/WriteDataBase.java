package com.computer_database.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.computer_database.model.Computer;

public class WriteDataBase {

	public static void main(String[] args) throws IOException, ParseException {
		
		//createComputer("computer 897", "test", null, null);
		createComputer("baejr");
	}
	
	public static void createComputer(String name, int manufID, Date intro, Date disco){
		Computer c = new Computer(ReadDataBase.lastComputerId()+1,name, manufID, ReadDataBase.getManufacturer(manufID), intro, disco);
		write(c,"create");
	}public static void createComputer(String name){
		Computer c = new Computer(ReadDataBase.lastComputerId()+1,name);
		write(c,"create");
	}
	public static void updateComputer(int id) throws IOException, ParseException{
		Computer c = new Computer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String userChoice=null;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		ReadDataBase.getComputer(c, id);
		do{
			System.out.println("Choose an option:\n\t1) Modify name\n\t2) Modify Manufacturer\n\t3) Modify introduction date\n\t4) Modify discontinution date\n\t5) Save changes\n\t6) Discard changes");
			userChoice = reader.readLine();
			switch(userChoice){
			case "1": //Change name
				System.out.println("Current name : "+c.getName()+"\n\tEnter new name !");
				c.setName(reader.readLine());
				break;
			case "2": //Change manufacturer
				System.out.println("Current manufacturer : "+c.getManufacturerID()+"\n\tChoose new manufacturer from the list: ");
				ReadDataBase.listCompanies();
				c.setManufacturerID(Integer.parseInt(reader.readLine()));
				break;
			case "3": //Change introduction date
				System.out.println("Current introdution date : "+c.getIntroductionDate()+"\n\tEnter new date ! [dd/mm/yyyy] t) for today's date");
				userChoice = reader.readLine();
				if(userChoice.equals("t")){
					c.setIntroductionDate(new Date());
				}else{
					c.setIntroductionDate(df.parse(userChoice));
				}
				break;
			case "4": //Change discontinution date
				System.out.println("Current discontinution date : "+c.getDiscontinutionDate()+"\n\tEnter new date ! [dd/mm/yyyy] t) for today's date");
				userChoice = reader.readLine();
				if(userChoice.equals("t")){
					c.setDiscontinutionDate(new Date());
				}else{
					c.setDiscontinutionDate(df.parse(userChoice));
				}
				break;
			case "5": //Save
				write(c,"update");
				System.out.println("Computer updated : "+c);
				break;
			case "6": //Discard
				System.out.println("Changes discarded.");
				break;
			default:
				System.out.println("try again, stupid");
				break;
			}
		}while((!userChoice.equals("6")) && (!userChoice.equals("5")));
	}
	public static void deleteComputer(int id){
		Computer c = new Computer();
		c.setID(id);
		write(c,"delete");
	}
	
	public static void write(Computer c, String action){
		int manufID = c.getManufacturerID();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//elements to access the db
		String url = "jdbc:mysql://localhost/computer-database-db";
		String login = "admincdb";
		String password = "qwerty1234";
		Connection cn = null;
		Statement st = null;
		String sql="";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(url, login, password);
			st = cn.createStatement();
			
			switch(action){
			
			case "create":	
				//adding new computer to computer db
				String dIS,dDS;
				//SQL request syntax --'
				Date dI=c.getIntroductionDate(); if(dI==null){dIS=null;} else {dIS="'"+df.format(dI)+"'";}
				Date dD=c.getDiscontinutionDate();if(dD==null){dDS=null;} else {dDS="'"+df.format(dD)+"'";}
				
				sql = ("INSERT INTO computer VALUES ("+(ReadDataBase.lastComputerId()+1)+",'"+c.getName()+"',"+dIS+","+dDS+","+((manufID==0)?null:manufID)+");");
				//System.out.println(sql);
				st.executeUpdate(sql);
				System.out.print("New Computer :"+c);
				break;
			case "update":
				//Update computer in computer db
				sql = ("UPDATE computer SET name=\""+c.getName()+"\", introduced=\""+df.format(c.getIntroductionDate())+"\", discontinued=\""+df.format(c.getDiscontinutionDate())+"\", company_id=\""+manufID+"\" WHERE id="+c.getID()+";");
				st.executeUpdate(sql);
				break;
				
			case "delete":
				//Update computer in computer db
				sql = ("DELETE FROM computer WHERE id="+c.getID()+";");
				st.executeUpdate(sql);
				break;
			
			default:
				System.out.println("Nah");
				break;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

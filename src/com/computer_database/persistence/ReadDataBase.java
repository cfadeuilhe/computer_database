package com.computer_database.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.computer_database.model.Company;
import com.computer_database.model.Computer;

public class ReadDataBase {
	
	public static void main(String[] args){
			
		System.out.println(lastComputerId()); //System.out.println(); listCompanies(); System.out.println(); 
		//showComputerDetails(12);
	}

	public static void listComputers(){
		List<Computer> list = new ArrayList<Computer>();
		read(list, null, "computer", 0);
		System.out.println("There are "+list.size()+" computers in the database :\n"+list.toString());
	}
	public static void listCompanies(){
		List<Company> list = new ArrayList<Company>();
		read(null, list, "company", 0);
		System.out.println("There are "+list.size()+" existing manufacturers :\n"+list.toString());
	}
	public static void showComputerDetails(int id){
		List<Computer> list = new ArrayList<Computer>();
		read(list, null, "computer", id);
		try{
		System.out.println(list.get(0));
		}catch(IndexOutOfBoundsException e){
			System.out.println("IndexOutOfBoundsException ID doesn't exist.");
		}
	}
	
	public static void getComputer(Computer c, int id){
		List<Computer> list = new ArrayList<Computer>();
		read(list, null, "computer", id);
		c.setComputer(list.get(0).getID(), list.get(0).getName(), list.get(0).getManufacturerID(), list.get(0).getIntroductionDate(), list.get(0).getDiscontinutionDate());
	}
	public static int lastComputerId(){
		List<Computer> list = new ArrayList<Computer>();
		read(list, null, "computer", 0);
		return list.get(list.size()-1).getID();
	}

	public static int numberOfComputers(){
		List<Computer> list = new ArrayList<Computer>();
		read(list, null, "computer", 0);
		return list.size();
	}
	public static int numberOfCompanies(){
		List<Company> list = new ArrayList<Company>();
		read(null, list, "company", 0);
		return list.size();
	}
	
	public static String getManufacturer(int id){
		String url = "jdbc:mysql://localhost/computer-database-db";
		String login = "admincdb";
		String password = "qwerty1234";
		Connection cn = null; 
		Statement st = null; 
		ResultSet rs = null;
		String manuf=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(url, login, password);
			st = cn.createStatement();
			String sqlManu = "SELECT * FROM company WHERE id="+id;
			Statement stB = null; ResultSet rsB = null;
			//stB = cn.createStatement(); 
			rs = st.executeQuery(sqlManu);
			rs.next();
			manuf = rs.getString("name");
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return manuf;
	}
	
	public static void read(List<Computer> list, List<Company> listB, String tableToRead, int id){
		
		String url = "jdbc:mysql://localhost/computer-database-db";
		String login = "admincdb";
		String password = "qwerty1234";
		Connection cn = null; 
		Statement st = null; 
		ResultSet rs = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			cn = DriverManager.getConnection(url, login, password);
			st = cn.createStatement();
			String sql;
			if(id!=0){
				sql = "SELECT * FROM "+tableToRead+" WHERE id="+id;
			}else{
				sql = "SELECT * FROM "+tableToRead;
			}
			rs = st.executeQuery(sql);
			while(rs.next()){
				switch(tableToRead){
				
				case "computer":
					String manuf=null;
					if(rs.getString("company_id") != null){
						String sqlManu = "SELECT * FROM company WHERE id="+(rs.getInt("company_id"));
						Statement stB = null; ResultSet rsB = null;
						stB = cn.createStatement(); rsB = stB.executeQuery(sqlManu);
						rsB.next();
						manuf = rsB.getString("name");
					}
					list.add(new Computer(rs.getInt("id"),rs.getString("name"),rs.getInt("company_id"),manuf,rs.getDate("introduced"),rs.getDate("discontinued")));
					break;
					
				case "company":
					listB.add(new Company(rs.getInt("id"),rs.getString("name")));
					break;
					
				default :
					
					break;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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

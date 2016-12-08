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
			
		listComputers(); System.out.println(); listCompanies();
	}

	public static void listComputers(){
		List<Computer> list = new ArrayList<Computer>();
		read(list,null,"computer");
		System.out.println("There are "+list.size()+" computers in the database :\n"+list.toString());
	}
	public static void listCompanies(){
		List<Company> list = new ArrayList<Company>();
		read(null,list,"company");
		System.out.println("There are "+list.size()+" existing manufacturers :\n"+list.toString());
	}
	
	public static void read(List<Computer> list, List<Company> listB, String tableToRead){
		
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
			String sql = "SELECT * FROM "+tableToRead;
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
					list.add(new Computer(rs.getString("name"),manuf,rs.getDate("introduced"),rs.getDate("discontinued")));
					break;
				case "company":
					listB.add(new Company(rs.getString("name")));
					break;
				default :
					
					break;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

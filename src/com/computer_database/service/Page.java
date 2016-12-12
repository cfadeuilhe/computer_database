package com.computer_database.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.computer_database.model.Computer;
public class Page{
	
	public static void main(String[] args) throws IOException{
		listXElements("computer", 3, 1);
	}
	
	public static void listXElements(String tableToRead, int nbElementsPerPage, int pageToDisplay) throws IOException{
		pageToDisplay--;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		List<Computer> list = new ArrayList<Computer>();
		read(list, tableToRead, nbElementsPerPage, pageToDisplay);
		System.out.println(list);
		
		String choice;
		do{
		System.out.println("Type p-previous page, n- next page, o-other page, c- change nb of elements, q- quit");
		choice = reader.readLine();
			switch(choice){
			case "p":
				pageToDisplay--;
				if(pageToDisplay >= 0){
					list.clear();
					read(list, tableToRead, nbElementsPerPage, pageToDisplay);
					System.out.println(list);
				}else{System.out.println("Cannot display negative pages bodoh"); pageToDisplay=-1;}
				break;
			case "n":
				pageToDisplay++;
				list.clear();
				read(list, tableToRead, nbElementsPerPage, pageToDisplay);
				System.out.println(list);
				break;
			case "o":
				list.clear();
				System.out.println("Enter the number of the page to display");
				pageToDisplay = Integer.parseInt(reader.readLine())-1;
				read(list, tableToRead, nbElementsPerPage, pageToDisplay);
				System.out.println(list);
				break;
			case "c":
				list.clear();
				System.out.println("Enter the number of elements per page wanted");
				nbElementsPerPage = Integer.parseInt(reader.readLine());
				System.out.println("Enter the number of the page to display");
				pageToDisplay = Integer.parseInt(reader.readLine())-1;
				read(list, tableToRead, nbElementsPerPage, pageToDisplay);
				System.out.println(list);
				break;
			case "q":
				System.out.println("See u soon");
				break;
			default:
				System.out.println("wrong character");
				break;
			}
		}while(!choice.equals("q"));
	}
	
	
	public static void read(List<Computer> list, String tableToRead, int nbElementsPerPage, int pageToDisplay){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

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
			sql = "SELECT * FROM "+tableToRead+" LIMIT "+nbElementsPerPage+" OFFSET "+(pageToDisplay*nbElementsPerPage)+";";
			rs = st.executeQuery(sql);
			while(rs.next()){
					String manuf=null;
					if(rs.getString("company_id") != null){
						String sqlManu = "SELECT * FROM company WHERE id="+(rs.getInt("company_id"));
						Statement stB = null; ResultSet rsB = null;
						stB = cn.createStatement(); rsB = stB.executeQuery(sqlManu);
						rsB.next();
						manuf = rsB.getString("name");
					}
					list.add(new Computer(rs.getInt("id"),rs.getString("name"),rs.getInt("company_id"),manuf,rs.getDate("introduced"),rs.getDate("discontinued")));
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

package com.computer_database.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ConnectionDAO : management of the database connection
 * 
 * @author juanita
 *
 */
public class ConnectionDAO {

	private final static String URL = "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private final static String LOGIN = "admincdb";
	private final static String PASSWORD = "qwerty1234";
	private Connection cn = null;
	private Statement st = null;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// Exit program because driver not found
			System.out.println("Driver not found");
			throw new RuntimeException(e);

		}
	}

	public Statement getConnection() {
		try {
			cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			st = cn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}

	public void closeConnection() {
		try {
			st.close();
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

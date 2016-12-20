package main.java.com.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnectionDAO : management of the database connection
 * 
 * @author juanita
 *
 */
public class ConnectionDao {

	private final static String URL = "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private final static String LOGIN = "admincdb";
	private final static String PASSWORD = "qwerty1234";
	private Connection cn = null;

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

	/**
	 * getConnection (to sql database)
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		try {
			cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection failed");
		}
		return cn;
	}

	/**
	 * closeConnection
	 */
	public void closeConnection() {
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

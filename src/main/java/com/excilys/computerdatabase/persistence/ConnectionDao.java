package com.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * ConnectionDAO : management of the database connection
 * 
 * @author juanita
 *
 */
public enum ConnectionDao {

    INSTANCE;

    // private final static String URL =
    // "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";
    // private final static String LOGIN = "admincdb";
    // private final static String PASSWORD = "qwerty1234";
    private HikariDataSource ds = new HikariDataSource();
    /*
     * private HikariConfig config = new
     * HikariConfig("/credentials.properties"); private HikariDataSource ds =
     * new HikariDataSource(config);
     */
    private Connection cn = null;

    /*
     * static { try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch
     * (ClassNotFoundException e) { e.printStackTrace(); // Exit program because
     * driver not found System.out.println("Driver not found"); throw new
     * RuntimeException(e);
     * 
     * } }
     */
    /**
     * getConnection (to sql database)
     * 
     * @return Connection
     */
    public Connection getConnection() {
        try {
            ds.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
            ds.setJdbcUrl("jdbc:mysql://localhost/computer-database-db");
            ds.setUsername("admincdb");
            ds.setPassword("qwerty1234");
            ds.setMaximumPoolSize(20);
            ds.addDataSourceProperty("databaseName", "computer-database-db");
            cn = ds.getConnection();

            // DriverManager.getConnection(URL, LOGIN, PASSWORD);
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

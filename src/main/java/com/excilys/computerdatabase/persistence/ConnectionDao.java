package com.excilys.computerdatabase.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final static Logger logger = LoggerFactory.getLogger(ConnectionDao.class);
    private static final String PROPERTIES_FILE = "credentials.properties";
    HikariDataSource dataSource;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Exit program because
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
        HikariConfig config;
        Connection cn = null;
        try {
            config = new HikariConfig();
            
            Properties properties = new Properties();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
            logger.info(propertiesFile + " ");
            try {
                properties.load(propertiesFile);
            } catch (IOException e) {
                logger.error( "ConnectionDao : getConnection() catched FileNotFoundException",e);
            }
            
            
            config.setJdbcUrl(properties.getProperty("jdbcURL"));
            config.setUsername(properties.getProperty("user"));
            config.setPassword(properties.getProperty("password"));
            
            dataSource = new HikariDataSource(config);
            
            cn = dataSource.getConnection();
            
        } catch (SQLException e) {
            logger.error("${enclosing_type} : ${enclosing_method}() catched ${exception_type}", e);
        }
        return cn;
    }

    public Connection initTransaction() {
        Connection connection = this.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("${enclosing_type} : ${enclosing_method}() catched ${exception_type}", e);
        }
        return connection;
    }

    public void commitTransaction(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("${enclosing_type} : ${enclosing_method}() catched ${exception_type}", e);
        }
    }

    public void rollbackConnection(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("${enclosing_type} : ${enclosing_method}() catched ${exception_type}", e);
        }
    }

    /**
     * closeConnection
     */
    public void closeConnection() {
        dataSource.close();
    }
}

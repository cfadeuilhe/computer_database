package com.excilys.computerdatabase.persistence;

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
    private static HikariDataSource dataSource;

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            HikariConfig config;
            config = new HikariConfig();

            Properties properties = new Properties();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);
            logger.info(propertiesFile + " ");
            try {
                properties.load(propertiesFile);
            } catch (IOException e) {
                logger.error("ConnectionDao : getConnection() catched FileNotFoundException", e);
                throw new RuntimeException(e);
            }

            config.setJdbcUrl(properties.getProperty("jdbcURL"));
            config.setUsername(properties.getProperty("user"));
            config.setPassword(properties.getProperty("password"));
            
            dataSource = new HikariDataSource(config);

        } catch (ClassNotFoundException e) {
            logger.error("ConnectionDao : () catched ClassNotFoundException", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * getConnection (to sql database)
     * 
     * @return Connection
     * @throws SQLException
     */
    public Connection getConnection() {
        return threadLocal.get();
    }

    public void closeConnection() {
        try {
            threadLocal.get().close();
        } catch (SQLException e) {
            logger.error( "ConnectionDao : closeConnection() catched SQLException",e);
        }
        threadLocal.remove();
    }

    public void initConnection() {
        try {
            threadLocal.set(dataSource.getConnection());
        } catch (SQLException e) {
            logger.error("Error initConnection", e);
        }
    }

    public void initTransaction() {
        try {
            // threadLocal.set(this.getConnection());
            threadLocal.get().setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("HikariConnectionProvider : beginTransaction() catched SQLException ", e);
        }
        // return threadLocal.get();
    } 

    public void commitTransaction() {
        try {
            threadLocal.get().commit();
            threadLocal.get().setAutoCommit(true);
            threadLocal.remove();
        } catch (SQLException e) {
            rollbackTransaction();
            logger.error("ConnectionDao : commitTransaction() catched SQLException", e);
        }
    }

    public void rollbackTransaction() {
        try {
            threadLocal.get().rollback();
            threadLocal.remove();
        } catch (SQLException e) {
            logger.error("ConnectionDao : rollbackTransaction() catched SQLException", e);
        }
    }
}

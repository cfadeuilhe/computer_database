package com.excilys.computerdatabase.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

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

public class ConnectionDao {
    
    private DataSource dataSource;
    
    private final static ConnectionDao CONNECTION_DAO_INSTANCE;
    private final static Logger logger = LoggerFactory.getLogger(ConnectionDao.class);
    private static final String PROPERTIES_FILE = "credentials.properties";
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

            HikariDataSource source = new HikariDataSource(config);
            CONNECTION_DAO_INSTANCE = new ConnectionDao(source);

        } catch (ClassNotFoundException e) {
            logger.error("ConnectionDao : () catched ClassNotFoundException", e);
            throw new RuntimeException(e);
        }
    }

    public ConnectionDao(DataSource ds) {
        this.dataSource = ds;
    }

    public static ConnectionDao getInstance() {
        return CONNECTION_DAO_INSTANCE;
    }

    /**
     * getConnection (to sql database)
     * 
     * @return Connection
     * @throws SQLException
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error( "ConnectionDao : getConnection() catched SQLException",e);
        }
        return null;
    }

    public void closeConnection() {
        try {
            threadLocal.get().close();
        } catch (SQLException e) {
            logger.error("ConnectionDao : closeConnection() catched SQLException", e);
        }
        threadLocal.remove();
    }

    public void initConnection() {
        try {
            dataSource.getConnection().close();
        } catch (SQLException e) {
            logger.error("Error initConnection", e);
        }
    }

    public void initTransaction() {
        try {
            threadLocal.get().setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("HikariConnectionProvider : beginTransaction() catched SQLException ", e);
        }
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

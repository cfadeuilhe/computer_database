package com.excilys.computerdatabase.persistence;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.mapper.RsMapper;
import com.excilys.computerdatabase.model.*;
import com.excilys.computerdatabase.util.Consts;

/**
 * Class CompanyDAO
 * 
 * @author juanita
 *
 */
@Repository
public class CompanyDao implements InterfaceDao {

    @Autowired
    private DataSource dataSource;
    
    private final static CompanyDao COMPANY_DAO_INSTANCE;
    private final static Logger logger;
    private final static RsMapper RS_TO_COMPUTER = new RsMapper();
    private final static String SQL_READ = "SELECT * FROM company";
    private final static String SQL_READ_PAGES = "SELECT * FROM company LIMIT ? OFFSET ?";
    private final static String SQL_READ_ONE = "SELECT * FROM company WHERE id=?";
    private final static String SQL_CREATE = "INSERT INTO company (name) VALUES ('?')";
    private final static String SQL_DELETE = "DELETE FROM company WHERE id=?";

    static {
        COMPANY_DAO_INSTANCE = new CompanyDao();
        logger = LoggerFactory.getLogger(CompanyDao.class);
    }

    public static CompanyDao getInstance() {
        return COMPANY_DAO_INSTANCE;
    }

    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }
    /**
     * read - get all Company from database
     * 
     * @return List<Company>
     */
    public List<Entity> read() {
        List<Entity> companyList = new ArrayList<Entity>();
        try (Connection cn = dataSource.getConnection(); PreparedStatement st = cn.prepareStatement(SQL_READ); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                companyList.add(new Company(rs.getInt(Consts.ID), rs.getString(Consts.NAME)));
            }
        } catch (SQLException e) {
            logger.error("Cannot read company list. ", e);
        }
        return companyList;
    }

    /**
     * readPages - sort by pages and return a specific page
     * 
     * @param Page
     * @return List<Company>
     */
    public List<Entity> readPages(Page p) {
        List<Entity> companyList = new ArrayList<Entity>();
        try (Connection cn = dataSource.getConnection(); PreparedStatement st = cn.prepareStatement(SQL_READ_PAGES)) {
            st.setLong(1, p.getPageSize());
            st.setLong(2, p.getOffset());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                companyList.add(RS_TO_COMPUTER.rsToCompany(rs));
            }
        } catch (SQLException e) {
            logger.error("Cannot read a specific page of companies. ", e);
        }
        return companyList;
    }

    /**
     * readOne - get a specific Company from database
     * 
     * @param id
     * @return Company
     */
    public Entity readOne(long id) {
        Company company = null;
        try (Connection cn = dataSource.getConnection(); PreparedStatement st = cn.prepareStatement(SQL_READ_ONE)) {
            if (id != 0)
                st.setLong(1, id);
            else
                st.setString(1, null);
            ResultSet rs = st.executeQuery();
            rs.next();
            company = new Company(rs.getInt(Consts.ID), rs.getString(Consts.NAME));
        } catch (SQLException e) {
            logger.error("Cannot read company with ID ", id, e);
        }
        return company;
    }

    /**
     * create - new Company in database
     * 
     * @param Company
     * @return 
     */
    public int create(Entity entity) {
        int newId = -1;
        Company c = (Company) entity;
        try (Connection cn = dataSource.getConnection(); PreparedStatement st = cn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, c.getName());
            st.executeUpdate();ResultSet resultSet = st.getGeneratedKeys();

            if (resultSet.next()) {
                newId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Cannot create new company ", e);
        }
        return newId;
    }

    public void delete(long id) throws PersistenceException {
        try (Connection cn = dataSource.getConnection(); PreparedStatement stCompany = cn.prepareStatement(SQL_DELETE)) {
            stCompany.setLong(1, id);
            stCompany.executeUpdate();
        } catch (SQLException e) {
            logger.error("Cannot delete company with ID ", id, e);
            throw new PersistenceException(e);
        }
    }
}

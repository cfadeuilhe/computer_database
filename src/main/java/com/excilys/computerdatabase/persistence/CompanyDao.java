package com.excilys.computerdatabase.persistence;

import java.util.*;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.mapper.JdbcCompanyMapper;
import com.excilys.computerdatabase.model.*;

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

    private JdbcTemplate jdbcTemplate;

    private final static Logger logger = LoggerFactory.getLogger(CompanyDao.class);

    private final static String SQL_READ = "SELECT * FROM company";
    private final static String SQL_READ_PAGES = "SELECT * FROM company LIMIT ? OFFSET ?";
    private final static String SQL_READ_ONE = "SELECT * FROM company WHERE id=?";
    private final static String SQL_CREATE = "INSERT INTO company (name) VALUES ('?')";
    private final static String SQL_DELETE = "DELETE FROM company WHERE id=?";

    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
    }

    /**
     * read - get all Company from database
     * 
     * @return List<Company>
     */
    public List<Entity> read() {

        jdbcTemplate = new JdbcTemplate(dataSource);
        return this.jdbcTemplate.query(SQL_READ, new JdbcCompanyMapper());
    }

    /**
     * readPages - sort by pages and return a specific page
     * 
     * @param Page
     * @return List<Company>
     */
    public List<Entity> readPages(Page p) {

        jdbcTemplate = new JdbcTemplate(dataSource);
        return this.jdbcTemplate.query(SQL_READ_PAGES, new Object[] { p.getPageSize(), p.getOffset() },
                new JdbcCompanyMapper());
    }

    /**
     * readOne - get a specific Company from database
     * 
     * @param id
     * @return Company
     */
    public Entity readOne(long id) {

        jdbcTemplate = new JdbcTemplate(dataSource);
        return this.jdbcTemplate.queryForObject(SQL_READ_ONE, new Object[] { (id != 0 ? id : null) },
                new JdbcCompanyMapper());
    }

    /**
     * create - new Company in database
     * 
     * @param Company
     * @return
     *//*
       * public int create(Entity entity) { int newId = -1; Company c =
       * (Company) entity; try (Connection cn =
       * DataSourceUtils.getConnection(dataSource); PreparedStatement st =
       * cn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
       * st.setString(1, c.getName()); st.executeUpdate();ResultSet resultSet =
       * st.getGeneratedKeys();
       * 
       * if (resultSet.next()) { newId = resultSet.getInt(1); } } catch
       * (SQLException e) { logger.error("Cannot create new company ", e); }
       * return newId; }
       */

    /**
     * delete - delete a Company
     * 
     * @param cn
     *            - Connection to use
     * @param id
     *            - id of the Company to delete
     */
    public void delete(long id) throws PersistenceException {

        jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            jdbcTemplate.update(SQL_DELETE, id);
        } catch (DataAccessException e) {
            throw new PersistenceException("te");
        }
    }
}

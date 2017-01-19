package com.excilys.computerdatabase.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.ComputerDao;

/**
 * class CompanyService
 * 
 * @author juanita
 *
 */
@Service
public class CompanyService {

    private final static Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private ComputerDao computerDao;
    @Autowired
    private DataSource dataSource;

    public CompanyService() {
        this.companyDao = CompanyDao.getInstance();
    }

    public void setCompanyDao(CompanyDao cd) {
        this.companyDao = cd;
    }

    public void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    public List<Entity> listEntities() {
        List<Entity> list = new ArrayList<Entity>();
        list = companyDao.read();
        return list;
    }

    public Entity readOne(long id) {
        Company c = (Company) companyDao.readOne(id);
        return c;
    }

    /**
     * delete a Company and all related Computers. rollback if there is any problem in the transaction
     * 
     * @param id - id of the Company to delete
     * @throws SQLException
     */
    public void delete(long id) throws SQLException {
        Connection cn = DataSourceUtils.getConnection(dataSource);
        try {
            computerDao.deleteByCompany(cn, id);
            companyDao.delete(cn, id);
        } catch (PersistenceException e) {
            cn.rollback();
            logger.error("delete() catched PersistenceException", e);
        }
    }
}

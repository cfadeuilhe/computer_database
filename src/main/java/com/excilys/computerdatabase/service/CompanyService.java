package com.excilys.computerdatabase.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.ConnectionDao;

/**
 * class CompanyService
 * 
 * @author juanita
 *
 */
public class CompanyService {

    private final static Logger logger = LoggerFactory.getLogger(CompanyService.class);

    private static CompanyService INSTANCE = new CompanyService(CompanyDao.INSTANCE);
	private static CompanyDao COMPANY_DAO = CompanyDao.INSTANCE;
    private static ComputerDao COMPUTER_DAO = ComputerDao.INSTANCE;

    private CompanyService(CompanyDao companyDao) {
        this.COMPANY_DAO = companyDao;
    }

    private CompanyService() {

    }

    public static CompanyService getInstance() {
        return INSTANCE;
    }
    
	public List<Entity> listCompanies() {
		List<Entity> list = new ArrayList<Entity>();
		list = COMPANY_DAO.read();
		return list;
	}

	public Company readOne(long id) {
		Company c = (Company) COMPANY_DAO.readOne(id);
		return c;
	}

    public void delete(long id) {
        Connection connection = ConnectionDao.INSTANCE.initTransaction();
        try {
            COMPUTER_DAO.deleteByCompany(id,connection);
            COMPANY_DAO.delete(id,connection);
            ConnectionDao.INSTANCE.commitTransaction();
        } catch (PersistenceException e) {
            logger.error("delete() catched PersistenceException", e);
            ConnectionDao.INSTANCE.rollbackTransaction();
        }
        
    }
}

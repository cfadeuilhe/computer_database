package com.excilys.computerdatabase.service;

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

	private CompanyDao companyDao;
    private ComputerDao computerDao;

    private CompanyService() {
        this.companyDao = CompanyDao.getInstance();
    }    

    public void setCompanyDao(CompanyDao cd) {
        this.companyDao = cd;
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

    public void delete(long id) {
        ConnectionDao.getInstance().initConnection();
        ConnectionDao.getInstance().initTransaction();
        try {
            computerDao.deleteByCompany(id);
            companyDao.delete(id);
            ConnectionDao.getInstance().commitTransaction();
        } catch (PersistenceException e) {
            logger.error("delete() catched PersistenceException", e);
            ConnectionDao.getInstance().rollbackTransaction();
        }

    }
}

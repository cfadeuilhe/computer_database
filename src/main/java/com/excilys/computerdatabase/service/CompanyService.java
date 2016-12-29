package com.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;

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
public class CompanyService {

    private static CompanyService INSTANCE = new CompanyService(CompanyDao.INSTANCE);
	private static CompanyDao COMPANY_DAO = CompanyDao.INSTANCE;

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
        COMPANY_DAO.delete(id);
    }
}

package com.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.persistence.CompanyDao;

/**
 * class CompanyService
 * 
 * @author juanita
 *
 */
public class CompanyService {

	private final static CompanyDao COMPANY_DAO = new CompanyDao();

	public List<Entity> listCompanies() {
		List<Entity> list = new ArrayList<Entity>();
		list = COMPANY_DAO.read();
		return list;
	}

	public Company readOne(long id) {
		Company c = (Company) COMPANY_DAO.readOne(id);
		return c;
	}
}

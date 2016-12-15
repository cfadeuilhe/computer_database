package com.computer_database.service;

import java.util.ArrayList;
import java.util.List;

import com.computer_database.model.Company;
import com.computer_database.model.Entity;
import com.computer_database.persistence.CompanyDAO;

/**
 * class CompanuService
 * 
 * @author juanita
 *
 */
public class CompanyService {

	private final static CompanyDAO COMPANY_DAO = new CompanyDAO();

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

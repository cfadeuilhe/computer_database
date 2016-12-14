package com.computer_database.service;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.computer_database.model.Company;
import com.computer_database.model.Entity;
import com.computer_database.persistence.CompanyDAO;
import com.computer_database.persistence.ConnectionDAO;

public class CompanyService {

	CompanyDAO companyDAO = new CompanyDAO();
	ConnectionDAO connection = new ConnectionDAO();

	public List<Entity> listCompanies() {
		List<Entity> list = new ArrayList<Entity>();
		Statement st = null;
		st = connection.getConnection();
		list = companyDAO.read(st);
		connection.closeConnection();
		return list;
	}

	public Company readOne(long id) {
		Statement st = null;
		st = connection.getConnection();
		Company c = (Company) companyDAO.readOne(st, id);
		connection.closeConnection();
		return c;
	}
}

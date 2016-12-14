package com.computer_database.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.computer_database.mapper.RsToObjectMapper;
import com.computer_database.model.*;

public class CompanyDAO implements InterfaceDAO {

	RsToObjectMapper rsToC = new RsToObjectMapper();

	public List<Entity> read(Statement st) {
		String sql = "SELECT * FROM company;";
		List<Entity> companyList = new ArrayList<Entity>();
		try {
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				companyList.add(new Company(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companyList;
	}

	public List<Entity> readPages(Statement st, Page p) {
		String sql = "SELECT * FROM company LIMIT " + p.getNbElementsPerPage() + " OFFSET "
				+ (p.getPageNumber() * p.getNbElementsPerPage()) + ";";
		List<Entity> companyList = new ArrayList<Entity>();
		try {
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				companyList.add(rsToC.rsToCompany(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companyList;
	}

	public Entity readOne(Statement st, long id) {
		String sql = "SELECT * FROM company WHERE id=" + id + ";";
		Company company = null;
		try {
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			company = new Company(rs.getInt("id"), rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}

	public void create(Statement st, Entity entity) {
		Company c = (Company) entity;
		String sql = ("INSERT INTO company (name) VALUES ('" + c.getName() + "';");
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Statement st, long id, Entity entity) {

	}

	public void delete(Statement st, long id) {

	}
}

package com.computer_database.persistence;

import java.sql.*;
import java.util.*;

import com.computer_database.mapper.RsToObjectMapper;
import com.computer_database.model.*;

public class CompanyDAO implements InterfaceDAO {

	private final static RsToObjectMapper RS_TO_COMPUTER = new RsToObjectMapper();
	private final static String SQL_READ = "SELECT * FROM company;";
	private final static ConnectionDAO CONNECTION = new ConnectionDAO();

	public List<Entity> read() {
		Statement st = CONNECTION.getConnection();
		List<Entity> companyList = new ArrayList<Entity>();
		try (ResultSet rs = st.executeQuery(SQL_READ)) {
			while (rs.next()) {
				companyList.add(new Company(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in sql :" + SQL_READ);
		}
		CONNECTION.closeConnection();
		return companyList;
	}

	public List<Entity> readPages(Page p) {
		Statement st = CONNECTION.getConnection();
		String sql = "SELECT * FROM company LIMIT " + p.getNbElementsPerPage() + " OFFSET "
				+ (p.getPageNumber() * p.getNbElementsPerPage()) + ";";
		List<Entity> companyList = new ArrayList<Entity>();
		try (ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				companyList.add(RS_TO_COMPUTER.rsToCompany(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in sql :" + sql);
		}
		CONNECTION.closeConnection();
		return companyList;
	}

	public Entity readOne(long id) {
		Statement st = CONNECTION.getConnection();
		String sql = "SELECT * FROM company WHERE id=" + id + ";";
		Company company = null;
		try (ResultSet rs = st.executeQuery(sql)) {
			rs.next();
			company = new Company(rs.getInt("id"), rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in sql :" + sql);
		}
		CONNECTION.closeConnection();
		return company;
	}

	public void create(Entity entity) {
		Statement st = CONNECTION.getConnection();
		Company c = (Company) entity;
		String sql = ("INSERT INTO company (name) VALUES ('" + c.getName() + "';");
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in sql :" + sql);
		}
		CONNECTION.closeConnection();
	}
}

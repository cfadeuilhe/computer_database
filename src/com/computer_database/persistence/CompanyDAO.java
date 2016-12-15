package com.computer_database.persistence;

import java.sql.*;
import java.util.*;

import com.computer_database.mapper.RsToObjectMapper;
import com.computer_database.model.*;

/**
 * Class CompanyDAO
 * 
 * @author juanita
 *
 */
public class CompanyDAO implements InterfaceDAO {

	private final static ConnectionDAO CONNECTION_FACTORY = new ConnectionDAO();
	private final static RsToObjectMapper RS_TO_COMPUTER = new RsToObjectMapper();
	private final static String SQL_READ = "SELECT * FROM company;";
	private final static String SQL_READ_PAGES = "SELECT * FROM company LIMIT ? OFFSET ?;";
	private final static String SQL_READ_ONE = "SELECT * FROM company WHERE id=?;";
	private final static String SQL_CREATE = "INSERT INTO company (name) VALUES ('?');";

	/**
	 * read - get all Company from database
	 * 
	 * @return List<Company>
	 */
	public List<Entity> read() {
		Connection cn = CONNECTION_FACTORY.getConnection();
		List<Entity> companyList = new ArrayList<Entity>();
		try (PreparedStatement st = cn.prepareStatement(SQL_READ); ResultSet rs = st.executeQuery();) {
			while (rs.next()) {
				companyList.add(new Company(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("mySQL error : " + SQL_READ);
		}
		CONNECTION_FACTORY.closeConnection();
		return companyList;
	}

	/**
	 * readPages - sort by pages and return a specific page
	 * 
	 * @param Page
	 * @return List<Company>
	 */
	public List<Entity> readPages(Page p) {
		Connection cn = CONNECTION_FACTORY.getConnection();
		List<Entity> companyList = new ArrayList<Entity>();
		try (PreparedStatement st = cn.prepareStatement(SQL_READ_PAGES);) {
			st.setLong(1, p.getNbElementsPerPage());
			st.setLong(2, p.getOffset());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				companyList.add(RS_TO_COMPUTER.rsToCompany(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("mySQL error : " + SQL_READ_PAGES);
		}
		CONNECTION_FACTORY.closeConnection();
		return companyList;
	}

	/**
	 * readOne - get a specific Company from database
	 * 
	 * @param id
	 * @return Company
	 */
	public Entity readOne(long id) {
		Connection cn = CONNECTION_FACTORY.getConnection();
		Company company = null;
		try (PreparedStatement st = cn.prepareStatement(SQL_READ_ONE);) {
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			rs.next();
			company = new Company(rs.getInt("id"), rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("mySQL error : " + SQL_READ_ONE);
		}
		CONNECTION_FACTORY.closeConnection();
		return company;
	}

	/**
	 * create - new Company in database
	 * 
	 * @param Company
	 */
	public void create(Entity entity) {
		Connection cn = CONNECTION_FACTORY.getConnection();
		Company c = (Company) entity;
		try (PreparedStatement st = cn.prepareStatement(SQL_CREATE);) {
			st.setString(1, c.getName());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("mySQL error : " + SQL_CREATE);
		}
		CONNECTION_FACTORY.closeConnection();
	}
}

package com.computer_database.persistence;

import java.sql.*;
import java.util.*;

import com.computer_database.mapper.RsToObjectMapper;
import com.computer_database.model.*;

/**
 * class ComputerDAO
 * 
 * @author juanita
 *
 */
public class ComputerDAO implements InterfaceDAO {

	private final static ConnectionDAO CONNECTION_FACTORY = new ConnectionDAO();
	private final static RsToObjectMapper RS_TO_COMPUTER = new RsToObjectMapper();
	private final static String SQL_READ = "SELECT * FROM computer;";
	private final static String SQL_READ_PAGES = "SELECT * FROM computer LIMIT ? OFFSET ?;";
	private final static String SQL_READ_ONE = "SELECT * FROM computer WHERE id=?;";
	private final static String SQL_CREATE = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('?','?','?',?);";
	private final static String SQL_UPDATE = ("UPDATE computer SET name=\"?\", introduced=?, discontinued=?, company_id=? WHERE id=?;");
	private final static String SQL_DELETE = ("DELETE FROM computer WHERE id=?;");

	/**
	 * read - get all Computer from database
	 * 
	 * @return List<Computer>
	 */
	public List<Entity> read() {
		Connection cn = CONNECTION_FACTORY.getConnection();
		List<Entity> computerList = new ArrayList<Entity>();
		try (PreparedStatement st = cn.prepareStatement(SQL_READ);) {
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				computerList.add(RS_TO_COMPUTER.rsToComputer(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("mySQL error : " + SQL_READ);
		}
		CONNECTION_FACTORY.closeConnection();
		return computerList;
	}

	/**
	 * readPages - sort by pages and return a specific page
	 * 
	 * @param Page
	 * @return List<Computer>
	 */
	public List<Entity> readPages(Page p) {
		Connection cn = CONNECTION_FACTORY.getConnection();
		List<Entity> computerList = new ArrayList<Entity>();
		if (p.getOffset() >= 0) {
			try (PreparedStatement st = cn.prepareStatement(SQL_READ_PAGES)) {
				st.setLong(1, p.getNbElementsPerPage());
				st.setLong(2, p.getOffset());
				ResultSet rs = st.executeQuery();
				while (rs.next()) {
					computerList.add(RS_TO_COMPUTER.rsToComputer(rs));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("error in sql :" + SQL_READ_PAGES);
			}
		}
		CONNECTION_FACTORY.closeConnection();
		return computerList;
	}

	/**
	 * readOne - get a specific Computer from database
	 * 
	 * @param id
	 * @return Computer
	 */
	public Entity readOne(long id) {
		Connection cn = CONNECTION_FACTORY.getConnection();
		Computer computer = null;
		try (PreparedStatement st = cn.prepareStatement(SQL_READ_ONE)) {
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next())
				computer = RS_TO_COMPUTER.rsToComputer(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in sql :" + SQL_READ_ONE);
		}
		CONNECTION_FACTORY.closeConnection();
		return computer;
	}

	/**
	 * create - new Computer in database
	 * 
	 * @param Computer
	 */
	public void create(Entity entity) {
		Connection cn = CONNECTION_FACTORY.getConnection();
		Computer computer = (Computer) entity;
		try (PreparedStatement st = cn.prepareStatement(SQL_CREATE)) {
			st.setString(1, computer.getName());
			st.setString(2, computer.getIntroducedDate().toString());
			st.setString(3, computer.getDiscontinuedDate().toString());
			st.setLong(4, computer.getCompany().getid());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in sql :" + SQL_CREATE);
		}
		CONNECTION_FACTORY.closeConnection();
	}

	/**
	 * update - update a certain Computer in database
	 * 
	 * @param id
	 * @param Computer
	 */
	public void update(long id, Entity entity) {
		Connection cn = CONNECTION_FACTORY.getConnection();
		Computer computer = (Computer) entity;
		System.out.println(SQL_UPDATE);
		try (PreparedStatement st = cn.prepareStatement(SQL_UPDATE)) {
			st.setString(1, computer.getName());
			st.setString(2,
					(computer.getIntroducedDate() != null ? ("\"" + computer.getIntroducedDate() + "\"") : (null)));
			st.setString(3,
					(computer.getDiscontinuedDate() != null ? ("\"" + computer.getDiscontinuedDate() + "\"") : (null)));
			st.setLong(4, computer.getCompany().getid());
			st.setLong(5, id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in sql :" + SQL_UPDATE);
		}
		CONNECTION_FACTORY.closeConnection();
	}

	/**
	 * delete - delete a certain Computer from database
	 * 
	 * @param id
	 */
	public void delete(long id) {
		Connection cn = CONNECTION_FACTORY.getConnection();
		try (PreparedStatement st = cn.prepareStatement(SQL_DELETE)) {
			st.setLong(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in sql :" + SQL_DELETE);
		}
		CONNECTION_FACTORY.closeConnection();
	}
}

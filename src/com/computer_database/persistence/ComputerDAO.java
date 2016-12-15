package com.computer_database.persistence;

import java.sql.*;
import java.util.*;

import com.computer_database.mapper.RsToObjectMapper;
import com.computer_database.model.*;

public class ComputerDAO implements InterfaceDAO {

	private final static RsToObjectMapper RS_TO_COMPUTER = new RsToObjectMapper();
	private final static String SQL_READ = "SELECT * FROM computer;";
	private final static ConnectionDAO CONNECTION = new ConnectionDAO();

	/**
	 * read database
	 * 
	 * @return list of entities (computers)
	 */
	public List<Entity> read() {
		Statement st = CONNECTION.getConnection();
		List<Entity> computerList = new ArrayList<Entity>();
		try (ResultSet rs = st.executeQuery(SQL_READ)) {
			while (rs.next()) {
				computerList.add(RS_TO_COMPUTER.rsToComputer(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		CONNECTION.closeConnection();
		return computerList;
	}

	public List<Entity> readPages(Page p) {
		Statement st = CONNECTION.getConnection();
		List<Entity> computerList = new ArrayList<Entity>();
		if (p.getOffset() >= 0) {
			String sql = "SELECT * FROM computer LIMIT " + p.getNbElementsPerPage() + " OFFSET " + p.getOffset() + ";";
			try (ResultSet rs = st.executeQuery(sql)) {
				while (rs.next()) {
					computerList.add(RS_TO_COMPUTER.rsToComputer(rs));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("error in sql :" + sql);
			}
		}
		CONNECTION.closeConnection();
		return computerList;
	}

	public Computer readOne(long id) {
		Statement st = CONNECTION.getConnection();
		String sql = "SELECT * FROM computer WHERE id=" + id + ";";
		Computer computer = null;
		try (ResultSet rs = st.executeQuery(sql)) {
			if (rs.next())
				computer = RS_TO_COMPUTER.rsToComputer(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in sql :" + sql);
		}
		CONNECTION.closeConnection();
		return computer;
	}

	public void create(Entity entity) {
		Statement st = CONNECTION.getConnection();
		Computer computer = (Computer) entity;
		String sql = ("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('" + computer.getName()
				+ "','" + computer.getIntroducedDate().toString() + "','" + computer.getDiscontinuedDate().toString()
				+ "'," + computer.getCompany().getid() + ");");
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in sql :" + sql);
		}
		CONNECTION.closeConnection();
	}

	public void update(long id, Entity entity) {
		Statement st = CONNECTION.getConnection();
		Computer computer = (Computer) entity;
		String sql = ("UPDATE computer SET name=\"" + computer.getName() + "\", introduced="
				+ (computer.getIntroducedDate() != null ? ("\"" + computer.getIntroducedDate() + "\"") : (null))
				+ ", discontinued="
				+ (computer.getDiscontinuedDate() != null ? ("\"" + computer.getDiscontinuedDate() + "\"") : (null))
				+ ", company_id=" + computer.getCompany().getid() + " WHERE id=" + id + ";");
		System.out.println(sql);
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in sql :" + sql);
		}
		CONNECTION.closeConnection();
	}

	public void delete(long id) {
		Statement st = CONNECTION.getConnection();
		String sql = ("DELETE FROM computer WHERE id=" + id + ";");
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in sql :" + sql);
		}
		CONNECTION.closeConnection();
	}
}

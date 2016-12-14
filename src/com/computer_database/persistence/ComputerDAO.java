package com.computer_database.persistence;

import java.sql.*;
import java.util.*;

import com.computer_database.mapper.RsToObjectMapper;
import com.computer_database.model.Computer;
import com.computer_database.model.Entity;
import com.computer_database.model.Page;

public class ComputerDAO implements InterfaceDAO {

	RsToObjectMapper rsToC = new RsToObjectMapper();

	/**
	 * read database
	 * 
	 * @return list of entities (computers)
	 */
	public List<Entity> read(Statement st) {
		String sql = "SELECT * FROM computer;";
		List<Entity> computerList = new ArrayList<Entity>();
		try {
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				computerList.add(rsToC.rsToComputer(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computerList;
	}

	public List<Entity> readPages(Statement st, Page p) {
		List<Entity> computerList = new ArrayList<Entity>();
		if (p.getOffset() >= 0) {
			String sql = "SELECT * FROM computer LIMIT " + p.getNbElementsPerPage() + " OFFSET " + p.getOffset() + ";";
			try {
				ResultSet rs = st.executeQuery(sql);
				while (rs.next()) {
					computerList.add(rsToC.rsToComputer(rs));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("error in sql :" + sql);
			}
		}
		return computerList;
	}

	public Computer readOne(Statement st, long id) {
		String sql = "SELECT * FROM computer WHERE id=" + id + ";";
		Computer computer = null;
		try {
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			computer = rsToC.rsToComputer(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

	public void create(Statement st, Entity entity) {
		Computer computer = (Computer) entity;
		String sql = ("INSERT INTO computer (name,introduced,discontinued,company_id) VALUES ('" + computer.getName()
				+ "','" + computer.getIntroducedDate().toString() + "','" + computer.getDiscontinuedDate().toString()
				+ "'," + computer.getCompany().getid() + ");");
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Statement st, long id, Entity entity) {
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
		}
	}

	public void delete(Statement st, long id) {
		String sql = ("DELETE FROM computer WHERE id=" + id + ";");
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

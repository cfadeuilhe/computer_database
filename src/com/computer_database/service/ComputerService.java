package com.computer_database.service;

import java.sql.*;
import java.util.*;

import com.computer_database.model.Computer;
import com.computer_database.model.Entity;
import com.computer_database.model.Page;
import com.computer_database.persistence.ComputerDAO;
import com.computer_database.persistence.ConnectionDAO;

public class ComputerService {

	ComputerDAO computerDAO = new ComputerDAO();
	ConnectionDAO connection = new ConnectionDAO();

	public List<Entity> listComputers() {
		List<Entity> list = new ArrayList<Entity>();
		Statement st = null;
		st = connection.getConnection();
		list = computerDAO.read(st);
		connection.closeConnection();
		return list;
	}

	public List<Entity> readPages(Page p) {
		List<Entity> list = new ArrayList<Entity>();
		Statement st = null;
		st = connection.getConnection();
		list = computerDAO.readPages(st, p);
		connection.closeConnection();
		return list;
	}

	public Computer read(long id) {
		Statement st = null;
		st = connection.getConnection();
		Computer c = computerDAO.readOne(st, id);
		connection.closeConnection();
		return c;
	}

	public void create(Computer computer) {
		Statement st = null;
		st = connection.getConnection();
		computerDAO.create(st, computer);
		connection.closeConnection();
	}

	public void update(long computerId, Computer computer) {
		Statement st = null;
		st = connection.getConnection();
		Computer c = computerDAO.readOne(st, computerId);
		if (!c.equals(computer)) {
			if (!c.getName().equals(computer.getName()))
				c.setName(computer.getName());
			if (!c.getCompany().equals(computer.getCompany()))
				c.setCompany(computer.getCompany());
			if (c.getIntroducedDate() != null) {
				if (!c.getIntroducedDate().equals(computer.getIntroducedDate()))
					c.setIntroducedDate(computer.getIntroducedDate());
			}
			if (c.getDiscontinuedDate() != null) {
				if (!c.getDiscontinuedDate().equals(computer.getDiscontinuedDate()))
					c.setDiscontinuedDate(computer.getDiscontinuedDate());
			}
			computerDAO.update(st, computerId, c);
		}
		connection.closeConnection();
	}

	public void delete(long id) {
		Statement st = null;
		st = connection.getConnection();
		computerDAO.delete(st, id);
		connection.closeConnection();
	}
}

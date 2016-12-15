package com.computer_database.service;

import java.util.*;

import com.computer_database.model.Computer;
import com.computer_database.model.Entity;
import com.computer_database.model.Page;
import com.computer_database.persistence.ComputerDAO;

public class ComputerService {

	private final static ComputerDAO COMPUTER_DAO = new ComputerDAO();

	public List<Entity> listComputers() {
		List<Entity> list = new ArrayList<Entity>();
		list = COMPUTER_DAO.read();
		return list;
	}

	public List<Entity> readPages(Page p) {
		List<Entity> list = new ArrayList<Entity>();
		list = COMPUTER_DAO.readPages(p);
		return list;
	}

	public Computer read(long id) {
		Computer c = COMPUTER_DAO.readOne(id);
		return c;
	}

	public void create(Computer computer) {
		COMPUTER_DAO.create(computer);
	}

	public void update(long computerId, Computer computer) {
		Computer c = COMPUTER_DAO.readOne(computerId);
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
			COMPUTER_DAO.update(computerId, c);
		}
	}

	public void delete(long id) {
		COMPUTER_DAO.delete(id);
	}
}

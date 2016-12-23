package com.excilys.computerdatabase.service;

import java.util.*;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.DtoMapper;
import com.excilys.computerdatabase.mapper.RsMapper;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.persistence.ComputerDao;

/**
 * class ComputerService
 * 
 * @author juanita
 *
 */
public class ComputerService {

	private ComputerDao COMPUTER_DAO = new ComputerDao();
	private final RsMapper COMPUTER_MAPPER = new RsMapper();
	
    public ComputerService(ComputerDao computerDao) {
        this.COMPUTER_DAO = computerDao;
    }
	
	public ComputerService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * listComputers - call DAO to get the list of all Computer from database
	 * 
	 * @return List<Computer>
	 */
	public List<Entity> listComputers() {
		List<Entity> list = new ArrayList<Entity>();
		list = COMPUTER_DAO.read();
		return list;
	}

	/**
	 * readPages - call DAO to get a specific Page of Computer from database
	 * 
	 * @param Page
	 * @return List<Computer>
	 */
	public List<Entity> readPages(Page p) {
		List<Entity> list = new ArrayList<Entity>();
		list = COMPUTER_DAO.readPages(p);
		return list;
	}

	/**
	 * readOne - call DAO to get a specific Computer from database
	 * 
	 * @param id
	 * @return Computer
	 */
	public Computer readOne(long id) {
		Computer c = (Computer) COMPUTER_DAO.readOne(id);
		return c;
	}

	/**
	 * create - call DAO to create a new Computer in database
	 * 
	 * @param Computer
	 */
	public void create(ComputerDto computerDto) {
		Computer computer = DtoMapper.dtoToComputer(computerDto);
		COMPUTER_DAO.create(computer);
	}

	/**
	 * update - sets the Computer object, then calls DAO to update a specific
	 * Computer in database
	 * 
	 * @param id
	 * @param Computer
	 */
	public void update(long id, Computer computer) {
		Computer c = (Computer) COMPUTER_DAO.readOne(id);
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
			COMPUTER_DAO.update(id, c);
		}
	}

	/**
	 * delete - call DAO to delete a specific Computer from database
	 * 
	 * @param id
	 */
	public void delete(long id) {
		COMPUTER_DAO.delete(id);
	}
}

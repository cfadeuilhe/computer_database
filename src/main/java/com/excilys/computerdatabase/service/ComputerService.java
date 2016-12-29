package com.excilys.computerdatabase.service;

import java.util.*;

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

    private static ComputerService INSTANCE = new ComputerService(ComputerDao.INSTANCE);
    private static ComputerDao COMPUTER_DAO;

    private ComputerService(ComputerDao computerDao) {
        this.COMPUTER_DAO = computerDao;
    }

    private ComputerService() {

    }

    public static ComputerService getInstance() {
        return INSTANCE;
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
    
    public long countComputers(String search){
        return COMPUTER_DAO.count(search);
    }

    public List<Entity> listSearch(String search) {
        List<Entity> list = new ArrayList<Entity>();
        list = COMPUTER_DAO.readSearch(search);
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
    public void create(Computer computer) {
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
        COMPUTER_DAO.update(id, computer);
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

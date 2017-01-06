package com.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.persistence.ConnectionDao;

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
     * listEntities - call DAO to get the list of all Computer from database
     * 
     * @return List<Computer>
     */
    public List<Entity> listEntities() {
        ConnectionDao.INSTANCE.initConnection();
        List<Entity> list = new ArrayList<Entity>();
        list = COMPUTER_DAO.read();
        ConnectionDao.INSTANCE.closeConnection();
        return list;
    }
    
    public long countEntities(String search) {
        ConnectionDao.INSTANCE.initConnection();
        long l = COMPUTER_DAO.count(search);
        ConnectionDao.INSTANCE.closeConnection();
        return l;
    }

    public List<Entity> listSearch(String search) {
        ConnectionDao.INSTANCE.initConnection();
        List<Entity> list = new ArrayList<Entity>();
        list = COMPUTER_DAO.readSearch(search);
        ConnectionDao.INSTANCE.closeConnection();
        return list;
    }

    /**
     * readPages - call DAO to get a specific Page of Computer from database
     * 
     * @param Page
     * @return List<Computer>
     */
    public List<Entity> readPages(Page p) {
        ConnectionDao.INSTANCE.initConnection();
        List<Entity> list = new ArrayList<Entity>();
        list = COMPUTER_DAO.readPages(p);
        ConnectionDao.INSTANCE.closeConnection();
        return list;
    }

    /**
     * readOne - call DAO to get a specific Computer from database
     * 
     * @param id
     * @return Computer
     */
    public Entity readOne(long id) {
        ConnectionDao.INSTANCE.initConnection();
        Computer c = (Computer) COMPUTER_DAO.readOne(id);
        ConnectionDao.INSTANCE.closeConnection();
        return c;
    }

    /**
     * create - call DAO to create a new Computer in database
     * 
     * @param Computer
     */
    public void create(Entity entity) {
        ConnectionDao.INSTANCE.initConnection();
        COMPUTER_DAO.create(entity);
        ConnectionDao.INSTANCE.closeConnection();
    }

    /**
     * update - sets the Computer object, then calls DAO to update a specific
     * Computer in database
     * 
     * @param id
     * @param Computer
     */
    public void update(long id, Entity entity) {
        ConnectionDao.INSTANCE.initConnection();
        COMPUTER_DAO.update(id, entity);
        ConnectionDao.INSTANCE.closeConnection();
    }

    /**
     * delete - call DAO to delete a specific Computer from database
     * 
     * @param id
     */
    public void delete(long id) {
        ConnectionDao.INSTANCE.initConnection();
        COMPUTER_DAO.delete(id);
        ConnectionDao.INSTANCE.closeConnection();
    }
}

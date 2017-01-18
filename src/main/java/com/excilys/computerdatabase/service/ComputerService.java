package com.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
@Service
public class ComputerService {

    @Autowired
    private ComputerDao computerDao;

    public ComputerService() {
        this.computerDao = ComputerDao.getInstance();
    }

    public void setComputerDao(ComputerDao cd) {
        this.computerDao = cd;
    }

    /**
     * listEntities - call DAO to get the list of all Computer from database
     * 
     * @return List<Computer>
     */
    public List<Entity> listEntities() {
        List<Entity> list = new ArrayList<Entity>();
        list = computerDao.read();
        return list;
    }
    
    public long countEntities(String search) {
        long l = computerDao.count(search);
        return l;
    }

    public List<Entity> listSearch(String search) {
        List<Entity> list = new ArrayList<Entity>();
        list = computerDao.readSearch(search);
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
        list = computerDao.readPages(p);
        return list;
    }

    /**
     * readOne - call DAO to get a specific Computer from database
     * 
     * @param id
     * @return Computer
     */
    public Entity readOne(long id) {
        Computer c = (Computer) computerDao.readOne(id);
        return c;
    }

    /**
     * create - call DAO to create a new Computer in database
     * 
     * @param Computer
     */
    public int create(Entity entity) {
        return computerDao.create(entity);
    }

    /**
     * update - sets the Computer object, then calls DAO to update a specific
     * Computer in database
     * 
     * @param id
     * @param Computer
     */
    public void update(long id, Entity entity) {
        computerDao.update(id, entity);
    }

    /**
     * delete - call DAO to delete a specific Computer from database
     * 
     * @param id
     */
    public void delete(long id) {
        computerDao.delete(id);
    }
}

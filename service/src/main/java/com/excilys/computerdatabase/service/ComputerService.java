package com.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.persistence.ComputerDao;

/**
 * class ComputerService
 * 
 * @author juanita
 *
 */
@Service
@Transactional
public class ComputerService implements InterfaceService<Computer> {

    @Autowired
    private ComputerDao computerDao;

    /**
     * call DAO to get the list of all Computer from database
     * 
     * @return List<Computer>
     */
    @Override
    public List<Computer> listEntities(Map<String, String> orderMap) {
        List<Computer> list = new ArrayList<Computer>();
        list = computerDao.read(orderMap);
        return list;
    }

    @Override
    public long countEntities(String search) {
        long l = computerDao.count(search);
        return l;
    }

    @Override
    public List<Computer> listSearch(String search, Map<String, String> orderMap) {
        List<Computer> list = new ArrayList<Computer>();
        list = computerDao.readSearch(search, orderMap);
        return list;
    }

    /**
     * call DAO to get a specific Page of Computer from database
     * 
     * @param Page
     * @return List<Computer>
     */
    @Override
    public List<Computer> readPages(Page p, Map<String, String> orderMap) {
        List<Computer> list = new ArrayList<Computer>();
        list = computerDao.readPages(p, orderMap);
        return list;
    }

    /**
     * call DAO to get a specific Computer from database
     * 
     * @param id
     * @return Computer
     */
    @Override
    public Computer readOne(long id) {
        Computer c = computerDao.readOne(id);
        return c;
    }

    /**
     * call DAO to create a new Computer in database
     * 
     * @param Computer
     */
    @Override
    public int create(Computer entity) {
        return computerDao.create(entity);
    }

    /**
     * sets the Computer object, then calls DAO to update a specific
     * Computer in database
     * 
     * @param id
     * @param Computer
     */
    @Override
    public void update(long id, Computer entity) {
        computerDao.update(id, entity);
    }

    /**
     * call DAO to delete a specific Computer from database
     * 
     * @param id
     */
    @Override
    public void delete(long id) {
        computerDao.delete(id);
    }
}

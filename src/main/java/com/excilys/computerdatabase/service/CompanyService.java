package com.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.ComputerDao;

/**
 * class CompanyService
 * 
 * @author juanita
 *
 */
@Service
@Transactional(readOnly = true)
public class CompanyService implements InterfaceService{

    private final static Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private ComputerDao computerDao;

    public  CompanyDao getCompanyDao() {
        return companyDao;
    }

    public void setCompanyDao(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public ComputerDao getComputerDao() {
        return computerDao;
    }

    public void setComputerDao(ComputerDao computerDao) {
        this.computerDao = computerDao;
    }

    public List<Entity> listEntities() {
        List<Entity> list = new ArrayList<Entity>();
        list = companyDao.read();
        return list;
    }

    public Entity readOne(long id) {
        Company c = (Company) companyDao.readOne(id);
        return c;
    }

    /**
     * delete a Company and all related Computers.
     * 
     * @param id - id of the Company to delete
     * @throws PersistenceException 
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = PersistenceException.class)
    public void delete(long id) throws PersistenceException {
        try {
            computerDao.deleteByCompany(id);
            companyDao.delete(id);
        } catch (PersistenceException e) {
            logger.error("delete() catched PersistenceException", e);
            throw new PersistenceException("company delete error");
        }
    }
}

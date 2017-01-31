package com.excilys.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.persistence.CompanyDao;
import com.excilys.computerdatabase.persistence.ComputerDao;

/**
 * class CompanyService
 * 
 * @author juanita
 *
 */
@Service
@Transactional
public class CompanyService implements InterfaceService<Company>{

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

    @Override
    public List<Company> listEntities(Map<String, String> orderMap) {
        List<Company> list = new ArrayList<Company>();
        list = companyDao.read(orderMap);
        return list;
    }

    @Override
    public Company readOne(long id) {
        Company c = companyDao.readOne(id);
        return c;
    }

    /**
     * delete a Company and all related Computers.
     * 
     * @param id - id of the Company to delete
     * @throws PersistenceException 
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = PersistenceException.class)
    public void delete(long id) throws PersistenceException {
        try {
            computerDao.deleteByCompany(id);
            companyDao.delete(id);
        } catch (PersistenceException e) {
            logger.error("delete() in class CompanyService catched PersistenceException", e);
            throw new PersistenceException("company delete error");
        }
    }
}

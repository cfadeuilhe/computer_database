package com.excilys.computerdatabase.persistence;

import java.util.*;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.model.QCompany;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

/**
 * Class CompanyDAO
 * 
 * @author juanita
 *
 */
@Repository
@Transactional
public class CompanyDao implements InterfaceDao<Company> {

    private static QCompany company = QCompany.company;

    @Autowired
    SessionFactory sessionFactory;

    private final static Logger logger = LoggerFactory.getLogger(ComputerDao.class);

    private Supplier<HibernateQueryFactory> queryFactory = () -> new HibernateQueryFactory(
            sessionFactory.getCurrentSession());

    /**
     * read - get all Company from database
     * 
     * @return List<Company>
     */
    @Override
    public List<Company> read(Map<String, String> orderMap) {

        return queryFactory.get().selectFrom(company).orderBy(company.name.asc()).fetch();
    }

    /**
     * readPages - sort by pages and return a specific page
     * 
     * @param Page
     * @return List<Company>
     */
    @Override
    public List<Company> readPages(Page p, Map<String, String> orderMap) {

        return queryFactory.get().selectFrom(company).limit(p.getPageSize()).offset(p.getOffset()).fetch();
    }

    /**
     * readOne - get a specific Company from database
     * 
     * @param id
     * @return Company
     */
    @Override
    public Company readOne(long id) {

        return queryFactory.get().selectFrom(company).where(company.id.eq(id)).fetchOne();
    }

    /**
     * delete - delete a Company
     * 
     * @param cn
     *            - Connection to use
     * @param id
     *            - id of the Company to delete
     */
    @Override
    public void delete(long id) throws PersistenceException {

        try {
            queryFactory.get().delete(company).where(company.id.eq(id)).execute();
        } catch (DataAccessException e) {
            logger.info("Error while deleting company number " + id);
            throw new PersistenceException("error deleting company " + id);
        }
    }
}

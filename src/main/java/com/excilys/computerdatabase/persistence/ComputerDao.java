package com.excilys.computerdatabase.persistence;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.*;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

/**
 * class ComputerDAO
 * 
 * @author juanita
 *
 */
@Repository
@Transactional
public class ComputerDao implements InterfaceDao<Computer> {

    private static QComputer computer = QComputer.computer;
    private static QCompany company = QCompany.company;

    @Autowired
    SessionFactory sessionFactory;

    private Supplier<HibernateQueryFactory> queryFactory = () -> new HibernateQueryFactory(
            sessionFactory.getCurrentSession());

    private final static Logger logger = LoggerFactory.getLogger(ComputerDao.class);

    /**
     * read - get all Computer from database
     * 
     * @return List<Computer>
     */
    @Override
    public List<Computer> read(Map<String, String> orderMap) {

        JPQLQuery<Computer> query = queryFactory.get().selectFrom(computer).leftJoin(computer.company, company);

        query = orderBy(query, orderMap);
        return query.fetch();
    }

    public long count(String search) {

        if (search != null && !search.isEmpty()) {
            return queryFactory.get().selectFrom(computer).leftJoin(computer.company, company)
                    .where(computer.name.contains(search)).fetch().size();
        } else {
            return queryFactory.get().selectFrom(computer).leftJoin(computer.company, company).fetch().size();
        }
    }

    public List<Computer> readSearch(String search, Map<String, String> orderMap) {
        JPQLQuery<Computer> query = queryFactory.get().selectFrom(computer).leftJoin(computer.company, company);
        if (search != null && !search.isEmpty()) {
            query = query.where(computer.name.contains(search));
        }
        query = orderBy(query, orderMap);
        return query.fetch();
    }

    /**
     * readPages - sort by pages and return a specific page
     * 
     * @param Page
     * @return List<Computer>
     */
    @Override
    public List<Computer> readPages(Page p, Map<String, String> orderMap) {

        JPQLQuery<Computer> query = queryFactory.get().selectFrom(computer).leftJoin(computer.company, company);
        if (p.getSearch() != null && !p.getSearch().isEmpty()) {
            query = query.where(computer.name.contains(p.getSearch()));
        }
        query = orderBy(query, orderMap);
        return query.limit(p.getPageSize()).offset(p.getOffset()).fetch();
    }

    /**
     * readOne - get a specific Computer from database
     * 
     * @param id
     * @return Computer
     */
    @Override
    public Computer readOne(long id) {

        return queryFactory.get().selectFrom(computer).leftJoin(computer.company, company).where(computer.id.eq(id))
                .fetchOne();
    }

    /**
     * create - new Computer in database
     * 
     * @param Computer
     * @return
     */
    @Override
    public int create(Computer computer) {

        sessionFactory.getCurrentSession().save(computer);
        return (int) computer.getId();
    }

    /**
     * update - update a certain Computer in database
     * 
     * @param id
     * @param Computer
     */
    @Override
    public void update(long id, Computer uComputer) {

        queryFactory.get().update(computer).where(computer.id.eq(id)).set(computer.name, uComputer.getName())
                .set(computer.introducedDate, uComputer.getIntroducedDate())
                .set(computer.discontinuedDate, uComputer.getDiscontinuedDate())
                .set(computer.company, uComputer.getCompany()).execute();
    }

    /**
     * delete - delete a certain Computer from database
     * 
     * @param id
     */
    @Override
    public void delete(long id) {

        queryFactory.get().delete(computer).where(computer.id.eq(id)).execute();
    }

    /**
     * 
     * @param cn
     * @param id
     * @throws PersistenceException
     */
    public void deleteByCompany(long id) throws PersistenceException {

        try {
            queryFactory.get().delete(computer).where(computer.company.id.eq(id)).execute();
        } catch (DataAccessException e) {
            logger.info("Error while deleting computer number " + id);
            throw new PersistenceException("error deleting computer " + id);
        }
    }

    public JPQLQuery<Computer> orderBy(JPQLQuery<Computer> query, Map<String, String> orderMap) {

        if (orderMap != null && !orderMap.isEmpty()) {
            for (Entry<String, String> entrySet : orderMap.entrySet()) {
                if (entrySet.getKey() != null && !entrySet.getKey().isEmpty()) {
                    switch (entrySet.getKey()) {
                    case "name":
                        if (entrySet.getValue().equals("asc")) {
                            query = query.orderBy(computer.name.asc());
                        } else if (entrySet.getValue().equals("desc")) {
                            query = query.orderBy(computer.name.desc());
                        }
                        break;
                    case "introduced":
                        if (entrySet.getValue().equals("asc")) {
                            query = query.orderBy(computer.introducedDate.asc());
                        } else if (entrySet.getValue().equals("desc")) {
                            query = query.orderBy(computer.introducedDate.desc());
                        }
                        break;
                    case "discontinued":
                        if (entrySet.getValue().equals("asc")) {
                            query = query.orderBy(computer.discontinuedDate.asc());
                        } else if (entrySet.getValue().equals("desc")) {
                            query = query.orderBy(computer.discontinuedDate.desc());
                        }
                        break;
                    case "company":
                        if (entrySet.getValue().equals("asc")) {
                            query = query.orderBy(company.name.asc());
                        } else if (entrySet.getValue().equals("desc")) {
                            query = query.orderBy(company.name.desc());
                        }
                        break;
                    }

                }
            }
        }
        return query;
    }
}

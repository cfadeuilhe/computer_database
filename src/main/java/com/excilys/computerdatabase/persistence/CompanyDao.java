package com.excilys.computerdatabase.persistence;

import java.util.*;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;
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
    
    private Supplier<HibernateQueryFactory> queryFactory =
                        () -> new HibernateQueryFactory(sessionFactory.getCurrentSession());

    /**
     * read - get all Company from database
     * 
     * @return List<Company>
     */
    @Override
    public List<Company> read(Map<String, String> orderMap) {

        return queryFactory.get().selectFrom(company).fetch();
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
     * create - new Company in database
     * 
     * @param Company
     * @return
     *//*
       * public int create(Company entity) { int newId = -1; Company c =
       * (Company) entity; try (Connection cn =
       * DataSourceUtils.getConnection(dataSource); PreparedStatement st =
       * cn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
       * st.setString(1, c.getName()); st.executeUpdate();ResultSet resultSet =
       * st.getGeneratedKeys();
       * 
       * if (resultSet.next()) { newId = resultSet.getInt(1); } } catch
       * (SQLException e) { logger.error("Cannot create new company ", e); }
       * return newId; }
       */

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
            throw new PersistenceException("te");
        }
    }
}

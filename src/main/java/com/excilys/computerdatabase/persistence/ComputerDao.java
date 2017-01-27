package com.excilys.computerdatabase.persistence;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.mapper.JdbcComputerMapper;
import com.excilys.computerdatabase.model.*;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static QComputer computer = QComputer.computer;
    private static QCompany company = QCompany.company;

    @Autowired
    SessionFactory sessionFactory;

    private Supplier<HibernateQueryFactory> queryFactory = 
            () -> new HibernateQueryFactory(sessionFactory.getCurrentSession());
    
    // private final static Logger logger = LoggerFactory.getLogger(ComputerDao.class);

    private final static String SQL_READ = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id";
    private final static String SQL_READ_SEARCH = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.name LIKE ? or company.name LIKE ?";
    private final static String SQL_READ_ONE = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.id=?";
    private final static String SQL_CREATE = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
    private final static String SQL_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    private final static String SQL_DELETE = "DELETE FROM computer WHERE id=?";
    private final static String SQL_DELETE_BY_COMPANY = "DELETE FROM computer WHERE company_id=?";
    private final static String SQL_COUNT = "SELECT COUNT(*) AS count FROM computer";

    /**
     * read - get all Computer from database
     * 
     * @return List<Computer>
     */
    @Override
    public List<Computer> read(Map<String, String> orderMap) {

        queryFactory.get().selectFrom(computer).leftJoin(computer.company, company).orderBy(computer.name.asc()).fetch();
        //List<Computer> list = queryFactory.get().selectFrom(computer).fetch();
        //return list;
        String sqlRead = SQL_READ;
        if (orderMap != null) {
            for (Entry<String, String> entrySet : orderMap.entrySet()) {
                if (entrySet.getKey() != null && !entrySet.getKey().isEmpty()) {
                    sqlRead = sqlRead.concat(" ORDER BY " + entrySet.getKey() + " " + entrySet.getValue());
                }
            }
        }

        return this.jdbcTemplate.query(sqlRead, new JdbcComputerMapper());
    }

    public long count(String search) {

        String sqlCount = SQL_COUNT;
        if (search != null && !search.isEmpty()) {
            sqlCount = sqlCount
                    .concat(" LEFT JOIN company ON computer.company_id=company.id WHERE computer.name LIKE '%")
                    .concat(search).concat("%' or company.name LIKE '").concat(search).concat("%'");
        }

        return this.jdbcTemplate.queryForObject(sqlCount, Integer.class);
    }

    public List<Computer> readSearch(String search) {

        queryFactory.get().selectFrom(computer).leftJoin(computer.company,company).where(computer.name.contains(search)).fetch();
        return this.jdbcTemplate.query(SQL_READ_SEARCH, new Object[] { "%" + search + "%", search + "%" },
                new JdbcComputerMapper());
    }

    /**
     * readPages - sort by pages and return a specific page
     * 
     * @param Page
     * @return List<Computer>
     */
    @Override
    public List<Computer> readPages(Page p, Map<String, String> orderMap) {

        return queryFactory.get().selectFrom(computer).leftJoin(computer.company, company).limit(p.getPageSize()).offset(p.getOffset()).orderBy(computer.name.asc()).fetch();
        
        /*String readPages = SQL_READ;

        if (orderMap != null) {
            for (Entry<String, String> entrySet : orderMap.entrySet()) {
                if (entrySet.getKey() != null && !entrySet.getKey().isEmpty()) {
                    readPages = readPages.concat(" ORDER BY " + entrySet.getKey() + " " + entrySet.getValue());
                }
            }
        }

        if (p.getOffset() >= 0) {
            if (p.getSearch() != null && !(p.getSearch().isEmpty())) {
                readPages = readPages.concat(" WHERE computer.name LIKE '%").concat(p.getSearch())
                        .concat("%' or company.name LIKE '").concat(p.getSearch()).concat("%'");
            }
            readPages += " LIMIT " + p.getPageSize() + " OFFSET " + p.getOffset();

        }

        return this.jdbcTemplate.query(readPages, new JdbcComputerMapper());*/
    }

    /**
     * readOne - get a specific Computer from database
     * 
     * @param id
     * @return Computer
     */
    @Override
    public Computer readOne(long id) {

        return this.jdbcTemplate.queryForObject(SQL_READ_ONE, new Object[] { id }, new JdbcComputerMapper());
    }

    /**
     * create - new Computer in database
     * 
     * @param Computer
     * @return
     */
    @Override
    public int create(Computer computer) {

        KeyHolder holder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, computer.getName());
                if (computer.getIntroducedDate() != null) {
                    ps.setString(2, computer.getIntroducedDate().toString());
                } else {
                    ps.setString(2, null);
                }
                if (computer.getDiscontinuedDate() != null) {
                    ps.setString(3, computer.getDiscontinuedDate().toString());
                } else {
                    ps.setString(3, null);
                }
                if (computer.getCompany() != null && computer.getCompany().getId() != 0) {
                    ps.setLong(4, computer.getCompany().getId());
                } else {
                    ps.setString(4, null);
                }

                return ps;
            }
        }, holder);

        return holder.getKey().intValue();

        /*
         * this.jdbcTemplate.update(SQL_CREATE, computer.getName(),
         * (computer.getIntroducedDate() != null ?
         * computer.getIntroducedDate().toString() : null),
         * (computer.getDiscontinuedDate() != null ?
         * computer.getDiscontinuedDate().toString() : null),
         * ((computer.getCompany() != null && computer.getCompany().getId() !=
         * 0) ? computer.getCompany().getId() : null));
         * 
         * int a = holder.getKey().intValue(); return a ;
         */
    }

    /**
     * update - update a certain Computer in database
     * 
     * @param id
     * @param Computer
     */
    @Override
    public void update(long id, Computer computer) {

        jdbcTemplate.update(SQL_UPDATE,
                new Object[] { computer.getName(),
                        (computer.getIntroducedDate() != null ? (computer.getIntroducedDate().toString()) : (null)),
                        (computer.getDiscontinuedDate() != null ? (computer.getDiscontinuedDate().toString()) : (null)),
                        (computer.getCompany() != null ? (computer.getCompany().getId()) : 0), id });
    }

    /**
     * delete - delete a certain Computer from database
     * 
     * @param id
     */
    @Override
    public void delete(long id) {

        jdbcTemplate.update(SQL_DELETE, id);
    }

    /**
     * 
     * @param cn
     * @param id
     * @throws PersistenceException
     */
    public void deleteByCompany(long id) throws PersistenceException {

        try {
            jdbcTemplate.update(SQL_DELETE_BY_COMPANY, id);
        } catch (DataAccessException e) {
            throw new PersistenceException("error delete computer by company");
        }
    }
}

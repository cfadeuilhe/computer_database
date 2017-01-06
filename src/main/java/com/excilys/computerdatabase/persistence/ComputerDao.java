package com.excilys.computerdatabase.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.mapper.RsMapper;
import com.excilys.computerdatabase.model.*;
import com.excilys.computerdatabase.util.Consts;

/**
 * class ComputerDAO
 * 
 * @author juanita
 *
 */
public enum ComputerDao implements InterfaceDao {

    INSTANCE;
    private final static Logger logger = LoggerFactory.getLogger(ComputerDao.class);
    private final static ConnectionDao CONNECTION_INSTANCE = ConnectionDao.INSTANCE;
    private final static RsMapper RS_TO_COMPUTER = new RsMapper();
    private final static String SQL_READ = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id";
    private final static String SQL_READ_SEARCH = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.name LIKE ? or company.name LIKE ?";
    private final static String SQL_READ_PAGES = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id";
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
    public List<Entity> read() {
        List<Entity> computerList = new ArrayList<Entity>();
        Connection cn = CONNECTION_INSTANCE.getConnection();
        try (PreparedStatement st = cn.prepareStatement(SQL_READ)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Computer c = RS_TO_COMPUTER.rsToComputer(rs);
                if (rs.getInt(Consts.COMPANY_ID_DB) != 0) {
                    c.setCompany(new Company(rs.getInt(Consts.COMPANY_ID_DB), rs.getString(Consts.COMPANY_NAME_DB)));
                }
                computerList.add(c);
            }
        } catch (SQLException e) {
            logger.error("ComputerDao : read() catched SQLException", e);
        }
        return computerList;
    }

    public long count(String search) {
        long count = 0;
        String sqlCount = SQL_COUNT;
        if (search != null && !search.isEmpty()) {
            sqlCount = sqlCount.concat(" LEFT JOIN company ON computer.company_id=company.id WHERE computer.name LIKE '%")
                    .concat(search).concat("%' or company.name LIKE '").concat(search).concat("%'");
        }
        Connection cn = CONNECTION_INSTANCE.getConnection();
        try (PreparedStatement st = cn.prepareStatement(sqlCount); ResultSet rs = st.executeQuery();) {
            while (rs.next()) {
                count = rs.getLong(Consts.COUNT);
            }
        } catch (SQLException e) {
            logger.error("ComputerDao : count() catched SQLException", e);
        }
        return count;
    }

    public List<Entity> readSearch(String search) {
        List<Entity> computerList = new ArrayList<Entity>();
        Connection cn = CONNECTION_INSTANCE.getConnection();
        try (PreparedStatement st = cn.prepareStatement(SQL_READ_SEARCH)) {
            st.setString(1, "%" + search + "%");
            st.setString(2, search + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Computer c = RS_TO_COMPUTER.rsToComputer(rs);
                if (rs.getInt(Consts.COMPANY_ID_DB) != 0) {
                    c.setCompany(new Company(rs.getInt(Consts.COMPANY_ID_DB), rs.getString(Consts.COMPANY_NAME_DB)));
                }
                computerList.add(c);
            }
        } catch (SQLException e) {
            logger.error("ComputerDao : readSearch() catched SQLException", e);
        }
        return computerList;
    }

    /**
     * readPages - sort by pages and return a specific page
     * 
     * @param Page
     * @return List<Computer>
     */
    public List<Entity> readPages(Page p) {
        List<Entity> computerList = new ArrayList<Entity>();
        String readPages = SQL_READ_PAGES;
        if (p.getOffset() >= 0) {
            if (p.getSearch() != null && !(p.getSearch().isEmpty())) {
                readPages = readPages.concat(" WHERE computer.name LIKE '%").concat(p.getSearch())
                        .concat("%' or company.name LIKE '").concat(p.getSearch()).concat("%'");
            }
            readPages += " LIMIT " + p.getPageSize() + " OFFSET " + p.getOffset();
            
            Connection cn = CONNECTION_INSTANCE.getConnection();
            try (PreparedStatement st = cn.prepareStatement(readPages)) {
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Computer c = RS_TO_COMPUTER.rsToComputer(rs);
                    if (rs.getInt(Consts.COMPANY_ID_DB) != 0) {
                        c.setCompany(
                                new Company(rs.getInt(Consts.COMPANY_ID_DB), rs.getString(Consts.COMPANY_NAME_DB)));
                    }
                    computerList.add(c);
                }
            } catch (SQLException e) {
                logger.error("ComputerDao : readPages() catched SQLException", e);
            }
        }
        return computerList;
    }

    /**
     * readOne - get a specific Computer from database
     * 
     * @param id
     * @return Computer
     */
    public Entity readOne(long id) {
        Computer computer = null;
        Connection cn = CONNECTION_INSTANCE.getConnection();
        try (PreparedStatement st = cn.prepareStatement(SQL_READ_ONE)) {
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                computer = RS_TO_COMPUTER.rsToComputer(rs);
                if (rs.getInt(Consts.COMPANY_ID_DB) != 0) {
                    computer.setCompany(
                            new Company(rs.getInt(Consts.COMPANY_ID_DB), rs.getString(Consts.COMPANY_NAME_DB)));
                }
            }
        } catch (SQLException e) {
            logger.error("ComputerDao : readOne() catched SQLException", e);
        }
        return computer;
    }

    /**
     * create - new Computer in database
     * 
     * @param Computer
     */
    public int create(Entity entity) {
        int newId = -1;
        Computer computer = (Computer) entity;
        Connection cn = CONNECTION_INSTANCE.getConnection();
        try (PreparedStatement st = cn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, computer.getName());
            if (computer.getIntroducedDate() != null) {
                st.setString(2, computer.getIntroducedDate().toString());
            } else {
                st.setString(2, null);
            }
            if (computer.getDiscontinuedDate() != null) {
                st.setString(3, computer.getDiscontinuedDate().toString());
            } else {
                st.setString(3, null);
            }
            if (computer.getCompany() != null && computer.getCompany().getId() != 0) {
                st.setLong(4, computer.getCompany().getId());
            } else {
                st.setString(4, null);
            }
            st.executeUpdate();
            ResultSet resultSet = st.getGeneratedKeys();

            if (resultSet.next()) {
                newId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("ComputerDao : create() catched SQLException", e);
        }
        return newId;
    }

    /**
     * update - update a certain Computer in database
     * 
     * @param id
     * @param Computer
     */
    public void update(long id, Entity entity) {
        Computer computer = (Computer) entity;
        Connection cn = CONNECTION_INSTANCE.getConnection();
        try (PreparedStatement st = cn.prepareStatement(SQL_UPDATE)) {
            st.setString(1, computer.getName());
            st.setString(2,
                    (computer.getIntroducedDate() != null ? (computer.getIntroducedDate().toString()) : (null)));
            st.setString(3,
                    (computer.getDiscontinuedDate() != null ? (computer.getDiscontinuedDate().toString()) : (null)));
            st.setLong(4, computer.getCompany().getId());
            st.setLong(5, id);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.error("ComputerDao : update() catched SQLException", e);
        }
    }

    /**
     * delete - delete a certain Computer from database
     * 
     * @param id
     */
    public void delete(long id) {
        Connection cn = CONNECTION_INSTANCE.getConnection();
        try (PreparedStatement st = cn.prepareStatement(SQL_DELETE)) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.toString());
        }
    }

    public void deleteByCompany(long id) throws PersistenceException {
        Connection connection = CONNECTION_INSTANCE.getConnection();
        try (PreparedStatement st = connection.prepareStatement(SQL_DELETE_BY_COMPANY)) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.error("ComputerDao : deleteByCompany() catched SQLException", e);
            throw new PersistenceException(e);
        }
    }
}

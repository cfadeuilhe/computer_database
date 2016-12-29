package com.excilys.computerdatabase.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.mapper.RsMapper;
import com.excilys.computerdatabase.model.*;
import com.excilys.computerdatabase.util.Consts;
import com.excilys.computerdatabase.validators.ComputerValidator;

/**
 * class ComputerDAO
 * 
 * @author juanita
 *
 */
public enum ComputerDao implements InterfaceDao {

    INSTANCE;

    private final static ConnectionDao CONNECTION_FACTORY = ConnectionDao.INSTANCE;
    private final static RsMapper RS_TO_COMPUTER = new RsMapper();
    private final static String SQL_READ = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id";
    private final static String SQL_READ_SEARCH = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE computer.name LIKE ? or company.name LIKE ?";
    private final static String SQL_READ_PAGES = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id";
    private final static String SQL_READ_ONE = "SELECT * FROM computer WHERE id=?";
    private final static String SQL_CREATE = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?)";
    private final static String SQL_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
    private final static String SQL_DELETE = "DELETE FROM computer WHERE id=?";

    /**
     * read - get all Computer from database
     * 
     * @return List<Computer>
     */
    public List<Entity> read() {
        Connection cn = CONNECTION_FACTORY.getConnection();
        List<Entity> computerList = new ArrayList<Entity>();
        try (PreparedStatement st = cn.prepareStatement(SQL_READ);) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Computer c = RS_TO_COMPUTER.rsToComputer(rs);
                if (rs.getInt("company_id") != 0) {
                    c.setCompany(new Company(rs.getInt(Consts.COMPANY_ID_DB), rs.getString(Consts.COMPANY_NAME_DB)));
                }
                ComputerValidator.validator(c);
                System.out.println(c);
                computerList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("mySQL error : " + SQL_READ);
        }
        CONNECTION_FACTORY.closeConnection();
        return computerList;
    }

    public List<Entity> readSearch(String search) {
        Connection cn = CONNECTION_FACTORY.getConnection();
        List<Entity> computerList = new ArrayList<Entity>();
        try (PreparedStatement st = cn.prepareStatement(SQL_READ_SEARCH);) {
            st.setString(1, "%" + search + "%");
            st.setString(2, search + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Computer c = RS_TO_COMPUTER.rsToComputer(rs);
                if (rs.getInt("company_id") != 0) {
                    c.setCompany(new Company(rs.getInt(Consts.COMPANY_ID_DB), rs.getString(Consts.COMPANY_NAME_DB)));
                }
                ComputerValidator.validator(c);
                computerList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("mySQL error : " + SQL_READ_SEARCH);
        }
        CONNECTION_FACTORY.closeConnection();
        return computerList;
    }

    /**
     * readPages - sort by pages and return a specific page
     * 
     * @param Page
     * @return List<Computer>
     */
    public List<Entity> readPages(Page p) {
        Connection cn = CONNECTION_FACTORY.getConnection();
        List<Entity> computerList = new ArrayList<Entity>();
        String readPages = SQL_READ_PAGES;
        if (p.getOffset() >= 0) {
            if (p.getSearch() != null && !(p.getSearch().isEmpty())) {
                readPages += " WHERE computer.name LIKE '%" + p.getSearch() + "%' or company.name LIKE '"
                        + p.getSearch() + "'";
            }
            readPages += " LIMIT " + p.getPageSize() + " OFFSET " + p.getOffset();

            try (PreparedStatement st = cn.prepareStatement(readPages)) {
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Computer c = RS_TO_COMPUTER.rsToComputer(rs);
                    if (rs.getInt("company_id") != 0) {
                        c.setCompany(new Company(rs.getInt(Consts.COMPANY_ID_DB), rs.getString(Consts.COMPANY_NAME_DB)));
                    }
                    ComputerValidator.validator(c);
                    computerList.add(c);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("error in sql :" + readPages);
            }
        }
        CONNECTION_FACTORY.closeConnection();
        return computerList;
    }

    /**
     * readOne - get a specific Computer from database
     * 
     * @param id
     * @return Computer
     */
    public Entity readOne(long id) {
        Connection cn = CONNECTION_FACTORY.getConnection();
        Computer computer = null;
        try (PreparedStatement st = cn.prepareStatement(SQL_READ_ONE)) {
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next())
                computer = RS_TO_COMPUTER.rsToComputer(rs);
            ComputerValidator.validator(computer);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in sql :" + SQL_READ_ONE);
        }
        CONNECTION_FACTORY.closeConnection();
        return computer;
    }

    /**
     * create - new Computer in database
     * 
     * @param Computer
     */
    public void create(Entity entity) {
        Connection cn = CONNECTION_FACTORY.getConnection();
        Computer computer = (Computer) entity;
        try (PreparedStatement st = cn.prepareStatement(SQL_CREATE)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in sql when creating computer");
        }
        CONNECTION_FACTORY.closeConnection();
    }

    /**
     * update - update a certain Computer in database
     * 
     * @param id
     * @param Computer
     */
    public void update(long id, Entity entity) {
        Connection cn = CONNECTION_FACTORY.getConnection();
        Computer computer = (Computer) entity;
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
            e.printStackTrace();
            System.out.println("error in sql :" + SQL_UPDATE);
        }
        CONNECTION_FACTORY.closeConnection();
    }

    /**
     * delete - delete a certain Computer from database
     * 
     * @param id
     */
    public void delete(long id) {
        Connection cn = CONNECTION_FACTORY.getConnection();
        try (PreparedStatement st = cn.prepareStatement(SQL_DELETE)) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error in sql :" + SQL_DELETE);
        }
        CONNECTION_FACTORY.closeConnection();
    }
}

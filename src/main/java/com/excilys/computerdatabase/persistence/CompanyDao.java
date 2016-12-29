package com.excilys.computerdatabase.persistence;

import java.sql.*;
import java.util.*;

import com.excilys.computerdatabase.mapper.RsMapper;
import com.excilys.computerdatabase.model.*;
import com.excilys.computerdatabase.util.Consts;

/**
 * Class CompanyDAO
 * 
 * @author juanita
 *
 */
public enum CompanyDao implements InterfaceDao {
    INSTANCE;

    private final static ConnectionDao CONNECTION_FACTORY = ConnectionDao.INSTANCE;
    private final static RsMapper RS_TO_COMPUTER = new RsMapper();
    private final static String SQL_READ = "SELECT * FROM company;";
    private final static String SQL_READ_PAGES = "SELECT * FROM company LIMIT ? OFFSET ?;";
    private final static String SQL_READ_ONE = "SELECT * FROM company WHERE id=?;";
    private final static String SQL_CREATE = "INSERT INTO company (name) VALUES ('?');";
    private final static String SQL_DELETE = "DELETE FROM company WHERE id=?";
    private final static String SQL_DELETE_COMPUTERS = "DELETE FROM computer WHERE computer.company_id=?";

    /**
     * read - get all Company from database
     * 
     * @return List<Company>
     */
    public List<Entity> read() {
        Connection cn = CONNECTION_FACTORY.getConnection();
        List<Entity> companyList = new ArrayList<Entity>();
        try (PreparedStatement st = cn.prepareStatement(SQL_READ); ResultSet rs = st.executeQuery();) {
            while (rs.next()) {
                companyList.add(new Company(rs.getInt(Consts.ID), rs.getString(Consts.NAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("mySQL error : " + SQL_READ);
        }
        CONNECTION_FACTORY.closeConnection();
        return companyList;
    }

    /**
     * readPages - sort by pages and return a specific page
     * 
     * @param Page
     * @return List<Company>
     */
    public List<Entity> readPages(Page p) {
        Connection cn = CONNECTION_FACTORY.getConnection();
        List<Entity> companyList = new ArrayList<Entity>();
        try (PreparedStatement st = cn.prepareStatement(SQL_READ_PAGES);) {
            st.setLong(1, p.getPageSize());
            st.setLong(2, p.getOffset());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                companyList.add(RS_TO_COMPUTER.rsToCompany(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("mySQL error : " + SQL_READ_PAGES);
        }
        CONNECTION_FACTORY.closeConnection();
        return companyList;
    }

    /**
     * readOne - get a specific Company from database
     * 
     * @param id
     * @return Company
     */
    public Entity readOne(long id) {
        Connection cn = CONNECTION_FACTORY.getConnection();
        Company company = null;
        try (PreparedStatement st = cn.prepareStatement(SQL_READ_ONE);) {
            if (id != 0)
                st.setLong(1, id);
            else
                st.setString(1, null);
            ResultSet rs = st.executeQuery();
            rs.next();
            company = new Company(rs.getInt(Consts.ID), rs.getString(Consts.NAME));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("mySQL error : " + SQL_READ_ONE);
        }
        CONNECTION_FACTORY.closeConnection();
        return company;
    }

    /**
     * create - new Company in database
     * 
     * @param Company
     */
    public void create(Entity entity) {
        Connection cn = CONNECTION_FACTORY.getConnection();
        Company c = (Company) entity;
        try (PreparedStatement st = cn.prepareStatement(SQL_CREATE);) {
            st.setString(1, c.getName());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("mySQL error : " + SQL_CREATE);
        }
        CONNECTION_FACTORY.closeConnection();
    }

    public void delete(long id) {
        try (Connection cn = CONNECTION_FACTORY.getConnection();) {
            cn.setAutoCommit(false);

            try {
                PreparedStatement stComputer = cn.prepareStatement(SQL_DELETE_COMPUTERS);
                stComputer.setLong(1, id);
                stComputer.executeUpdate();

                PreparedStatement stCompany = cn.prepareStatement(SQL_DELETE);
                stCompany.setLong(1, id);
                stCompany.executeUpdate();
            } catch (SQLException e) {
                cn.rollback();
                e.printStackTrace();
                System.out.println("SQL ISSUE -> ROLLBACK");
            }
            cn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("CONNECTION ISSUE");
        }
        CONNECTION_FACTORY.closeConnection();
    }
}

package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.persistence.ComputerDao;
import com.excilys.computerdatabase.util.Consts;

/**
 * class RsToObjectMapper
 * 
 * @author juanita
 *
 */
public class RsMapper {

    private final static Logger logger = LoggerFactory.getLogger(ComputerDao.class);

    /**
     * Mapper from ResultSet to Computer
     * 
     * @param ResultSet
     * @return Computer
     */
    public Computer rsToComputer(ResultSet rs) {
        ComputerBuilder computerBuilder = new ComputerBuilder();
        try {
            computerBuilder.id(rs.getInt(Consts.ID)).name(rs.getString(Consts.NAME));
            if (rs.getDate(Consts.INTRODUCED_DATE) != null) {
                computerBuilder.introducedDate(rs.getDate(Consts.INTRODUCED_DATE).toLocalDate());
            }
            if (rs.getDate(Consts.DISCONTINUED_DATE) != null) {
                computerBuilder.discontinuedDate(rs.getDate(Consts.DISCONTINUED_DATE).toLocalDate());
            }
        } catch (SQLException e) {
            logger.error("${enclosing_type} : ${enclosing_method}() catched ${exception_type}", e);
        }
        return computerBuilder.build();
    }

    /**
     * Mapper from ResultSet to Company
     * 
     * @param ResultSet
     * @return Company
     */
    public Company rsToCompany(ResultSet rs) {
        Company company = new Company();
        try {
            company.setId(rs.getInt(Consts.ID));
            company.setName(rs.getString(Consts.NAME));
        } catch (SQLException e) {
            logger.error("${enclosing_type} : ${enclosing_method}() catched ${exception_type}", e);
        }
        return company;
    }
}

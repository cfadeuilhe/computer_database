package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.util.Consts;

/**
 * class RsToObjectMapper
 * 
 * @author juanita
 *
 */
public class RsMapper {
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return company;
    }
}

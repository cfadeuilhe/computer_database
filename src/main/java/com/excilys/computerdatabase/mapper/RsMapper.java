package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.model.*;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.service.CompanyService;

/**
 * class RsToObjectMapper
 * 
 * @author juanita
 *
 */
public class RsMapper {
    private final static CompanyService COMPANY_SERVICE = CompanyService.getInstance();
    /**
     * Mapper from ResultSet to Computer
     * 
     * @param ResultSet
     * @return Computer
     */
    public Computer rsToComputer(ResultSet rs) {
        ComputerBuilder computerBuilder = new ComputerBuilder();
        try {
            computerBuilder.id(rs.getInt("id")).name(rs.getString("name"));
            if (rs.getDate("introduced") != null) {
                computerBuilder.introducedDate(rs.getDate("introduced").toLocalDate());
            }
            if (rs.getDate("discontinued") != null) {
                computerBuilder.discontinuedDate(rs.getDate("discontinued").toLocalDate());
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
            company.setId(rs.getInt("id"));
            company.setName(rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return company;
    }
}

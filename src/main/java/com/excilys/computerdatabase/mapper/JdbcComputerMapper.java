package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.util.Consts;

public class JdbcComputerMapper implements RowMapper<Computer>{

    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException{
        
        ComputerBuilder computerBuilder = new ComputerBuilder();
        computerBuilder.name(rs.getString(Consts.NAME)).id(rs.getLong(Consts.ID));
        if (rs.getDate(Consts.INTRODUCED_DATE) != null) {
            computerBuilder.introducedDate(rs.getDate(Consts.INTRODUCED_DATE).toLocalDate());
        }
        if (rs.getDate(Consts.DISCONTINUED_DATE) != null) {
            computerBuilder.discontinuedDate(rs.getDate(Consts.DISCONTINUED_DATE).toLocalDate());
        }
        computerBuilder.company(new Company(rs.getInt(Consts.COMPANY_ID_DB), rs.getString(Consts.COMPANY_NAME_DB)));
        return computerBuilder.build();
    }
}

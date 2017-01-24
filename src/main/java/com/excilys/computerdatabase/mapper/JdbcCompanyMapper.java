package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.util.Consts;

public class JdbcCompanyMapper implements RowMapper<Entity> {
    
    
    public Entity mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Company(rs.getLong(Consts.ID), rs.getString(Consts.NAME));
    }
}

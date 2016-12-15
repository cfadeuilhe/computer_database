package com.computer_database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.computer_database.model.*;
import com.computer_database.model.Computer.ComputerBuilder;
import com.computer_database.service.CompanyService;

/**
 * class RsToObjectMapper
 * @author juanita
 *
 */
public class RsToObjectMapper {

	/**
	 * Mapper from ResultSet to Computer
	 * 
	 * @param ResultSet
	 * @return Computer
	 */
	public Computer rsToComputer(ResultSet rs) {
		ComputerBuilder cBuilder = new ComputerBuilder();
		CompanyService cCo = new CompanyService();
		Company company = new Company();
		try {
			if (rs.getInt("company_id") != 0) {
				company = cCo.readOne(rs.getInt("company_id"));
			}
			cBuilder.id(rs.getInt("id")).name(rs.getString("name")).company(company);
			if (rs.getDate("introduced") != null) {
				cBuilder.introducedDate(rs.getDate("introduced").toLocalDate());
			}
			if (rs.getDate("discontinued") != null) {
				cBuilder.discontinuedDate(rs.getDate("discontinued").toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cBuilder.build();
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
			company.setid(rs.getInt("id"));
			company.setName(rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}
}

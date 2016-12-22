package main.java.com.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import main.java.com.computerdatabase.dtos.ComputerDto;
import main.java.com.computerdatabase.model.*;
import main.java.com.computerdatabase.model.Computer.ComputerBuilder;
import main.java.com.computerdatabase.service.CompanyService;

/**
 * class RsToObjectMapper
 * 
 * @author juanita
 *
 */
public class Mapper {

	private final static CompanyService COMPANY_SERVICE = new CompanyService();

	public ComputerDto computerToDto(Computer computer) {
		ComputerDto computerDto = new ComputerDto();
		computerDto.setId(computer.getid());
		computerDto.setName(computer.getName());
		if (computer.getIntroducedDate() != null) {
			computerDto.setIntroducedDate(computer.getIntroducedDate().toString());
		} else {
			computerDto.setIntroducedDate(null);
		}
		if (computer.getDiscontinuedDate() != null) {
			computerDto.setDiscontinuedDate(computer.getDiscontinuedDate().toString());
		} else {
			computerDto.setDiscontinuedDate(null);
		}
		if (computer.getCompany() != null) {
			computerDto.setCompanyId(computer.getCompany().getid());
		} else {
			computerDto.setCompanyId(0);
		}
		return computerDto;
	}

	public Computer dtoToComputer(ComputerDto computerDto) {
		ComputerBuilder computerBuilder = new ComputerBuilder();

		Company company = new Company();
		if (computerDto.getCompanyId() != 0) {
			company = COMPANY_SERVICE.readOne(computerDto.getCompanyId());
		}

		computerBuilder.id(computerDto.getId()).name(computerDto.getName()).company(company);
		if (computerDto.getIntroducedDate() != null) {
			computerBuilder.introducedDate(LocalDate.parse(computerDto.getIntroducedDate()));
		}
		if (computerDto.getDiscontinuedDate() != null) {
			computerBuilder.discontinuedDate(LocalDate.parse(computerDto.getDiscontinuedDate()));
		}
		
		Computer computer = computerBuilder.build();

		return computer;
	}

	/**
	 * Mapper from ResultSet to Computer
	 * 
	 * @param ResultSet
	 * @return Computer
	 */
	public Computer rsToComputer(ResultSet rs) {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		Company company = new Company();
		try {
			if (rs.getInt("company_id") != 0) {
				company = COMPANY_SERVICE.readOne(rs.getInt("company_id"));
			}
			computerBuilder.id(rs.getInt("id")).name(rs.getString("name")).company(company);
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
			company.setid(rs.getInt("id"));
			company.setName(rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}
}

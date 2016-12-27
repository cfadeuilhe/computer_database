package com.excilys.computerdatabase.mapper;

import java.time.LocalDate;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.service.CompanyService;

public class DtoMapper {
    private final static CompanyService COMPANY_SERVICE = new CompanyService();

    public static ComputerDto computerToDto(Computer computer) {
        ComputerDto computerDto = new ComputerDto();
        computerDto.setId(computer.getId());
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

    public static Computer dtoToComputer(ComputerDto computerDto) {
        ComputerBuilder computerBuilder = new ComputerBuilder();

        Company company = new Company();
        if (computerDto.getCompanyId() != 0) {
            company = COMPANY_SERVICE.readOne(computerDto.getCompanyId());
        }

        computerBuilder.id(computerDto.getId()).name(computerDto.getName()).company(company);
        if (computerDto.getIntroducedDate() != "") {
            computerBuilder.introducedDate(LocalDate.parse(computerDto.getIntroducedDate()));
        }
        if (computerDto.getDiscontinuedDate() != "") {
            computerBuilder.discontinuedDate(LocalDate.parse(computerDto.getDiscontinuedDate()));
        }

        Computer computer = computerBuilder.build();

        return computer;
    }
}

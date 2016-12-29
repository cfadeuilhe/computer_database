package com.excilys.computerdatabase.mapper;

import java.time.LocalDate;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.dto.ComputerDto.ComputerDtoBuilder;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;

public class DtoMapper {

    public static ComputerDto computerToDto(Computer computer) {
        ComputerDtoBuilder computerDtoBuilder = new ComputerDtoBuilder();

        computerDtoBuilder.id(computer.getId()).name(computer.getName());
        if (computer.getIntroducedDate() != null) {
            computerDtoBuilder.introducedDate(computer.getIntroducedDate().toString());
        }
        if (computer.getDiscontinuedDate() != null) {
            computerDtoBuilder.discontinuedDate(computer.getDiscontinuedDate().toString());
        }
        if (computer.getCompany() != null) {
            computerDtoBuilder.companyId(computer.getCompany().getId()).companyName(computer.getCompany().getName());
        }

        return computerDtoBuilder.build();
    }

    public static Computer dtoToComputer(ComputerDto computerDto) {
        ComputerBuilder computerBuilder = new ComputerBuilder();

        computerBuilder.id(computerDto.getId()).name(computerDto.getName());
        if (!computerDto.getIntroducedDate().trim().isEmpty()) {
            computerBuilder.introducedDate(LocalDate.parse(computerDto.getIntroducedDate()));
        }
        if (!computerDto.getDiscontinuedDate().trim().isEmpty()) {
            computerBuilder.discontinuedDate(LocalDate.parse(computerDto.getDiscontinuedDate()));
        }

        return computerBuilder.build();
    }
}

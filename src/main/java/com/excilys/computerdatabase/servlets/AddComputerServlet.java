package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.DtoMapper;
import com.excilys.computerdatabase.mapper.RequestMapper;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.validators.ComputerDtoValidator;

public class AddComputerServlet extends HttpServlet {

    private final static CompanyService COMPANY_SERVICE = CompanyService.getInstance();
    private final static ComputerService COMPUTER_SERVICE = ComputerService.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Entity> list = new ArrayList<Entity>();
        list = COMPANY_SERVICE.listCompanies();
        request.setAttribute("companyList", list);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ComputerDto computerDto = RequestMapper.toComputerDto(request);
        List<String> errorsList = ComputerDtoValidator.validator(computerDto);
        
        if (errorsList.isEmpty()) {
            // create computer
            Computer computer = DtoMapper.dtoToComputer(computerDto);
            computer.setCompany(new Company(computerDto.getCompanyId(), computerDto.getCompanyName()));
            COMPUTER_SERVICE.create(computer);
            request.setAttribute("computer", computerDto);
        } else {
            request.setAttribute("computerWrong", computerDto);
            request.setAttribute("errors", errorsList);
        }
        doGet(request, response);
    }
}

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
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.validators.ComputerDtoValidator;

public class EditComputerServlet extends HttpServlet {
	
	private final static CompanyService COMPANY_SERVICE = new CompanyService();
	private final static ComputerService COMPUTER_SERVICE = new ComputerService();
	private final static ComputerBuilder COMPUTER_BUILDER = new ComputerBuilder();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Entity> list = new ArrayList<Entity>();
		list = COMPANY_SERVICE.listCompanies();
		request.setAttribute("companyList", list);

        ComputerDto computerDto = RequestMapper.toComputerDto(request);
        request.setAttribute("computerToEdit", computerDto);
        
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}
	
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        ComputerDto computerDto = RequestMapper.toComputerDto(request);
        System.out.println(computerDto);
        List<String> errorsList = ComputerDtoValidator.validator(computerDto);
        System.out.println(errorsList);
        if (errorsList.isEmpty()) {
            // update computer
            Computer computer = DtoMapper.dtoToComputer(computerDto);
            COMPUTER_SERVICE.update(computer.getId(), computer);
            request.setAttribute("computer", computerDto);
        } else {
            request.setAttribute("computerWrong", computerDto);
            request.setAttribute("errors", errorsList);
        }
        doGet(request, response);
    }
	
}

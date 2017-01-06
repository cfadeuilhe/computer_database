package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.DtoMapper;
import com.excilys.computerdatabase.mapper.RequestMapper;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Consts;
import com.excilys.computerdatabase.validators.ComputerDtoValidator;

public class AddComputerServlet extends HttpServlet {

    private final static Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
    private final static CompanyService COMPANY_SERVICE = CompanyService.getInstance();
    private final static ComputerService COMPUTER_SERVICE = ComputerService.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Entity> list = new ArrayList<Entity>();
        list = COMPANY_SERVICE.listEntities();
        request.setAttribute(Consts.COMPANY_LIST, list);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ComputerDto computerDto = RequestMapper.toComputerDto(request);
        Map<String,String> errorsMap = ComputerDtoValidator.validate(computerDto);
        
        if (errorsMap.isEmpty()) {
            // create computer
            Computer computer = DtoMapper.dtoToComputer(computerDto);
            computer.setCompany(new Company(computerDto.getCompanyId(), computerDto.getCompanyName()));
            COMPUTER_SERVICE.create(computer);
            request.getSession().setAttribute("computer", computerDto);
        } else {
            request.getSession().setAttribute("computerWrong", computerDto);
            request.getSession().setAttribute("errors", errorsMap);
        }
        
        response.sendRedirect("addComputer");
    }
}

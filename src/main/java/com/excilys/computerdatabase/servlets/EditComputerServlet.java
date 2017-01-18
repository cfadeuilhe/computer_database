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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

public class EditComputerServlet extends HttpServlet {

    private static final long serialVersionUID = 4051934716238154424L;
    private final static Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;

    @Override
    public void init() {
       WebApplicationContext contextApp = WebApplicationContextUtils.getWebApplicationContext(getServletContext());       

       this.computerService = (ComputerService)contextApp.getBean("computerService");
       this.companyService = (CompanyService)contextApp.getBean("companyService");
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Entity> list = new ArrayList<Entity>();
        list = companyService.listEntities();
        request.setAttribute("companyList", list);

        String test = request.getParameter(Consts.PAGE);
        int p = 1;
        if (test != null && !test.isEmpty()) {
            p = Integer.parseInt(request.getParameter(Consts.PAGE));
        }
        ComputerDto computerDto = RequestMapper.toComputerDto(request);
        request.setAttribute("computerToEdit", computerDto);
        request.setAttribute("pageNumber", p);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ComputerDto computerDto = RequestMapper.toComputerDto(request);
        String test = request.getParameter(Consts.PAGE);
        int p = 1;
        if (test != null && !test.isEmpty()) {
            p = Integer.parseInt(request.getParameter(Consts.PAGE));
        }
        Map<String, String> errorsList = ComputerDtoValidator.validate(computerDto);
        if (errorsList.isEmpty()) {
            // update computer
            Computer computer = DtoMapper.dtoToComputer(computerDto);
            computer.setCompany(new Company(computerDto.getCompanyId(), computerDto.getCompanyName()));
            computerService.update(computer.getId(), computer);
            request.getSession().setAttribute("computer", computerDto);
            response.sendRedirect("dashboard?page=" + p);
        } else {
            request.getSession().setAttribute("computerToEdit", computerDto);
            request.getSession().setAttribute("errors", errorsList);
            response.sendRedirect("editComputer");
        }
    }

}

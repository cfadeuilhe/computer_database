package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.List;

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
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Consts;
import com.excilys.computerdatabase.validators.ComputerDtoValidator;

public class DeleteComputerServlet extends HttpServlet {
    
    final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
    private final static ComputerService COMPUTER_SERVICE = ComputerService.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String selection = request.getParameter(Consts.SELECTION);
        String[] parse = selection.split(",");
        for (int i = 0; i < parse.length; i++) {
            COMPUTER_SERVICE.delete(Long.parseLong(parse[i]));
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}

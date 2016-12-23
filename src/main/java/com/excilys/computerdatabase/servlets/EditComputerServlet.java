package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

public class EditComputerServlet extends HttpServlet {
	
	private final static CompanyService COMPANY_SERVICE = new CompanyService();
	private final static ComputerService COMPUTER_SERVICE = new ComputerService();
	private final static ComputerBuilder COMPUTER_BUILDER = new ComputerBuilder();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Entity> list = new ArrayList<Entity>();
		list = COMPANY_SERVICE.listCompanies();
		request.setAttribute("companyList", list);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}
	
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
         
        String name = req.getParameter("computerName");
        String introduced = req.getParameter("introduced");
        String discontinued = req.getParameter("discontinued");
        String companyId = req.getParameter("companyId");
        
        Computer computer = COMPUTER_BUILDER.name(name).build();
        //COMPUTER_SERVICE.create(computer);
        
        //RequestDispatcher rd = req.getRequestDispatcher("/dashboard");
        //rd.forward(req, resp);
         
    }
	
}

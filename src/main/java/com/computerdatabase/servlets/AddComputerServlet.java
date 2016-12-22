package main.java.com.computerdatabase.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.computerdatabase.dtos.ComputerDto;
import main.java.com.computerdatabase.mapper.Mapper;
import main.java.com.computerdatabase.model.Company;
import main.java.com.computerdatabase.model.Computer;
import main.java.com.computerdatabase.model.Entity;
import main.java.com.computerdatabase.model.Computer.ComputerBuilder;
import main.java.com.computerdatabase.service.CompanyService;
import main.java.com.computerdatabase.service.ComputerService;

public class AddComputerServlet extends HttpServlet {

	private final static CompanyService COMPANY_SERVICE = new CompanyService();
	private final static ComputerService COMPUTER_SERVICE = new ComputerService();
	private final static ComputerBuilder COMPUTER_BUILDER = new ComputerBuilder();
	private final static Mapper COMPUTER_MAPPER = new Mapper();

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

		LocalDate introduced = null, discontinued = null;
		int companyId = 1;
		String name = request.getParameter("computerName");

		// DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		if ((request.getParameter("introduced") != null) && (request.getParameter("introduced") != "")) {
			introduced = LocalDate.parse(request.getParameter("introduced"));
		}
		if (request.getParameter("discontinued") != null && (request.getParameter("discontinued") != "")) {
			discontinued = LocalDate.parse(request.getParameter("discontinued"));
		}

		if (request.getParameter("companyId") != null && (request.getParameter("companyId") != "")) {
			companyId = Integer.parseInt(request.getParameter("companyId"));
		} else {
			companyId = 0;
		}

		Company company = COMPANY_SERVICE.readOne(companyId);

		Computer computer = COMPUTER_BUILDER.name(name).introducedDate(introduced).discontinuedDate(discontinued)
				.company(company).build();
		
		ComputerDto computerDto = COMPUTER_MAPPER.computerToDto(computer);

		COMPUTER_SERVICE.create(computerDto);

		List<Entity> list = new ArrayList<Entity>();
		list = COMPANY_SERVICE.listCompanies();
		request.setAttribute("companyList", list);
		
		request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);

	}
}

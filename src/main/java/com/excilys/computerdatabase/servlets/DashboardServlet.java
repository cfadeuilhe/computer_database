package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.mapper.RequestMapper;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

public class DashboardServlet extends HttpServlet {

    private final static ComputerService COMPUTER_SERVICE = new ComputerService();
    private final static CompanyService COMPANY_SERVICE = new CompanyService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Entity> list = new ArrayList<Entity>();
        String search = request.getParameter("search");
        if (search != null) {
            list = COMPUTER_SERVICE.listSearch(request.getParameter("search"));
        } else {
            list = COMPUTER_SERVICE.listComputers();
        }
        Page p = RequestMapper.requestToPage(request);
        
        request.setAttribute("completeList", list);
        request.setAttribute("page", p);
        System.out.println(p.getComputerList());

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}

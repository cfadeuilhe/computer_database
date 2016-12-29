package com.excilys.computerdatabase.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.mapper.RequestMapper;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Consts;

public class DashboardServlet extends HttpServlet {

    final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
    private final static ComputerService COMPUTER_SERVICE = ComputerService.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long count = 0;
        String search = request.getParameter(Consts.SEARCH);
        count = COMPUTER_SERVICE.countComputers(search);
        Page p = RequestMapper.requestToPage(request);

        request.setAttribute("count", count);
        request.setAttribute("page", p);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String selection = request.getParameter(Consts.SELECTION);
        String[] parse = selection.split(",");
        for (int i = 0; i < parse.length; i++) {
            COMPUTER_SERVICE.delete(Long.parseLong(parse[i]));
        }

        doGet(request, response);
    }
}

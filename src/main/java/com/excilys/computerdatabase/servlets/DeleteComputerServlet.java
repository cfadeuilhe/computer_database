package com.excilys.computerdatabase.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Consts;

public class DeleteComputerServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -9167111070314009743L;
    private final static Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
    private final static ComputerService COMPUTER_SERVICE = ComputerService.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String selection = request.getParameter(Consts.SELECTION);
        String[] parse = selection.split(",");
        for (int i = 0; i < parse.length; i++) {
            COMPUTER_SERVICE.delete(Long.parseLong(parse[i]));
        }

        response.sendRedirect("dashboard");
    }
}

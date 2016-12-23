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
import com.excilys.computerdatabase.service.ComputerService;

public class DashboardServlet extends HttpServlet {

    private final static ComputerService COMPUTER_SERVICE = new ComputerService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Page p = RequestMapper.requestToPage(request);
        
        List<Entity> list = new ArrayList<Entity>();
        list = COMPUTER_SERVICE.listComputers();
        
        request.setAttribute("completeList", list);
        request.setAttribute("page", p);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    public long checkParameter(String s, long defaultVal) {
        long r;
        if (s != null) {
            try {
                r = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                r = defaultVal;
            }
        } else {
            r = defaultVal;
        }
        return r;
    }

}

package com.excilys.computerdatabase.servlets;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.computerdatabase.mapper.RequestMapper;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Consts;

public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = -2927308147872440092L;
    private final static Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
    @Autowired
    private ComputerService computerService;
    
    @Override
    public void init() {
       WebApplicationContext contextApp = WebApplicationContextUtils.getWebApplicationContext(getServletContext());       

       this.computerService = (ComputerService)contextApp.getBean(ComputerService.class);
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long count = 0;
        String search = request.getParameter(Consts.SEARCH);
        Map<String, String> order = new HashMap<>();
        
        count = computerService.countEntities(search);
        Page p;
        if (search != null && !search.trim().isEmpty()) {
            p = RequestMapper.requestToPage(request, computerService.listSearch(search));
        } else {
            p = RequestMapper.requestToPage(request, computerService.listEntities());
        }
        p.setComputerList(computerService.readPages(p));
        
        String t = request.getParameter("order");
        request.setAttribute(Consts.COUNT, count);
        request.setAttribute(Consts.PAGE, p);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }
}

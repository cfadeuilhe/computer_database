package com.excilys.computerdatabase.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.DtoMapper;
import com.excilys.computerdatabase.mapper.RequestMapper;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Consts;
import com.excilys.computerdatabase.validators.ComputerDtoValidator;

public class DashboardServlet extends HttpServlet {

    private final static ComputerService COMPUTER_SERVICE = ComputerService.getInstance();
    private final static CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Entity> list = new ArrayList<Entity>();
        String search = request.getParameter(Consts.SEARCH);
        if (search != null) {
            if (!search.isEmpty()) {
                list = COMPUTER_SERVICE.listSearch(request.getParameter(Consts.SEARCH));
            } else {
                list = COMPUTER_SERVICE.listComputers();
            }
        } else {
            list = COMPUTER_SERVICE.listComputers();
        }
        Page p = RequestMapper.requestToPage(request);

        request.setAttribute("completeList", list);
        request.setAttribute("page", p);

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String selection = request.getParameter(Consts.SELECTION);
        String[] parse = selection.split(",");
        for(int i=0; i<parse.length; i++){
            COMPUTER_SERVICE.delete(Long.parseLong(parse[i]));
        }

        doGet(request, response);
    }
}

package com.excilys.computerdatabase.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.ComputerService;

public class RequestMapper {

    private final static ComputerService COMPUTER_SERVICE = ComputerService.getInstance();

    public static Page requestToPage(HttpServletRequest request) {

        List<Entity> list = new ArrayList<Entity>();
        String search = request.getParameter("search");
        if (search != null) {
            if (!search.isEmpty()) {
                list = COMPUTER_SERVICE.listSearch(search);
            } else {
                list = COMPUTER_SERVICE.listComputers();
            }
        } else {
            list = COMPUTER_SERVICE.listComputers();
        }
        int currentPage = (int) checkParameter(request.getParameter("page"), 1);
        long pageSize = checkParameter(request.getParameter("limit"), 10),
                pageCount = (list.size() % 10 == 0) ? (list.size() / pageSize) : (list.size() / pageSize + 1);

        Page page = new Page(currentPage, pageSize, pageCount);
        if (search != null) {
            if (!search.isEmpty()) {
                page.setSearch(search);
                page.setComputerList(COMPUTER_SERVICE.searchPages(page));
            } else {
                page.setComputerList(COMPUTER_SERVICE.readPages(page));
            }
        } else {
            page.setComputerList(COMPUTER_SERVICE.readPages(page));
        }

        return page;
    }

    public static ComputerDto toComputerDto(HttpServletRequest request) {
        long companyId = 0;
        ComputerDto computerDto = null;
        if (request.getParameter("companyId") != null && (request.getParameter("companyId") != "")) {
            companyId = Integer.parseInt(request.getParameter("companyId"));
        }

        if (request.getParameter("id") != null && (request.getParameter("id") != ""))
            computerDto = new ComputerDto(Integer.parseInt(request.getParameter("id")),
                    request.getParameter("computerName"), request.getParameter("introduced"),
                    request.getParameter("discontinued"), companyId);
        else
            computerDto = new ComputerDto(request.getParameter("computerName"), request.getParameter("introduced"),
                    request.getParameter("discontinued"), companyId);

        return computerDto;
    }

    public static long checkParameter(String s, long defaultVal) {
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

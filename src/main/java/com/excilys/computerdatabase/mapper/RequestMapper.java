package com.excilys.computerdatabase.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.ComputerService;

public class RequestMapper {

    private final static ComputerService COMPUTER_SERVICE = new ComputerService();

    public static Page requestToPage(HttpServletRequest request) {

        List<Entity> list = new ArrayList<Entity>();
        list = COMPUTER_SERVICE.listComputers();
        int currentPage = (int) checkParameter(request.getParameter("page"), 1);
        long pageSize = checkParameter(request.getParameter("limit"), 10), pageCount = (list.size() % 10 == 0)?(list.size() / pageSize):(list.size() / pageSize +1);
        
        Page page = new Page(currentPage, pageSize, pageCount);
        page.setComputerList(COMPUTER_SERVICE.readPages(page));
        return page;
    }
    
    public static ComputerDto toComputerDto(HttpServletRequest request){
        long companyId = 0;
        if (request.getParameter("companyId") != null && (request.getParameter("companyId") != "")) {
            companyId = Integer.parseInt(request.getParameter("companyId"));
        }
        ComputerDto computerDto = new ComputerDto(request.getParameter("computerName"), request.getParameter("introduced"), request.getParameter("discontinued"), companyId);
        
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

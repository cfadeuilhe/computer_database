package com.excilys.computerdatabase.mapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.dto.ComputerDto.ComputerDtoBuilder;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.util.Consts;

public class RequestMapper {

    private final static int PAGE_NUMBER_DEFAULT = 1;
    private final static int PAGE_SIZE_DEFAULT = 10;

    public static Page requestToPage(HttpServletRequest request, List<Entity> list) {
        String search = request.getParameter(Consts.SEARCH);
        
        int currentPage = (checkIsNumber(request.getParameter(Consts.PAGE)))
                ? (Integer.parseInt(request.getParameter(Consts.PAGE))) : PAGE_NUMBER_DEFAULT;
        int pageSize = (checkIsNumber(request.getParameter(Consts.LIMIT)))
                ? (Integer.parseInt(request.getParameter(Consts.LIMIT))) : PAGE_SIZE_DEFAULT;
        int pageCount = (list.size() % 10 == 0) ? (list.size() / pageSize) : (list.size() / pageSize + 1);

        Page page = new Page(currentPage, pageSize, pageCount);
        if (search != null && !search.trim().isEmpty()) {
            page.setSearch(search);
        }
        
        return page;
    }

    public static ComputerDto toComputerDto(HttpServletRequest request) {
        ComputerDtoBuilder computerDtoBuilder = new ComputerDtoBuilder();

        computerDtoBuilder.name(request.getParameter(Consts.COMPUTER_NAME)).introducedDate(request.getParameter(Consts.INTRODUCED_DATE))
                .discontinuedDate(request.getParameter(Consts.DISCONTINUED_DATE));

        if (request.getParameter(Consts.COMPANY_ID) != null && (!request.getParameter(Consts.COMPANY_ID).trim().isEmpty())) {
            computerDtoBuilder.companyId(Integer.parseInt(request.getParameter(Consts.COMPANY_ID)));

        }
        if (request.getParameter(Consts.ID) != null && (!request.getParameter(Consts.ID).trim().isEmpty())) {
            computerDtoBuilder.id(Integer.parseInt(request.getParameter(Consts.ID)));
        }

        return computerDtoBuilder.build();
    }

    public static boolean checkIsNumber(String number) {
        if (number == null || number.isEmpty()) {
            return false;
        } else {
            return number.matches("\\d+");
        }
    }
}

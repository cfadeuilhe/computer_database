package com.excilys.computerdatabase.mapper;

import java.util.List;
import java.util.Map;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.dto.ComputerDto.ComputerDtoBuilder;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.util.Consts;

public class RequestMapper {

    private final static int PAGE_NUMBER_DEFAULT = 1;
    private final static int PAGE_SIZE_DEFAULT = 10;

    public static Page requestToPage(Map<String, String> requestMap, List<Computer> list) {
        String search = requestMap.get(Consts.SEARCH);

        int currentPage = (checkIsNumber(requestMap.get(Consts.PAGE))) ? (Integer.parseInt(requestMap.get(Consts.PAGE)))
                : PAGE_NUMBER_DEFAULT;
        int pageSize = (checkIsNumber(requestMap.get(Consts.LIMIT))) ? (Integer.parseInt(requestMap.get(Consts.LIMIT)))
                : PAGE_SIZE_DEFAULT;
        int pageCount = (list.size() % 10 == 0) ? (list.size() / pageSize) : (list.size() / pageSize + 1);

        Page page = new Page(currentPage, pageSize, pageCount);
        if (search != null && !search.trim().isEmpty()) {
            page.setSearch(search);
        }

        return page;
    }

    public static ComputerDto toComputerDto(Map<String, String> requestMap) {
        ComputerDtoBuilder computerDtoBuilder = new ComputerDtoBuilder();

        computerDtoBuilder.name(requestMap.get(Consts.COMPUTER_NAME))
                .introducedDate(requestMap.get(Consts.INTRODUCED_DATE))
                .discontinuedDate(requestMap.get(Consts.DISCONTINUED_DATE));

        if (requestMap.get(Consts.COMPANY_ID) != null
                && (!requestMap.get(Consts.COMPANY_ID).trim().isEmpty())) {
            computerDtoBuilder.companyId(Integer.parseInt(requestMap.get(Consts.COMPANY_ID)));

        }
        if (requestMap.get(Consts.ID) != null && (!requestMap.get(Consts.ID).trim().isEmpty())) {
            computerDtoBuilder.id(Integer.parseInt(requestMap.get(Consts.ID)));
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

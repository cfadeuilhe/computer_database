package com.excilys.computerdatabase.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.DtoMapper;
import com.excilys.computerdatabase.mapper.RequestMapper;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Consts;

@Controller
@RequestMapping("/")
public class EditController {

    private final static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/editComputer")
    public ModelAndView getEdit(@RequestParam Map<String, String> requestMap) {
        logger.info("edit computer get controller");

        ModelAndView model = new ModelAndView("editComputer");

        List<Company> list = new ArrayList<Company>();
        list = companyService.listEntities(null);
        model.addObject("companyList", list);

        String orderBy = requestMap.get("order");
        String[] order;
        Map<String, String> orderMap = new HashMap<String, String>();
        if (orderBy != null && !orderBy.isEmpty()) {
            order = orderBy.split("[.]");
            orderMap.put(order[0], order[1]);
        }
        Page p = RequestMapper.requestToPage(requestMap, computerService.listEntities(orderMap));
        p.setOrder(orderBy);
        ComputerDto computerDto = RequestMapper.toComputerDto(requestMap);
        model.addObject("computerToEdit", computerDto);
        model.addObject("page", p);
        model.addObject("computerDto", computerDto);

        return model;
    }

    @PostMapping(value = "/editComputer")
    public ModelAndView postEdit(@Valid @ModelAttribute("computerDto") ComputerDto computerDto,
            BindingResult bindingResult) {
        logger.info("edit computer post controller");

        ModelAndView model = new ModelAndView("editComputer");
        if (!bindingResult.hasErrors()) {
            // create computer
            model = new ModelAndView("redirect:dashboard");
            Computer computer = DtoMapper.dtoToComputer(computerDto);
            computer.setCompany(new Company(computerDto.getCompanyId(), computerDto.getCompanyName()));
            computerService.update(computer.getId(), computer);
            model.addObject("computer", computerDto);
        } else {
            model = new ModelAndView("editComputer");
            List<Company> list = new ArrayList<Company>();
            list = companyService.listEntities(null);
            model.addObject(Consts.COMPANY_LIST, list);
        }

        return model;
    }
}

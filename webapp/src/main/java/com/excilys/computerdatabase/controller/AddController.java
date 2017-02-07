package com.excilys.computerdatabase.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.DtoMapper;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Consts;

@Controller
@RequestMapping("/")
public class AddController {

    private final static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;
    
    @GetMapping(value = "/addComputer")
    public ModelAndView getAdd() {

        logger.info("add computer get controller");
        
        ModelAndView model = new ModelAndView("addComputer");

        List<Company> list = new ArrayList<Company>();
        list = companyService.listEntities(null);
        model.addObject(Consts.COMPANY_LIST, list);
        model.addObject("computerDto", new ComputerDto());

        return model;
    }

    @PostMapping(value = "/addComputer")
    public ModelAndView postAdd(@Valid @ModelAttribute("computerDto") ComputerDto computerDto,
            BindingResult bindingResult) {

        logger.info("add computer post controller");
        
        ModelAndView model;// = new
                           // ModelAndView("/WEB-INF/views/addComputer.jsp");
        if (!bindingResult.hasErrors()) {
            // create computer
            model = new ModelAndView("redirect:dashboard");
            Computer computer = DtoMapper.dtoToComputer(computerDto);
            computer.setCompany(new Company(computerDto.getCompanyId(), computerDto.getCompanyName()));
            computerService.create(computer);
            model.addObject("computer", computerDto);
        } else {
            model = new ModelAndView("addComputer");
            List<Company> list = new ArrayList<Company>();
            list = companyService.listEntities(null);
            model.addObject(Consts.COMPANY_LIST, list);
        }

        return model;
    }
}

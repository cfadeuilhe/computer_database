package com.excilys.computerdatabase.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.DtoMapper;
import com.excilys.computerdatabase.mapper.RequestMapper;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Consts;

@Controller
@RequestMapping("/")
public class DashboardController {
    
    private final static Logger logger = LoggerFactory.getLogger(DashboardController.class);
    
    @Autowired
    private ComputerService computerService;
    @Autowired
    private CompanyService companyService;
    
    public ComputerService getComputerService() {
        return computerService;
    }

    public void setComputerService(ComputerService computerService) {
        this.computerService = computerService;
    }
    
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView getDashboard(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView model = new ModelAndView("dashboard");
        
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
        model.addObject(Consts.COUNT, count);
        model.addObject(Consts.PAGE, p);
        
        return model;
    }
    
    @RequestMapping(value = "/addComputer", method = RequestMethod.GET)
    public ModelAndView getAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView model = new ModelAndView("addComputer");
        
        List<Entity> list = new ArrayList<Entity>();
        list = companyService.listEntities();
        model.addObject(Consts.COMPANY_LIST, list);
        model.addObject("computerDto", new ComputerDto());
        
        return model;
    }
    
    @RequestMapping(value = "/addComputer", method = RequestMethod.POST)
    public ModelAndView postAdd(@Valid @ModelAttribute("computerDto")ComputerDto computerDto, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView model;// = new ModelAndView("/WEB-INF/views/addComputer.jsp");
        String page = request.getParameter("page.pageCount");
        if (!bindingResult.hasErrors()) {
            // create computer
            model = new ModelAndView("redirect:dashboard");
            Computer computer = DtoMapper.dtoToComputer(computerDto);
            computer.setCompany(new Company(computerDto.getCompanyId(), computerDto.getCompanyName()));
            computerService.create(computer);
            model.addObject("computer", computerDto);
        }
        else{
            model = new ModelAndView("addComputer");
        }
        
        return model;
    }
    
    @RequestMapping(value = "/editComputer", method = RequestMethod.GET)
    public ModelAndView getEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView model = new ModelAndView("editComputer");
        
        List<Entity> list = new ArrayList<Entity>();
        list = companyService.listEntities();
        model.addObject("companyList", list);

        String test = request.getParameter(Consts.PAGE);
        int p = 1;
        if (test != null && !test.isEmpty()) {
            p = Integer.parseInt(request.getParameter(Consts.PAGE));
        }
        ComputerDto computerDto = RequestMapper.toComputerDto(request);
        model.addObject("computerToEdit", computerDto);
        model.addObject("pageNumber", p);
        model.addObject("computerDto", computerDto);
        
        return model;
    }
    
    @RequestMapping(value = "/editComputer", method = RequestMethod.POST)
    public ModelAndView postEdit(@Valid @ModelAttribute("computerDto")ComputerDto computerDto, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        ModelAndView model = new ModelAndView("editComputer");
        String page = request.getParameter("page.pageCount");
        if (!bindingResult.hasErrors()) {
            // create computer
            model = new ModelAndView("redirect:dashboard");
            Computer computer = DtoMapper.dtoToComputer(computerDto);
            computer.setCompany(new Company(computerDto.getCompanyId(), computerDto.getCompanyName()));
            computerService.update(computer.getId(), computer);
            model.addObject("computer", computerDto);
        }
        else{
            model.addObject("computerToEdit", computerDto);
            model = new ModelAndView("editComputer");
        }
        
        return model;
    }

}

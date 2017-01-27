package com.excilys.computerdatabase.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
public class DashboardController {

    // private final static Logger logger =
    // LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ComputerService computerService;
    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/dashboard")
    public ModelAndView getDashboard(@RequestParam Map<String, String> requestMap) {

        ModelAndView model = new ModelAndView("dashboard");

        long count = 0;
        String search = requestMap.get(Consts.SEARCH);
        String orderBy = requestMap.get("order"); 
        Map<String, String> order = new HashMap<String, String>();
        order.put(orderBy, "asc");
        count = computerService.countEntities(search);
        Page p;
        if (requestMap.get(Consts.SEARCH) != null) {
            p = RequestMapper.requestToPage(requestMap, computerService.listSearch(search));
        } else {
            p = RequestMapper.requestToPage(requestMap, computerService.listEntities(order));
        }
        if (search != null && !search.trim().isEmpty()) {

        } else {

        }
        p.setOrder(orderBy);
        p.setComputerList(computerService.readPages(p, order));

        // String t = request.getParameter("order");
        model.addObject(Consts.COUNT, count);
        model.addObject(Consts.PAGE, p);

        return model;
    }

    @GetMapping(value = "/addComputer")
    public ModelAndView getAdd() {

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

    @GetMapping(value = "/editComputer")
    public ModelAndView getEdit(@RequestParam Map<String, String> requestMap) {

        ModelAndView model = new ModelAndView("editComputer");

        List<Company> list = new ArrayList<Company>();
        list = companyService.listEntities(null);
        model.addObject("companyList", list);

        String test = requestMap.get(Consts.PAGE);
        int p = 1;
        if (test != null && !test.isEmpty()) {
            p = Integer.parseInt(requestMap.get(Consts.PAGE));
        }
        ComputerDto computerDto = RequestMapper.toComputerDto(requestMap);
        model.addObject("computerToEdit", computerDto);
        model.addObject("pageNumber", p);
        model.addObject("computerDto", computerDto);

        return model;
    }

    @PostMapping(value = "/editComputer")
    public ModelAndView postEdit(@Valid @ModelAttribute("computerDto") ComputerDto computerDto,
            BindingResult bindingResult) {

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

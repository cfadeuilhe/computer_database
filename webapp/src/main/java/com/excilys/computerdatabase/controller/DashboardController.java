package com.excilys.computerdatabase.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.mapper.RequestMapper;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Consts;

@Controller
@RequestMapping("/")
public class DashboardController {

    private final static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ComputerService computerService;

    @GetMapping(value = "/dashboard")
    public ModelAndView getDashboard(@RequestParam Map<String, String> requestMap) {
        logger.info("dashboard get controller");

        ModelAndView model = new ModelAndView("dashboard");

        long count = 0;
        String search = requestMap.get(Consts.SEARCH);
        String orderBy = requestMap.get("order");
        String[] order;
        Map<String, String> orderMap = new HashMap<String, String>();
        if (orderBy != null && !orderBy.isEmpty()) {
            order = orderBy.split("[.]");
            orderMap.put(order[0], order[1]);
        }
        count = computerService.countEntities(search);
        Page p;
        if (requestMap.get(Consts.SEARCH) != null) {
            p = RequestMapper.requestToPage(requestMap, computerService.listSearch(search, orderMap));
        } else {
            p = RequestMapper.requestToPage(requestMap, computerService.listEntities(orderMap));
        }

        p.setOrder(orderBy);
        p.setComputerList(computerService.readPages(p, orderMap));

        model.addObject(Consts.COUNT, count);
        model.addObject(Consts.PAGE, p);

        return model;
    }
}

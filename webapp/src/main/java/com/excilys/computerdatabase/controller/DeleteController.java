package com.excilys.computerdatabase.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Consts;

@Controller
@RequestMapping("/")
public class DeleteController {

     private final static Logger logger = LoggerFactory.getLogger(DashboardController.class);
    
    @Autowired
    private ComputerService computerService;

    @PostMapping(value = "/deleteComputer")
    public ModelAndView postDelete(@RequestParam Map<String, String> requestMap) {
        
        logger.info("delete computer post controller");
        
        String selection = requestMap.get(Consts.SELECTION);
        String[] parse = selection.split(",");
        for (int i = 0; i < parse.length; i++) {
            computerService.delete(Long.parseLong(parse[i]));
        }

        ModelAndView model = new ModelAndView("redirect:dashboard");
        return model;
    }
}

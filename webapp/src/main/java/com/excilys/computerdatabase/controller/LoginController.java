package com.excilys.computerdatabase.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @GetMapping(value = "/login")
    public ModelAndView getLogin(@RequestParam Map<String, String> requestMap) {
        logger.info("login computer get controller");

        ModelAndView model = new ModelAndView("login");

        String error = requestMap.get("error");
        String logout = requestMap.get("logout");

        if (error != null) {
            model.addObject("error", "error.login");
        }

        if (logout != null) {
            model.addObject("logout", "logout.success");
        }

        return model;
    }
}

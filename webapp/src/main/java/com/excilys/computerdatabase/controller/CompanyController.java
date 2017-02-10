package com.excilys.computerdatabase.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.service.CompanyService;

@RestController
@RequestMapping("/")
public class CompanyController {

    private final static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private CompanyService companyService;

    @GetMapping("/company")
    public @ResponseBody ResponseEntity<List<Company>> getCompany(@RequestParam Map<String, String> requestMap) {
        logger.info("JSON company get controller");

        String orderBy = requestMap.get("order");
        String[] order;
        Map<String, String> orderMap = new HashMap<String, String>();
        if (orderBy != null && !orderBy.isEmpty()) {
            order = orderBy.split("[.]");
            orderMap.put(order[0], order[1]);
        }
        List<Company> companyList = companyService.listEntities(orderMap);
        return new ResponseEntity<List<Company>>(companyList, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public @ResponseBody ResponseEntity<Company> getCompanyId(@PathVariable("id") int compId) {
        logger.info("JSON companyid get controller");

        Company c = companyService.readOne(compId);
        if (c != null)
            return new ResponseEntity<Company>(c, HttpStatus.OK);
        else
            return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/company/{id}")
    public @ResponseBody ResponseEntity<Integer> deleteCompany(@PathVariable("id") int compId) {
        try {
            companyService.delete(compId);
        } catch (PersistenceException e) {
            logger.error("Could not delete Company number " + compId + " or one of the associated Computers");
            e.printStackTrace();
        }
        return null;
    }
}

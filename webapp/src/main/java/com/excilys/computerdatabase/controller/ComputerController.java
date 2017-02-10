package com.excilys.computerdatabase.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.service.ComputerService;

@RestController
@RequestMapping("/")
public class ComputerController {

    private final static Logger logger = LoggerFactory.getLogger(ComputerController.class);

    @Autowired
    private ComputerService computerService;

    @GetMapping("/computers")
    public ResponseEntity<List<Computer>> getComputers(@RequestParam Map<String, String> requestMap) {
        logger.info("GET computers cli");

        String orderBy = requestMap.get("order");
        String[] order;
        Map<String, String> orderMap = new HashMap<String, String>();
        if (orderBy != null && !orderBy.isEmpty()) {
            order = orderBy.split("[.]");
            orderMap.put(order[0], order[1]);
        }
        List<Computer> computerList = computerService.listEntities(orderMap);
        return new ResponseEntity<List<Computer>>(computerList, HttpStatus.OK);
    }

    @PutMapping("/computers/{id}")
    public int updateComputer(@RequestBody Computer computer, @PathVariable("id") int compId) {
        logger.info("PUT computer cli");

        // Computer computer = DtoMapper.dtoToComputer(computerDto);
        // computer.setCompany(new Company(computerDto.getCompanyId(),
        // computerDto.getCompanyName()));
        computerService.update(compId, computer);

        return 0;
    }

    @DeleteMapping("/computers/{id}")
    public int deleteComputer(@PathVariable("id") int compId) {
        logger.info("DELETE computer cli");

        // Computer computer = DtoMapper.dtoToComputer(computer);
        // computer.setCompany(new Company(computer.getCompanyId(),
        // computer.getCompanyName()));
        computerService.delete(compId);

        return 0;
    }

    @PostMapping("/computers")
    public ResponseEntity<Integer> creatomputer(@RequestBody Computer computer) {
        logger.info("POST computer cli");

        // Computer computer = DtoMapper.dtoToComputer(computerDto);
        // computer.setCompany(new Company(computerDto.getCompanyId(),
        // computerDto.getCompanyName()));
        computerService.create(computer);

        return new ResponseEntity<Integer>(0, HttpStatus.OK);
    }

    @GetMapping("/computers/{id}")
    public ResponseEntity<Computer> getComputersId(@PathVariable("id") int compId) {
        logger.info("GET computer by id cli");

        if (compId <= 0) {
            return new ResponseEntity<Computer>(HttpStatus.UNAUTHORIZED);
        }
        Computer computer = computerService.readOne(compId);
        if (computer != null) {
            return new ResponseEntity<Computer>(computer, HttpStatus.OK);
        } else {
            return new ResponseEntity<Computer>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/computers/{page}/{size}")
    public ResponseEntity<List<Computer>> getComputersPage(@RequestParam Map<String, String> requestMap,
            @PathVariable("page") int compPage, @PathVariable("size") int pageSize) {
        logger.info("GET computer page cli");

        String orderBy = requestMap.get("order");
        String[] order;
        Map<String, String> orderMap = new HashMap<String, String>();
        if (orderBy != null && !orderBy.isEmpty()) {
            order = orderBy.split("[.]");
            orderMap.put(order[0], order[1]);
        }
        Page p = new Page(compPage, 10, (int) computerService.countEntities(null));
        if (pageSize > 0) {
            p.setPageSize(pageSize);
        }
        List<Computer> computerList = computerService.readPages(p, orderMap);
        if (computerList != null)
            return new ResponseEntity<List<Computer>>(computerList, HttpStatus.OK);
        else
            return new ResponseEntity<List<Computer>>(HttpStatus.NOT_FOUND);
    }
}

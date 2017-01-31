package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.Company;

@ContextConfiguration(locations = "classpath:Spring-Module-Test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyServiceTest {

    @Autowired
    CompanyService companyService;

    @Test
    @Transactional
    @Rollback(true)
    public void testListEntities() {
        List<Company> companiesList = companyService.listEntities(null);
        assertNotNull(companiesList);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadOne() {
        Company company = companyService.readOne(1);
        assertTrue(company instanceof Company);
        assertNotNull(company);
    }
}

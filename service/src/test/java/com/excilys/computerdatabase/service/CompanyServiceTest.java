package com.excilys.computerdatabase.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:Spring-Module-Test.xml")
public class CompanyServiceTest {

    @Autowired
    CompanyService companyService;

    @Test
    @Transactional
    @Rollback(true)
    public void testListEntities() {
        List<Company> companiesList = companyService.listEntities(null);
        assertNotNull("The list of companies should not be null.", companiesList);
        assertEquals("There are 42 companies in the test database : size of the list should be 42.", companiesList.size(), 42);
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testListEntitiesOrdered() {
        Map<String, String> orderMap = new HashMap<String, String>();
        orderMap.put("name", "asc");
        List<Company> companiesList = companyService.listEntities(orderMap);
        assertNotNull("The list of companies should not be null.", companiesList);
        assertNotEquals("The first element's ID should not be 1 since it is ordered.", companiesList.get(1).getId(), 1);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadOne() {
        Company company = companyService.readOne(1);
        assertTrue(company instanceof Company);
        assertNotNull("The company should not be null.", company);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadOneWrongId() {
        Company company = companyService.readOne(44);
        assertNull("The company should be null since that ID does not exist.", company);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete() {
        try {
            companyService.delete(1);
        } catch (PersistenceException e) {
            assertTrue(true);
        }
        Company company = companyService.readOne(1);
        assertNull("The company should be null since it has just been deleted.", company);
    }
}

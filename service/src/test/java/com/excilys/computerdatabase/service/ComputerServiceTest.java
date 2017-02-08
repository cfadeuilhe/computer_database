package com.excilys.computerdatabase.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:Spring-Module-Test.xml")
public class ComputerServiceTest {

    @Autowired
    ComputerService computerService;

    @Test
    @Transactional
    @Rollback(true)
    public void testListEntities() {
        List<Computer> computersList = computerService.listEntities(null);
        assertEquals(61, computersList.size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testListEntitiesOrdered() {
        Map<String, String> orderMap = new HashMap<String, String>();
        orderMap.put("name", "asc");
        List<Computer> computersList = computerService.listEntities(orderMap);
        assertEquals("", computersList.size(), 61);
        assertNotEquals("The first element's ID should not be 1 since it is ordered.", 1, computersList.get(1).getId());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCountEntities() {
        computerService.countEntities(null);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testListAndCountEntities() {
        List<Computer> computersList = computerService.listEntities(null);
        assertNotNull(computersList);
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testListSearch() {
        List<Computer> computerList = new ArrayList<Computer>();
        computerList = computerService.listSearch("Apple", null);
        assertNotNull("List of computers should not be empty.", computerList);
        assertEquals("Should have 19 computers with 'Apple' search.", 24, computerList.size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadPages() {
        List<Computer> computerList = new ArrayList<Computer>();
        Page p = new Page(2, 10, 5); // current, size, count
        p.setSearch("Apple");
        computerList = computerService.readPages(p, null);
        assertNotNull("List of computers should not be empty.", computerList);
        assertTrue("Should have a page of 10 computers.", computerList.size() == 10);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadOne() {
        Computer computer = computerService.readOne(1);
        assertTrue(computer instanceof Computer);
        assertNotNull("Computer should not be null.", computer);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadOneWrongId() {
        Computer computer = computerService.readOne(63);
        assertNull("Computer should be null since there isn't a 63rd db entry.", computer);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreate() {
        ComputerBuilder builder = new ComputerBuilder();
        Computer computer = builder.name("testcreate").build();
        assertTrue("Computer has not been created properly.", computerService.create(computer) > 0);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdate() {
        ComputerBuilder builder = new ComputerBuilder();
        Computer computer = builder.name("testupdate").build();
        computerService.update(1, computer);
        assertEquals("Computer was not updated properly.", computer.getName(), computerService.readOne(1).getName());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete() {
        computerService.delete(1);
        Computer computer = computerService.readOne(1);
        assertTrue("Computer has not been deleted properly.", Objects.isNull(computer));
    }
}

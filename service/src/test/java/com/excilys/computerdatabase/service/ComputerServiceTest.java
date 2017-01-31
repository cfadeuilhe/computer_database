package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertEquals;
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

import com.excilys.computerdatabase.model.Computer;

@ContextConfiguration(locations = "classpath:Spring-Module-Test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ComputerServiceTest {

    @Autowired
    ComputerService computerService;

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
    public void testListEntities() {
        List<Computer> computersList = computerService.listEntities(null);
        long count = computerService.countEntities(null);
        assertEquals(computersList.size(), count);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadOne() {
        Computer computer = computerService.readOne(1);
        assertTrue(computer instanceof Computer);
        assertNotNull(computer);
    }
}

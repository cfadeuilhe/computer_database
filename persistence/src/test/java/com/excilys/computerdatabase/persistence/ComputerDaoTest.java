package com.excilys.computerdatabase.persistence;

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

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.model.Page;
import com.excilys.computerdatabase.persistence.ComputerDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:Spring-Module-Test.xml")
public class ComputerDaoTest {

    @Autowired
    ComputerDao computerDao;

    @Test
    @Transactional
    @Rollback(true)
    public void testRead() {
        List<Computer> computerList = new ArrayList<Computer>();
        computerList = computerDao.read(null);
        assertNotNull("List of computers should not be empty.", computerList);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadOrdered() {
        Map<String, String> orderMap = new HashMap<String, String>();
        orderMap.put("name", "asc");
        List<Computer> computerList = new ArrayList<Computer>();
        computerList = computerDao.read(orderMap);
        assertNotNull("List of computers should not be empty.", computerList);
        assertNotEquals("The first element's ID should not be 1 since it is ordered.", computerList.get(1).getId(), 1);
    }

    /**
     * Tests count(), without search parameter.
     * 
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testCount() {
        long count = computerDao.count(null);
        assertTrue("Count method should return the same number as read().size.",
                count == computerDao.read(null).size());
    }

    /**
     * Tests count(), with search parameter.
     * 
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testCountSearch() {
        long count = computerDao.count("Apple");
        assertTrue("Count method should return the same number as read().size.",
                count == computerDao.readSearch("Apple", null).size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadSearch() {
        List<Computer> computerList = new ArrayList<Computer>();
        computerList = computerDao.readSearch("Apple", null);
        assertNotNull("List of computers should not be empty.", computerList);
        assertEquals("Should have 44 computers with 'Apple' search.", 24, computerList.size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadPages() {
        List<Computer> computerList = new ArrayList<Computer>();
        Page p = new Page(2, 10, 5); // current, size, count
        p.setSearch("Apple");
        computerList = computerDao.readPages(p, null);
        assertNotNull("List of computers should not be empty.", computerList);
        assertTrue("Should have a page of 10 computers.", computerList.size() == 10);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadOne() {
        Computer computer = computerDao.readOne(1);
        assertTrue(computer instanceof Computer);
        assertNotNull("Computer should not be null.", computer);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadOneWrongId() {
        Computer computer = computerDao.readOne(63);
        assertTrue("Computer should be null since there isn't a 63rd db entry.", Objects.isNull(computer));
        assertNull("Computer should be null since there isn't a 63rd db entry.", computer);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCreate() {
        ComputerBuilder builder = new ComputerBuilder();
        Computer c = builder.name("testcreate").build();
        assertTrue("Computer has not been created properly.", computerDao.create(c) > 0);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdate() {
        ComputerBuilder builder = new ComputerBuilder();
        Computer c = builder.name("testupdate").build();
        computerDao.update(1, c);
        assertEquals("Computer was not updated properly.", computerDao.readOne(1).getName(), c.getName());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete() {
        computerDao.delete(1);
        Computer c = computerDao.readOne(1);
        assertTrue("Computer has not been deleted properly.", Objects.isNull(c));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteByCompany() {
        try {
            computerDao.deleteByCompany(1);
        } catch (PersistenceException e) {
            assertTrue(true);
        }
        ;
        Computer computer = computerDao.readOne(1);
        assertNull("Computer with company number 1 has not been deleted", computer);
        computer = computerDao.readOne(6);
        assertNull("Computer with company number 1 has not been deleted", computer);
    }
}

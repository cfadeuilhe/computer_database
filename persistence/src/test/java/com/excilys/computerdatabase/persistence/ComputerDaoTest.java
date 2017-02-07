package com.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.persistence.ComputerDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:Spring-Module-Test.xml")
public class ComputerDaoTest {

    @Autowired
    ComputerDao computerDao;

    /**
     * Tests count(), without search parameter.
     * 
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testCreate() {
        ComputerBuilder builder = new ComputerBuilder();
        Computer c = builder.name("testcreate").build();
        assertTrue(computerDao.create(c) > 0);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDelete() {
        computerDao.delete(1);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadOne() {
        Computer computer = computerDao.readOne(1);
        assertTrue(computer instanceof Computer);
        assertNotNull(computer);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadAndCount() {
        List<Computer> computerList = new ArrayList<Computer>();
        computerList = computerDao.read(null);
        assertNotNull(computerList);
        long count = computerDao.count(null);
        assertTrue(count == computerList.size());
    }
}

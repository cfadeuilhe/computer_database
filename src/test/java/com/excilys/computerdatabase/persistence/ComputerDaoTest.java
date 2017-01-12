package com.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Entity;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.persistence.ComputerDao;

public class ComputerDaoTest {

    /**
     * Tests count(), without search parameter.
     * 
     */
    
    @Test
    public void testCreate(){
        ComputerBuilder builder = new ComputerBuilder();
        Computer c = builder.name("testcreate").build();
        ConnectionDao.INSTANCE.initConnection();
        ComputerDao cDao = ComputerDao.INSTANCE;
        assertTrue(cDao.create(c) > 0);
    }
    
    @Test
    public void testDelete(){
        ConnectionDao.INSTANCE.initConnection();
        ComputerDao cDao = ComputerDao.INSTANCE;
        cDao.delete(76);
    }
    
    @Test
    public void testReadOne() {
        ConnectionDao.INSTANCE.initConnection();
        ComputerDao cDao = ComputerDao.INSTANCE;
        Computer computer = (Computer) cDao.readOne(1);
        assertTrue(computer instanceof Computer);
        assertNotNull(computer);
    }
    
    @Test
    public void testReadAndCount(){
        ConnectionDao.INSTANCE.initConnection();
        ComputerDao cDao = ComputerDao.INSTANCE;
        List<Entity> computerList = new ArrayList<Entity>();
        computerList = cDao.read();
        assertNotNull(computerList);
        long count = cDao.count(null);
        assertTrue(count == computerList.size());
    }
}

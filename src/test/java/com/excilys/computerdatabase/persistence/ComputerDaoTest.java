package com.excilys.computerdatabase.persistence;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.computerdatabase.persistence.ComputerDao;

public class ComputerDaoTest {

    @Test
    /**
     * Tests count(), without search parameter.
     * 
     */
    public void testCount() {
        ComputerDao cDao = ComputerDao.INSTANCE;
        long count = cDao.count(null);
            assertNotNull(count);
        assertTrue(count == 563);
    }
}
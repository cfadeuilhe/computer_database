package com.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.excilys.computerdatabase.model.Company;

public class CompanyDaoTest {
    
    /**
     * Tests readOne().
     * 
     */
    @Test
    public void testReadOne() {
        ConnectionDao.INSTANCE.initConnection();
        CompanyDao cDao = CompanyDao.INSTANCE;
        Company company = (Company) cDao.readOne(1);
        assertNotNull(company);
    }
}

package com.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:Spring-Module-Test.xml")
public class CompanyDaoTest {

    @Autowired
    private CompanyDao companyDao;

    @Before
    public void initMocks(){
        
    }
    /**
     * Tests readOne().
     * 
     */
    @Test
    @Transactional
    @Rollback(true)
    public void testReadOne() {
        Company company = companyDao.readOne(1);
        assertNotNull(company);
    }
}

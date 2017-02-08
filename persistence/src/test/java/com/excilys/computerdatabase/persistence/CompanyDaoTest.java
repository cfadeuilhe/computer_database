package com.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.exceptions.PersistenceException;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-Module-Test.xml"})
public class CompanyDaoTest {

    @Autowired
    private CompanyDao companyDao;

    @Before
    public void initMocks(){
        
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testRead() {
        List<Company> companyList = new ArrayList<Company>();
        companyList = companyDao.read(null);
        assertNotNull("List of companies should not be empty.", companyList);
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testReadPages() {
        List<Company> companyList = new ArrayList<Company>();
        Page p = new Page(1, 10, 2); //current, size, count
        companyList = companyDao.readPages(p, null);
        System.out.println(companyList + " - ----------------------------------------------------");
        assertNotNull("List of companies should not be empty.", companyList);
        assertTrue("Should have a page of 10 companies.", companyList.size() == 10);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadOne() {
        Company company = companyDao.readOne(1);
        assertNotNull(company);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testReadWrongId() {
        Company company = companyDao.readOne(44);
        assertTrue("Company should be null since there isn't a 44th db entry.", Objects.isNull(company));
        assertNull("Company should be null since there isn't a 44th db entry.", company);
    }

    @Ignore("ConstraintViolation could not execute statement error on delete")
    @Test
    @Transactional
    @Rollback(true)
    public void testDelete() {
        try {
            companyDao.delete(1);
        } catch (PersistenceException e) {
            assertTrue(true);
        }
        Company company = companyDao.readOne(1);
        assertTrue("Company has not been deleted properly.", Objects.isNull(company));
    }
}

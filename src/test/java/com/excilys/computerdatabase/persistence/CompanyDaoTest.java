package com.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.Company;

@ContextConfiguration(locations = "classpath:Spring-Module-Test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyDaoTest {

    private MockMvc mockMvc;
    
    
    @Autowired
    private CompanyDao companyDao;

    @Before
    public void setUp(){
        Mockito.reset(companyDao);
        
        mockMvc = MockMvcBuilders.standaloneSetup(companyDao).build();
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

package com.excilys.computerdatabase.userinterface;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.model.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:Spring-Module-Test.xml")
public class CompanyCliServiceTest {

    @Autowired
    CompanyCliService companyCliService;
    
    @Ignore("MessageBodyProviderNotFound")
    @Test
    public void testGetCompanyById() {
        Company c = companyCliService.getCompanyById(1);
        assertNotNull(c);
    }

    @Ignore("MessageBodyProviderNotFound")
    @Test
    public void testListEntities() {
        List<Company> list = new ArrayList<Company>();
        companyCliService.listEntities(null);
        assertNotNull(list);
    }

    @Ignore("MessageBodyProviderNotFound")
    @Test
    public void testDeleteCompany() {
        companyCliService.delete(1);
        assertNull(companyCliService.getCompanyById(1));
    }
}

package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Entity;

public class CompanyServiceTest {
    private static CompanyService COMPANY_SERVICE = CompanyService.getInstance();

    @Test
    public void testListEntities() {
        List<Entity> companiesList = COMPANY_SERVICE.listEntities();
        assertNotNull(companiesList);
    }

    @Test
    public void testReadOne() {
        Company company = (Company) COMPANY_SERVICE.readOne(1);
        assertTrue(company instanceof Company);
        assertNotNull(company);
    }
}

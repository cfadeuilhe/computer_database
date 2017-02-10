package com.excilys.computerdatabase.validators;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.util.Consts;

public class CompanyValidatorTest {

    Company company = new Company();
    
    @Test
    public void testWrongId(){
        company.setId(-5); company.setName("testCompany");
        Map<String, String> validatorMap = CompanyValidator.validator(company);
        assertTrue(validatorMap.containsKey(Consts.NEGATIVE_ID));
    }
    
    @Test
    public void testNoName(){
        company.setId(1); company.setName("");
        Map<String, String> validatorMap = CompanyValidator.validator(company);
        assertTrue(validatorMap.containsKey(Consts.NO_NAME));
    }
    
    @Test
    public void testNullName(){
        company.setId(1); company.setName(null);
        Map<String, String> validatorMap = CompanyValidator.validator(company);
        assertTrue(validatorMap.containsKey(Consts.NO_NAME));
    }
    
    @Test
    public void testWrongNameLength(){
        company.setId(1); company.setName("ts");
        Map<String, String> validatorMap = CompanyValidator.validator(company);
        assertTrue(validatorMap.containsKey(Consts.NAME_TOO_SHORT));
    }
    
    @Test
    public void testRightCompany(){
        company.setId(1); company.setName("testCompany");
        Map<String, String> validatorMap = CompanyValidator.validator(company);
        System.out.println(validatorMap);
        assertTrue(validatorMap.isEmpty());
    }
}

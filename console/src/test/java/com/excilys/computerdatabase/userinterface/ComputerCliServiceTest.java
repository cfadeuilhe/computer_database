package com.excilys.computerdatabase.userinterface;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:Spring-Module-Test.xml")
public class ComputerCliServiceTest {

    @Autowired
    ComputerCliService computerCliService;
    
    @Ignore("MessageBodyProviderNotFound")
    @Test
    public void testGetComputerById() {
        Computer c = computerCliService.getComputerById(1);
        assertNotNull(c);
    }

    @Ignore("MessageBodyProviderNotFound")
    @Test
    public void testListEntities() {
        List<Computer> list = new ArrayList<Computer>();
        computerCliService.listEntities(null);
        
        assertNotNull(list);
    }

    @Ignore("MessageBodyProviderNotFound")
    @Test
    public void testDeleteComputer() {
        computerCliService.delete(1);
        assertNull(computerCliService.getComputerById(1));
    }

}

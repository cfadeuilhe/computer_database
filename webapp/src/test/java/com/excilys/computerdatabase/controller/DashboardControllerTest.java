package com.excilys.computerdatabase.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.service.ComputerService;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:Spring-Module-Test.xml")
public class DashboardControllerTest {

    @Autowired
    private ComputerService computerServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Before
    public void initMocks(){
        
    }
    @Test
    public void testA() throws Exception{
        
        ComputerBuilder cBuilder = new ComputerBuilder();
        Computer computer = cBuilder.name("test").id(1).introducedDate(LocalDate.now()).company(new Company(1, "tesCompany")).build();
    
        when(computerServiceMock.listEntities(any(Map.class))).thenReturn(Arrays.asList(computer));
        
       // this.mockMvc.perform(get("/dashboard"));
    }
}
/*package com.excilys.computerdatabase.mapper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;

public class DtoMapperTest {

    Computer computer; Company company;
    ComputerDto computerDto;
    
    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);

        company = Mockito.mock(Company.class);
        Mockito.when(company.getId()).thenReturn(1L);
        Mockito.when(company.getName()).thenReturn("TestCompany");
        
        computer = Mockito.mock(Computer.class);
        Mockito.when(computer.getId()).thenReturn(2L);
        Mockito.when(computer.getName()).thenReturn("TestComputer");
        Mockito.when(computer.getIntroducedDate()).thenReturn(null);
        Mockito.when(computer.getDiscontinuedDate()).thenReturn(null);
        Mockito.when(computer.getCompany()).thenReturn(company);
    }
    
    @Test
    public void testComputerToDto(){
        ComputerDto c = DtoMapper.computerToDto(computer);
        assertEquals(c.getId(), 1);
        assertEquals(c.getName(), "TestComputer");
    }
    
    @Test
    public void testDtoToComputer(){
        
    }
}*/

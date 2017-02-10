package com.excilys.computerdatabase.mapper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;

public class DtoMapperTest {

    Computer computerMock; Company companyMock;
    ComputerDto computerDtoMock;
    
    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);

        companyMock = Mockito.mock(Company.class);
        Mockito.when(companyMock.getId()).thenReturn(1L);
        Mockito.when(companyMock.getName()).thenReturn("TestCompany");
        
        computerMock = Mockito.mock(Computer.class);
        Mockito.when(computerMock.getId()).thenReturn(2L);
        Mockito.when(computerMock.getName()).thenReturn("TestComputer");
        Mockito.when(computerMock.getIntroducedDate()).thenReturn(null);
        Mockito.when(computerMock.getDiscontinuedDate()).thenReturn(null);
        Mockito.when(computerMock.getCompany()).thenReturn(companyMock);
        
        computerDtoMock = Mockito.mock(ComputerDto.class);
        Mockito.when(computerDtoMock.getId()).thenReturn(3L);
        Mockito.when(computerDtoMock.getName()).thenReturn("TestDtoComputer");
        Mockito.when(computerDtoMock.getIntroducedDate()).thenReturn(null);
        Mockito.when(computerDtoMock.getDiscontinuedDate()).thenReturn(null);
        Mockito.when(computerDtoMock.getCompanyId()).thenReturn(1L);
        Mockito.when(computerDtoMock.getCompanyName()).thenReturn("TestDtoCompany");
    }
    
    @Test
    public void testComputerToDto(){
        ComputerDto computerDto = DtoMapper.computerToDto(computerMock);
        assertEquals(2, computerDto.getId());
        assertEquals("TestComputer", computerDto.getName());
        assertEquals(1, computerDto.getCompanyId());
        assertEquals("TestCompany", computerDto.getCompanyName());
    }
    
    @Test
    public void testDtoToComputer(){
        Computer computer = DtoMapper.dtoToComputer(computerDtoMock);
        assertEquals(3, computer.getId());
        assertEquals("TestDtoComputer", computer.getName());
    }
}

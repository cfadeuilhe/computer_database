package com.excilys.computerdatabase.mapper;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Page;

public class RequestMapperTest {

    Map<String, String> requestMap;
    List<Computer> list;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        requestMap = Mockito.mock(Map.class);
        Mockito.when(requestMap.get("search")).thenReturn("Apple");
        Mockito.when(requestMap.get("page")).thenReturn("1");
        Mockito.when(requestMap.get("limit")).thenReturn("10");
        Mockito.when(requestMap.get("computerName")).thenReturn("computer");
        Mockito.when(requestMap.get("companyId")).thenReturn("2");
        Mockito.when(requestMap.get("id")).thenReturn("666");
        Mockito.when(requestMap.get("introduced")).thenReturn("10/10/2016");
        Mockito.when(requestMap.get("discontinued")).thenReturn("15/10/2016");
        
        list = Mockito.mock(List.class);
        Mockito.when(list.size()).thenReturn(40);
    }

    @Test
    public void testRequestToPage(){
        Page p = RequestMapper.requestToPage(requestMap, list);
        assertEquals(1, p.getCurrentPage());
        assertEquals(10, p.getPageSize());
        assertEquals(40/10, p.getPageCount());
        assertEquals("Apple", p.getSearch());
    }
    
    @Test
    public void testToComputerDto(){
        ComputerDto computerDto = RequestMapper.toComputerDto(requestMap);
        assertEquals("computer", computerDto.getName());
        assertEquals(666, computerDto.getId());
        assertEquals(2, computerDto.getCompanyId());
        assertEquals("10/10/2016", computerDto.getIntroducedDate());
        assertEquals("15/10/2016", computerDto.getDiscontinuedDate());
    }
    
    @Test
    public void testCheckIsNumber() {
        assertTrue(RequestMapper.checkIsNumber("123"));
    }

    @Test
    public void testCheckIsNumberNotANumber() {
        assertFalse(RequestMapper.checkIsNumber("ThisIsNotANumber"));
    }
}

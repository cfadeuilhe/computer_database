/*package test.java.com.computerdatabase.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import main.java.com.computerdatabase.model.Entity;
import main.java.com.computerdatabase.model.Computer;
import main.java.com.computerdatabase.model.Computer.ComputerBuilder;
import main.java.com.computerdatabase.persistence.ComputerDao;
import main.java.com.computerdatabase.service.ComputerService;

public class TestComputerService {
	private ComputerBuilder builder = new ComputerBuilder();
	private ComputerDao mock;
	private ComputerService test;
    
	
	@Before
	public void setUp() {
	    //MockitoAnnotations.initMocks(this);
		mock = mock(ComputerDao.class);
		test = new ComputerService(mock);
	}
	
	@Test
	public void testList() {

		assertNull(test.listComputers());
		
		List<Entity> computerList = new ArrayList<Entity>();
		Computer c = builder.name("test").build();
		computerList.add(c);
		
		when(mock.read()).thenReturn(computerList);
		test.listComputers();
		
		//List<Entity> list = new ArrayList<Entity>();
		//list = test.listComputers();
		assertNotNull(test.listComputers());
		//long l = list.size();
		//assertEquals(l,574);
	}
}*/

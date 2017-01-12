package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Entity;


public class ComputerServiceTest {
    private static ComputerService COMPUTER_SERVICE = ComputerService.getInstance();
    
    @Test
    public void testCountEntities(){
        COMPUTER_SERVICE.countEntities(null);
    }
    
    @Test
    public void testListAndCountEntities() {
        List<Entity> computersList = COMPUTER_SERVICE.listEntities();
        assertNotNull(computersList);
    }
    
    @Test
    public void testListEntities() {
        List<Entity> computersList = COMPUTER_SERVICE.listEntities();
        long count = COMPUTER_SERVICE.countEntities(null);
        assertEquals(computersList.size(), count);
    }
    
    @Test
    public void testReadOne() {
        Computer computer = (Computer) COMPUTER_SERVICE.readOne(1);
        assertTrue(computer instanceof Computer);
        assertNotNull(computer);
    }
}

package com.excilys.computerdatabase.validators;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.Computer.ComputerBuilder;
import com.excilys.computerdatabase.util.Consts;

public class ComputerValidatorTest {

    ComputerBuilder computerBuilder = new ComputerBuilder();
    Computer computer = computerBuilder.name("").build();
    
    @Test
    public void testWrongId(){
        computer.setId(-5); computer.setName("testComputer");
        
        Map<String, String> validatorMap = ComputerValidator.validator(computer);
        assertTrue(validatorMap.containsKey(Consts.NEGATIVE_ID));
    }
    
    @Test
    public void testNoName(){
        computer.setId(1); computer.setName("");
        
        Map<String, String> validatorMap = ComputerValidator.validator(computer);
        assertTrue(validatorMap.containsKey(Consts.NO_NAME));
    }
    
    @Test
    public void testNullName(){
        computer.setId(1); computer.setName(null);
        
        Map<String, String> validatorMap = ComputerValidator.validator(computer);
        assertTrue(validatorMap.containsKey(Consts.NO_NAME));
    }
    
    @Test
    public void testWrongNameLength(){
        computer.setId(1); computer.setName("ts");
        
        Map<String, String> validatorMap = ComputerValidator.validator(computer);
        assertTrue(validatorMap.containsKey(Consts.NAME_TOO_SHORT));
    }

    /*
    @Test
    public void testWrongDateOrder(){
        computer.setId(1); computer.setName("testComputer"); //computer.setIntroducedDate(introducedDate);
        
        Map<String, String> validatorMap = ComputerValidator.validator(computer);
        assertTrue(validatorMap.containsKey(Consts.NAME_TOO_SHORT));
    }*/
    
    @Test
    public void testRightComputer(){
        computer.setId(1); computer.setName("testComputer");
        
        Map<String, String> validatorMap = ComputerValidator.validator(computer);
        System.out.println(validatorMap);
        assertTrue(validatorMap.isEmpty());
    }
}

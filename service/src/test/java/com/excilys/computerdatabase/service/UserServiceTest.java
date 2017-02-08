package com.excilys.computerdatabase.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:Spring-Module-Test.xml")
public class UserServiceTest {

    @Autowired
    UserService userService;
        
    @Test
    @Transactional
    @Rollback(true)
    public void testReadByName(){
        User user = userService.readByName("user");
        assertNotNull("user object should not be null.", user);
        assertTrue("username should be 'user'.", user.getUsername().equals("user"));
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testReadByNameWrong() {
        User user = userService.readByName("doesNotExist");
        assertTrue("user object should be null.", Objects.isNull(user));
        assertNull("user object should be null.", user);
    }
}

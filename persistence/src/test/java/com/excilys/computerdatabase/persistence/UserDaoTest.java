package com.excilys.computerdatabase.persistence;

import static org.junit.Assert.*;

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
@ContextConfiguration(locations = {"classpath:Spring-Module-Test.xml"})
public class UserDaoTest {

    @Autowired
    UserDao userDao;
    
    @Test
    @Transactional
    @Rollback(true)
    public void testReadByName() {
        User user = userDao.readByName("user");
        assertNotNull("user object should not be null.", user);
        assertTrue("username should be 'user'.", user.getUsername().equals("user"));
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void testReadByNameWrong() {
        User user = userDao.readByName("doesNotExist");
        assertTrue("user object should be null.", Objects.isNull(user));
        assertNull("user object should be null.", user);
    }
}

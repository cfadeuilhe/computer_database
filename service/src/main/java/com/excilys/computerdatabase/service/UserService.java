package com.excilys.computerdatabase.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.User;
import com.excilys.computerdatabase.persistence.UserDao;

@Service
public class UserService implements InterfaceService<User>, UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public User readOne(long id) {
        User u = userDao.readOne(id);
        return u;
    }

    @Override
    public User readByName(String name) {
        User u = userDao.readByName(name);
        return u;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user = this.readByName(username);

        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
                true, true, true, user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList()));
    }

}

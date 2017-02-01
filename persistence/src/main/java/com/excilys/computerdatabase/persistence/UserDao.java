package com.excilys.computerdatabase.persistence;

import java.util.function.Supplier;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.model.QUser;
import com.excilys.computerdatabase.model.User;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

@Repository
public class UserDao implements InterfaceDao<User> {

    private static QUser user = QUser.user;

    @Autowired
    SessionFactory sessionFactory;

    private Supplier<HibernateQueryFactory> queryFactory = () -> new HibernateQueryFactory(
            sessionFactory.getCurrentSession());

    public User readByName(String name) {
        User u = queryFactory.get().selectFrom(user).where(user.username.eq(name)).fetchOne();
        return u;
    }

}

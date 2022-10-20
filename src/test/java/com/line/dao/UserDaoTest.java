package com.line.dao;

import com.line.dao.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)

class UserDaoTest {

    @Autowired
    ApplicationContext context;

    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {

        UserDao userDao = context.getBean("aUserDao", UserDao.class);
        userDao.deleteAll();

        User user = new User("1", "soonmin","1234");
        userDao.add(user);
        assertEquals(1,userDao.getCount());

        User selecteduser = userDao.select("1");
        assertEquals("soonmin", selecteduser.getName());

        userDao.deleteAll();
        assertEquals(0,userDao.getCount());
    }

    @Test
    void count() throws SQLException, ClassNotFoundException {
        UserDao userDao = context.getBean("aUserDao", UserDao.class);

        User user1 = new User("1","a","aaaa");
        User user2 = new User("2","b","bbbb");
        User user3 = new User("3","c","cccc");

        userDao.deleteAll();
        assertEquals(0,userDao.getCount());

        userDao.add(user1);
        assertEquals(1,userDao.getCount());

        userDao.add(user2);
        assertEquals(2,userDao.getCount());

        userDao.add(user3);
        assertEquals(3,userDao.getCount());

    }
}
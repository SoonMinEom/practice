package com.line.dao;

import com.line.dao.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)

class UserDaoTest {
    @Autowired
    ApplicationContext context;

    // 반복되는 객체 생성은 beforeeach로 처리!!
    UserDao userDao;

    @BeforeEach
    void setUp(){
        userDao = context.getBean("aUserDao", UserDao.class);
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {

        userDao.deleteAll();

        User user1 = new User("1","a","aaaa");

        userDao.add(user1);
        assertEquals(1,userDao.getCount());

        User selecteduser = userDao.select("1");
        assertEquals(user1.getName(), selecteduser.getName());

        userDao.deleteAll();
        assertEquals(0,userDao.getCount());
    }

    @Test
    void count() throws SQLException, ClassNotFoundException {

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

    @Test
    void select() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.select("30");
        });
    }
}
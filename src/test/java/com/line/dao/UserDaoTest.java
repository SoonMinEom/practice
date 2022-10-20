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

//ExtendWith -> 스프링의 기능을 test 클래스(junit)에서도 사용할 수 있게 해줌.
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)

class UserDaoTest {

    // Autowired = Spring을 사용해 context를 만들기 = singleton 적용.
    // 밑의 테스트를 실행할 때마다 새로 생성되는 것이 아니라, 한 번 생성해서 계속 씀.
    @Autowired
    ApplicationContext context;

    // 반복되는 객체 생성은 beforeeach로 처리!!
    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("aUserDao", UserDao.class);
        this.user1 = new User("1","a","aaaa");
        this.user2 = new User("2","b","bbbb");
        this.user3 = new User("3","c","cccc");
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
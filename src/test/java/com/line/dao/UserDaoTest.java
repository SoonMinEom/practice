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
        User user = new User("1", "soonmin","1234");
        userDao.add(user);
    }
}
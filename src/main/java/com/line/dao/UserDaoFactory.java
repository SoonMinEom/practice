package com.line.dao;

import com.line.dao.conncetionMaker.ConnectionMakerA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {

    @Bean
    public UserDao aUserDao(){
        UserDao userDao = new UserDao(new ConnectionMakerA());
        return userDao;
    }
}

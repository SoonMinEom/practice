package com.line.dao;

import com.line.dao.conncetionMaker.ConnectionMaker;
import com.line.dao.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException, ClassNotFoundException {

        Connection conn = connectionMaker.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users(id, name, password) VALUES(?,?,?)"
        );

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public User select(String input) throws ClassNotFoundException, SQLException {

        Connection conn = connectionMaker.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "SELECT id, name, password FROM users where id =?"
        );

        ps.setString(1, input);

        ResultSet rs =ps.executeQuery();
        rs.next();

        User user = new User(rs.getString("id"), rs.getString("name"),rs.getString("password"));

        rs.close();
        ps.close();
        conn.close();

        return user;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        Connection conn = connectionMaker.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM users"
        );

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public static void main(String[] args) {


    }
}

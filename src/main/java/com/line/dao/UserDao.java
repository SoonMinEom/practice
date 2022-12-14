package com.line.dao;

import com.line.dao.conncetionMaker.ConnectionMaker;
import com.line.dao.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

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

        ResultSet rs = ps.executeQuery();

        // 예외 처리 잡아내기
        User user = null;

        // next()로 데이터가 있는 지 확인하고, 있을 경우에만 정보 가져오기
        if (rs.next()) {
            user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
        }

        rs.close();
        ps.close();
        conn.close();

        // 데이터가 없어서 객체가 안만들어지면, 예외처리
        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return user;
    }

    public void deleteAll() {

        Connection conn = null;
        PreparedStatement ps = null;

        // DB 연결 시 발생할 예외를 try/catch로 잡기.
        try {

            conn = connectionMaker.getConnection();
            ps = conn.prepareStatement("DELETE FROM users");
            ps.executeUpdate();

        } catch (ClassNotFoundException e) {

            throw new RuntimeException(e);

        } catch (SQLException e) {

            throw new RuntimeException(e);

        } finally { // 예외가 발생하든 안하든, .close()는 반드시 실행!

            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }


    public int getCount() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = connectionMaker.getConnection();
            ps = conn.prepareStatement("SELECT count(*) from users");

            rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            return count;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {// 예외가 발생하든 안하든, .close()는 반드시 실행!
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static void main(String[] args) {


    }
}

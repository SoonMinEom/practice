package com.line.dao.conncetionMaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class ConnectionMakerA implements ConnectionMaker{

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> evn = System.getenv();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                evn.get("DB_HOST"), evn.get("DB_NAME"), evn.get("DB_PASSWORD")
        );

        return conn;
    }
}

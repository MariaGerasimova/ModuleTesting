package ru.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class DBConect {
    //static String dbUrl = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:init.sql'";
    //static String dbUrl = "jdbc:h2:tcp://localhost:9092/default";
    static String dbUrl = "jdbc:h2:.\\Office";

    //static String dbUser = "sa";
    //static String dbPassword = "";
    static HashSet dbPassword1 ;

    public static Connection getConnection(){

        Connection connection = null;

        try {
            //connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}

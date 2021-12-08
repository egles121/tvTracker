package com.example.tvtracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class SQLconnect {
    private String dbURL = "bootcamptvtracker.cgnkkelv2t0b.us-east-2.rds.amazonaws.com";
    private Connection conn;
    private Statement statement;

    public SQLconnect () {

        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + dbURL, "admin", "Born2cess!");
            statement = conn.createStatement();



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Avoid leak if an exception was thrown in createStatement
            if (statement == null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public void addSubject(String subject) throws SQLException {
//        statement.executeUpdate("INSERT INTO `Subject` VALUES ('" +
//                subject + "')" );
//    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected static void getEpisodes() {
        SQLconnect connection = new SQLconnect();

        try {
            ResultSet resultSet = connection.statement.executeQuery("SELECT * FROM TVtrackerDB.tv_shows LIMIT 3;");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String rating = resultSet.getString("rating");
                String officialSite = resultSet.getString("officialSite");
                System.out.println(name);
                System.out.println("Rating: " + rating);
                System.out.println("Link: " + officialSite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connection.close();
    }







}
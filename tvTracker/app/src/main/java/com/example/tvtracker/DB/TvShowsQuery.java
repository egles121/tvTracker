package com.example.tvtracker.DB;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TvShowsQuery extends SqlQuery {

    public static String getName() {
        String query = "SELECT * FROM tv_shows LIMIT 1";
        String result = "";

        Connection connection = SqlConnection.connect();
        try {
            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    result = resultSet.getString("name");
                }
                return result;
            }
        } catch (SQLException e) {
            Log.e("SQL Error ", e.getMessage());
            try {
                connection.close();
            } catch (SQLException er) {
                Log.e("SQL Error ", er.getMessage());
            }
        }
        return null;
    }

    // return name of the show based on provided id
    public static String getName(int id) {
        String query = "SELECT name FROM tv_shows WHERE id = ?";
        String result = "";

        Connection connection = SqlConnection.connect();
        try {
            if (connection != null) {
                PreparedStatement prepStatement = connection.prepareStatement(query);
                prepStatement.setInt(1, id);
                ResultSet resultSet = prepStatement.executeQuery(query);
                while (resultSet.next()) {
                    result = resultSet.getString("name");
                }
                return result;
            }
        } catch (SQLException e) {
            Log.e("SQL Error ", e.getMessage());
            try {
                connection.close();
            } catch (SQLException er) {
                Log.e("SQL Error ", er.getMessage());
            }
        }
        return null;
    }




}

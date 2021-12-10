package com.example.tvtracker.DB;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class users extends SqlQuery {

    public static String insertDetails(String username, String password, String email) {
        String query = "INSERT INTO Users (user_id, username, password, email) VALUES (null, ?, ?, ?)";

        Connection connection = SqlConnection.connect();
        try {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);
                preparedStatement.setString(3,email);
                preparedStatement.executeUpdate();
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

    public static boolean userExists(String username) {

        String query = "SELECT username FROM Users WHERE username = ?;";

        Connection connection = SqlConnection.connect();
        try {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,username);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet == null) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }

        } catch (SQLException e) {
            Log.e("SQL Error ", e.getMessage());
            try {
                connection.close();
            } catch (SQLException er) {
                Log.e("SQL Error ", er.getMessage());
            }
        }

    return true;
    }


}

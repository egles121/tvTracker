package com.example.tvtracker.DB;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserQuery extends SqlQuery{


    public static int userExists(String username, String password) {
        String query = "SELECT user_id FROM Users WHERE username = ? AND password = ?";

        Connection connection = SqlConnection.connect();
        try {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet == null) {
                    return 0;
                } else {
                    while(resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            Log.e("SQL Error ", e.getMessage());
            try {
                connection.close();
            } catch (SQLException er) {
                Log.e("SQL Error ", er.getMessage());
            }
        }
        return 0;
    }

}

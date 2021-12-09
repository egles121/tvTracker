package com.example.tvtracker.DB;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TvShowsQuery extends SqlQuery {

    // TODO change this method to accept "ID" of a tv-show as a parameter
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

}

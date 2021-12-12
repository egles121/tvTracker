package com.example.tvtracker.DB;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tvtracker.Favorites;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User_dataQuery extends SqlQuery{

    public static void saveFavoriteShow(int userId, int tvShowId) {
        String query = "INSERT INTO user_data (user_id, tvShow_id) VALUES (?, ?);";

        Connection connection = SqlConnection.connect();
        try {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, tvShowId);
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
    }

    public static void delFavoriteShow(int userId, int tvShowId) {
        String query = "DELETE FROM user_data WHERE user_id = ? AND tvShow_id = ?;";

        Connection connection = SqlConnection.connect();
        try {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, tvShowId);
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
    }

    public static ArrayList<Integer> favoriteShows(int userID) {
        String query = "SELECT DISTINCT tvShow_id FROM user_data WHERE user_id = ?";
        ArrayList<Integer> resultArray = new ArrayList<Integer>();

        Connection connection = SqlConnection.connect();
        try {
            if (connection != null) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, userID);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    resultArray.add(resultSet.getInt(1));
                }
                return resultArray;
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

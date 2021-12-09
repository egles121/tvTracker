package com.example.tvtracker.DB;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
    private static final String DbUrl = "bootcamptvtracker.cgnkkelv2t0b.us-east-2.rds.amazonaws.com/TVtrackerDB";
    Connection connection;

    private SqlConnection() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + DbUrl, "admin", "Born2cess!");
        } catch (ClassNotFoundException e) {
            Log.e("Error ", e.getMessage());
        } catch (SQLException e) {
            Log.e("SQL Error ", e.getMessage());
        }
    }

    public static Connection connect() {
        SqlConnection sqlConnection = new SqlConnection();
        return sqlConnection.connection;
    }

}

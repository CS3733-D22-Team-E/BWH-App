package edu.wpi.energetic_easter_bunnies.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBWrapper {
    Connection connection = null;

    public DBWrapper() {
        try {
            connection = DriverManager.getConnection("jdbc:derby:myDB;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void quit() throws SQLException {
        connection.close();
    }

}

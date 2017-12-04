/*
	Description: Basic class to give access to the database
	History: Class created: 11/17/2017 - Maximiliano Pozzi
*/
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class MySQLAccess {
	
    static Connection connect = null;
    static PreparedStatement statement = null;
    static ResultSet resultSet = null;

    // Creates a connection with the DB
    static void connectToDB() throws SQLException, ClassNotFoundException {
        // Loads the MySQL connector
        Class.forName("com.mysql.jdbc.Driver");
        connect = DriverManager.getConnection("jdbc:mysql://localhost/weatherBoard?user=root&password=castillo3&useSSL=false");
    }
    // You need to close the resultSet
    static void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
        	
        }
    }

}

package org.example.database;

import org.example.guis.RegisterGui;
import org.example.objects.User;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.*;

public class DBConnection {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/bankapp";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "123456";

    // validate if this user exits and there is an object with it information
    public static User validateLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            // estabilish a connection to the database using configutations
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME,DB_PASSWORD);
            // create sql query
            PreparedStatement statement = connection.prepareStatement(query);

            // replace the ? with values
            // parameter index refering to the iteration of the ? so 1 is the first ? and 2 is the second ?
            statement.setString(1, username);
            statement.setString(2, password);

            // execute query and store into a result set
            ResultSet resultSet = statement.executeQuery();

            // next() returns true or false
            // true = query returned data and result set now points to the first row
            // false = query returned no data and result set equals to null
            if (resultSet.next()){
                // sucess
                // get id
                int userId = resultSet.getInt("id");

                // get current balance
                BigDecimal currentBalance = resultSet.getBigDecimal("current_balance");

                // return user object
                return new User(userId, username, password, currentBalance);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // not valid user
        return null;
    }

    public void registerAccount(String username, String password) {
        String query = "INSERT INTO users (username, password, current_balance) VALUES (?, ?, ?)";

        int userId = 0;
        BigDecimal initialCurrent = new BigDecimal(0);

        User user = new User(userId, username, password, initialCurrent);
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setBigDecimal(3, user.getCurrentBalance());

            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

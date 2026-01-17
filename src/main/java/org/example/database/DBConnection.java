package org.example.database;

import org.example.guis.RegisterGui;
import org.example.objects.Transaction;
import org.example.objects.User;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

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

    // register new user to the database
    // true - register sucess
    // false - register fails
    public static boolean register(String username, String password) {
        String query = "INSERT INTO users (username, password, current_balance) VALUES (?, ?, ?)";
        try {
            // first we will need to check if the username has already been taken
            if (!chekcUser(username)) {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setString(1, username);
                statement.setString(2, password);
                statement.setBigDecimal(3, new BigDecimal(0));

                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // check if username already exists in the db
    // true - user exists
    // false - user doesn't exists
    private static boolean chekcUser(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // true - update to db was a success
    // false - update to the db was a fail
    public static boolean addTransactionToDatabase(Transaction transaction) {
        String query = "INSERT INTO transactions (user_id, transaction_type, transaction_amount, transaction_date) " +
                "VALUES (?, ?, ?, NOW())";

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement insertTransaction = connection.prepareStatement(query);

            // NOW() will put in the current date

            insertTransaction.setInt(1, transaction.getUserId());
            insertTransaction.setString(2, transaction.getTransactionType());
            insertTransaction.setBigDecimal(3, transaction.getTransactionAmount());

            insertTransaction.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // true - update balance successful
    // false - update balance fail
    public static boolean updateCurrentBalance(User user) {
        String query = "UPDATE users SET current_balance = ? WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement updateBalance = connection.prepareStatement(query);

            updateBalance.setBigDecimal(1, user.getCurrentBalance());
            updateBalance.setInt(2, user.getId());

            updateBalance.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // true - transfer was a success
    // false - transfer failed

    public static boolean transfer(User user, String transferredUsername, float transferAmount) {
        String query = "SELECT * FROM users WHERE username = ?";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement queryUser = connection.prepareStatement(query);

            queryUser.setString(1, transferredUsername);
            ResultSet resultSet = queryUser.executeQuery();

            while (resultSet.next()) {
                // perform transfer
                User transferredUser = new User(
                        resultSet.getInt("id"),
                        transferredUsername,
                        resultSet.getString("password"),
                        resultSet.getBigDecimal("current_balance")
                );

                // create transaction
                Transaction transaction = new Transaction(user.getId(), "Transfer",
                  new BigDecimal(-transferAmount), null);

                // this transaction will belong to the transferred user
                Transaction receivedTransaction = new Transaction(transferredUser.getId(), "Transfer",
                        new BigDecimal(transferAmount), null);

                // update transfer user
                transferredUser.setCurrentBalance(transferredUser.getCurrentBalance().add(BigDecimal.valueOf(transferAmount)));
                updateCurrentBalance(transferredUser);

                // update user current balance
                user.setCurrentBalance(user.getCurrentBalance().add(BigDecimal.valueOf(transferAmount)));
                updateCurrentBalance(user);

                // add these transactions to the database
                addTransactionToDatabase(transaction);
                addTransactionToDatabase(receivedTransaction);

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // get all transactions (user for past transaction)
    public static ArrayList<Transaction> getPastTransaction(User user) {
        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        String query = "SELECT * FROM transaction WHERE user_id = ?";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement selectAllTransactions = connection.prepareStatement(query);

            selectAllTransactions.setInt(1, user.getId());

            ResultSet resultSet = selectAllTransactions.executeQuery();

            // iterate throught the results (if any)
            while(resultSet.next()) {
                // create transaction obj
                Transaction transaction = new Transaction(
                        user.getId(),
                        resultSet.getString("transaction_type"),
                        resultSet.getBigDecimal("transaction_amount"),
                        resultSet.getDate("transaction_date")
                        );

                // store into array list
                pastTransactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pastTransactions;
    }
}








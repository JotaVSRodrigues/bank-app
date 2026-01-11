package org.example.objects;

import org.example.database.DBConnection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
*   User entity which is used to store user information (i.e. id, username, password and current balance)
* */
public class User {
    private int id;
    private final String username, password;
    private BigDecimal currentBalance;

    public User(int id, String username, String password, BigDecimal currentBalance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.currentBalance = currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        // store new value to 2nd decimal place
        currentBalance = currentBalance.setScale(2, RoundingMode.FLOOR);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
}

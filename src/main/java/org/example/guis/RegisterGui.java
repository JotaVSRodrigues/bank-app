package org.example.guis;

import javax.swing.*;
import java.awt.*;

public class RegisterGui extends BaseFrame {

    public RegisterGui() {
        super("Bank App Register");
    }

    @Override
    protected void addGuiComponentes() {
        // create banking app label and defining properties
        JLabel bankingAppLabel = new JLabel("Banking Application");
        bankingAppLabel.setBounds(0, 20, super.getWidth(), 40);
        bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(bankingAppLabel);

        // username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 130, getWidth() - 30, 24);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        // username field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameField);

        // password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 220, getWidth() - 50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        // create password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 250, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordField);

        // re-type password label
        JLabel retypePasswordLabel = new JLabel("Re-type Password:");
        retypePasswordLabel.setBounds(20, 310, getWidth() - 50, 24);
        retypePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(retypePasswordLabel);

        // re-type password field
        JPasswordField retypePasswordField = new JPasswordField();
        retypePasswordField.setBounds(20, 340, getWidth() - 50, 40);
        retypePasswordField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(retypePasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 460, getWidth() - 50, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        add(registerButton);

        JLabel loginLabel = new JLabel("<html><a href=\"#\">Have an account? Sign-in here</a></html>");
        loginLabel.setBounds(0, 510, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginLabel);
    }
}

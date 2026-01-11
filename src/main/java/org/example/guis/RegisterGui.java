package org.example.guis;

import org.example.database.DBConnection;
import org.example.objects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField);

                DBConnection conn = new DBConnection();
                User user = DBConnection.validateLogin(username, password);

                if (username == user.getUsername()) {
                    JOptionPane.showMessageDialog(null, "This username is already in use.");
                } else if (usernameField == null && passwordField == null) {
                    JOptionPane.showMessageDialog(null, "Some field of your registering is still null.");
                } else {
                    conn.registerAccount(username, password);
                }
            }
        });
        add(registerButton);

        JLabel loginLabel = new JLabel("<html><a href=\"#\">Have an account? Sign-in here</a></html>");
        loginLabel.setBounds(0, 510, getWidth() - 10, 30);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // dispose the actual frame
                RegisterGui.this.dispose();

                // go to the login frame
                new LoginGui().setVisible(true);
            }
        });
        add(loginLabel);
    }
}

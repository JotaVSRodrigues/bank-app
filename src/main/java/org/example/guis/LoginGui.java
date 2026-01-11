package org.example.guis;

import org.example.database.DBConnection;
import org.example.objects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
    This gui will allow user to login or launch the register gui
    This extends from the BaseFrame which emans we will need to define our own addGuiComponents()
*/
public class LoginGui extends BaseFrame {
    public LoginGui() {
        super("Banking App Login");
    }

    @Override
    protected void addGuiComponentes() {
        // create banking app label
        JLabel bankingAppLabel = new JLabel("Banking Application");

        // set the location and the size of the gui component
        bankingAppLabel.setBounds(0, 20, super.getWidth(), 40);

        // change the font style
        bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        // center text in JLabel
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // add to gui
        add(bankingAppLabel);

        // username label
        JLabel usernameLabel = new JLabel("Username");

        // getWidth() returns us the width of our frame which is about 420
        usernameLabel.setBounds(20, 120, getWidth() - 30, 24);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameLabel);

        // create username field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(usernameField);

        // password label
        JLabel passwordLabel = new JLabel("Password");

        // getWidth() returns us the width of our frame which is about 420
        passwordLabel.setBounds(20, 280, getWidth() - 50, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordLabel);

        // crate password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 320, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(passwordField);

        // create login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 460, getWidth() - 50, 40);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 20));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get username
                String username = usernameField.getText();

                // get password
                String password = String.valueOf(passwordField.getPassword());

                // validate login
                User user = DBConnection.validateLogin(username, password);

                // if user is null it means invalid, otherwise it is a valid account
                if (user != null) {
                    // means valid login

                    // dispose the gui
                    LoginGui.this.dispose();

                    // launch back app gui
                    BankingAppGui bankingAppGui = new BankingAppGui(user);
                    bankingAppGui.setVisible(true);

                    // show suces dialog
                    JOptionPane.showMessageDialog(bankingAppGui, "You login was a sucess!");
                } else {
                    // invalid login
                    JOptionPane.showMessageDialog(LoginGui.this, "Login failed...");
                }
            }
        });
        add(loginButton);

        // create register label
        JLabel registerLabel = new JLabel("<html><a href=\"#\">Don't have an account? Register Here</a></html>");
        registerLabel.setBounds(0, 510, getWidth() - 10, 30);
        registerLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // adds an event listener so when the mouse is clicked it will launch the register gui
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // dispose of this gui
                LoginGui.this.dispose();

                // launch the register gui
                new RegisterGui().setVisible(true);
            }
        });
        add(registerLabel);
    }
}

package org.example;

import org.example.db_objs.User;
import org.example.guis.BankingAppGui;
import org.example.guis.LoginGui;
import org.example.guis.RegisterGui;

import javax.swing.*;
import java.math.BigDecimal;

public class AppLauncher
{
    public static void main( String[] args ) {
        // use invokeLater to make updates to the gui more thread-safe
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new LoginGui().setVisible(true);
//                new RegisterGui().setVisible(true);

                new BankingAppGui(
                        new User(1, "username", "password", new BigDecimal("20.00"))
                ).setVisible(true);
            }
        });
    }
}

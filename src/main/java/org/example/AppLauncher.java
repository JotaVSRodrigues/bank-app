package org.example;

import org.example.guis.LoginGui;
import org.example.guis.RegisterGui;

import javax.swing.*;

public class AppLauncher
{
    public static void main( String[] args ) {
        // use invokeLater to make updates to the gui more thread-safe
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegisterGui().setVisible(true);
            }
        });
    }
}

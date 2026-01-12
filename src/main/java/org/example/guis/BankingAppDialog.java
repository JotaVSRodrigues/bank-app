package org.example.guis;

import org.example.database.DBConnection;
import org.example.objects.Transaction;
import org.example.objects.User;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class BankingAppDialog extends JDialog implements ActionListener {
    private BankingAppGui bankingAppGui;
    private User user;
    private JLabel balanceLabel, enterAmountLabel, enterUserLabel;
    private JTextField enterAmountField, enterUserField;
    private JButton actionButton;

    public BankingAppDialog(BankingAppGui bankingAppGui, User user) {
        // set the size
        setSize(400, 400);

        // add focus to the dialog (can't interact with anything else until dialog is closed)
        setModal(true);

        // loads in the center of our banking gui
        setLocationRelativeTo(bankingAppGui);

        // when user closes dialog, it releases its resources that are being used
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // prevents dialog from being resized
        setResizable(false);

        // allows us to manually specify the size and position of each component
        setLayout(null);

        // we will need reference to our gui so that we can update the current balance
        this.bankingAppGui = bankingAppGui;

        // we will need access to the user info to make updates to our db or retrieves
        this.user = user;
    }

    public void addCurrent() {
        // balance label
        balanceLabel = new JLabel("Balance: R$" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        // enter amount label
        enterAmountLabel = new JLabel("Enter Amount: ");
        enterAmountLabel.setBounds(0, 50, getWidth() - 20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountLabel);

        // enter amount field
        enterAmountField = new JTextField();
        enterAmountField.setBounds(15, 80, getWidth() - 50, 40);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterAmountField);

    }

    public void addActionButton (String actionButtonType){
        actionButton = new JButton(actionButtonType);
        actionButton.setBounds(15, 300, getWidth() - 50, 40);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        actionButton.addActionListener(this);
        add(actionButton);
    }

    public void addUserField() {
        // enter user label
        enterUserLabel = new JLabel("Enter User: ");
        enterUserLabel.setBounds(0, 160, getWidth() - 20, 20);
        enterUserLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        enterUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserLabel);

        // enter user field
        enterUserField = new JTextField();
        enterUserField.setBounds(15, 190, getWidth() - 50, 40);
        enterUserField.setFont(new Font("Dialog", Font.BOLD, 20));
        enterUserField.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterUserField);
    }

    private void handleTransaction(String transactionType, float amountVal) {
        Transaction transaction;

        if (transactionType.equalsIgnoreCase("Deposit")){
            // deposit transaction type
            // add to current balance
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amountVal)));

            // create transaction
            // we leave date null because we are going to be using the NOW() in SQL which will get the current date
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(amountVal), null);

        } else {
            // withdraw transaction type
            // subtract from current balance
            user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amountVal)));

            // we want to show a negative sign for the amount val when withdrawing
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(-amountVal), null);
        }

        // update database
        if (DBConnection.addTransactionToDatabase(transaction) && DBConnection.updateCurrentBalance(user)) {
            // reset these fields
            resetFieldsAndUpdateCurrentBalance();

            // show success dialog
            JOptionPane.showMessageDialog(this, transactionType + " Successfully!");
        } else {
            JOptionPane.showMessageDialog(this, transactionType + " Failed...");
        }
    }

    private void resetFieldsAndUpdateCurrentBalance() {
        // reset fields
        enterAmountField.setText("");

        // only appears when transfer is clicked
        if (enterUserField != null) {
            enterUserField.setText("");
        }

        // update current balance on dialog
        balanceLabel.setText("Balance R$" + user.getCurrentBalance());

        // update current balance on main gui
        bankingAppGui.getCurrentBalanceField().setText("R$" + user.getCurrentBalance());
    }

    private void handleTransfer(User user, String transferredUser, float amount){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        // get amount val
        float amountVal = Float.parseFloat(enterAmountField.getText());

        // pressed deposit
        if (buttonPressed.equalsIgnoreCase("Deposit")) {
            // we want to handle the deposit transaction
            handleTransaction(buttonPressed, amountVal);
        } else {
            // pressed withdraw or transfer

            // validate input by making sure that withdraw or transfer amount is less than current balance
            // if result is -1 means that entered amount is more, 0 means they are equal, and 1 means that
            // the entered amount is less
            int result = user.getCurrentBalance().compareTo(BigDecimal.valueOf(amountVal));
            if (result < 0){
                // display error dialog
                JOptionPane.showMessageDialog(this, "Error: input value is more than current balance");
                return;
            }

            // check to see if withdraw or transfer was pressed
            if (buttonPressed.equalsIgnoreCase("Withdraw")) {
                handleTransaction(buttonPressed, amountVal);
            } else {
                // transfer
                String transferredUser = enterUserField.getText();

                // handle transfer
            }
        }
    }
}

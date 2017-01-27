package bank.account.simulation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class BankAccountSim extends Frame implements ActionListener {

    Label label = new Label(" ");
    Label label1 = new Label(" ");
    TextField t[] = new TextField[4];
    Label l[] = new Label[4];
    Button button = new Button("Create Account");
    Button button1 = new Button("Test Account");
    BankAccount b;

    BankAccountSim() {
        addWindowListener(new NewWindowAdapter());
        setLayout(new GridLayout(2, 0));
        Panel p = new Panel();
        Panel p1 = new Panel();
        button.addActionListener(this);
        button1.addActionListener(this);
        p.setLayout(new GridLayout(5, 2));
        p1.add(label1);
        p1.add(label);
        l[0] = new Label("Account Number");
        l[1] = new Label("Initial Balance");
        l[2] = new Label("Deposit Amount");
        l[3] = new Label("Withdraw Amount");
        for (int i = 0; i < 4; i++) {
            t[i] = new TextField(10);
            p.add(l[i]);
            p.add(t[i]);
        }
        p.add(button);
        p.add(button1);
        button1.setVisible(false);
        l[2].setVisible(false);
        l[3].setVisible(false);
        t[2].setVisible(false);
        t[3].setVisible(false);
        add(p);
        add(p1);
    }

    String testAccount(int d_amt, int w_amt) {
        String message;
        b.deposit(d_amt);
        message = "Transaction Succesful";
        try {
            b.withdraw(w_amt);
        } catch (FundsInsufficientException fe) {
            fe = new FundsInsufficientException(b.amount, w_amt);
            message = String.valueOf(fe);
        }
        return message;
    }

    public void actionPerformed(ActionEvent ae) {
        String str = ae.getActionCommand();
        if (str.equals("Create Account")) {
            b = new BankAccount(Integer.parseInt(t[0].getText()),
                    Integer.parseInt(t[1].getText()));
            button1.setVisible(true);
            l[2].setVisible(true);
            l[3].setVisible(true);
            t[2].setVisible(true);
            t[3].setVisible(true);
            button.setVisible(false);
            l[0].setVisible(false);
            l[1].setVisible(false);
            t[0].setVisible(false);
            t[1].setVisible(false);
            label1.setText("Account : " + b.accno + ", Current Balance: "+b.amount);
                        return;
        } else {

            label.setText(testAccount(Integer.parseInt(t[2].getText()),
                    Integer.parseInt(t[3].getText())));
            label1.setText("Account : " + b.accno + ", Current Balance: "+b.amount);
                }
        }
        public static void main(String arg[]) {
        BankAccountSim at = new BankAccountSim();
        at.setTitle("Bank Account Test");
        at.setSize(600, 200);
        at.setVisible(true);
    }
}

class NewWindowAdapter extends WindowAdapter {

    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }
}

class BankAccount {

    int accno;
    int amount;

    BankAccount(int num, int amt) {
        accno = num;
        amount = amt;
    }

    public void deposit(int amt) {
        amount = amount + amt;
    }

    public void withdraw(int amt) throws FundsInsufficientException {
        if (amt > amount) {
            throw new FundsInsufficientException(amount, amt);
        } else {
            amount = amount - amt;
        }
    }
}

class FundsInsufficientException extends Exception {

    int balance;
    int withdraw_amount;

    FundsInsufficientException(int bal, int w_amt) {
        balance = bal;
        withdraw_amount = w_amt;
    }

    public String toString() {
        return "Your withdraw amount (" + withdraw_amount + ") is less than the balance ("+balance+"). No withdrawal was recorded.";
        }
}



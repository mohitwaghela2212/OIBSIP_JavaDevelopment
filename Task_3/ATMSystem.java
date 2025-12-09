import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ATMSystem extends JFrame {

    private CardLayout cards;
    private JPanel cardPanel;

    private JTextField txtUser;
    private JPasswordField txtPin;

    private JTextArea historyArea;

    private User currentUser;

    public ATMSystem() {
        setTitle("ATM System - Java GUI");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cards = new CardLayout();
        cardPanel = new JPanel(cards);

        cardPanel.add(loginPanel(), "login");
        cardPanel.add(menuPanel(), "menu");
        cardPanel.add(historyPanel(), "history");
        cardPanel.add(withdrawPanel(), "withdraw");
        cardPanel.add(depositPanel(), "deposit");
        cardPanel.add(transferPanel(), "transfer");

        add(cardPanel);
        cards.show(cardPanel, "login");
    }

    // ---------------- LOGIN PANEL ----------------
    private JPanel loginPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.white);
        p.setLayout(null);

        JLabel title = new JLabel("ATM Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(150, 20, 200, 40);

        JLabel lblUser = new JLabel("User ID:");
        lblUser.setBounds(120, 100, 120, 30);
        txtUser = new JTextField();
        txtUser.setBounds(200, 100, 180, 30);

        JLabel lblPin = new JLabel("PIN:");
        lblPin.setBounds(120, 150, 120, 30);
        txtPin = new JPasswordField();
        txtPin.setBounds(200, 150, 180, 30);

        JButton btnLogin = styledButton("Login", "icons/login.png");
        btnLogin.setBounds(180, 220, 140, 40);

        btnLogin.addActionListener(e -> {
            if (txtUser.getText().equals("1234") && new String(txtPin.getPassword()).equals("1111")) {
                currentUser = new User("1234", 10000);
                cards.show(cardPanel, "menu");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User ID or PIN!");
            }
        });

        p.add(title);
        p.add(lblUser); p.add(txtUser);
        p.add(lblPin); p.add(txtPin);
        p.add(btnLogin);

        return p;
    }


    // ---------------- MAIN MENU PANEL ----------------
    private JPanel menuPanel() {
        JPanel p = new JPanel();
        p.setBackground(Color.white);
        p.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel title = new JLabel("ATM Menu", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JButton btnHistory = styledButton("Transaction History", "icons/history.png");
        btnHistory.addActionListener(e -> {
            updateHistory();
            cards.show(cardPanel, "history");
        });

        JButton btnWithdraw = styledButton("Withdraw", "icons/withdraw.png");
        btnWithdraw.addActionListener(e -> cards.show(cardPanel, "withdraw"));

        JButton btnDeposit = styledButton("Deposit", "icons/deposit.png");
        btnDeposit.addActionListener(e -> cards.show(cardPanel, "deposit"));

        JButton btnTransfer = styledButton("Transfer", "icons/transfer.png");
        btnTransfer.addActionListener(e -> cards.show(cardPanel, "transfer"));

        JButton btnQuit = styledButton("Quit", "icons/exit.png");
        btnQuit.addActionListener(e -> System.exit(0));

        p.add(title);
        p.add(btnHistory);
        p.add(btnWithdraw);
        p.add(btnDeposit);
        p.add(btnTransfer);
        p.add(btnQuit);

        return p;
    }

    // ---------------- TRANSACTION HISTORY PANEL ----------------
    private JPanel historyPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.white);

        JLabel title = new JLabel("Transaction History", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        JButton btnBack = styledButton("Back", "icons/back.png");
        btnBack.addActionListener(e -> cards.show(cardPanel, "menu"));

        p.add(title, BorderLayout.NORTH);
        p.add(new JScrollPane(historyArea), BorderLayout.CENTER);
        p.add(btnBack, BorderLayout.SOUTH);

        return p;
    }

    // ---------------- WITHDRAW PANEL ----------------
    private JPanel withdrawPanel() {
        JPanel p = new JPanel(null);

        JLabel lbl = new JLabel("Enter amount to withdraw:");
        lbl.setBounds(120, 60, 250, 30);

        JTextField txt = new JTextField();
        txt.setBounds(150, 100, 180, 30);

        JButton submit = styledButton("Withdraw", "icons/withdraw.png");
        submit.setBounds(160, 160, 150, 40);

        JButton back = styledButton("Back", "icons/back.png");
        back.setBounds(160, 220, 150, 40);

        submit.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(txt.getText());

                if (currentUser.withdraw(amount)) {
                    JOptionPane.showMessageDialog(this, "Withdrawal Successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient Balance!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount!");
            }
        });

        back.addActionListener(e -> cards.show(cardPanel, "menu"));

        p.add(lbl);
        p.add(txt);
        p.add(submit);
        p.add(back);

        return p;
    }

    // ---------------- DEPOSIT PANEL ----------------
    private JPanel depositPanel() {
        JPanel p = new JPanel(null);

        JLabel lbl = new JLabel("Enter amount to deposit:");
        lbl.setBounds(120, 60, 250, 30);

        JTextField txt = new JTextField();
        txt.setBounds(150, 100, 180, 30);

        JButton submit = styledButton("Deposit", "icons/deposit.png");
        submit.setBounds(160, 160, 150, 40);

        JButton back = styledButton("Back", "icons/back.png");
        back.setBounds(160, 220, 150, 40);

        submit.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(txt.getText());
                currentUser.deposit(amount);
               JOptionPane.showMessageDialog(this, "Deposit Successful!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount!");
            }
        });

        back.addActionListener(e -> cards.show(cardPanel, "menu"));

        p.add(lbl);
        p.add(txt);
        p.add(submit);
        p.add(back);

        return p;
    }

    // ---------------- TRANSFER PANEL ----------------
    private JPanel transferPanel() {
        JPanel p = new JPanel(null);

        JLabel lblAcc = new JLabel("Receiver Account:");
        lblAcc.setBounds(120, 50, 250, 30);

        JTextField txtAcc = new JTextField();
        txtAcc.setBounds(150, 80, 180, 30);

        JLabel lblAmt = new JLabel("Amount:");
        lblAmt.setBounds(120, 130, 250, 30);

        JTextField txtAmt = new JTextField();
        txtAmt.setBounds(150, 160, 180, 30);

        JButton submit = styledButton("Transfer", "icons/transfer.png");
        submit.setBounds(160, 210, 150, 40);

        JButton back = styledButton("Back", "icons/back.png");
        back.setBounds(160, 270, 150, 40);

        submit.addActionListener(e -> {
            try {
                int amt = Integer.parseInt(txtAmt.getText());

                if (currentUser.withdraw(amt)) {
                    JOptionPane.showMessageDialog(this, "Transfer Successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient Balance!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input");
            }
        });

        back.addActionListener(e -> cards.show(cardPanel, "menu"));

        p.add(lblAcc); p.add(txtAcc);
        p.add(lblAmt); p.add(txtAmt);
        p.add(submit); p.add(back);

        return p;
    }

    // ---------------- UPDATE HISTORY ----------------
    private void updateHistory() {
        StringBuilder sb = new StringBuilder();
        for (Transaction t : currentUser.history) {
            sb.append(t.type).append(" - ").append(t.amount)
                    .append(" | Balance: ").append(t.balanceAfter)
                    .append("\n");
        }
        historyArea.setText(sb.toString());
    }

    // ---------------- STYLED BUTTON (FIXED PATH) ----------------
    private JButton styledButton(String text, String iconPath) {
        JButton b = new JButton(text);

        try {
            ImageIcon rawIcon = new ImageIcon(iconPath);
            Image scaled = rawIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            b.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            System.out.println("Icon missing: " + iconPath);
        }

        b.setFocusPainted(false);
        b.setBackground(new Color(30, 144, 255));
        b.setForeground(Color.white);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMSystem().setVisible(true));
    }
}

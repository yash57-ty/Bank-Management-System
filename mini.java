package bank;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class mini extends JFrame implements ActionListener {

    String pin;
    JLabel label2, label3, label4;
    JTextArea textArea;   
    JButton button;

    mini(String pin) {
        this.pin = pin;

        setTitle("Mini Statement");
        setLayout(null);

        
        label2 = new JLabel("Indian Bank");
        label2.setFont(new Font("System", Font.BOLD, 20));
        label2.setBounds(180, 20, 200, 30);
        add(label2);

        
        label3 = new JLabel();
        label3.setBounds(20, 70, 400, 20);
        add(label3);

        
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("System", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBounds(20, 110, 440, 300);
        add(scroll);

       
        label4 = new JLabel();
        label4.setBounds(20, 430, 400, 20);
        add(label4);

        
        button = new JButton("Exit");
        button.setBounds(20, 480, 100, 25);
        button.addActionListener(this);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        add(button);

        displayCardNumber();
        displayTransactions();

        getContentPane().setBackground(Color.WHITE);
        setSize(500, 550);
        setLocation(100, 100);
        setVisible(true);
    }

    private void displayCardNumber() {
        try {
            conn c = new conn();
            String sql = "SELECT CARD_NO FROM login WHERE pin = ?";
            PreparedStatement pstmt = c.con.prepareStatement(sql);
            pstmt.setString(1, pin);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String cardno = rs.getString("CARD_NO");
                label3.setText("Card Number: " + cardno.substring(0, 4) + "XXXXXXXX" + cardno.substring(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayTransactions() {
        try {
            conn c = new conn();
            int balance = 0;

            String sql = "SELECT * FROM bank WHERE pin = ? ORDER BY date DESC"; // ✅ Latest first
            PreparedStatement pstmt = c.con.prepareStatement(sql);
            pstmt.setString(1, pin);
            ResultSet rs = pstmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append(rs.getString("date"))
                  .append("    ")
                  .append(rs.getString("type"))
                  .append("    Rs.")
                  .append(rs.getString("amount"))
                  .append("\n");

                if (rs.getString("type").equalsIgnoreCase("deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }

            textArea.setText(sb.toString());
            label4.setText("Your Total Balance is Rs " + balance);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }

    public static void main(String args[]) {
        new mini("1234"); // test with sample pin
    }
}

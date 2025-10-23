package com.spqs.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPage {

    public RegisterPage() {
        JFrame frame = new JFrame("Create Account");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Name
        panel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        panel.add(nameField);

        // Email
        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        panel.add(emailField);

        // College ID
        panel.add(new JLabel("College ID:"));
        JTextField collegeField = new JTextField();
        panel.add(collegeField);

        // Password
        panel.add(new JLabel("Password:"));
        JPasswordField passField = new JPasswordField();
        panel.add(passField);

        // Department
        panel.add(new JLabel("Department:"));
        JTextField deptField = new JTextField();
        panel.add(deptField);

        // Year of Study
        panel.add(new JLabel("Year of Study:"));
        String[] years = {"1", "2", "3", "4"};
        JComboBox<String> yearCombo = new JComboBox<>(years);
        panel.add(yearCombo);

        // Role selection
        panel.add(new JLabel("Role:"));
        JPanel rolePanel = new JPanel(new FlowLayout());
        JRadioButton studentBtn = new JRadioButton("Student");
        JRadioButton operatorBtn = new JRadioButton("Operator");
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(studentBtn);
        roleGroup.add(operatorBtn);
        rolePanel.add(studentBtn);
        rolePanel.add(operatorBtn);
        panel.add(rolePanel);

        // Register button
        JButton registerBtn = new JButton("Register");
        panel.add(new JLabel());
        panel.add(registerBtn);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        // ---------------------------
        // ActionListener for Register
        // ---------------------------
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String collegeId = collegeField.getText().trim();
                String password = new String(passField.getPassword()).trim();
                String department = deptField.getText().trim();
                String year = (String) yearCombo.getSelectedItem();
                String role = studentBtn.isSelected() ? "Student" : "Operator";

                // ---------------------------
                // Simple Email Validation
                // ---------------------------
                if (!email.contains("@") || !email.contains(".")) {
                    JOptionPane.showMessageDialog(frame,
                            "Please enter a valid email address containing '@' and '.'",
                            "Invalid Email",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Stop processing
                }

                // You can add JDBC insert here using try-catch
                try {
                    // TODO: insert user into DB
                    JOptionPane.showMessageDialog(frame,
                            "Registration successful for " + role + "!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Error saving to database: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        new RegisterPage();
    }
}

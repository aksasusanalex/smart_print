import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterPage extends JFrame implements ActionListener {
    JTextField emailField, collegeIdField, deptField, yearField;
    JPasswordField passField;
    JRadioButton studentBtn, operatorBtn;
    JButton registerBtn, loginBtn;

    public RegisterPage() {
        setTitle("Register Account");
        setSize(400, 400);
        setLayout(new GridLayout(8, 2, 5, 5));
        setLocationRelativeTo(null);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("College ID:"));
        collegeIdField = new JTextField();
        add(collegeIdField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        add(new JLabel("Department:"));
        deptField = new JTextField();
        add(deptField);

        add(new JLabel("Year of Study:"));
        yearField = new JTextField();
        add(yearField);

        add(new JLabel("Role:"));
        JPanel rolePanel = new JPanel();
        studentBtn = new JRadioButton("Student", true);
        operatorBtn = new JRadioButton("Operator");
        ButtonGroup bg = new ButtonGroup();
        bg.add(studentBtn);
        bg.add(operatorBtn);
        rolePanel.add(studentBtn);
        rolePanel.add(operatorBtn);
        add(rolePanel);

        registerBtn = new JButton("Register");
        loginBtn = new JButton("Go to Login");
        add(registerBtn);
        add(loginBtn);

        registerBtn.addActionListener(this);
        loginBtn.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users (email, college_id, password, department, year_of_study, role) VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, emailField.getText());
            ps.setString(2, collegeIdField.getText());
            ps.setString(3, new String(passField.getPassword()));
            ps.setString(4, deptField.getText());
            ps.setString(5, yearField.getText());
            ps.setString(6, studentBtn.isSelected() ? "student" : "operator");
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration Successful!");
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

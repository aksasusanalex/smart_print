import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {
    JTextField emailField;
    JPasswordField passField;
    JButton loginBtn, registerBtn;

    public LoginPage() {
        setTitle("Login");
        setSize(350, 200);
        setLayout(new GridLayout(3, 2, 5, 5));
        setLocationRelativeTo(null);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passField = new JPasswordField();
        add(passField);

        loginBtn = new JButton("Login");
        registerBtn = new JButton("Register");
        add(loginBtn);
        add(registerBtn);

        loginBtn.addActionListener(this);
        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterPage();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
            ps.setString(1, emailField.getText());
            ps.setString(2, new String(passField.getPassword()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                JOptionPane.showMessageDialog(this, "Login Successful! Role: " + role);
                dispose();
                if (role.equals("student")) {
                    new StudentHome(emailField.getText());
                } else {
                    new OperatorHome();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Email or Password");
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

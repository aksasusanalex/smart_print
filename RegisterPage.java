import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterPage extends JFrame {
    private JTextField emailTextField, collegeIdTextField, departmentTextField, yearTextField;
    private JPasswordField passwordField;
    private JRadioButton studentRadioButton, operatorRadioButton;
    private JButton registerButton;

    public RegisterPage() {
        setTitle("Register");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));

       
        add(new JLabel("Email:"));
        emailTextField = new JTextField();
        add(emailTextField);

        add(new JLabel("College ID:"));
        collegeIdTextField = new JTextField();
        add(collegeIdTextField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Department:"));
        departmentTextField = new JTextField();
        add(departmentTextField);

        add(new JLabel("Year of Study:"));
        yearTextField = new JTextField();
        add(yearTextField);

        add(new JLabel("Role:"));
        JPanel rolePanel = new JPanel();
        studentRadioButton = new JRadioButton("Student");
        operatorRadioButton = new JRadioButton("Operator");
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(studentRadioButton);
        roleGroup.add(operatorRadioButton);
        rolePanel.add(studentRadioButton);
        rolePanel.add(operatorRadioButton);
        add(rolePanel);

       
        registerButton = new JButton("Register");
        add(registerButton);

       
        add(new JLabel());

       
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        setVisible(true);
    }

    private void registerUser() {
        String email = emailTextField.getText().trim();
        String collegeId = collegeIdTextField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String department = departmentTextField.getText().trim();
        String year = yearTextField.getText().trim();
        String role = studentRadioButton.isSelected() ? "student" :
                      operatorRadioButton.isSelected() ? "operator" : "";

        
        if (email.isEmpty() || collegeId.isEmpty() || password.isEmpty() ||
            department.isEmpty() || year.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields and select a role.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


       
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/college_db", "root", "your_password"
            );

            String query = "INSERT INTO users (email, college_id, password, department, year, role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, collegeId);
            stmt.setString(3, password);
            stmt.setString(4, department);
            stmt.setString(5, year);
            stmt.setString(6, role);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Try again.");
            }

            stmt.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + se.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ce) {
            JOptionPane.showMessageDialog(this, "JDBC Driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        emailTextField.setText("");
        collegeIdTextField.setText("");
        passwordField.setText("");
        departmentTextField.setText("");
        yearTextField.setText("");
        studentRadioButton.setSelected(false);
        operatorRadioButton.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterPage());
    }
}


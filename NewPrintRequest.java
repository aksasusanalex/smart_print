import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class NewPrintRequest extends JFrame implements ActionListener {
    JTextField docNameField, copiesField;
    JRadioButton oneSideBtn, twoSideBtn, bwBtn, colorBtn, prePayBtn, postPayBtn;
    JButton submitBtn;
    String userEmail;

    public NewPrintRequest(String email) {
        this.userEmail = email;

        setTitle("New Print Request");
        setSize(400, 400);
        setLayout(new GridLayout(7, 2, 5, 5));
        setLocationRelativeTo(null);

        add(new JLabel("Document Name:"));
        docNameField = new JTextField();
        add(docNameField);

        add(new JLabel("Sides:"));
        JPanel sidesPanel = new JPanel();
        oneSideBtn = new JRadioButton("One Side", true);
        twoSideBtn = new JRadioButton("Two Side");
        ButtonGroup sidesGroup = new ButtonGroup();
        sidesGroup.add(oneSideBtn);
        sidesGroup.add(twoSideBtn);
        sidesPanel.add(oneSideBtn);
        sidesPanel.add(twoSideBtn);
        add(sidesPanel);

        add(new JLabel("Color:"));
        JPanel colorPanel = new JPanel();
        bwBtn = new JRadioButton("B/W", true);
        colorBtn = new JRadioButton("Color");
        ButtonGroup colorGroup = new ButtonGroup();
        colorGroup.add(bwBtn);
        colorGroup.add(colorBtn);
        colorPanel.add(bwBtn);
        colorPanel.add(colorBtn);
        add(colorPanel);

        add(new JLabel("Number of Copies:"));
        copiesField = new JTextField();
        add(copiesField);

        add(new JLabel("Payment:"));
        JPanel payPanel = new JPanel();
        prePayBtn = new JRadioButton("Pre-pay", true);
        postPayBtn = new JRadioButton("Post-pay");
        ButtonGroup payGroup = new ButtonGroup();
        payGroup.add(prePayBtn);
        payGroup.add(postPayBtn);
        payPanel.add(prePayBtn);
        payPanel.add(postPayBtn);
        add(payPanel);

        submitBtn = new JButton("Submit Request");
        add(submitBtn);

        submitBtn.addActionListener(this);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO print_requests(user_email, document_name, sides, color, copies, payment_mode, status) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, userEmail);
            ps.setString(2, docNameField.getText());
            ps.setString(3, oneSideBtn.isSelected() ? "One Side" : "Two Side");
            ps.setString(4, bwBtn.isSelected() ? "B/W" : "Color");
            ps.setInt(5, Integer.parseInt(copiesField.getText()));
            ps.setString(6, prePayBtn.isSelected() ? "Pre-pay" : "Post-pay");
            ps.setString(7, "Pending");
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Print Request Submitted!");
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

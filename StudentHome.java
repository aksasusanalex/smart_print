import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentHome extends JFrame {
    String email;

    public StudentHome(String userEmail) {
        this.email = userEmail;

        setTitle("Student Home");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JButton profileBtn = new JButton("Profile");
        JButton historyBtn = new JButton("History");
        JButton newRequestBtn = new JButton("New Print Request");

        profileBtn.addActionListener(e -> new ProfilePage(email));
        historyBtn.addActionListener(e -> new HistoryPage(email));
        newRequestBtn.addActionListener(e -> new NewPrintRequest(email));

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(profileBtn);
        panel.add(newRequestBtn);
        panel.add(historyBtn);

        add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

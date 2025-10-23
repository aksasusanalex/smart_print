import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ProfilePage extends JFrame {
    String userEmail;

    public ProfilePage(String email) {
        this.userEmail = email;

        setTitle("Profile");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 5, 5));

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email=?");
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                add(new JLabel("Email:"));
                add(new JLabel(rs.getString("email")));

                add(new JLabel("College ID:"));
                add(new JLabel(rs.getString("college_id")));

                add(new JLabel("Department:"));
                add(new JLabel(rs.getString("department")));

                add(new JLabel("Year of Study:"));
                add(new JLabel(rs.getString("year_of_study")));

                add(new JLabel("Role:"));
                add(new JLabel(rs.getString("role")));
            }

            rs.close();
            ps.close();
        } catch(Exception e){
            e.printStackTrace();
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}

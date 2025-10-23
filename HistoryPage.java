import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class HistoryPage extends JFrame {
    String userEmail;
    DefaultListModel<String> listModel;
    JList<String> historyList;

    public HistoryPage(String email) {
        this.userEmail = email;

        setTitle("Print History");
        setSize(500, 400);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        historyList = new JList<>(listModel);
        add(new JScrollPane(historyList), BorderLayout.CENTER);

        loadHistory();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    void loadHistory() {
        listModel.clear();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM print_requests WHERE user_email=? ORDER BY request_id DESC");
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String item = "Doc: " + rs.getString("document_name") +
                        ", Copies: " + rs.getInt("copies") +
                        ", Sides: " + rs.getString("sides") +
                        ", Color: " + rs.getString("color") +
                        ", Payment: " + rs.getString("payment_mode") +
                        ", Status: " + rs.getString("status");
                listModel.addElement(item);
            }
            rs.close();
            ps.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

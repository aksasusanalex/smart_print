import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class OperatorHome extends JFrame {
    DefaultListModel<String> listModel;
    JList<String> queueList;

    public OperatorHome() {
        setTitle("Operator Queue");
        setSize(600, 400);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        queueList = new JList<>(listModel);
        add(new JScrollPane(queueList), BorderLayout.CENTER);

        JButton processBtn = new JButton("Mark as Completed");
        add(processBtn, BorderLayout.SOUTH);

        processBtn.addActionListener(e -> markCompleted());

        loadQueue();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void loadQueue() {
        listModel.clear();
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM print_requests ORDER BY request_id ASC");
            while(rs.next()) {
                String item = "ID:" + rs.getInt("request_id") +
                        ", Doc:" + rs.getString("document_name") +
                        ", User:" + rs.getString("user_email") +
                        ", Status:" + rs.getString("status");
                listModel.addElement(item);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    void markCompleted() {
        int index = queueList.getSelectedIndex();
        if(index == -1) return;

        String selected = listModel.get(index);
        int id = Integer.parseInt(selected.split(",")[0].split(":")[1]);

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE print_requests SET status='Completed' WHERE request_id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Marked Completed!");
            loadQueue();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

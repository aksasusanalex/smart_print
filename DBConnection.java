import java.sql.*;

public class DBConnection {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Connect to MySQL database
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/smartprint", "root", "your_password"
                );

                Statement st = conn.createStatement();

            
                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS users (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        email VARCHAR(100) UNIQUE,
                        college_id VARCHAR(50),
                        password VARCHAR(50),
                        department VARCHAR(50),
                        year_of_study VARCHAR(20),
                        role VARCHAR(20)
                    );
                """);

               
                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS print_requests (
                        request_id INT AUTO_INCREMENT PRIMARY KEY,
                        user_email VARCHAR(100),
                        document_name VARCHAR(100),
                        sides VARCHAR(20),
                        color VARCHAR(20),
                        copies INT,
                        payment_mode VARCHAR(20),
                        status VARCHAR(20)
                    );
                """);

                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}


import java.sql.*;

public class DBConnection {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:smartprint.db");

                // create users table if not exists
                Statement st = conn.createStatement();
                String usersTable = """
                    CREATE TABLE IF NOT EXISTS users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        email TEXT UNIQUE,
                        college_id TEXT,
                        password TEXT,
                        department TEXT,
                        year_of_study TEXT,
                        role TEXT
                    );
                """;
                st.execute(usersTable);

                String requestsTable = """
                    CREATE TABLE IF NOT EXISTS print_requests (
                        request_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_email TEXT,
                        document_name TEXT,
                        sides TEXT,
                        color TEXT,
                        copies INTEGER,
                        payment_mode TEXT,
                        status TEXT
                    );
                """;
                st.execute(requestsTable);
                st.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}

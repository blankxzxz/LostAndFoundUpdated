package hellofx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/lost_found";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            System.out.println("DBConnection: Attempting to connect to database...");
            System.out.println("DBConnection: URL: " + URL);
            System.out.println("DBConnection: USER: " + USER);
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("DBConnection: Connection successful!");
            return conn;
        } catch (SQLException e) {
            System.err.println("DBConnection: Connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
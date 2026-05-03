package hellofx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    public static boolean checkCredentials(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if a matching row is found

        } catch (SQLException e) {
            System.err.println("AdminDAO: Login check failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
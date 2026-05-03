package hellofx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemDAO {

    public static ObservableList<Item> getAllItems() {
        ObservableList<Item> list = FXCollections.observableArrayList();
        System.out.println("ItemDAO: Attempting to get all items from database...");

        String sql = "SELECT * FROM items";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("ItemDAO: Database connection successful, executing query...");
            int count = 0;
            while (rs.next()) {
                Item item = new Item(
                        rs.getString("name"),
                        rs.getString("found_by"),
                        rs.getString("date_found"),
                        rs.getString("description")
                );
                if(rs.getBoolean("approved")) item.approve();
                list.add(item);
                count++;
                System.out.println("ItemDAO: Loaded item: " + item.getName() + " (approved: " + item.isApproved() + ")");
            }
            System.out.println("ItemDAO: Total items loaded: " + count);

        } catch (SQLException e) {
            System.err.println("ItemDAO: Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ItemDAO: Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public static void addItem(Item item) {
        String sql = "INSERT INTO items (name, found_by, date_found, description, approved) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getFoundBy());
            stmt.setString(3, item.getDate());
            stmt.setString(4, item.getDescription());
            stmt.setBoolean(5, item.isApproved());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void approveItem(String name) {
        String sql = "UPDATE items SET approved = TRUE WHERE name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
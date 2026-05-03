package hellofx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RetrievalRequestDAO {

    public static void addRequest(RetrievalRequest request) {
        String sql = "INSERT INTO retrieval_requests (item_name, claimant_name, claimant_email, ownership_proof, attach_file, date_lost, approved) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, request.getItemName());
            stmt.setString(2, request.getClaimantName());
            stmt.setString(3, request.getClaimantEmail());
            stmt.setString(4, request.getOwnershipProof());
            stmt.setString(5, request.getAttachFile());
            stmt.setString(6, request.getDateLost());
            stmt.setBoolean(7, false);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<RetrievalRequest> getPendingRequests() {
        ObservableList<RetrievalRequest> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM retrieval_requests WHERE approved = FALSE";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RetrievalRequest r = new RetrievalRequest(
                        rs.getString("item_name"),
                        rs.getString("claimant_name"),
                        rs.getString("claimant_email"),
                        rs.getString("ownership_proof"),
                        rs.getString("attach_file"),
                        rs.getString("date_lost")
                );
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void approveRequest(String claimantEmail, String itemName) {
        String sql = "UPDATE retrieval_requests SET approved = TRUE WHERE claimant_email = ? AND item_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, claimantEmail);
            stmt.setString(2, itemName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
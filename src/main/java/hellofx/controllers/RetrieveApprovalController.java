package hellofx.controllers;

import hellofx.NotificationManager;
import hellofx.RetrievalRequest;
import hellofx.RetrievalRequestDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RetrieveApprovalController {

    @FXML private TableView<RetrievalRequest> pendingRequestsTable;
    @FXML private TableColumn<RetrievalRequest, String> itemNameColumn;
    @FXML private TableColumn<RetrievalRequest, String> claimantNameColumn;
    @FXML private TableColumn<RetrievalRequest, String> claimantEmailColumn;
    @FXML private TableColumn<RetrievalRequest, String> ownershipProofColumn;
    @FXML private TableColumn<RetrievalRequest, String> dateLostColumn;
    @FXML private TableColumn<RetrievalRequest, String> attachFileColumn;

    @FXML
    public void initialize() {
        System.out.println("RetrieveApprovalController: Initializing...");
        try {
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            claimantNameColumn.setCellValueFactory(new PropertyValueFactory<>("claimantName"));
            claimantEmailColumn.setCellValueFactory(new PropertyValueFactory<>("claimantEmail"));
            ownershipProofColumn.setCellValueFactory(new PropertyValueFactory<>("ownershipProof"));
            dateLostColumn.setCellValueFactory(new PropertyValueFactory<>("dateLost"));
            attachFileColumn.setCellValueFactory(new PropertyValueFactory<>("attachFile"));

            ObservableList<RetrievalRequest> pending = RetrievalRequestDAO.getPendingRequests();
            pendingRequestsTable.setItems(pending);
            System.out.println("RetrieveApprovalController: Loaded " + pending.size() + " pending requests");
        } catch (Exception e) {
            System.err.println("RetrieveApprovalController: Error initializing: " + e.getMessage());
            e.printStackTrace();
            showErrorDialog("Database Error", "Failed to load pending retrieval requests: " + e.getMessage());
        }
    }

    @FXML
    public void approveSelectedRequest() {
        RetrievalRequest selected = pendingRequestsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            RetrievalRequestDAO.approveRequest(selected.getClaimantEmail(), selected.getItemName());

            NotificationManager.showNotification("Retrieval approved for: " + selected.getItemName()
                    + " by " + selected.getClaimantName());

            // Refresh table after approval
            ObservableList<RetrievalRequest> updated = RetrievalRequestDAO.getPendingRequests();
            pendingRequestsTable.setItems(updated);
            System.out.println("RetrieveApprovalController: Approved request for " + selected.getItemName());
        } else {
            showErrorDialog("No Selection", "Please select a retrieval request to approve.");
        }
    }

    @FXML
    public void goHome() {
        navigateTo("/hellofx/views/home.fxml");
    }

    @FXML
    public void goToNotifications() {
        System.out.println("RetrieveApprovalController: Notifications clicked");
    }

    @FXML
    public void goBackToAdminHome() {
        navigateTo("/hellofx/views/adminhome.fxml");
    }

    private void navigateTo(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) pendingRequestsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("RetrieveApprovalController: Failed to navigate to " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
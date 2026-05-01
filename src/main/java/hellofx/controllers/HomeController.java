package hellofx.controllers;

import hellofx.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class HomeController {

    @FXML private StackPane contentArea;
    @FXML private Button pendingButton;

    private Node homeContent;
    private static HomeController instance;

    public HomeController() { instance = this; }

    @FXML
    public void initialize() {
        updatePendingButton();
        if(!contentArea.getChildren().isEmpty()) homeContent = contentArea.getChildren().get(0);
    }

    private void loadPage(String fxml) {
        try {
            System.out.println("HomeController: Loading page: " + fxml);
            Node page = FXMLLoader.load(getClass().getResource("/hellofx/" + fxml));
            contentArea.getChildren().setAll(page);
            System.out.println("HomeController: Successfully loaded page: " + fxml);
        } catch(Exception e) {
            System.err.println("HomeController: Failed to load page: " + fxml);
            System.err.println("HomeController: Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML public void goHome() {
        if(homeContent != null) contentArea.getChildren().setAll(homeContent);
    }
    @FXML public void goToReport() { loadPage("report.fxml"); }
    @FXML public void goToView() { loadPage("view.fxml"); }
    @FXML public void goToNotifications() { loadPage("notifications_tab.fxml"); }
    @FXML public void goToPending() {
        if(Session.isAdmin()) loadPage("admin.fxml");
        else loadPage("pending.fxml");
    }

    private void updatePendingButton() {
        if(pendingButton != null){
            if(Session.isAdmin()){
                pendingButton.setText("Admin Approval");
                pendingButton.setStyle("-fx-background-color: transparent; -fx-text-fill: gold;");
            } else {
                pendingButton.setText("Pending Items");
                pendingButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }
        }
    }

    public static void refreshAdmin() { if(instance != null) instance.updatePendingButton(); }
    public static void openNotifications() { if(instance != null) instance.goToNotifications(); }
}
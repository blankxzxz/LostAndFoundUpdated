package hellofx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class AdminHomeController {

    @FXML private Button goHomeBtn;

    @FXML
    public void goHome() {
        navigateTo("/hellofx/views/home.fxml");
    }

    @FXML
    public void goToNotifications() {
        // Hook up your notifications view here if you have one
        System.out.println("AdminHomeController: Notifications clicked");
    }

    @FXML
    public void goToLostItemApproval() {
        navigateTo("/hellofx/views/lostitemapproval.fxml");
    }

    @FXML
    public void goToRetrieveItemApproval() {
        navigateTo("/hellofx/views/retrieveitemapproval.fxml");
    }

    private void navigateTo(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) goHomeBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("AdminHomeController: Failed to navigate to " + fxmlPath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
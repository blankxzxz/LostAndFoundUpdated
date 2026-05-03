package hellofx.controllers;

import java.io.IOException;
import java.util.Optional;

import hellofx.AdminDAO;
import hellofx.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HomeController {

    @FXML private StackPane contentArea;
    @FXML private Button pendingButton;

    private Node homeContent;
    private static HomeController instance;

    public static HomeController getInstance() { return instance; }
    public HomeController() { instance = this; }

    private void loadPage(String fxml) {
        try {
            String path = "/hellofx/" + fxml;
            java.net.URL url = getClass().getResource(path);
            System.out.println("Trying path: " + path);
            System.out.println("Resolved URL: " + url);
            Node page = FXMLLoader.load(url);
            contentArea.getChildren().setAll(page);
        } catch (Exception e) {
            System.err.println("Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        updatePendingButton();
        if (!contentArea.getChildren().isEmpty()) {
            homeContent = contentArea.getChildren().get(0);
        }
    }

    public void goHome() {
        contentArea.getChildren().setAll(homeContent);
    }

    public void loadConfirm() {
        loadPage("confirm.fxml");
    }

   public void switchToAdminHome(ActionEvent event) {
    try {
        Parent adminRoot = FXMLLoader.load(getClass().getResource("/hellofx/adminHome.fxml"));
        Scene adminScene = new Scene(adminRoot);
        
        // Get the current window (Stage) and set the new Scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(adminScene);
        window.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    @FXML public void goToReport() { loadPage("report.fxml"); }
    @FXML public void goToView() { loadPage("view.fxml"); }
    @FXML public void goToRetrieve() { loadPage("retrieve.fxml"); }
    @FXML public void goToNotifications() { loadPage("notifications_tab.fxml"); }

    @FXML
    public void goToPending() {
        if (Session.isAdmin()) loadPage("admin.fxml");
        else loadPage("pending.fxml");
    }

    @FXML
    public void goToAdminLogin() {
        if (Session.isAdmin()) {
            // Already logged in — go straight to admin home
            loadPage("adminhome.fxml");
            return;
        }

        // Build the login dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Admin Login");
        dialog.setHeaderText("Enter admin credentials to continue.");

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Form fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(errorLabel, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Focus username field on open
        javafx.application.Platform.runLater(usernameField::requestFocus);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == loginButtonType) {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (AdminDAO.checkCredentials(username, password)) {
                Session.toggleAdmin();
                updatePendingButton();
                System.out.println("HomeController: Admin login successful.");
                loadPage("adminhome.fxml");
            } else {
                System.out.println("HomeController: Invalid admin credentials.");
                // Show dialog again with error
                showLoginError();
            }
        }
    }

    private void showLoginError() {
        Dialog<ButtonType> errorDialog = new Dialog<>();
        errorDialog.setTitle("Login Failed");
        errorDialog.setHeaderText(null);

        Label msg = new Label("❌ Incorrect username or password. Please try again.");
        msg.setStyle("-fx-text-fill: red;");
        errorDialog.getDialogPane().setContent(msg);
        errorDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        errorDialog.showAndWait();

        // Re-open the login dialog
        goToAdminLogin();
    }

    private void updatePendingButton() {
        if (pendingButton != null) {
            if (Session.isAdmin()) {
                pendingButton.setText("Admin Approval");
                pendingButton.setStyle("-fx-background-color: transparent; -fx-text-fill: gold;");
            } else {
                pendingButton.setText("Pending Items");
                pendingButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }
        }
    }

    public static void refreshAdmin() { if (instance != null) instance.updatePendingButton(); }
    public static void openNotifications() { if (instance != null) instance.goToNotifications(); }
}
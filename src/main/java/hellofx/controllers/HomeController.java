    package hellofx.controllers;

    import java.io.IOException;
    
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
public static HomeController getInstance() {
    return instance;
}
        public HomeController() { instance = this; }

        

        

    private void loadPage(String fxml) {
    try {
        String path = "/hellofx/" + fxml;
        java.net.URL url = getClass().getResource(path);
        System.out.println("Trying path: " + path);
        System.out.println("Resolved URL: " + url);
        Node page = FXMLLoader.load(url);
        contentArea.getChildren().setAll(page);
    } catch(Exception e) {
        System.err.println("Failed: " + e.getMessage());
        e.printStackTrace();
    }
}

      

@FXML
public void initialize() {
    updatePendingButton();
    // Save the home content AFTER it's loaded
    if (!contentArea.getChildren().isEmpty()) {
        homeContent = contentArea.getChildren().get(0);
    }
}

public void goHome() {
    contentArea.getChildren().setAll(homeContent); // ✅ restores home screen
}

public void loadConfirm() {
    loadPage("confirm.fxml"); 
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

    
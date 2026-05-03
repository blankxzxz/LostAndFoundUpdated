package hellofx.controllers;

import hellofx.Item;
import hellofx.ItemDAO;
import hellofx.NotificationManager;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ReportController {

    @FXML private TextField itemName;
    @FXML private TextField foundBy;
    @FXML private TextField date;
    @FXML private TextArea description;
    @FXML private VBox formArea;

    @FXML
    public void submitItem() {
        try {
            Item item = new Item(itemName.getText(), foundBy.getText(), date.getText(), description.getText());
            ItemDAO.addItem(item);
            NotificationManager.showNotification("Item submitted for approval");
        } catch (Exception e) { 
            System.err.println("ReportController: DB error: " + e.getMessage());
        }
        showConfirmation();
    }

    private void showConfirmation() {
    
    while (formArea.getChildren().size() > 1) {
        formArea.getChildren().remove(1);
    }

    Label confirmed = new Label("The Item has been reported.");
    confirmed.setStyle("-fx-text-fill: white; -fx-font-size: 40px; -fx-font-weight: bold;");
    Label thanks = new Label("Thank you");
    thanks.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-style: italic;");
    VBox box = new VBox(15, confirmed, thanks);
    box.setAlignment(Pos.CENTER);
    box.setStyle("-fx-background-color:#013D5A; -fx-background-radius:8; -fx-padding:60;");
    box.setPrefHeight(300);
    box.setPrefWidth(900);
    formArea.getChildren().add(box);
}
    @FXML
    public void goHome() {
        HomeController.getInstance().goHome();
    }
}
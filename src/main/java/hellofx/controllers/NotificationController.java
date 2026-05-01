package hellofx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NotificationController {

    @FXML
    private Label notificationLabel;

    public void setMessage(String message) {
        notificationLabel.setText(message);
    }
}
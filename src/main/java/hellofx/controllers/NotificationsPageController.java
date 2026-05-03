package hellofx.controllers;

import hellofx.Notification;
import hellofx.NotificationStore;
import hellofx.Session;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class NotificationsPageController {

    @FXML
    private Label titleLabel;

    @FXML
    private ListView<String> notificationList;

    @FXML
    public void initialize() {
        if (Session.isAdmin()) {
            titleLabel.setText("Pending Requests (Admin)");
        } else {
            titleLabel.setText("Notifications");
        }

        notificationList.getItems().clear();

        for (Notification n : NotificationStore.notifications) {
            notificationList.getItems().add(n.getMessage());
        }

        NotificationStore.notifications.addListener((ListChangeListener<Notification>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Notification n : change.getAddedSubList()) {
                        notificationList.getItems().add(n.getMessage());
                    }
                }
            }
        });
    }
}
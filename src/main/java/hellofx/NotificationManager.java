package hellofx;

import hellofx.controllers.HomeController;
import hellofx.controllers.NotificationController;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class NotificationManager {

    public static void showNotification(String message) {
        try {
            NotificationStore.notifications.add(new Notification(message));

            FXMLLoader loader = new FXMLLoader(NotificationManager.class.getResource("/hellofx/Notification.fxml"));
            Scene scene = new Scene(loader.load());
            scene.setFill(null);

            NotificationController controller = loader.getController();
            controller.setMessage(message);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.setX(1200);
            stage.setY(60);
            stage.show();

            scene.getRoot().setOnMouseClicked(e -> {
                stage.close();
                HomeController.openNotifications();
            });

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), scene.getRoot());
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), scene.getRoot());
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setDelay(Duration.seconds(3));
            fadeOut.setOnFinished(e -> stage.close());
            fadeOut.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
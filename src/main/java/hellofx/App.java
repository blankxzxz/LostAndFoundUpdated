package hellofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        loadScene("home.fxml");
        stage.setTitle("QCU Lost and Found");
        stage.show();
    }

    public static void loadScene(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/hellofx/" + fxml));
        Scene scene = new Scene(loader.load());

        scene.setOnKeyTyped(e -> {
            if (!e.getCharacter().isEmpty()) {
                AdminCodeBuffer.add(e.getCharacter().charAt(0));
            }
        });

        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}
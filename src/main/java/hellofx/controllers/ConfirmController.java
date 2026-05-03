package hellofx.controllers;

import javafx.fxml.FXML;

public class ConfirmController {
    @FXML
    public void goHome() {
        HomeController.getInstance().goHome();
    }
}
package hellofx.controllers;

import hellofx.NotificationManager;
import hellofx.RetrievalRequest;
import hellofx.RetrievalRequestDAO;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class RetrieveController {

    @FXML private TextField itemName;
    @FXML private TextField claimantName;
    @FXML private TextField claimantEmail;
    @FXML private TextField ownershipProof;
    @FXML private TextField attachFile;
    @FXML private TextField dateLost;
    @FXML private VBox formArea;

    @FXML
    public void submitRequest() {
        try {
            RetrievalRequest request = new RetrievalRequest(
                itemName.getText(),
                claimantName.getText(),
                claimantEmail.getText(),
                ownershipProof.getText(),
                attachFile.getText(),
                dateLost.getText()
            );
            RetrievalRequestDAO.addRequest(request);
            NotificationManager.showNotification("Retrieval request submitted for approval");
        } catch (Exception e) {
            System.err.println("RetrieveController: DB error: " + e.getMessage());
        }
        showConfirmation();
    }

    private void showConfirmation() {
        while (formArea.getChildren().size() > 1) {
            formArea.getChildren().remove(1);
        }

        Label mainMsg = new Label("Your request has been submitted.");
        mainMsg.setStyle("-fx-text-fill: #E8A020; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label subMsg = new Label("You will be notified via your provided email once it is ready for claiming.");
        subMsg.setStyle("-fx-text-fill: white; -fx-font-size: 13px; -fx-font-style: italic; -fx-font-weight: bold;");
        subMsg.setWrapText(true);
        subMsg.setTextAlignment(TextAlignment.CENTER);

        VBox innerBox = new VBox(10, mainMsg, subMsg);
        innerBox.setAlignment(Pos.CENTER);
        innerBox.setStyle("-fx-background-color: #1A4A6B; -fx-background-radius: 8; -fx-border-color: white; -fx-border-radius: 8; -fx-border-width: 2; -fx-padding: 40;");
        innerBox.setMaxWidth(600);

        VBox outerBox = new VBox(innerBox);
        outerBox.setAlignment(Pos.CENTER);
        outerBox.setStyle("-fx-background-color: #6B8A9A; -fx-background-radius: 8; -fx-padding: 60;");

        formArea.getChildren().add(outerBox);
    }

    @FXML
    public void goHome() {
        HomeController.getInstance().goHome();
    }
}
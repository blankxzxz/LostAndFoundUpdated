package hellofx.controllers;

import hellofx.Item;
import hellofx.ItemDAO;
import hellofx.NotificationManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReportController {

    @FXML private TextField itemName;
    @FXML private TextField foundBy;
    @FXML private TextField date;
    @FXML private TextArea description;

    @FXML
    public void submitItem() {
        Item item = new Item(itemName.getText(), foundBy.getText(), date.getText(), description.getText());
        ItemDAO.addItem(item);

        NotificationManager.showNotification("Item submitted for approval");

        itemName.clear();
        foundBy.clear();
        date.clear();
        description.clear();
    }
}
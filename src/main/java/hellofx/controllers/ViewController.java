package hellofx.controllers;

import hellofx.Item;
import hellofx.ItemDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ViewController {

    @FXML private TableView<Item> approvedTable;
    @FXML private TableColumn<Item,String> itemNameColumn;
    @FXML private TableColumn<Item,String> foundByColumn;
    @FXML private TableColumn<Item,String> dateFoundColumn;
    @FXML private TableColumn<Item,String> descriptionColumn;
    private SortedList<Item> sortedApprovedItems;
    @FXML
    public void initialize() {
        System.out.println("ViewController: Initializing...");
        try {
            itemNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
            foundByColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFoundBy()));
            dateFoundColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));
            descriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));

            // Add custom comparator for date column to sort chronologically
            dateFoundColumn.setComparator((date1, date2) -> {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate d1 = LocalDate.parse(date1, formatter);
                    LocalDate d2 = LocalDate.parse(date2, formatter);
                    return d2.compareTo(d1); // Reverse order: newest first
                } catch (DateTimeParseException e) {
                    return date1.compareTo(date2); // Fallback to alphabetical
                }
            });

            FilteredList<Item> approvedItems = new FilteredList<>(ItemDAO.getAllItems(), Item::isApproved);
            sortedApprovedItems = new SortedList<>(approvedItems);
            approvedTable.setItems(sortedApprovedItems);
            System.out.println("ViewController: Loaded " + approvedItems.size() + " approved items");
        } catch (Exception e) {
            System.err.println("ViewController: Error initializing: " + e.getMessage());
            e.printStackTrace();
            showErrorDialog("Database Error", "Failed to load approved items from database: " + e.getMessage());
        }
    }

    @FXML
    public void sortByName() {
        System.out.println("ViewController: Sorting by name");
        sortedApprovedItems.setComparator((item1, item2) -> item1.getName().compareToIgnoreCase(item2.getName()));
        System.out.println("ViewController: Sort by name completed");
    }

    @FXML
    public void sortByFoundBy() {
        System.out.println("ViewController: Sorting by found by");
        sortedApprovedItems.setComparator((item1, item2) -> item1.getFoundBy().compareToIgnoreCase(item2.getFoundBy()));
        System.out.println("ViewController: Sort by found by completed");
    }

    @FXML
    public void sortByDate() {
        System.out.println("ViewController: Sorting by date");
        sortedApprovedItems.setComparator((item1, item2) -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate d1 = LocalDate.parse(item1.getDate(), formatter);
                LocalDate d2 = LocalDate.parse(item2.getDate(), formatter);
                return d2.compareTo(d1); // Newest first
            } catch (DateTimeParseException e) {
                return item1.getDate().compareTo(item2.getDate());
            }
        });
        System.out.println("ViewController: Sort by date completed");
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
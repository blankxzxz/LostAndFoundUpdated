package hellofx.controllers;

import hellofx.Item;
import hellofx.ItemDAO;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PendingController {

    @FXML private TableView<Item> pendingTable;
    @FXML private TableColumn<Item,String> itemNameColumn;
    @FXML private TableColumn<Item,String> foundByColumn;
    @FXML private TableColumn<Item,String> dateFoundColumn;
    @FXML private TableColumn<Item,String> descriptionColumn;
    private SortedList<Item> sortedPendingItems;
    @FXML
    public void initialize() {
        System.out.println("PendingController: Initializing...");
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        foundByColumn.setCellValueFactory(new PropertyValueFactory<>("foundBy"));
        dateFoundColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

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

        FilteredList<Item> pendingItems = new FilteredList<>(ItemDAO.getAllItems(), item -> !item.isApproved());
        sortedPendingItems = new SortedList<>(pendingItems);
        pendingTable.setItems(sortedPendingItems);
        System.out.println("PendingController: Loaded " + pendingItems.size() + " pending items");
    }

    @FXML
    public void sortByName() {
        System.out.println("PendingController: Sorting by name");
        sortedPendingItems.setComparator((item1, item2) -> item1.getName().compareToIgnoreCase(item2.getName()));
        System.out.println("PendingController: Sort by name completed");
    }

    @FXML
    public void sortByFoundBy() {
        System.out.println("PendingController: Sorting by found by");
        sortedPendingItems.setComparator((item1, item2) -> item1.getFoundBy().compareToIgnoreCase(item2.getFoundBy()));
        System.out.println("PendingController: Sort by found by completed");
    }

    @FXML
    public void sortByDate() {
        System.out.println("PendingController: Sorting by date");
        sortedPendingItems.setComparator((item1, item2) -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate d1 = LocalDate.parse(item1.getDate(), formatter);
                LocalDate d2 = LocalDate.parse(item2.getDate(), formatter);
                return d2.compareTo(d1); // Newest first
            } catch (DateTimeParseException e) {
                return item1.getDate().compareTo(item2.getDate());
            }
        });
        System.out.println("PendingController: Sort by date completed");
    }
}
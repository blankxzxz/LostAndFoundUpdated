package hellofx;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {

    private final SimpleStringProperty name;
    private final SimpleStringProperty foundBy;
    private final SimpleStringProperty date;
    private final SimpleStringProperty description;
    private final SimpleBooleanProperty approved;

    public Item(String name, String foundBy, String date, String description) {
        this.name = new SimpleStringProperty(name);
        this.foundBy = new SimpleStringProperty(foundBy);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
        this.approved = new SimpleBooleanProperty(false);
    }

    public String getName() { return name.get(); }
    public String getFoundBy() { return foundBy.get(); }
    public String getDate() { return date.get(); }
    public String getDescription() { return description.get(); }
    public boolean isApproved() { return approved.get(); }
    public void approve() { approved.set(true); }
}
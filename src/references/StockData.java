package references;

import javafx.beans.property.*;

public class StockData {
    public SimpleIntegerProperty PKID = new SimpleIntegerProperty();
    public SimpleStringProperty Category = new SimpleStringProperty();
    public ObjectProperty ImageLink = new SimpleObjectProperty();
    public SimpleStringProperty Name = new SimpleStringProperty();
    public SimpleIntegerProperty Quantity = new SimpleIntegerProperty();
    public SimpleDoubleProperty Price = new SimpleDoubleProperty();

    public Integer getPKID() {
        return PKID.get();
    }

    public Object getCategory() {
        return Category.get();
    }

    public Object getImageLink() {
        return ImageLink.get();
    }

    public Object getName() {
        return Name.get();
    }

    public Object getQuantity() {
        return Quantity.get();
    }

    public Object getPrice() {
        return Price.get();
    }
}

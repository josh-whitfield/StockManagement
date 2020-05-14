package classes;

import javafx.beans.property.*;

public class StockData {
    public SimpleIntegerProperty PKID = new SimpleIntegerProperty();
    public SimpleStringProperty Category = new SimpleStringProperty();
    public ObjectProperty Image = new SimpleObjectProperty();
    public SimpleStringProperty Name = new SimpleStringProperty();
    public SimpleIntegerProperty Quantity = new SimpleIntegerProperty();
    public SimpleDoubleProperty Price = new SimpleDoubleProperty();
    public SimpleDoubleProperty TotalValue = new SimpleDoubleProperty();

    public Integer getPKID() {
        return PKID.get();
    }

    public String getCategory() {
        return Category.get();
    }

    public Object getImage() {
        return Image.get();
    }

    public String getName() {
        return Name.get();
    }

    public Integer getQuantity() {
        return Quantity.get();
    }

    public Double getPrice() {
        return Price.get();
    }

    public Double getTotalValue() {
        return TotalValue.get();
    }
}


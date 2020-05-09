package Classes;

import javafx.beans.property.SimpleStringProperty;

public class storage extends StockData {
    public SimpleStringProperty Capacity = new SimpleStringProperty();

    public String getCapacity() {
        return Capacity.get();
    }
}


package classes;

import javafx.beans.property.SimpleStringProperty;

public class memory extends StockData {
    public SimpleStringProperty Size = new SimpleStringProperty();

    public String getSize() {
        return Size.get();
    }
}


package Classes;

import javafx.beans.property.SimpleStringProperty;

public class fans_cooling extends StockData {
    public SimpleStringProperty Size = new SimpleStringProperty();

    public String getSize() {
        return Size.get();
    }
}


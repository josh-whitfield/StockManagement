package classes;

import javafx.beans.property.SimpleStringProperty;

public class keyboards extends StockData {
    public SimpleStringProperty Wireless = new SimpleStringProperty();

    public String getWireless() {
        return Wireless.get();
    }
}


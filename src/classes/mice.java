package classes;

import javafx.beans.property.SimpleStringProperty;

public class mice extends StockData {
    public SimpleStringProperty Wireless = new SimpleStringProperty();

    public String getWireless() {
        return Wireless.get();
    }
}

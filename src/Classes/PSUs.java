package Classes;

import javafx.beans.property.SimpleStringProperty;

public class PSUs extends StockData {
    public SimpleStringProperty Watts = new SimpleStringProperty();

    public String getWatts() {
        return Watts.get();
    }
}



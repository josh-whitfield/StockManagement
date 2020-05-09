package Classes;

import javafx.beans.property.SimpleStringProperty;

public class Cases extends StockData {
    public SimpleStringProperty Dimensions = new SimpleStringProperty();

    public String getDimensions() {
        return Dimensions.get();
    }
}


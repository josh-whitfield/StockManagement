package classes;

import javafx.beans.property.SimpleStringProperty;

public class graphicsCards extends StockData {
    public SimpleStringProperty VRAM = new SimpleStringProperty();

    public String getVRAM() {
        return VRAM.get();
    }
}



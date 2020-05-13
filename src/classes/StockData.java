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
}


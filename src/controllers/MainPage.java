package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.util.List;

public class MainPage {
    @FXML
    ListView lvContents;
    @FXML
    TableView tvStockTable;

    @FXML
    public void initialize() {
        ObservableList elements = FXCollections.observableArrayList();

        List<String> categoryList = database.MainPage.getCategories();
        elements.addAll(categoryList);

        lvContents.setItems(elements);
        lvContents.getSelectionModel().select(0);

        updateTableView(String.valueOf(lvContents.getSelectionModel().getSelectedItem()));
    }

    @FXML
    public void handleMouseClick(MouseEvent arg0) {
        updateTableView(String.valueOf(lvContents.getSelectionModel().getSelectedItem()));
    }

    public void updateTableView(String category) {
        /*tvStockTable.getItems().clear();
        Method classMethods[] = null;
        switch (category) {
            case "cases":
                classMethods = Cases.class.getMethods();
                break;
            case "fans-cooling":
                classMethods = fans_cooling.class.getMethods();
                break;
            case "graphicsCards":
                classMethods = graphicsCards.class.getMethods();
                break;
            case "memory":
                classMethods = memory.class.getMethods();
                break;
            case "storage":
                classMethods = storage.class.getMethods();
                break;
            case "PSUs":
                classMethods = PSUs.class.getMethods();
                break;
            case "monitors":
                classMethods = monitors.class.getMethods();
                break;
            case "keyboards":
                classMethods = keyboards.class.getMethods();
                break;
            case "mice":
                classMethods = mice.class.getMethods();
                break;
        }

        for (Method method : classMethods) {
            String name = method.getName();
            if (name.startsWith("get")) {
                String propName = name.replace("get", "");
                TableColumn column = new TableColumn(propName);
                column.setCellValueFactory(new PropertyValueFactory<>(propName));
                tvStockTable.getColumns().add(column);

                String[] hideCols = {"PKID", "Class", "Category"};
                if (Arrays.stream(hideCols).anyMatch(propName::equals)) {
                    column.setVisible(false);
                }
            }
        }
*/

        try {
            ResultSet resultSet = database.MainPage.getTableData(category);
            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                    if (param.getValue().get(j) != null) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    } else {
                        return null;
                    }
                });

                tvStockTable.getColumns().addAll(col);
                //resultSet.columnNames.add(col.getText());
            }

            while (resultSet.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }

            //FINALLY ADDED TO TableView
            tvStockTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void searchStock(MouseEvent mouseEvent) {
    }
}

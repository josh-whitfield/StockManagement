package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class MainPage {
    @FXML
    ListView lvContents;
    @FXML
    TableView tvStockTable;
    @FXML
    TextField txtSearch;

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
    public void changeCategory(MouseEvent arg0) {
        updateTableView(String.valueOf(lvContents.getSelectionModel().getSelectedItem()));
    }

    public void updateTableView(String category) {
        try {
            tvStockTable.getColumns().clear();
            tvStockTable.getItems().clear();

            ResultSet resultSet = database.MainPage.getTableData(category);
            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            String[] excludedCols = {"PKID", "Category", "ImageLink"};

            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                if (!Arrays.asList(excludedCols).contains(resultSet.getMetaData().getColumnName(i + 1))) {
                    TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                        if (param.getValue().get(j) != null) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        } else {
                            return null;
                        }
                    });

                    tvStockTable.getColumns().addAll(col);
                }
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

    public void updateTableView(ResultSet resultSet) {
        try {
            tvStockTable.getColumns().clear();
            tvStockTable.getItems().clear();

            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            String[] excludedCols = {"PKID", "Category", "ImageLink"};

            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                if (!Arrays.asList(excludedCols).contains(resultSet.getMetaData().getColumnName(i + 1))) {
                    TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                        if (param.getValue().get(j) != null) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        } else {
                            return null;
                        }
                    });

                    tvStockTable.getColumns().addAll(col);
                }
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

    @FXML
    public void viewImage(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) { //Checking double click
            try {
                Object selectedItems = tvStockTable.getSelectionModel().getSelectedItems().get(0);
                String category = selectedItems.toString().split(",")[1].substring(1);
                String imageLink = selectedItems.toString().split(",")[2].substring(1);

                BufferedImage image = ImageIO.read(getClass().getResource(String.format("/resources/images/%s/%s", category, imageLink)));
                Image newImage = image.getScaledInstance(500, 500, Image.SCALE_DEFAULT);

                JLabel picLabel = new JLabel(new ImageIcon(newImage));
                JOptionPane.showMessageDialog(null, picLabel, "Product Image", JOptionPane.PLAIN_MESSAGE, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void searchStock() {
        updateTableView(database.MainPage.getSearchResults(txtSearch.getText()));
    }

    public void amendStock() {
        try {
            Object selectedItems = tvStockTable.getSelectionModel().getSelectedItems().get(0);
            if (selectedItems != null) {
                String newAmount;
                if (selectedItems.toString().split(",").length == 7) {
                    newAmount = JOptionPane.showInputDialog("Please enter new amount", selectedItems.toString().split(",")[4].substring(1));
                } else {
                    newAmount = JOptionPane.showInputDialog("Please enter new amount", selectedItems.toString().split(",")[5].substring(1));
                }
                if (newAmount != null) {
                    database.MainPage.updateStockQuantity(Integer.parseInt(selectedItems.toString().split(",")[0].substring(1)), Integer.parseInt(newAmount));
                    updateTableView(selectedItems.toString().split(",")[1].substring(1));
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a value", "Nothing Selected", JOptionPane.PLAIN_MESSAGE, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Please enter a whole number", "Invalid Amount", JOptionPane.PLAIN_MESSAGE, null);
        }
    }
}

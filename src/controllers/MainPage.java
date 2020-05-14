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

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPage extends Component {
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
    public void changeCategory() {
        updateTableView(String.valueOf(lvContents.getSelectionModel().getSelectedItem()));
    }

    public void updateTableView(String category) {
        try {
            tvStockTable.getColumns().clear();
            tvStockTable.getItems().clear();

            String className = "classes." + category;
            Class<?> dynamicClass = Class.forName(className); // convert string classname to class
            Object objClass = dynamicClass.newInstance(); // invoke empty constructor
            Method setNameMethod = objClass.getClass().getMethod("buildData", TableView.class);

            tvStockTable.setItems((ObservableList) setNameMethod.invoke(objClass, tvStockTable));
            tvStockTable.getColumns().remove(0);
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
                /*
                String category = selectedItems.toString().split(",")[1].substring(1);
                String imageLink = selectedItems.toString().split(",")[2].substring(1);

                BufferedImage image = ImageIO.read(getClass().getResource(String.format("/resources/images/%s/%s", category, imageLink)));
                Image newImage = image.getScaledInstance(500, 500, Image.SCALE_DEFAULT);

                JLabel picLabel = new JLabel(new ImageIcon(newImage));
                JOptionPane.showMessageDialog(null, picLabel, "Product Image", JOptionPane.PLAIN_MESSAGE, null);
            */
            } catch (Exception e) {
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

    public void exportCurrentData() throws IOException {
        Writer writer = null;
        try {
            JFileChooser fc = new JFileChooser();
            fc.showSaveDialog(this);
            String test = fc.getSelectedFile().toString();
            File file = new File(test + ".csv");
            writer = new BufferedWriter(new FileWriter(file));

            for (Object row : tvStockTable.getItems()) {
                writer.write(row.toString().replace("[", "").replace("]", "") + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }

    public void exportAllData() throws IOException {
        ResultSet rs = database.MainPage.getAllData();
        Writer writer = null;
        try {
            JFileChooser fc = new JFileChooser();
            fc.showSaveDialog(this);
            String test = fc.getSelectedFile().toString();
            File file = new File(test + ".csv");
            writer = new BufferedWriter(new FileWriter(file));

            ResultSetMetaData metaData = rs.getMetaData();
            int columns = metaData.getColumnCount();

            while (rs.next()) {
                ArrayList<String> records = new ArrayList<>();
                for (int i = 1; i <= columns; i++) {
                    String value = rs.getString(i);
                    records.add(value);
                }

                String record = null;
                for (String s : records) record += s + ",";
                writer.append(record.replace("null", "") + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
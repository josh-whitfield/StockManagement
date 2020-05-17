package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import references.UserDetails;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainPage extends Component {
    @FXML
    GridPane mainPageGrid;
    @FXML
    ListView lvContents;
    @FXML
    TableView tvStockTable;
    @FXML
    TextField txtSearch;
    @FXML
    Label lblWelcome;
    @FXML
    Button btnAmendStock;

    @FXML
    public void initialize() {
        //Disable amend stock buttons for non-admins
        lblWelcome.setText("Welcome, " + UserDetails.Username);
        if (UserDetails.AccessLevel == 0) {
            btnAmendStock.setDisable(true);
        }

        //Insert into memory the Category list
        ObservableList elements = FXCollections.observableArrayList();
        List<String> categoryList = database.MainPage.getCategories();
        elements.addAll(categoryList);

        //Assign the categories to the ListView and select the first one
        lvContents.setItems(elements);
        lvContents.getSelectionModel().select(0);

        //Update the TableView with default data
        updateTableView(String.valueOf(lvContents.getSelectionModel().getSelectedItem()));
    }

    @FXML
    private void changeCategory() {
        txtSearch.setText("");
        //Update TableView with newly selected Category
        updateTableView(String.valueOf(lvContents.getSelectionModel().getSelectedItem()));
    }

    //Update the TableView based on existing Classes
    private void updateTableView(String category) {
        try {
            //Clear the TableView
            tvStockTable.getColumns().clear();
            tvStockTable.getItems().clear();

            //Retrieve the relevant class using reflection
            Object objClass = getDynamicClass(category.replace('/', '_'));
            //Run the buildData() method in that class
            Method setNameMethod = objClass.getClass().getMethod("buildData", TableView.class);

            //Assign the newly collected data to the TableView
            tvStockTable.setItems((ObservableList) setNameMethod.invoke(objClass, tvStockTable));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    //Update TableView based on SQL results
    private void updateTableView(ResultSet resultSet) {
        try {
            //Clear the TableView
            tvStockTable.getColumns().clear();
            tvStockTable.getItems().clear();

            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            //Exclude these columns as they're not needed
            String[] excludedCols = {"PKID", "ImageLink"};

            //Loop through SQL result columns
            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                //Exclude the above listed columns
                if (!Arrays.asList(excludedCols).contains(resultSet.getMetaData().getColumnName(i + 1))) {
                    //Fetch the column name
                    TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
                    //Create new TableView column based on SQL details
                    col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                        if (param.getValue().get(j) != null) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        } else {
                            return null;
                        }
                    });

                    //Add the new column to TableView
                    tvStockTable.getColumns().addAll(col);
                }
            }

            //Loop through SQL results to populate data
            while (resultSet.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }

            //Add data to TableView
            tvStockTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    private Object getDynamicClass(String classString) {
        try {
            //Convert string classname to class
            String className = "classes." + classString;
            Class<?> dynamicClass = Class.forName(className);

            //Return empty constructor
            return dynamicClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }
    }

    @FXML
    //Run when a TableView row is double clicked
    private void viewImage(javafx.scene.input.MouseEvent mouseEvent) {
        //Check it was double clicked
        if (mouseEvent.getClickCount() == 2) {
            try {
                //If the row is not empty
                if (tvStockTable.getSelectionModel().getSelectedItem() != null) {
                    //Get the currently selected Category
                    String category = String.valueOf(lvContents.getSelectionModel().getSelectedItem());

                    //Get the ID column and generate the image link from name
                    TableColumn colImage = (TableColumn) tvStockTable.getColumns().get(1);
                    String name = (String) colImage.getCellObservableValue(
                            tvStockTable.getSelectionModel().getSelectedIndex()).getValue();
                    String imageLink = name.replace(' ', '_') + ".jpg";

                    //Get the image from Resources
                    BufferedImage image = ImageIO.read(getClass().getResource(String.format("/resources/images/%s/%s", category, imageLink)));
                    Image newImage = image.getScaledInstance(500, 500, Image.SCALE_DEFAULT);

                    //Produce the image in a pop-up window
                    JLabel picLabel = new JLabel(new ImageIcon(newImage));
                    JOptionPane.showMessageDialog(null, picLabel, "Product Image", JOptionPane.PLAIN_MESSAGE, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void searchStock() {
        //Update TableView from SQL results
        updateTableView(database.MainPage.getSearchResults(txtSearch.getText()));
    }

    @FXML
    private void amendStock() {
        try {
            //If something is selected in TableView
            if (tvStockTable.getSelectionModel().getSelectedItem() != null) {
                //If the user is NOT amending from Search results
                if (Objects.equals(txtSearch.getText(), "") || txtSearch.getText() == null) {
                    //Get the ID for the record
                    TableColumn colPKID = (TableColumn) tvStockTable.getColumns().get(0);
                    int pkid = (int) colPKID.getCellObservableValue(
                            tvStockTable.getSelectionModel().getSelectedIndex()).getValue();

                    //Get the current quantity
                    TableColumn colQuantity = (TableColumn) tvStockTable.getColumns().get(4);
                    int quantity = (int) colQuantity.getCellObservableValue(
                            tvStockTable.getSelectionModel().getSelectedIndex()).getValue();

                    //Retrieve new amount from popup, handled by catch
                    String newAmount = JOptionPane.showInputDialog("Please enter new amount", quantity);
                    if (newAmount != null) {
                        //Update database, and refresh table
                        database.MainPage.updateStockQuantity(pkid, Integer.parseInt(newAmount));
                        updateTableView(String.valueOf(lvContents.getSelectionModel().getSelectedItem()));
                    }
                    //If the User IS amending from Search results
                } else {
                    //Retrieve CSV of items
                    Object selectedItems = tvStockTable.getSelectionModel().getSelectedItems().get(0);
                    if (selectedItems != null) {
                        //Retrieve new amount from popup, handled by catch
                        String newAmount = JOptionPane.showInputDialog("Please enter new amount", selectedItems.toString().split(",")[4].substring(1));
                        if (newAmount != null) {
                            //Update database, and refresh table
                            database.MainPage.updateStockQuantity(Integer.parseInt(selectedItems.toString().split(",")[0].substring(1)), Integer.parseInt(newAmount));
                            updateTableView(database.MainPage.getSearchResults(txtSearch.getText()));
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a value", "Nothing Selected", JOptionPane.PLAIN_MESSAGE, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Please enter a whole number", "Invalid Amount", JOptionPane.PLAIN_MESSAGE, null);
        }
    }

    @FXML
    private void exportCurrentData() throws IOException {
        //If the user is NOT amending from Search results
        if (Objects.equals(txtSearch.getText(), "") || txtSearch.getText() == null) {
            //Run SProc with Category
            writeToFile(database.MainPage.getTableData(String.valueOf(lvContents.getSelectionModel().getSelectedItem())));
        } else {
            //Run SProc with Search Results
            writeToFile(database.MainPage.getSearchResults(txtSearch.getText()));
        }
    }

    @FXML
    private void exportAllData() throws IOException {
        //Get all base stock data from SQL
        writeToFile(database.MainPage.getAllData());
    }

    private void writeToFile(ResultSet rs) throws IOException {
        Writer writer = null;
        try {
            //Get FilePath from user input
            JFileChooser fc = new JFileChooser();
            fc.showSaveDialog(this);
            String filePath = fc.getSelectedFile().toString();
            File file = new File(filePath + ".csv");
            writer = new BufferedWriter(new FileWriter(file));

            //Get column details from SQL
            ResultSetMetaData metaData = rs.getMetaData();
            int columns = metaData.getColumnCount();

            double sumValue = 0;
            boolean firstRun = true;
            //Loop through SQL rows
            while (rs.next()) {
                ArrayList<String> records = new ArrayList<>();
                //Loop through SQL columns
                for (int i = 1; i <= columns; i++) {
                    String value;
                    //Store Column names
                    if (firstRun) {
                        value = metaData.getColumnName(i);
                    } else {
                        value = rs.getString(i);
                    }
                    records.add(value);

                    //Store value of all stock held
                    if (i == columns && !firstRun) {
                        String test = rs.getString(i);
                        sumValue += Double.parseDouble(rs.getString(i));
                    }
                }
                firstRun = false;

                //Physically write main to file
                String record = null;
                for (String s : records) record += s + ",";
                writer.append(record.replace("null", "") + "\n");
            }
            //Physically write value of all stock held
            writer.append("Total Value: " + Math.round(sumValue * 100.0) / 100.0);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //Clear the writers to free memory
            writer.flush();
            writer.close();
        }
    }

    @FXML
    private void logOut() {
        database.MainPage.removeAutoLogin(UserDetails.Username);

        try {
            //Load Log In page
            Parent root = FXMLLoader.load(getClass().getResource("/resources/view/LogIn.fxml"));
            Stage stage = (Stage) mainPageGrid.getScene().getWindow();
            stage.setTitle("Log In");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
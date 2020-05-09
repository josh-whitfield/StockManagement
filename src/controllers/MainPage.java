package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import references.StockData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.util.List;

public class MainPage {
    @FXML
    ListView lvContents;
    @FXML
    TableView tvStockTable;

    private ObservableList<StockData> data;

    @FXML
    public void initialize() {
        ObservableList elements = FXCollections.observableArrayList();

        List<String> categoryList = database.MainPage.getCategories();
        elements.addAll(categoryList);

        lvContents.setItems(elements);
        lvContents.getSelectionModel().select(0);

        TableColumn PKID = new TableColumn("PKID");
        TableColumn Image = new TableColumn("Image");
        TableColumn Name = new TableColumn("Name");
        TableColumn Quantity = new TableColumn("Quantity");
        TableColumn Price = new TableColumn("Price");
        TableColumn TotalValue = new TableColumn("TotalValue");
        tvStockTable.getColumns().addAll(PKID, Image, Name, Quantity, Price, TotalValue);

        PKID.setCellValueFactory(
                new PropertyValueFactory<StockData, Integer>("PKID"));
        Image.setCellValueFactory(
                new PropertyValueFactory<StockData, ImageView>("Image"));
        Name.setCellValueFactory(
                new PropertyValueFactory<StockData, String>("Name"));
        Quantity.setCellValueFactory(
                new PropertyValueFactory<StockData, Integer>("Quantity"));
        Price.setCellValueFactory(
                new PropertyValueFactory<StockData, Double>("Price"));
        TotalValue.setCellValueFactory(
                new PropertyValueFactory<StockData, Double>("TotalValue"));

        PKID.setVisible(false);

        updateTableView(String.valueOf(lvContents.getSelectionModel().getSelectedItem()));
    }

    @FXML
    public void handleMouseClick(MouseEvent arg0) {
        updateTableView(String.valueOf(lvContents.getSelectionModel().getSelectedItem()));
    }

    public void updateTableView(String category) {
        tvStockTable.getItems().clear();
        data = FXCollections.observableArrayList();

        try {
            ResultSet rs = database.MainPage.getTableData(category);
            while (rs.next()) {
                StockData cm = new StockData();
                cm.PKID.set(rs.getInt("PKID"));
                BufferedImage originalImage = ImageIO.read(getClass().getResource(String.format("/resources/images/%s/%s", rs.getString("Category"), rs.getString("ImageLink"))));
                Image img = SwingFXUtils.toFXImage(originalImage, null);
                ImageView mv = new ImageView();
                mv.setImage(img);
                mv.setFitWidth(70);
                mv.setFitHeight(80);
                cm.PKID.set(rs.getInt("PKID"));
                cm.Image.set(mv);
                cm.Name.set(rs.getString("Name"));
                cm.Quantity.set(rs.getInt("Quantity"));
                cm.Price.set(rs.getDouble("Price"));
                cm.TotalValue.set(rs.getDouble("TotalValue"));
                data.add(cm);
            }
            tvStockTable.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void searchStock(MouseEvent mouseEvent) {
    }
}

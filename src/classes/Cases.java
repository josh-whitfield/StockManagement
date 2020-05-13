package classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;

public class Cases extends StockData {
    public SimpleStringProperty Dimensions = new SimpleStringProperty();

    private ObservableList<Cases> data;

    public ObservableList<Cases> buildData(TableView tvStockTable) {
        TableColumn PKID = new TableColumn("PKID");
        TableColumn Name = new TableColumn("Name");
        TableColumn Image = new TableColumn("Image");
        TableColumn Dimensions = new TableColumn("Dimensions");
        TableColumn Quantity = new TableColumn("Quantity");
        TableColumn Price = new TableColumn("Price");
        TableColumn TotalValue = new TableColumn("TotalValue");
        tvStockTable.getColumns().addAll(PKID, Name, Image, Dimensions, Quantity, Price, TotalValue);

        PKID.setCellValueFactory(new PropertyValueFactory<Cases, String>("PKID"));
        Name.setCellValueFactory(new PropertyValueFactory<Cases, String>("Name"));
        Image.setCellValueFactory(new PropertyValueFactory<Cases, ImageView>("Image"));
        Dimensions.setCellValueFactory(new PropertyValueFactory<Cases, String>("Dimensions"));
        Quantity.setCellValueFactory(new PropertyValueFactory<Cases, Integer>("Quantity"));
        Price.setCellValueFactory(new PropertyValueFactory<Cases, Double>("Price"));
        TotalValue.setCellValueFactory(new PropertyValueFactory<Cases, Double>("TotalValue"));

        data = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = database.MainPage.getTableData("cases");
            while (resultSet.next()) {
                Cases cm = new Cases();
                cm.PKID.set(resultSet.getInt("PKID"));
                cm.Name.set(resultSet.getString("Name"));

                BufferedImage image = ImageIO.read(getClass().getResource(
                        String.format("/resources/images/%s/%s", resultSet.getString("Category"),
                                resultSet.getString("ImageLink"))));
                Image newImage = SwingFXUtils.toFXImage(image, null);
                ImageView mv = new ImageView();
                mv.setImage(newImage);
                mv.setFitWidth(70);
                mv.setFitHeight(80);
                cm.Image.set(mv);

                cm.Dimensions.set(resultSet.getString("Dimensions"));
                cm.Quantity.set(resultSet.getInt("Quantity"));
                cm.Price.set(resultSet.getDouble("Price"));
                cm.TotalValue.set(resultSet.getDouble("TotalValue"));

                data.add(cm);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
            return null;
        }
    }
}
package classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;

public class Monitors extends StockData {
    public SimpleStringProperty Size = new SimpleStringProperty();

    public String getSize() {
        return Size.get();
    }

    private ObservableList<Monitors> data;

    public ObservableList<Monitors> buildData(TableView tvStockTable) {
        TableColumn PKID = new TableColumn("PKID");
        TableColumn Name = new TableColumn("Name");
        TableColumn Image = new TableColumn("Image");
        TableColumn Size = new TableColumn("Size");
        TableColumn Quantity = new TableColumn("Quantity");
        TableColumn Price = new TableColumn("Price");
        TableColumn TotalValue = new TableColumn("TotalValue");
        tvStockTable.getColumns().addAll(PKID, Name, Image, Size, Quantity, Price, TotalValue);

        PKID.setCellValueFactory(new PropertyValueFactory<Monitors, String>("PKID"));
        Name.setCellValueFactory(new PropertyValueFactory<Monitors, String>("Name"));
        Image.setCellValueFactory(new PropertyValueFactory<Monitors, ImageView>("Image"));
        Size.setCellValueFactory(new PropertyValueFactory<Monitors, String>("Size"));
        Quantity.setCellValueFactory(new PropertyValueFactory<Monitors, Integer>("Quantity"));
        Price.setCellValueFactory(new PropertyValueFactory<Monitors, Double>("Price"));
        TotalValue.setCellValueFactory(new PropertyValueFactory<Monitors, Double>("TotalValue"));

        data = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = database.MainPage.getTableData("Monitors");
            while (resultSet.next()) {
                Monitors cm = new Monitors();
                cm.PKID.set(resultSet.getInt("PKID"));
                cm.Name.set(resultSet.getString("Name"));

                BufferedImage image = ImageIO.read(getClass().getResource(
                        String.format("/resources/images/%s/%s", resultSet.getString("Category"),
                                resultSet.getString("ImageLink"))));
                javafx.scene.image.Image newImage = SwingFXUtils.toFXImage(image, null);
                ImageView mv = new ImageView();
                mv.setImage(newImage);
                mv.setFitWidth(70);
                mv.setFitHeight(80);
                cm.Image.set(mv);

                cm.Size.set(resultSet.getString("Size"));
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



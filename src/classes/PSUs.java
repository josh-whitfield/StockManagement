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

public class PSUs extends StockData {
    public SimpleStringProperty Watts = new SimpleStringProperty();

    public String getWatts() {
        return Watts.get();
    }

    private ObservableList<PSUs> data;

    public ObservableList<PSUs> buildData(TableView tvStockTable) {
        TableColumn PKID = new TableColumn("PKID");
        TableColumn Name = new TableColumn("Name");
        TableColumn Image = new TableColumn("Image");
        TableColumn Watts = new TableColumn("Watts");
        TableColumn Quantity = new TableColumn("Quantity");
        TableColumn Price = new TableColumn("Price");
        TableColumn TotalValue = new TableColumn("TotalValue");
        tvStockTable.getColumns().addAll(PKID, Name, Image, Watts, Quantity, Price, TotalValue);

        PKID.setCellValueFactory(new PropertyValueFactory<PSUs, String>("PKID"));
        Name.setCellValueFactory(new PropertyValueFactory<PSUs, String>("Name"));
        Image.setCellValueFactory(new PropertyValueFactory<PSUs, ImageView>("Image"));
        Watts.setCellValueFactory(new PropertyValueFactory<PSUs, String>("Watts"));
        Quantity.setCellValueFactory(new PropertyValueFactory<PSUs, Integer>("Quantity"));
        Price.setCellValueFactory(new PropertyValueFactory<PSUs, Double>("Price"));
        TotalValue.setCellValueFactory(new PropertyValueFactory<PSUs, Double>("TotalValue"));

        data = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = database.MainPage.getTableData("PSUs");
            while (resultSet.next()) {
                PSUs cm = new PSUs();
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

                cm.Watts.set(resultSet.getString("Watts"));
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



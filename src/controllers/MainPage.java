package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
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

    //private TableView tvStockTable;
    @FXML
    public void initialize() {
        ObservableList elements = FXCollections.observableArrayList();

        List<String> categoryList = database.MainPage.getCategories();
        elements.addAll(categoryList);

        lvContents.setItems(elements);
        lvContents.getSelectionModel().select(0);

        /*TableColumn PKID = new TableColumn("PKID");
        TableColumn Category = new TableColumn("Category");
        TableColumn ImageLink = new TableColumn("ImageLink");
        TableColumn Name = new TableColumn("Name");
        TableColumn Quantity = new TableColumn("Quantity");
        TableColumn Price = new TableColumn("Price");
        tvStockTable.getColumns().addAll(PKID,Category,ImageLink,Name,Quantity,Price);*/

        PKID.setCellValueFactory(
                new PropertyValueFactory<StockData, Integer>("PKID"));
        Category.setCellValueFactory(
                new PropertyValueFactory<StockData, String>("Category"));
        ImageLink.setCellValueFactory(
                new PropertyValueFactory<StockData, ImageView>("ImageLink"));
        Name.setCellValueFactory(
                new PropertyValueFactory<StockData, String>("Name"));
        Quantity.setCellValueFactory(
                new PropertyValueFactory<StockData, Integer>("Quantity"));
        Price.setCellValueFactory(
                new PropertyValueFactory<StockData, Double>("Price"));

        updateTableView(String.valueOf(lvContents.getSelectionModel().getSelectedItem()));
    }

    public void updateTableView(String category) {
        data = FXCollections.observableArrayList();
        try {
            ResultSet rs = database.MainPage.getTableData(category);
            while (rs.next()) {
                StockData cm = new StockData();
                cm.PKID.set(rs.getInt("PKID"));
                String test = String.format("/resources/images/%s/%s", rs.getString("Category"), rs.getString("ImageLink"));
                BufferedImage originalImage = ImageIO.read(getClass().getResource(String.format("/resources/images/%s/%s", rs.getString("Category"), rs.getString("ImageLink"))));
                Image img = SwingFXUtils.toFXImage(originalImage, null);
                ImageView mv = new ImageView();
                mv.setImage(img);
                mv.setFitWidth(70);
                mv.setFitHeight(80);
                cm.PKID.set(rs.getInt("PKID"));
                cm.Category.set(rs.getString("Category"));
                cm.ImageLink.set(mv);
                cm.Name.set(rs.getString("Name"));
                cm.Quantity.set(rs.getInt("Quantity"));
                cm.Price.set(rs.getDouble("Price"));
                data.add(cm);
            }
            tvStockTable.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    /*  public void updateTableView(String category) {
          tableData = FXCollections.observableArrayList();
          try{
              ResultSet sqlResults = database.MainPage.getTableData(category);
              for(int i=0 ; i<sqlResults.getMetaData().getColumnCount(); i++){
                  final int j = i;
                  TableColumn col = new TableColumn(sqlResults.getMetaData().getColumnName(i+1));
                  col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                  tvStockTable.getColumns().addAll(col);
              }

              while(sqlResults.next()){
                  //Iterate Row
                  ObservableList<String> row = FXCollections.observableArrayList();
                  for(int i=1 ; i<=sqlResults.getMetaData().getColumnCount(); i++){
                      //Iterate Column
                      row.add(sqlResults.getString(i));
                  }
                  tableData.add(row);
              }

              //FINALLY ADDED TO TableView
              tvStockTable.setItems(tableData);
          }catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");
          }
      }
  */
    public void searchStock(MouseEvent mouseEvent) {
    }
}

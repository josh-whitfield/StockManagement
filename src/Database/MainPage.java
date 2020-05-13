package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class MainPage {
    public static List<String> getCategories() {
        try {
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_GetCategories(?)}");
            myStmt.registerOutParameter(1, Types.VARCHAR);

            myStmt.execute();
            String categories = myStmt.getString(1);
            myConn.close();

            String[] categoryArray = categories.split(",");
            List<String> categoryList = new ArrayList<>();
            for (String category : categoryArray) {
                categoryList.add(category.trim());
            }

            return categoryList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getTableData(String category) {
        try {
            Connection myConn = database.DB_Connect.getConnection();
            ResultSet tableData = myConn.createStatement().executeQuery(String.format("CALL usp_GetStockInfo('%s');", category));

            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getAllData() {
        try {
            Connection myConn = database.DB_Connect.getConnection();
            ResultSet tableData = myConn.createStatement().executeQuery("CALL usp_GetAllStock;");

            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateStockQuantity(int PKID, int newQuantity) {
        try {
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveStockUpdate(?,?)}");
            myStmt.setInt(1, PKID);
            myStmt.setInt(2, newQuantity);

            myStmt.execute();
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getSearchResults(String searchTerm) {
        try {
            Connection myConn = database.DB_Connect.getConnection();
            ResultSet tableData = myConn.createStatement().executeQuery(String.format("CALL usp_SearchStock('%s');", searchTerm));

            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

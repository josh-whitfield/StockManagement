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
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            //Call Stored Procedure with Parameters
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_GetCategories(?)}");
            myStmt.registerOutParameter(1, Types.VARCHAR);

            //Execute Stored Procedure
            myStmt.execute();
            String categories = myStmt.getString(1);
            //Close SQL connection
            myConn.close();

            String[] categoryArray = categories.split(",");
            List<String> categoryList = new ArrayList<>();
            //Add SQL results to Category List
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
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            //Call Stored Procedure in SQL query, leave connection open for ResultSet
            ResultSet tableData = myConn.createStatement().executeQuery(String.format("CALL usp_GetStockInfo('%s');", category));

            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Called for exporting all Stock Data
    public static ResultSet getAllData() {
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            //Call Stored Procedure, leave connection open for ResultSet
            ResultSet tableData = myConn.createStatement().executeQuery("CALL usp_GetAllStock;");

            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateStockQuantity(int PKID, int newQuantity) {
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            //Call Stored Procedure with Parameters
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveStockUpdate(?,?)}");
            myStmt.setInt(1, PKID);
            myStmt.setInt(2, newQuantity);

            //Execute Stored Procedure
            myStmt.execute();
            //Close SQL connection
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getSearchResults(String searchTerm) {
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            //Call Stored Procedure, leave connection open for ResultSet
            ResultSet tableData = myConn.createStatement().executeQuery(String.format("CALL usp_SearchStock('%s');", searchTerm));

            return tableData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void removeAutoLogin(String username) {
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            //Call Stored Procedure with Parameters
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_RemoveAutoLogin(?)}");
            myStmt.setString(1, username);

            //Execute Stored Procedure
            myStmt.execute();
            //Close SQL connection
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

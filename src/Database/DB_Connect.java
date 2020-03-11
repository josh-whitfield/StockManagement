package database;

import java.sql.*;
import javax.swing.JOptionPane;

public class DB_Connect {
    public static Connection GetDatabaseConnection() {
        String myUrl = "jdbc:mysql://localhost:3306/stock_management";
        try {
            return DriverManager.getConnection(myUrl, "root", "Password99");
        }
        catch(Exception e){
            return null;
        }
    }
}

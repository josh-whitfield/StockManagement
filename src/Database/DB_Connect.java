package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_Connect {
    public static Connection getConnection() {
        //Establish URL connection to local DB
        String myUrl = "jdbc:mysql://localhost:3306/stock_management?serverTimezone=GMT";
        try {
            return DriverManager.getConnection(myUrl, "root", "Password99");
        } catch (Exception e) {
            return null;
        }
    }
}

package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_Connect {
    public static Connection connection() {
        String myUrl = "jdbc:mysql://localhost:3306/stock_management";
        try {
            return DriverManager.getConnection(myUrl, "root", "Password99");
        }
        catch(Exception e){
            return null;
        }
    }
}

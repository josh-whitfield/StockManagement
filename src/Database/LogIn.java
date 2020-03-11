package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.util.Properties;

public class LogIn {
    public static boolean CheckLogIn(String username, String password){
        try {
            Connection myConn = database.DB_Connect.GetDatabaseConnection();
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_CheckLogIn(?,?,?)}");
            myStmt.setString(1,username);
            myStmt.setString(2,password);
            myStmt.registerOutParameter(3, Types.BIT);

            myStmt.execute();
            boolean correctLogin = myStmt.getBoolean(3);
            myConn.close();

            return correctLogin;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}

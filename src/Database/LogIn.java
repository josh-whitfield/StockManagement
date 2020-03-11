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
            return false;
        }
    }

    public static boolean SaveLogIn(String username, String password, String macAddress, String PC_Username){
        try {
            Connection myConn = database.DB_Connect.GetDatabaseConnection();
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveLogIn(?,?,?,?)}");
            myStmt.setString(1,username);
            myStmt.setString(2,password);
            myStmt.setString(3,macAddress);
            myStmt.setString(4,PC_Username);

            myStmt.execute();
            myConn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

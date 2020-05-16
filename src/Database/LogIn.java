package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class LogIn {
    public static boolean checkLogIn(String username, String password){
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            //Call Stored Procedure with Parameters
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_CheckLogIn(?,?,?)}");
            myStmt.setString(1, username);
            myStmt.setString(2, password);
            myStmt.registerOutParameter(3, Types.BIT);

            //Execute Stored Procedure and return true or false value
            myStmt.execute();
            boolean correctLogin = myStmt.getBoolean(3);

            //Close SQL connection
            myConn.close();
            return correctLogin;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void saveLogIn(String username, String macAddress, String PC_Username) {
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            //Call Stored Procedure with Parameters
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_KeepLoggedIn(?,?,?)}");
            myStmt.setString(1, username);
            myStmt.setString(2, macAddress);
            myStmt.setString(3, PC_Username);

            //Execute Stored Procedure
            myStmt.execute();
            //Close SQL connection
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

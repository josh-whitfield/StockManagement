package database;

import references.UserDetails;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class Main {
    public static boolean checkAutoLogIn(String macAddress, String PC_Username){
        try {
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_CheckAutoLogIn(?,?,?)}");
            myStmt.setString(1,macAddress);
            myStmt.setString(2,PC_Username);
            myStmt.registerOutParameter(3, Types.BIT);

            myStmt.execute();
            boolean autoLogin = myStmt.getBoolean(3);
            myConn.close();
            return autoLogin;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void getAccountDetails(String macAddress, String PC_Username){
        try {
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_GetAccountDetails(?,?,?,?)}");
            myStmt.setString(1,macAddress);
            myStmt.setString(2,PC_Username);
            myStmt.registerOutParameter(3, Types.VARCHAR);
            myStmt.registerOutParameter(4, Types.VARCHAR);

            myStmt.execute();
            String username = myStmt.getString(3);
            int accessLevel = myStmt.getInt(4);
            myConn.close();

            UserDetails.Username = username;
            UserDetails.AccessLevel = accessLevel;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

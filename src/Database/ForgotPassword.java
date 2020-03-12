package database;

import java.sql.CallableStatement;
import java.sql.Connection;

public class ForgotPassword {
    public static void UpdatePassword(String username, String password){
        try {
            Connection myConn = database.DB_Connect.connection();
            assert myConn != null;
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_ChangePassword(?,?)}");
            myStmt.setString(1,username);
            myStmt.setString(2, password);

            myStmt.execute();
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

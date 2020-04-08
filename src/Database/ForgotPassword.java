package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class ForgotPassword {
    public static boolean checkSecurityQuestion(int questionIndex, String username, String UserAnswer){
        try {
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_CheckSecurityQuestion(?,?,?,?)}");
            myStmt.setInt(1,questionIndex);
            myStmt.setString(2, username);
            myStmt.setString(3, UserAnswer);
            myStmt.registerOutParameter(4, Types.BIT);

            myStmt.execute();
            boolean correctAnswer = myStmt.getBoolean(4);
            myConn.close();
            return correctAnswer;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void updatePassword(String username, String password){
        try {
            Connection myConn = database.DB_Connect.getConnection();
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

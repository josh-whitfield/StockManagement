package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class ForgotPassword {
    public static boolean checkSecurityQuestion(int questionIndex, String username, String UserAnswer){
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            //Call Stored Procedure with Parameters
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_CheckSecurityQuestion(?,?,?,?)}");
            myStmt.setInt(1,questionIndex);
            myStmt.setString(2, username);
            myStmt.setString(3, UserAnswer);
            myStmt.registerOutParameter(4, Types.BIT);

            //Execute Stored Procedure and return true or false value
            myStmt.execute();
            boolean correctAnswer = myStmt.getBoolean(4);
            //Close SQL connection
            myConn.close();
            return correctAnswer;
        } catch (Exception e) {
            e.printStackTrace();
            //Return false on error to deny pass
            return false;
        }
    }

    public static void updatePassword(String username, String password){
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            //Call Stored Procedure with Parameters
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_ChangePassword(?,?)}");
            myStmt.setString(1,username);
            myStmt.setString(2, password);

            //Execute Stored Procedure
            myStmt.execute();
            //Close SQL connection
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

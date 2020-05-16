package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class CreateAccount {
    public static int checkUsernameTaken(String username) {
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            //Call Stored Procedure with Parameters
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_CheckUsernameTaken(?,?)}");
            myStmt.setString(1, username);
            myStmt.registerOutParameter(2, Types.INTEGER);

            //Execute Stored Procedure and return integer value
            myStmt.execute();
            int usernameCount = myStmt.getInt(2);

            //Close SQL connection
            myConn.close();
            return usernameCount;
        } catch (Exception e) {
            e.printStackTrace();
            //Return value beyond one for error handling
            return 2;
        }
    }

    public static void saveUserDetails(String username, String password, int accessLevel) {
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            //Call Stored Procedure with Parameters
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveLogIn(?,?,?)}");
            myStmt.setString(1, username);
            myStmt.setString(2, password);
            myStmt.setInt(3, accessLevel);

            //Execute Stored Procedure
            myStmt.execute();
            //Close SQL connection
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveSecurityQuestions(String username, String answer1, String answer2, String answer3) {
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            //Call Stored Procedure with Parameters
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveSecurityQuestions(?,?,?,?)}");
            myStmt.setString(1, username);
            myStmt.setString(2, answer1);
            myStmt.setString(3, answer2);
            myStmt.setString(4, answer3);

            //Execute Stored Procedure
            myStmt.execute();
            //Close SQL connection
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveSalt(String username, byte[] password, byte[] answerOne, byte[] answerTwo, byte[] answerThree) {
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            //Call Stored Procedure with Parameters
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveSalt(?,?,?,?,?)}");
            myStmt.setString(1, username);
            myStmt.setBytes(2, password);
            myStmt.setBytes(3, answerOne);
            myStmt.setBytes(4, answerTwo);
            myStmt.setBytes(5, answerThree);

            //Execute Stored Procedure and return true or false value
            myStmt.execute();
            //Close SQL connection
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

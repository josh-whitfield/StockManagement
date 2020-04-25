package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class CreateAccount {
    public static int checkUsernameTaken(String username) {
        try {
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_CheckUsernameTaken(?,?)}");
            myStmt.setString(1, username);
            myStmt.registerOutParameter(2, Types.INTEGER);

            myStmt.execute();
            int usernameCount = myStmt.getInt(2);
            myConn.close();
            return usernameCount;
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
    }

    public static void saveUserDetails(String username, String password, int accessLevel) {
        try {
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveLogIn(?,?,?)}");
            myStmt.setString(1, username);
            myStmt.setString(2, password);
            myStmt.setInt(3, accessLevel);

            myStmt.execute();
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveSecurityQuestions(String username, String answer1, String answer2, String answer3) {
        try {
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveSecurityQuestions(?,?,?,?)}");
            myStmt.setString(1, username);
            myStmt.setString(2, answer1);
            myStmt.setString(3, answer2);
            myStmt.setString(4, answer3);

            myStmt.execute();
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveSalt(String username, byte[] password, byte[] answerOne, byte[] answerTwo, byte[] answerThree) {
        try {
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveSalt(?,?,?,?,?)}");
            myStmt.setString(1, username);
            myStmt.setBytes(2, password);
            myStmt.setBytes(3, answerOne);
            myStmt.setBytes(4, answerTwo);
            myStmt.setBytes(5, answerThree);

            myStmt.execute();
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

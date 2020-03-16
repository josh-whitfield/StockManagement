package database;

import java.sql.CallableStatement;
import java.sql.Connection;

public class CreateAccount {
    public static void SaveSalt(String username){
        try {
            Connection myConn = database.DB_Connect.connection();
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveSalt(?,?,?,?,?)}");
            myStmt.setString(1,username);
            myStmt.setBytes(2,references.Hashing.salt());
            myStmt.setBytes(3,references.Hashing.salt());
            myStmt.setBytes(4,references.Hashing.salt());
            myStmt.setBytes(5,references.Hashing.salt());

            myStmt.execute();
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SaveAnswers(String username, String answer1, String answer2, String answer3){
        try {
            Connection myConn = database.DB_Connect.connection();
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveSecurityQuestions(?,?,?,?)}");
            myStmt.setString(1,username);
            myStmt.setString(2,answer1);
            myStmt.setString(3,answer2);
            myStmt.setString(4,answer3);

            myStmt.execute();
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package database;

import java.sql.CallableStatement;
import java.sql.Connection;

public class CreateAccount {
    public static void saveSalt(String username){
        try {
            Connection myConn = database.DB_Connect.getConnection();
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveSalt(?,?,?,?,?)}");
            myStmt.setString(1,username);
            myStmt.setBytes(2,references.Hashing.createSalt());
            myStmt.setBytes(3,references.Hashing.createSalt());
            myStmt.setBytes(4,references.Hashing.createSalt());
            myStmt.setBytes(5,references.Hashing.createSalt());

            myStmt.execute();
            myConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveAnswers(String username, String answer1, String answer2, String answer3){
        try {
            Connection myConn = database.DB_Connect.getConnection();
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

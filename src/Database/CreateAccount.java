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
}

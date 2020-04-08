package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class Global {
    public static byte[] getSalt(String username, String saltType){
        try {
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_FindSalt(?,?,?)}");
            myStmt.setString(1,username);
            myStmt.setString(2,saltType);
            myStmt.registerOutParameter(3, Types.BLOB);

            myStmt.execute();
            byte[] salt = myStmt.getBytes(3);
            myConn.close();
            return salt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

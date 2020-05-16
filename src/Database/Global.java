package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

//Stored Procedure(s) that may be called by multiple classes
public class Global {
    public static byte[] getSalt(String username, String saltType){
        try {
            //Establish new SQL connection
            Connection myConn = database.DB_Connect.getConnection();
            assert myConn != null;
            //Call Stored Procedure with Parameters
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_FindSalt(?,?,?)}");
            myStmt.setString(1,username);
            myStmt.setString(2,saltType);
            myStmt.registerOutParameter(3, Types.BLOB);

            //Execute Stored Procedure and return blob/byte[] value
            myStmt.execute();
            byte[] salt = myStmt.getBytes(3);
            //Close SQL connection
            myConn.close();
            return salt;
        } catch (Exception e) {
            e.printStackTrace();
            //Return Null to stop and further procedures
            return null;
        }
    }
}

package database;

import references.Hashing;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class LogIn {
    public static byte[] getSalt(String username){
        try {
            Connection myConn = database.DB_Connect.connection();
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_FindSalt(?,?)}");
            myStmt.setString(1,username);
            myStmt.registerOutParameter(2, Types.BLOB);

            myStmt.execute();
            byte[] salt = myStmt.getBytes(2);
            myConn.close();
            return salt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkLogIn(String username, String password, byte[] salt){
        try {
            String hashedPassword = Hashing.hashPassword(password, salt);

            String myString = hashedPassword;
            StringSelection stringSelection = new StringSelection(myString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            Connection myConn = database.DB_Connect.connection();
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_CheckLogIn(?,?,?)}");
            myStmt.setString(1,username);
            myStmt.setString(2,hashedPassword);
            myStmt.registerOutParameter(3, Types.BIT);

            myStmt.execute();
            boolean correctLogin = myStmt.getBoolean(3);
            myConn.close();
            return correctLogin;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean saveLogIn(String username, String password, String macAddress, String PC_Username){
        try {
            Connection myConn = database.DB_Connect.connection();
            CallableStatement myStmt = myConn.prepareCall("{CALL usp_SaveLogIn(?,?,?,?)}");
            myStmt.setString(1,username);
            myStmt.setString(2,password);
            myStmt.setString(3,macAddress);
            myStmt.setString(4,PC_Username);

            myStmt.execute();
            myConn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

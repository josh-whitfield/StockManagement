package Database;

import java.sql.*;
import javax.swing.JOptionPane;

public class DB_Connect {
    public static void main(String[] args) {
        String myDriver = "org.gjt.mm.mysql.Driver";
        String myUrl = "jdbc:mysql://localhost:3306/stock_management";
        try {
            Connection conn = DriverManager.getConnection(myUrl, "root", "MISB3W1serSQL!");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"test","test",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

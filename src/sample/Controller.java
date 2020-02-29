package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.sql.*;
import java.util.Properties;

public class Controller {
    @FXML CheckBox chkStayLoggedIn;
    @FXML TextField txtUsername;
    @FXML TextField txtPassword;

    public void logIn(ActionEvent actionEvent) {
        //login sql stuff here
        Connection myConn = null;
        CallableStatement myStmt = null;
        boolean correctLogin = false;

        try {
            //Store username and encoded password (look into salting as well) as text file somewhere on PC
            //On log out, clear text file

            String url = "jdbc:mysql://localhost:3306/stock_management";
            Properties info = new Properties();
            info.put("user","root");
            info.put("password","MISB3W1serSQL!");

            myConn = DriverManager.getConnection(url,info);
            myStmt = myConn.prepareCall("{CALL usp_CheckLogIn(?,?,?)}");
            myStmt.setString(1,txtUsername.getText());
            myStmt.setString(2,txtPassword.getText());
            myStmt.registerOutParameter(3, Types.BIT);

            myStmt.execute();
            correctLogin = myStmt.getBoolean(3);

            if (correctLogin) {
                JOptionPane.showMessageDialog(null, "Credentials Valid", "Credentials Valid", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Incorrect username or password", "Invalid Login", JOptionPane.INFORMATION_MESSAGE);
                txtPassword.setText("");
                txtUsername.requestFocus();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forgotPassword(ActionEvent actionEvent) {
    }

    public void createAccount(ActionEvent actionEvent) {
    }
}

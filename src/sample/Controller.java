package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
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
            //Store username and encoded password (look into salting as well) as text file somewhere on PC ()
            //On log out, clear text file
//For "Keep me logged in" also save timestamp so it can expire. Date log in saved, date log in expires
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
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getClassLoader().getResource("@../sample/forgotPassword.fxml"),resources);
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAccount(ActionEvent actionEvent) {
    }
}

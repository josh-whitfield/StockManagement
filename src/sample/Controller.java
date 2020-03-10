package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.*;
import java.util.Properties;

public class Controller {
    @FXML CheckBox chkStayLoggedIn;
    @FXML TextField txtUsername;
    @FXML TextField txtPassword;
    @FXML GridPane loginGrid;

    public void logIn(ActionEvent actionEvent) {
        //login sql stuff here
        Connection myConn = null;
        CallableStatement myStmt = null;
        boolean correctLogin = false;

        try {
            String url = "jdbc:mysql://localhost:3306/stock_management";
            Properties info = new Properties();
            info.put("user","root");
            info.put("password","Password99");

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

            if (chkStayLoggedIn.isSelected()) {
                byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                //TODO - Store mac address and PC user Name
                

                JOptionPane.showMessageDialog(null, String.format("Mac Address: %s",sb.toString()), "Temp", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, String.format("User Name: %s",System.getProperty("user.name")), "Temp", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forgotPassword(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("forgotPassword.fxml"));
            Stage stage = (Stage) loginGrid.getScene().getWindow();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAccount(ActionEvent actionEvent) {
    }
}

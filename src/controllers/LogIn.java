package controllers;
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

public class LogIn {
    @FXML CheckBox chkStayLoggedIn;
    @FXML TextField txtUsername;
    @FXML TextField txtPassword;
    @FXML GridPane loginGrid;

    public void logIn(ActionEvent actionEvent) {
        Connection myConn = null;
        CallableStatement myStmt = null;
        boolean correctLogin = false;

        try {
            if (database.LogIn.CheckLogIn(txtUsername.getText(),txtPassword.getText())) {
                JOptionPane.showMessageDialog(null, "Credentials Valid", "Credentials Valid", JOptionPane.INFORMATION_MESSAGE);

                if (chkStayLoggedIn.isSelected()) {
                    byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    database.LogIn.SaveLogIn(txtUsername.getText(),txtPassword.getText(),sb.toString(),System.getProperty("user.name"));
                }
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
        try{
            Parent root = FXMLLoader.load(getClass().getResource("ForgotPassword.fxml"));
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

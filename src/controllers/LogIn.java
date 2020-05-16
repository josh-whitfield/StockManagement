package controllers;

import database.Global;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import references.Hashing;
import references.PC_Credentials;

import javax.swing.*;
import java.io.IOException;

public class LogIn {
    @FXML GridPane loginGrid;
    @FXML TextField txtUsername;
    @FXML TextField txtPassword;
    @FXML CheckBox chkStayLoggedIn;

    private void logIn() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        //Check for blanks
        if (!username.equals("") && !password.equals("")) {
            try {
                //Check if user and pass are valid in DB (hashed and salted)
                if (database.LogIn.checkLogIn(username, Hashing.hashValue(password, Global.getSalt(username, "Password")))) {
                    //Get PC user details
                    String macAddress = PC_Credentials.getMacAddress();
                    String PC_Username = PC_Credentials.getPcUsername();
                    //Save details for automatic log in if checked
                    if (chkStayLoggedIn.isSelected())
                        database.LogIn.saveLogIn(username, macAddress, PC_Username);
                    try {
                        //Save account details in memory
                        Global.getAccessLevel(username);
                        //Load main Stock Management page
                        Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MainPage.fxml"));
                        Stage stage = (Stage) loginGrid.getScene().getWindow();
                        stage.setTitle("Stock Management");
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //Clear and set password field on incorrect details
                    JOptionPane.showMessageDialog(null, "Incorrect username or password", "Invalid Login", JOptionPane.INFORMATION_MESSAGE);
                    txtPassword.setText("");
                    txtUsername.requestFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //Clear and set password field on missing details
            JOptionPane.showMessageDialog(null, "Please enter a username and password", "Invalid Login", JOptionPane.INFORMATION_MESSAGE);
            txtPassword.setText("");
            txtUsername.requestFocus();
        }
    }

    private void forgotPassword() {
        try {
            //Load Forgot Password page
            Parent root = FXMLLoader.load(getClass().getResource("/resources/view/ForgotPassword.fxml"));
            Stage stage = (Stage) loginGrid.getScene().getWindow();
            stage.setTitle("Forgot Password");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAccount() {
        try {
            //Load Create Account page
            Parent root = FXMLLoader.load(getClass().getResource("/resources/view/CreateAccount.fxml"));
            Stage stage = (Stage) loginGrid.getScene().getWindow();
            stage.setTitle("Create Account");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

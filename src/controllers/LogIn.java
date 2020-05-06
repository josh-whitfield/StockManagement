package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import references.PC_Credentials;

import javax.swing.*;
import java.io.IOException;

public class LogIn {
    @FXML GridPane loginGrid;
    @FXML TextField txtUsername;
    @FXML TextField txtPassword;
    @FXML CheckBox chkStayLoggedIn;

    public void logIn() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        //TODO - Remove below
        username = "testAdmin";
        password = "Whitf9919!";

        if (!username.equals("") && !password.equals("")) {
            try {
                if (database.LogIn.checkLogIn(username, references.Hashing.hashValue(password, database.Global.getSalt(username, "Password")))) {
                    if (chkStayLoggedIn.isSelected())
                        database.LogIn.saveLogIn(username, password, PC_Credentials.getMacAddress(), PC_Credentials.getPcUsername());
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MainPage.fxml"));
                        Stage stage = (Stage) loginGrid.getScene().getWindow();
                        stage.setTitle("Stock Management");
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password", "Invalid Login", JOptionPane.INFORMATION_MESSAGE);
                    txtPassword.setText("");
                    txtUsername.requestFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a username and password", "Invalid Login", JOptionPane.INFORMATION_MESSAGE);
            txtPassword.setText("");
            txtUsername.requestFocus();
        }
    }

    public void forgotPassword() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/view/ForgotPassword.fxml"));
            Stage stage = (Stage) loginGrid.getScene().getWindow();
            stage.setTitle("Forgot Password");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAccount() {
        try {
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

package controllers;

import javafx.event.ActionEvent;
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

    public void logIn(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        try {
            if (database.LogIn.checkLogIn(username,password,database.LogIn.getSalt(username))) {
                if (chkStayLoggedIn.isSelected())
                    database.LogIn.saveLogIn(username, password, PC_Credentials.macAddress(), PC_Credentials.PC_Username());
                //TODO - Open new window
                try{
                    Parent root = FXMLLoader.load(getClass().getResource("/resources/view/ForgotPassword.fxml"));
                    Stage stage = (Stage) loginGrid.getScene().getWindow();
                    stage.setTitle("Forgot Password");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
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
            Parent root = FXMLLoader.load(getClass().getResource("/resources/view/ForgotPassword.fxml"));
            Stage stage = (Stage) loginGrid.getScene().getWindow();
            stage.setTitle("Forgot Password");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAccount(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/resources/view/ForgotPassword.fxml"));
            Stage stage = (Stage) loginGrid.getScene().getWindow();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

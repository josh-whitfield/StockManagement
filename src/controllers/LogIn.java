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

import javax.swing.*;
import java.io.IOException;

public class LogIn {
    @FXML CheckBox chkStayLoggedIn;
    @FXML TextField txtUsername;
    @FXML TextField txtPassword;
    @FXML GridPane loginGrid;

    public void logIn(ActionEvent actionEvent) {
        try {
            if (database.LogIn.checkLogIn(txtUsername.getText(),txtPassword.getText())) {
                JOptionPane.showMessageDialog(null, "Credentials Valid", "Credentials Valid", JOptionPane.INFORMATION_MESSAGE);
                if (chkStayLoggedIn.isSelected()) {
                    database.LogIn.saveLogIn(txtUsername.getText(),txtPassword.getText(),references.UserCredentials.macAddress(),references.UserCredentials.PC_Username());
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

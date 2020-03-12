package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class ForgotPassword {
    @FXML GridPane forgotPasswordGrid;
    @FXML TextField txtUsername;
    @FXML ChoiceBox cbSecurityQuestion;
    @FXML TextField txtAnswer;

    public void initialize(){
        cbSecurityQuestion.setItems(FXCollections.observableArrayList(
                "Please pick one of the following:",new Separator(), "What is your spouse or partner's mother's maiden name? ", "What was the name of your first pet?", "In what city were you born?")
        );
        cbSecurityQuestion.setValue("Please pick one of the following:");
    }

    public void logIn(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/view/LogIn.fxml"));
            Stage stage = (Stage) forgotPasswordGrid.getScene().getWindow();
            stage.setTitle("Log In");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submit(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String chosenQuestion = (String) cbSecurityQuestion.getSelectionModel().getSelectedItem();
        String answer = txtAnswer.getText();

        String missingInfo = "";
        if (username == "" || username.isEmpty()) missingInfo += "Please give your username\r\n";
        if (chosenQuestion == "Please pick one of the following:")
            missingInfo += "Please choose a security question\r\n";
        if (answer == "" || answer.isEmpty()) missingInfo += "Please give your answer\r\n";

        if (missingInfo != "")
            JOptionPane.showMessageDialog(null, "There is missing information:\r\n" + missingInfo, "Missing Information", JOptionPane.INFORMATION_MESSAGE);

        int selectedIndex = cbSecurityQuestion.getSelectionModel().getSelectedIndex();
    }
}

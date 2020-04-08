package controllers;

import database.Global;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import references.Hashing;

import javax.swing.*;

public class CreateAccount {
    @FXML GridPane createAccountGrid;
    @FXML TextField txtUsername;
    @FXML TextField txtPassword;
    @FXML TextField txtConfirmPassword;
    @FXML TextField txtAnswer1;
    @FXML TextField txtAnswer2;
    @FXML TextField txtAnswer3;

    public void createAccount() {
        //Validate data
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String answer1 = txtAnswer1.getText();
        String answer2 = txtAnswer2.getText();
        String answer3 = txtAnswer3.getText();

        StringBuilder missingInfo = new StringBuilder();

        if (username == "" || username.isEmpty()) missingInfo.append("Please give your username\r\n");
        if (password == "" || password.isEmpty()) missingInfo.append("Please give your password\r\n");
        if (confirmPassword == "" || confirmPassword.isEmpty()) missingInfo.append("Please confirm your password\r\n");
        if (answer1 == "" || answer1.isEmpty() || answer2 == "" || answer2.isEmpty() || answer3 == "" || answer3.isEmpty())
            missingInfo.append("Please answer all security questions\r\n");

        if (missingInfo.toString() != "") {
            JOptionPane.showMessageDialog(null, "There is missing information:\r\n" + missingInfo.toString(), "Missing Information", JOptionPane.INFORMATION_MESSAGE);
            clearPasswordFields();
        } else {
            if (password != confirmPassword) {
                JOptionPane.showMessageDialog(null, "Please ensure your passwords match", "Passwords do not match", JOptionPane.INFORMATION_MESSAGE);
                clearPasswordFields();
            } else {
                int selectedIndex = cbSecurityQuestion.getSelectionModel().getSelectedIndex() - 1;
                boolean correctAnswer = database.ForgotPassword.checkSecurityQuestion(selectedIndex, username, Hashing.hashValue(answer, Global.getSalt(username, String.format("Question%s", selectedIndex))));
                JOptionPane.showMessageDialog(null, correctAnswer, "Submitted", JOptionPane.INFORMATION_MESSAGE);
                //Create Salt

                //Hash

                //Store to DB
            }
        }
    }

    private void clearPasswordFields() {
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        txtPassword.requestFocus();
    }
}

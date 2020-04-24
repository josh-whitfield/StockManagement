package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import references.Hashing;

import javax.swing.*;

public class CreateAccount {
    @FXML
    GridPane createAccountGrid;
    @FXML
    TextField txtUsername;
    @FXML
    TextField txtPassword;
    @FXML
    TextField txtConfirmPassword;
    @FXML
    ChoiceBox<String> cbAccessLevel;
    @FXML
    TextField txtAnswer1;
    @FXML
    TextField txtAnswer2;
    @FXML
    TextField txtAnswer3;

    public void initialize() {
        cbAccessLevel.setItems(FXCollections.observableArrayList("User", "Admin"));
    }

    public void createAccount() {
        //Validate data
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String answer1 = txtAnswer1.getText();
        String answer2 = txtAnswer2.getText();
        String answer3 = txtAnswer3.getText();

        StringBuilder missingInfo = new StringBuilder();

        if (username.equals("") || username.isEmpty()) missingInfo.append("Please give your username\r\n");
        if (password.equals("") || password.isEmpty()) missingInfo.append("Please give your password\r\n");
        if (confirmPassword.equals("") || confirmPassword.isEmpty())
            missingInfo.append("Please confirm your password\r\n");
        if (answer1.equals("") || answer1.isEmpty() || answer2.equals("") || answer2.isEmpty() || answer3.equals("") || answer3.isEmpty())
            missingInfo.append("Please answer all security questions\r\n");

        if (!missingInfo.toString().equals("")) {
            JOptionPane.showMessageDialog(null, "There is missing information:\r\n" + missingInfo.toString(), "Missing Information", JOptionPane.INFORMATION_MESSAGE);
            clearPasswordFields();
        } else {
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Please ensure your passwords match", "Passwords do not match", JOptionPane.INFORMATION_MESSAGE);
                clearPasswordFields();
            } else {
                //Create Salt
                byte[] passwordSalt = Hashing.createSalt();
                byte[] answerOneSalt = Hashing.createSalt();
                byte[] answerTwoSalt = Hashing.createSalt();
                byte[] answerThreeSalt = Hashing.createSalt();
                //Hash
                String hashedPassword = Hashing.hashValue(password, passwordSalt);
                String hashedAnswerOne = Hashing.hashValue(password, answerOneSalt);
                String hashedAnswerTwo = Hashing.hashValue(password, answerTwoSalt);
                String hashedAnswerThree = Hashing.hashValue(password, answerThreeSalt);
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

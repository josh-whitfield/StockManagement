package controllers;

import database.Global;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import references.Hashing;

import java.io.IOException;

public class CreateAccount {
    @FXML
    Label lblUsernameInvalid;
    @FXML
    Label lblUsernameRequired;
    @FXML
    Label lblUsernameTaken;
    @FXML
    Label lblInvalidPassword;
    @FXML
    Label lblPasswordMismatch;
    @FXML
    Label lblPasswordsMissing;
    @FXML
    Label lblMissingQuestionOne;
    @FXML
    Label lblMissingQuestionTwo;
    @FXML
    Label lblMissingQuestionThree;
    @FXML
    Label lblAccessLevelRequired;
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
        //Populate Access level drop down
        cbAccessLevel.setItems(FXCollections.observableArrayList("User", "Admin"));

        //Hide all errors by default
        lblUsernameRequired.setVisible(false);
        lblUsernameInvalid.setVisible(false);
        lblUsernameTaken.setVisible(false);
        lblPasswordsMissing.setVisible(false);
        lblPasswordMismatch.setVisible(false);
        lblInvalidPassword.setVisible(false);
        lblAccessLevelRequired.setVisible(false);
        lblMissingQuestionOne.setVisible(false);
        lblMissingQuestionTwo.setVisible(false);
        lblMissingQuestionThree.setVisible(false);
    }

    @FXML
    private void createAccount() {
        try {
            //If data is valid...
            if (!validateData()) {
                //Get all entered Info
                String username = txtUsername.getText();
                String password = txtPassword.getText();
                String accessLevel = cbAccessLevel.getValue();
                String answer1 = txtAnswer1.getText();
                String answer2 = txtAnswer2.getText();
                String answer3 = txtAnswer3.getText();

                //Hash and salt sensitive information
                byte[] passwordSalt = Hashing.createSalt();
                byte[] answerOneSalt = Hashing.createSalt();
                byte[] answerTwoSalt = Hashing.createSalt();
                byte[] answerThreeSalt = Hashing.createSalt();

                //Save Log in details
                database.CreateAccount.saveUserDetails(
                        username,
                        Hashing.hashValue(password, passwordSalt),
                        accessLevel.equals("Admin") ? 1 : 0);

                //Save recovery details
                database.CreateAccount.saveSecurityQuestions(
                        username,
                        Hashing.hashValue(answer1, answerOneSalt),
                        Hashing.hashValue(answer2, answerTwoSalt),
                        Hashing.hashValue(answer3, answerThreeSalt));

                //Save salts
                database.CreateAccount.saveSalt(
                        username,
                        passwordSalt,
                        answerOneSalt,
                        answerTwoSalt,
                        answerThreeSalt);

                Global.getAccessLevel(username);

                //Open main stock management page
                Parent root = FXMLLoader.load(getClass().getResource("/resources/view/MainPage.fxml"));
                Stage stage = (Stage) createAccountGrid.getScene().getWindow();
                stage.setTitle("Stock Management");
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateData() {
        //Get all entered info
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String accessLevel = cbAccessLevel.getValue();
        String answer1 = txtAnswer1.getText();
        String answer2 = txtAnswer2.getText();
        String answer3 = txtAnswer3.getText();

        boolean errorsFound = false;
        //Validate Username
        if (username.equals("")) {
            lblUsernameRequired.setVisible(true);
            errorsFound = true;
        } else if (username.length() < 8 || username.length() > 64 || !username.matches("[a-zA-Z0-9-._]+")) {
            lblUsernameRequired.setVisible(false);
            lblUsernameInvalid.setVisible(true);
            errorsFound = true;
        } else if (database.CreateAccount.checkUsernameTaken(username) > 0) {
            lblUsernameRequired.setVisible(false);
            lblUsernameInvalid.setVisible(false);
            lblUsernameTaken.setVisible(true);
            errorsFound = true;
        } else {
            lblUsernameRequired.setVisible(false);
            lblUsernameInvalid.setVisible(false);
            lblUsernameTaken.setVisible(false);
        }

        //Validate password
        if (password.equals("") || confirmPassword.equals("")) {
            lblPasswordsMissing.setVisible(true);
            clearPasswordFields();
            errorsFound = true;
        } else if (!password.equals(confirmPassword)) {
            lblPasswordsMissing.setVisible(false);
            lblPasswordMismatch.setVisible(true);
            clearPasswordFields();
            errorsFound = true;
        } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,64}$")) {
            lblPasswordsMissing.setVisible(false);
            lblPasswordMismatch.setVisible(false);
            lblInvalidPassword.setVisible(true);
            clearPasswordFields();
            errorsFound = true;
        } else {
            lblPasswordsMissing.setVisible(false);
            lblPasswordMismatch.setVisible(false);
            lblInvalidPassword.setVisible(false);
        }

        //Validate access level
        if (accessLevel == null) {
            lblAccessLevelRequired.setVisible(true);
            errorsFound = true;
        } else {
            lblAccessLevelRequired.setVisible(false);
        }

        //Validate Security Question answers
        if (answer1.equals("")) {
            lblMissingQuestionOne.setVisible(true);
            errorsFound = true;
        } else {
            lblMissingQuestionOne.setVisible(false);
        }
        if (answer2.equals("")) {
            lblMissingQuestionTwo.setVisible(true);
            errorsFound = true;
        } else {
            lblMissingQuestionTwo.setVisible(false);
        }
        if (answer3.equals("")) {
            lblMissingQuestionThree.setVisible(true);
            errorsFound = true;
        } else {
            lblMissingQuestionThree.setVisible(false);
        }

        return errorsFound;
    }

    private void clearPasswordFields() {
        //Clear passwords and set focus
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        txtPassword.requestFocus();
    }

    @FXML
    private void logIn() {
        try {
            //Redirect back to log in
            Parent root = FXMLLoader.load(getClass().getResource("/resources/view/LogIn.fxml"));
            Stage stage = (Stage) createAccountGrid.getScene().getWindow();
            stage.setTitle("Log In");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

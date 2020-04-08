package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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


        //Create Salt

        //Hash

        //Store to DB
    }
}

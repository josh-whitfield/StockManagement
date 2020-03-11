package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ForgotPassword {
    @FXML ChoiceBox cbSecurityQuestion;

    ObservableList<String> cursors = FXCollections.observableArrayList("a","b","c");

    public void forgotPassword(){
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(cursors);

    }
}

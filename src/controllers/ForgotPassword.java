package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPassword {
    @FXML GridPane forgotPasswordGrid;
    @FXML ChoiceBox cbSecurityQuestion;

    ObservableList<String> cursors = FXCollections.observableArrayList("a","b","c");

    public void forgotPassword(){
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(cursors);
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
}

package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class ForgotPasswordController {

    ObservableList<String> cursors = FXCollections.observableArrayList("a","b","c");

    public void forgotPassword(){
        ChoiceBox<String> choiceBox = new ChoiceBox<String>();
        choiceBox.setItems(cursors);
    }
}

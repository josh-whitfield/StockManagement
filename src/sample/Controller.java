package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class Controller {
    @FXML CheckBox chkStayLoggedIn;

    public void logIn(ActionEvent actionEvent) {
        //login sql stuff here
        if (chkStayLoggedIn.isSelected()){
            JOptionPane.showMessageDialog(null, "Checkbox is selected", "Selected", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null, "Checkbox is NOT selected", "NOT Selected", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void forgotPassword(ActionEvent actionEvent) {
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getClassLoader().getResource("@../sample/forgotPassword.fxml"),resources);
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAccount(ActionEvent actionEvent) {
    }
}

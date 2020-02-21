package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javax.swing.*;

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
    }

    public void createAccount(ActionEvent actionEvent) {
    }
}

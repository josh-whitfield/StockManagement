package controllers;

import database.Global;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import references.Hashing;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ForgotPassword {
    @FXML GridPane forgotPasswordGrid;
    @FXML TextField txtUsername;
    @FXML ChoiceBox cbSecurityQuestion;
    @FXML TextField txtAnswer;

    public void initialize(){
        //Populate Security Question dropdown and set default value
        cbSecurityQuestion.setItems(FXCollections.observableArrayList(
                "Please pick one of the following:", new Separator(),
                "What is your mother's maiden name?",
                "What was the name of your first pet?",
                "In what city were you born?"));
        cbSecurityQuestion.setValue("Please pick one of the following:");
    }

    @FXML
    private void logIn() {
        try {
            //Redirect back to log in page
            Parent root = FXMLLoader.load(getClass().getResource("/resources/view/LogIn.fxml"));
            Stage stage = (Stage) forgotPasswordGrid.getScene().getWindow();
            stage.setTitle("Log In");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void submit() {
        //Get entered info
        String username = txtUsername.getText();
        String chosenQuestion = (String) cbSecurityQuestion.getSelectionModel().getSelectedItem();
        String answer = txtAnswer.getText();

        //Validate any missing info
        StringBuilder missingInfo = new StringBuilder();
        if (username.equals("") || username.isEmpty()) missingInfo.append("Please give your username\r\n");
        if (chosenQuestion.equals("Please pick one of the following:"))
            missingInfo.append("Please choose a security question\r\n");
        if (answer.equals("") || answer.isEmpty()) missingInfo.append("Please give your answer\r\n");

        if (!missingInfo.toString().equals(""))
            JOptionPane.showMessageDialog(null, "There is missing information:\r\n" + missingInfo.toString(), "Missing Information", JOptionPane.INFORMATION_MESSAGE);
        else {
            //Check security question versus saved answer (both hashed and salted)
            int selectedIndex = cbSecurityQuestion.getSelectionModel().getSelectedIndex() - 1;
            if (database.ForgotPassword.checkSecurityQuestion(
                    selectedIndex, username, Hashing.hashValue(
                            answer, Global.getSalt(
                                    username, String.format("Question%s", selectedIndex))))) {

                //While password is not null (cancelled) or valid, keep returning the new password module
                List<String> password = enterNewPassword();
                while (password.get(0) != "Pass") {
                    JOptionPane.showMessageDialog(null, password.get(1), "Invalid Password", JOptionPane.INFORMATION_MESSAGE);
                    password = enterNewPassword();
                }
                if (password.get(0) == "Pass") {
                    //Update password
                    database.ForgotPassword.updatePassword(username, Hashing.hashValue(password.get(1), Global.getSalt(username, "Password")));
                    JOptionPane.showMessageDialog(null, "Password changed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    logIn();
                }
                //Wrong Security Answer
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect answer for Security Question", "Incorrect Answer", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private List<String> enterNewPassword() {
        //Declare properties for new JPanel
        JTextField txtPassword = new JPasswordField(5);
        JTextField txtConfirmPassword = new JPasswordField(5);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Use between 8 and 64 characters"));
        passwordPanel.add(new JLabel("with a mix of letters, numbers & symbols"));
        passwordPanel.add(new JLabel(""));
        passwordPanel.add(new JLabel("New Password:"));
        passwordPanel.add(txtPassword);
        passwordPanel.add(Box.createHorizontalStrut(15)); // a spacer
        passwordPanel.add(new JLabel("Confirm Password:"));
        passwordPanel.add(txtConfirmPassword);
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));

        int result = JOptionPane.showConfirmDialog(null, passwordPanel,
                "Please Enter New Password", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String password = txtPassword.getText();
            String confirmPassword = txtConfirmPassword.getText();

            //Validate and return password/error info
            List<String> returnValue = new ArrayList<>();
            if (!password.equals(confirmPassword)) {
                returnValue.add("Error");
                returnValue.add("Passwords do not match");
                return returnValue;
            } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,64}$")) {
                returnValue.add("Error");
                returnValue.add("Password does not meet criteria");
                return returnValue;
            } else {
                returnValue.add("Pass");
                returnValue.add(password);
                return returnValue;
            }
        } else {
            return null;
        }
    }
}

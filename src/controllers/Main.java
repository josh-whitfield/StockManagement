package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import references.PC_Credentials;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root;
        String macAddress = PC_Credentials.getMacAddress();
        String PC_Username = PC_Credentials.getPcUsername();

        if (database.Main.checkAutoLogIn(macAddress, PC_Username)) {
            database.Main.getAccountDetails(macAddress, PC_Username);
            root = FXMLLoader.load(getClass().getResource("/resources/view/MainPage.fxml"));
            primaryStage.setTitle("Stock Management");
        }
        else {
            root = FXMLLoader.load(getClass().getResource("/resources/view/LogIn.fxml"));
            primaryStage.setTitle("Log In");
        }
        primaryStage.getIcons().add(new Image("/resources/images/iBuyerIcon.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        root.requestFocus();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

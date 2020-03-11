package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root;
        if (database.Main.checkAutoLogIn(references.UserCredentials.macAddress(),references.UserCredentials.PC_Username())) {
            //TODO - Change to Main Page once built
            root = FXMLLoader.load(getClass().getResource("/resources/view/LogIn.fxml"));
            primaryStage.setTitle("Log In");
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

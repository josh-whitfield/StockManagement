package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
//TODO - Check to see if the User and Mac address has a stored valid session
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
        primaryStage.setTitle("Log In");
        primaryStage.getIcons().add(new Image("/Images/iBuyerIcon.png"));
        primaryStage.setScene(new Scene(root));
        root.requestFocus();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("splashScreen.fxml"));
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        Font.loadFont(getClass().getResourceAsStream("Opificio_neue-regular.ttf"), 14);
        primaryStage.getScene().setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package main;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class SplashScreenController {

    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    public void initialize(){
        setRotate(circle1);
        setRotate2(circle2, 180, 9);
        setRotate2(circle3, 145, 12);
    }

    private void setRotate(Circle circle) {
        RotateTransition rotateTransition = setRotateMethod(circle, 360, 8);
        rotateTransition.setOnFinished(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("firstScreen.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) circle1.getScene().getWindow();
            stage.close();
            Stage primaryStage = new Stage();
            primaryStage.setAlwaysOnTop(true);
            assert root != null;
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.getScene().setFill(Color.TRANSPARENT);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
            MainScreenController.drag(root, primaryStage);
        });
    }

    private RotateTransition setRotateMethod(Circle circle, int angle, int duration) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(duration), circle);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setByAngle(angle);
        rotateTransition.setDelay(Duration.seconds(0));
        rotateTransition.setRate(3);
        rotateTransition.setCycleCount(1);
        rotateTransition.play();
        return rotateTransition;
    }

    private void setRotate2(Circle circle, int angle, int duration){
        setRotateMethod(circle, angle, duration);
    }
}

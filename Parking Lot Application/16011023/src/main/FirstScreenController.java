package main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class FirstScreenController {

    @FXML
    private AnchorPane loginField;
    @FXML
    private JFXTextField autoparkName;
    @FXML
    private JFXTextField hourlyFee;
    @FXML
    private JFXTextField dailySubscriptionFee;
    @FXML
    private JFXTextField capacityXML;
    @FXML
    private JFXDatePicker todayDate;
    @FXML
    private Label welcome;
    @FXML
    private JFXButton open;
    @FXML
    private JFXButton close;

    private static FirstScreenController firstScreenController=new FirstScreenController();
    public static FirstScreenController getInstance() {
        return firstScreenController;
    }

    public void initialize(){
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), welcome);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(4);
        dropShadow.setColor(Color.valueOf("#dc6180"));
        welcome.setEffect(dropShadow);
        MainScreenController.getInstance().dropShadow(loginField,1.5,1.5,.25,Color.valueOf("#424242"));
    }

    @FXML
    public void onOpenButton() {
        boolean isNameValid = setName(autoparkName);
        boolean isFeeValid = SetFeeController.getInstance().setFee(hourlyFee, true);
        boolean isSubscriptionFeeValid = SetFeeController.getInstance().setFee(dailySubscriptionFee, false);
        boolean isCapacityValid = SetCapacityController.getInstance().setCapacity(capacityXML);
        boolean isDateValid = SetTodayController.getInstance().setDate(todayDate);

        if(isNameValid && isFeeValid && isSubscriptionFeeValid && isCapacityValid && isDateValid){
            loadSecondStage();
        }
    }

    @FXML
    private void loadSecondStage() throws NumberFormatException, NullPointerException{
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.getScene().setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        MainScreenController.drag(root,stage);
        onCloseButton();
    }

    @FXML
    private boolean setName(JFXTextField autoparkName){
        boolean isNameValid=true;
        String name=autoparkName.getText().trim();
        if(name.isEmpty()){
            isNameValid=false;
        }
        if (isNameValid){
            Autopark.getInstance().setName(name);
        }
        return isNameValid;
    }

    @FXML
    public void onEnterPressed(){
        open.setOnKeyReleased(event -> {
            if(event.getCode()==KeyCode.ENTER){
                onOpenButton();
            }
        });
    }

    @FXML
    public void onMinimizeButton(){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void onCloseButton(){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}

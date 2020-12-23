package main;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SearchSubscriptionController {

    @FXML
    private AnchorPane field;

    @FXML
    private JFXTextField plate;

    @FXML
    private JFXCheckBox searchSubscriptionCheckBox;

    @FXML
    private JFXCheckBox closeInfoCheckBox;

    @FXML
    private Label subscriptionInfoLabel;

    public void initialize(){
        MainScreenController.getInstance().dropShadow(field,3,3,0.5, Color.valueOf("#424242"));
    }

    @FXML
    public void onCloseInfoCheckBox(){
        MainScreenController.getInstance().checkBoxAnimation(searchSubscriptionCheckBox,closeInfoCheckBox,null,plate,subscriptionInfoLabel, null,null, null, Duration.seconds(0.5));
    }

    @FXML
    public void onOkayButton(){
        if(!closeInfoCheckBox.isDisable()){
            searchSubscriptionCheckBox.setSelected(false);
            closeInfoCheckBox.setDisable(true);
            closeInfoCheckBox.setOpacity(0);
            subscriptionInfoLabel.setText(null);
        }
        SubscribedVehicle subscribedVehicle;
        if(MainScreenController.getInstance().checkPlateValidity(plate, searchSubscriptionCheckBox)){
            return;
        }
        String finalPlate = PlateFormatter.formatPlate(plate.getText().trim());
        subscribedVehicle = Autopark.getInstance().searchVehicle(finalPlate);
        searchSubscription(subscribedVehicle,finalPlate);
    }

    @FXML
    private void searchSubscription(SubscribedVehicle sv, String finalPlate){
        finalPlate = PlateFormatter.printPlate(finalPlate);
        if(sv ==null){
            searchSubscriptionCheckBox.setIndeterminate(true);
            searchSubscriptionCheckBox.setText("Abonelik bulunamadı.");
            MainScreenController.getInstance().checkBoxAnimation(searchSubscriptionCheckBox,null,null, plate,null,null,null, null ,Duration.seconds(1));
        }else if(sv.getSubscription().isValid()){
            searchSubscriptionCheckBox.setIndeterminate(true);
            searchSubscriptionCheckBox.setText("Abonelik süresi geçersiz:");
            subscriptionInfoLabel.setText("Plaka:\t\t"+finalPlate+"\nBaşlangıç:\t"+sv.getSubscription().getBegin().toString()+"\nBitiş:\t\t"+sv.getSubscription().getEnd().toString());
            closeInfoCheckBox.setOpacity(1);
            closeInfoCheckBox.setDisable(false);
        }else{
            searchSubscriptionCheckBox.setSelected(true);
            searchSubscriptionCheckBox.setText("Abonelik bulundu:");
            subscriptionInfoLabel.setText("Plaka:\t\t"+finalPlate+"\nBaşlangıç:\t"+sv.getSubscription().getBegin().toString()+"\nBitiş:\t\t"+sv.getSubscription().getEnd().toString());
            closeInfoCheckBox.setOpacity(1);
            closeInfoCheckBox.setDisable(false);
        }
    }

}

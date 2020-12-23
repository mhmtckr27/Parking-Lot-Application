package main;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SearchVehicleController {

    @FXML
    private AnchorPane field;
    @FXML
    private JFXTextField plate;
    @FXML
    private JFXCheckBox searchVehicleCheckBox;
    @FXML
    private JFXCheckBox closeInfoCheckBox;
    @FXML
    private Label carInfoLabel;

    public void initialize(){
        MainScreenController.getInstance().dropShadow(field,3,3,0.5,Color.valueOf("#424242"));
    }

    @FXML
    public void onCloseInfoCheckBox(){
        MainScreenController.getInstance().checkBoxAnimation(searchVehicleCheckBox,closeInfoCheckBox,null,plate,carInfoLabel, null,null, null, Duration.seconds(0.5));
    }

    @FXML
    void onOkayButton() {
        ParkRecord parkRecord;
        if(!closeInfoCheckBox.isDisable()){
            searchVehicleCheckBox.setSelected(false);
            closeInfoCheckBox.setDisable(true);
            closeInfoCheckBox.setOpacity(0);
            carInfoLabel.setText(null);
        }
        String finalPlate = "";
        try {
            finalPlate = PlateFormatter.formatPlate(plate.getText().trim());
        }catch (NullPointerException ignored){}
        if(finalPlate.isEmpty()){
            searchVehicleCheckBox.setIndeterminate(true);
            searchVehicleCheckBox.setText("Plaka girmediniz.");
            MainScreenController.getInstance().checkBoxAnimation(searchVehicleCheckBox,closeInfoCheckBox,null,plate,carInfoLabel, null, null,null,Duration.seconds(1));
        }
        if(!finalPlate.isEmpty()) {
            parkRecord = Autopark.getInstance().isParked(finalPlate);
            if(parkRecord!=null){
                searchVehicleCheckBox.setSelected(true);
                closeInfoCheckBox.setUnCheckedColor(Color.YELLOW);
                searchVehicleCheckBox.setText("Araç bulundu:");
                carInfoLabel.setText(parkRecord.toString());
                closeInfoCheckBox.setOpacity(1);
                closeInfoCheckBox.setDisable(false);
            }else{
                System.out.println(finalPlate);
                searchVehicleCheckBox.setIndeterminate(true);
                searchVehicleCheckBox.setText("Araç bulunamadı.");
                MainScreenController.getInstance().checkBoxAnimation(searchVehicleCheckBox,closeInfoCheckBox,null,plate,carInfoLabel, null,null,null,Duration.seconds(1));
            }
        }
    }
}

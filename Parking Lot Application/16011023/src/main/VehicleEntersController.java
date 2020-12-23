package main;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class VehicleEntersController {

    @FXML
    private AnchorPane field;
    @FXML
    private JFXTextField plate;
    @FXML
    private JFXTimePicker enterTimeXML;
    @FXML
    private JFXDatePicker enterDateXML;
    @FXML
    private JFXToggleButton isOfficialToggle;
    @FXML
    private JFXCheckBox vehicleEntersCheckBox;

    public void initialize(){
        MainScreenController.getInstance().dropShadow(field,3,3,0.5, Color.valueOf("#424242"));
        enterTimeXML.set24HourView(true);
    }

    @FXML
    void onOkayButton() {
        boolean isValid = true;
        if(MainScreenController.getInstance().checkPlateValidity(plate, vehicleEntersCheckBox)){
            return;
        }
        String plateString = PlateFormatter.formatPlate(plate.getText().trim());
        Date enterDate = null;
        Time enterTime = null;
        try{
            enterDate = new Date(enterDateXML.getValue().getDayOfMonth(),enterDateXML.getValue().getMonthValue(),enterDateXML.getValue().getYear());
            enterTime = new Time(enterTimeXML.getValue().getHour(),enterTimeXML.getValue().getMinute());
        }catch (NullPointerException e){
            isValid=false;
        }
        if(isValid){
            isValid = Autopark.getInstance().vehicleEnters(plateString, enterDate, enterTime, isOfficialToggle.isSelected());
            if(isValid) {
                vehicleEntersCheckBox.setText("Araç giriş yaptı.");
                vehicleEntersCheckBox.setSelected(true);
            }else if(Autopark.getInstance().getCapacity() == Autopark.getInstance().getCurrentOccupancy()) {
                vehicleEntersCheckBox.setText("Otopark dolu.");
                vehicleEntersCheckBox.setIndeterminate(true);
            }else{
                vehicleEntersCheckBox.setText("Araç zaten park halinde.");
                vehicleEntersCheckBox.setIndeterminate(true);
            }
            MainScreenController.getInstance().checkBoxAnimation(vehicleEntersCheckBox,null,isOfficialToggle,plate,null,enterDateXML,null, enterTimeXML, Duration.seconds(1));
        }else {
            EntersExitsNullDateOrTime(enterDate, vehicleEntersCheckBox, MainScreenController.getInstance());
        }
    }

    static void EntersExitsNullDateOrTime(Date enterDate, JFXCheckBox vehicleEntersCheckBox, MainScreenController instance) {
        if(enterDate==null){
            vehicleEntersCheckBox.setText("Tarih seçmediniz.");
            vehicleEntersCheckBox.setIndeterminate(true);
            instance.checkBoxAnimation(vehicleEntersCheckBox,null,null,null,null,null,null, null, Duration.seconds(1));
        }else{
            vehicleEntersCheckBox.setText("Saat seçmediniz.");
            vehicleEntersCheckBox.setIndeterminate(true);
            instance.checkBoxAnimation(vehicleEntersCheckBox,null,null,null,null,null,null, null, Duration.seconds(1));
        }
    }
}

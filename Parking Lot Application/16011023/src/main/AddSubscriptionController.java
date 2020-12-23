package main;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class AddSubscriptionController {

    @FXML
    private AnchorPane field;
    @FXML
    private JFXTextField plate;
    @FXML
    private JFXDatePicker startDate;
    @FXML
    private JFXDatePicker endDate;
    @FXML
    private JFXCheckBox addSubscriptionCheckBox;

    private static AddSubscriptionController addSubscriptionController = new AddSubscriptionController();

    public static AddSubscriptionController getInstance(){
        return addSubscriptionController;
    }

    @FXML
    public void onOkayButton() {
        boolean isEntryValid=true;
        SubscribedVehicle sv = null;
        String plateString;
        if((plate==null) || (plate.getText()==null) || plate.getText().trim().isEmpty()){
            addSubscriptionCheckBox.setText("Plaka girmediniz.");
            addSubscriptionCheckBox.setIndeterminate(true);
            MainScreenController.getInstance().checkBoxAnimation(addSubscriptionCheckBox,null,null, null,null,null,null, null, Duration.seconds(1));
            return;
        }
        plateString = PlateFormatter.formatPlate(plate.getText().trim());
        try {
            sv = Autopark.getInstance().searchVehicle(plateString);
        }catch (NullPointerException e){
            isEntryValid = false;
        }

        if((sv==null)||(sv.getSubscription().isValid())){
            try {
                if(sv==null) {
                    sv = new SubscribedVehicle(plateString);
                }
                Date begin = new Date(startDate.getValue().getDayOfMonth(), startDate.getValue().getMonthValue(), startDate.getValue().getYear());
                Date end = new Date(endDate.getValue().getDayOfMonth(), endDate.getValue().getMonthValue(), endDate.getValue().getYear());
                sv.setSubscription(new Subscription(begin, end, plateString));
            }catch (NullPointerException e){
                isEntryValid=false;
            }
            //subscription begin date cannot be equal to or after subscription end date.
            if(isEntryValid) {
                if (endDate.getValue().isBefore(startDate.getValue()) || endDate.getValue().isEqual(startDate.getValue())) {
                    addSubscriptionCheckBox.setIndeterminate(true);
                    addSubscriptionCheckBox.setText("Tarih aralığı negatif.");
                    System.err.println("Tarih aralığı negatif.");
                    MainScreenController.getInstance().checkBoxAnimation(addSubscriptionCheckBox,null,null,plate,null,startDate,endDate, null, Duration.seconds(1));
                    return;
                }
            }
            if(isEntryValid) {
                addSubscrition(sv);
                addSubscriptionCheckBox.setSelected(true);
                addSubscriptionCheckBox.setText("Abonelik eklendi.");
                MainScreenController.getInstance().checkBoxAnimation(addSubscriptionCheckBox,null, null, plate,null, startDate, endDate, null, Duration.seconds(1));
            }else {
                if(startDate.getValue()==null){
                    addSubscriptionCheckBox.setIndeterminate(true);
                    addSubscriptionCheckBox.setText("Başlangıç seçmediniz.");
                    MainScreenController.getInstance().checkBoxAnimation(addSubscriptionCheckBox,null,null, null,null,null,null, null, Duration.seconds(1));
                }
                else if(endDate.getValue()==null){
                    addSubscriptionCheckBox.setIndeterminate(true);
                    addSubscriptionCheckBox.setText("Bitiş seçmediniz.");
                    MainScreenController.getInstance().checkBoxAnimation(addSubscriptionCheckBox,null,null,null,null,null,null, null, Duration.seconds(1));
                }
            }
        }else{
            addSubscriptionCheckBox.setIndeterminate(true);
            addSubscriptionCheckBox.setText("Araç zaten abone.");
            System.err.println("Araç zaten abone");
            MainScreenController.getInstance().checkBoxAnimation(addSubscriptionCheckBox,null,null,plate,null,startDate,endDate, null, Duration.seconds(1));
        }
    }

    public void initialize(){
        MainScreenController.getInstance().dropShadow(field,3,3,0.5,Color.valueOf("#424242"));
    }

    private void addSubscrition(SubscribedVehicle sv){
        Autopark.getInstance().addVehicle(sv);
    }
}

package main;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SetTodayController {

    @FXML
    private AnchorPane field;

    @FXML
    private JFXCheckBox setTodayCheckBox;

    @FXML
    private Label dateInfoLabel;

    @FXML
    private JFXDatePicker todayDate;

    private static SetTodayController setTodayController = new SetTodayController();
    public static SetTodayController getInstance(){
        return setTodayController;
    }

    public void initialize(){
        MainScreenController.getInstance().dropShadow(field,3,3,0.5, Color.valueOf("#424242"));
    }

    @FXML
    public void onOkayButton() {
        if(setDate(todayDate)){
            setTodayCheckBox.setSelected(true);
            setTodayCheckBox.setText("Tarih değiştirildi.");
            dateInfoLabel.setText("Yeni tarih: "+Date.getToday().toString());
            setTodayCheckBox.setIndeterminate(false);
            MainScreenController.getInstance().checkBoxAnimation(setTodayCheckBox,null, null,null,dateInfoLabel,todayDate,null, null, Duration.seconds(2));
        }else{
            setTodayCheckBox.setText("Tarih seçmediniz.");
            setTodayCheckBox.setIndeterminate(true);
            MainScreenController.getInstance().checkBoxAnimation(setTodayCheckBox,null,null, null,null, null,null, null, Duration.seconds(1));
        }
    }

    boolean setDate(JFXDatePicker todayDate){
        try {
            Date.getToday();
        }catch (NullPointerException e){
            Date.setToday(new Date(todayDate.getValue().getDayOfMonth(),todayDate.getValue().getMonthValue(),todayDate.getValue().getYear()));
        }
        Date oldDate = Date.getToday();
        Date newDate = null;
        boolean isDateValid=true;
        try {
            newDate = new Date(todayDate.getValue().getDayOfMonth(), todayDate.getValue().getMonthValue(), todayDate.getValue().getYear());
            Date today = new Date(todayDate.getValue().getDayOfMonth(), todayDate.getValue().getMonthValue(), todayDate.getValue().getYear());
            Date.setToday(today);
        }catch (NullPointerException e){
            isDateValid=false;
        }
        if(isDateValid && (oldDate!=null) && (!newDate.isEqualsWith(oldDate))){
            Autopark.getInstance().setIncomeDaily(0);
        }
        return isDateValid;
    }
}

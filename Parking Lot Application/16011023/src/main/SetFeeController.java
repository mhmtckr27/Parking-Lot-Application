package main;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SetFeeController {

    @FXML
    private AnchorPane field;
    @FXML
    private JFXTextField feeXML;
    @FXML
    private JFXCheckBox setFeeCheckBox;
    @FXML
    private Label feeInfoLabel;
    @FXML
    private JFXToggleButton hourlyDailyToggle;

    private static SetFeeController setFeeController = new SetFeeController();
    public static SetFeeController getInstance(){
        return setFeeController;
    }

    public void initialize(){
        MainScreenController.getInstance().dropShadow(field,3,3,0.5, Color.valueOf("#424242"));
    }

    @FXML
    void onOkayButton() {
        boolean isDaily = hourlyDailyToggle.isSelected();
        if(setFee(feeXML, !isDaily)){
            setFeeCheckBox.setSelected(true);
            if(isDaily) {
                setFeeCheckBox.setText("Günlük ücret değiştirildi.");
                feeInfoLabel.setText("Yeni ücret: "+ Autopark.getInstance().getDailySubscriptionFee());
            }else{
                setFeeCheckBox.setText("Saatlik ücret değiştirildi.");
                feeInfoLabel.setText("Yeni ücret: "+ Autopark.getInstance().getHourlyFee());
            }
            setFeeCheckBox.setIndeterminate(false);
            MainScreenController.getInstance().checkBoxAnimation(setFeeCheckBox,null,hourlyDailyToggle,feeXML,feeInfoLabel,null,null, null, Duration.seconds(2));
        }else{
            setFeeCheckBox.setText("Ücret girmediniz.");
            setFeeCheckBox.setIndeterminate(true);
            MainScreenController.getInstance().checkBoxAnimation(setFeeCheckBox,null,null,null,null, null,null, null, Duration.seconds(1));
        }
    }

    boolean setFee(JFXTextField Fee, boolean isHourly){
        double fee = 0;
        try {
            fee = Double.parseDouble(Fee.getText().trim());
        }catch (NumberFormatException | NullPointerException ignored){
        }
        if(fee<=0){
            return false;
        }
        if(isHourly) {
            Autopark.getInstance().setHourlyFee(fee);
            System.out.println("hourly: " +Autopark.getInstance().getHourlyFee());
        }else{
            Autopark.getInstance().setDailySubscriptionFee(fee);
            System.out.println("daily: "+Autopark.getInstance().getDailySubscriptionFee());
        }
        return true;
    }
}
package main;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SetCapacityController {

    @FXML
    private AnchorPane field;

    @FXML
    private JFXTextField capacityXML;

    @FXML
    private JFXCheckBox setCapacityCheckBox;

    @FXML
    private Label capacityInfoLabel;

    private static SetCapacityController setCapacityController = new SetCapacityController();

    public static SetCapacityController getInstance(){
        return setCapacityController;
    }

    public void initialize() {
        MainScreenController.getInstance().dropShadow(field,3,3,0.5, Color.valueOf("#424242"));
    }

    @FXML
    void onOkayButton() {
        if(setCapacity(capacityXML)){
            setCapacityCheckBox.setSelected(true);
            setCapacityCheckBox.setText("Kapasite değiştirildi.");
            capacityInfoLabel.setText("Yeni kapasite: "+Autopark.getInstance().getCapacity());
            setCapacityCheckBox.setIndeterminate(false);
            MainScreenController.getInstance().checkBoxAnimation(setCapacityCheckBox,null,null,capacityXML,capacityInfoLabel,null,null, null, Duration.seconds(1.25));
        }else{
            setCapacityCheckBox.setText("Kapasite girmediniz.");
            setCapacityCheckBox.setIndeterminate(true);
            MainScreenController.getInstance().checkBoxAnimation(setCapacityCheckBox,null,null, null,null, null, null, null, Duration.seconds(0.75));
        }
    }

    boolean setCapacity(JFXTextField capacityXML) {
        boolean isCapacityValid = true;
        int capacity = 0;
        try {
            capacity = Integer.parseUnsignedInt(capacityXML.getText().trim());
        } catch (NumberFormatException | NullPointerException e) {
            isCapacityValid = false;
        }
        if (isCapacityValid) {
            Autopark.getInstance().setCapacity(capacity);
        }
        return isCapacityValid;
    }
}

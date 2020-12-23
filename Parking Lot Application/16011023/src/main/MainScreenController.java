package main;

import com.jfoenix.controls.*;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

public class MainScreenController {

    @FXML
    private JFXTextField plateXML;
    @FXML
    private JFXTimePicker exitTimeXML;
    @FXML
    private JFXDatePicker exitDateXML;
    @FXML
    private JFXCheckBox vehicleExitsCheckBox;
    @FXML
    private TableView<SubscribedVehicleModel> subscriptionTableView;
    @FXML
    private TableColumn<SubscribedVehicleModel, String> plateColumn;
    @FXML
    private TableColumn<SubscribedVehicleModel, String> startDateColumn;
    @FXML
    private TableColumn<SubscribedVehicleModel, String> endDateColumn;
    @FXML
    private TableColumn<SubscribedVehicleModel, Double> paidColumnSub;
    @FXML
    private TableView<ParkRecordModel> logsTable;
    @FXML
    private TableColumn<ParkRecordModel, String> plateColumnLog;
    @FXML
    private TableColumn<ParkRecordModel, String> enterDate;
    @FXML
    private TableColumn<ParkRecordModel, String> enterHour;
    @FXML
    private TableColumn<ParkRecordModel, String> exitDate;
    @FXML
    private TableColumn<ParkRecordModel, String> exitHour;
    @FXML
    private TableColumn<ParkRecordModel, Double> paidColumn;
    @FXML
    private AnchorPane centerPane;
    @FXML
    private JFXButton close;
    @FXML
    private JFXButton minimize;
    @FXML
    private Label autoparkName;
    @FXML
    private AnchorPane vehicleExitsPane;
    @FXML
    private Label infoHourly;
    @FXML
    private Label infoDaily;
    @FXML
    private Label infoIncome;
    @FXML
    private Label infoDate;
    @FXML
    private Label infoOccupancy;
    @FXML
    private Label infoCapacity;

    private static MainScreenController MainScreenController = new MainScreenController();
    public static MainScreenController getInstance() {
        return MainScreenController;
    }

    @FXML
    public void initialize() {
        autoparkName.setText(Autopark.getInstance().getName());

        plateColumn.setCellValueFactory(new PropertyValueFactory<>("plate"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("beginDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        paidColumnSub.setCellValueFactory(new PropertyValueFactory<>("paid"));

        plateColumnLog.setCellValueFactory(new PropertyValueFactory<>("plate"));
        enterDate.setCellValueFactory(new PropertyValueFactory<>("enterD"));
        exitDate.setCellValueFactory(new PropertyValueFactory<>("exitD"));
        enterHour.setCellValueFactory(new PropertyValueFactory<>("enterT"));
        exitHour.setCellValueFactory(new PropertyValueFactory<>("exitT"));
        paidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));

        logsTable.setItems(ParkRecordModel.getParkRecordModels());
        subscriptionTableView.setItems(SubscribedVehicleModel.getSubscribedVehicleModels());
        refresh();
    }

    private void refresh(){
        infoDaily.setText(Autopark.getInstance().getDailySubscriptionFee() +" ₺");
        infoHourly.setText(Autopark.getInstance().getHourlyFee() +" ₺");
        infoCapacity.setText(String.valueOf(Autopark.getInstance().getCapacity()));
        infoDate.setText(Date.getToday().toString());

        AutoparkModel.getInstance().todayProperty().addListener((observable, oldValue, newValue) -> infoDate.setText(Date.getToday().toString()));
        AutoparkModel.getInstance().incomeProperty().addListener((observable, oldValue, newValue) -> infoIncome.setText(AutoparkModel.getInstance().getIncome() + " ₺"));
        AutoparkModel.getInstance().occupancyProperty().addListener((observable, oldValue, newValue) -> infoOccupancy.setText(String.valueOf(AutoparkModel.getInstance().getOccupancy())));
        AutoparkModel.getInstance().dailyFeeProperty().addListener((observable, oldValue, newValue) -> infoDaily.setText(AutoparkModel.getInstance().getDailyFee() + " ₺"));
        AutoparkModel.getInstance().hourlyFeeProperty().addListener((observable, oldValue, newValue) -> infoHourly.setText(AutoparkModel.getInstance().getHourlyFee() + " ₺"));
        AutoparkModel.getInstance().capacityProperty().addListener((observable, oldValue, newValue) -> infoCapacity.setText(String.valueOf(AutoparkModel.getInstance().getCapacity())));
    }

    /* Following method is used by SearchSubscriptionController and VehicleEntersController classes
    for checking plate field is empty or not. Reason I used function is prevent code duplication. */
    boolean checkPlateValidity(JFXTextField plate, JFXCheckBox jfxCheckBox) {
        if ((plate == null) || (plate.getText() == null) || plate.getText().trim().isEmpty()) {
            jfxCheckBox.setIndeterminate(true);
            jfxCheckBox.setText("Plaka girmediniz.");
            getInstance().checkBoxAnimation(jfxCheckBox, null, null, null, null, null, null, null, Duration.seconds(1));
            return true;
        }
        return false;
    }

    //Used to create some messages to inform user
    void checkBoxAnimation(JFXCheckBox jfxCheckBox1, JFXCheckBox jfxCheckBox2, JFXToggleButton jfxToggleButton, JFXTextField jfxTextField, Label label, JFXDatePicker jfxDatePicker1, JFXDatePicker jfxDatePicker2, JFXTimePicker jfxTimePicker, Duration duration) {
        PauseTransition delay = new PauseTransition(duration);
        delay.setOnFinished(event -> {
            if (jfxCheckBox1 != null) {
                jfxCheckBox1.setSelected(false);
                jfxCheckBox1.setIndeterminate(false);
                jfxCheckBox1.setText(null);
            }
            if (jfxToggleButton != null) {
                jfxToggleButton.setSelected(false);
            }
            if (jfxTextField != null) {
                jfxTextField.setText(null);
            }
            if (label != null) {
                label.setText(null);
            }
            if (jfxTimePicker != null) {
                jfxTimePicker.setValue(null);
            }
            if (jfxCheckBox2 != null) {
                jfxCheckBox2.setSelected(false);
                jfxCheckBox2.setOpacity(0);
                jfxCheckBox2.setDisable(true);
            }
            if (jfxDatePicker1 != null) {
                jfxDatePicker1.setValue(null);
            }
            if (jfxDatePicker2 != null) {
                jfxDatePicker2.setValue(null);
            }
        });
        delay.play();
    }

    @FXML
    public void onVehicleEnters() {
        loadToCenterPane("vehicleEnters.fxml");
    }

    @FXML
    public void onVehicleExits() {
        vehicleExitsPane.setVisible(true);
        centerPane.getChildren().clear();
        centerPane.getChildren().add(vehicleExitsPane);
        exitTimeXML.set24HourView(true);
        dropShadow(vehicleExitsPane,3,3,0.5, Color.valueOf("#424242"));
    }

    @FXML
    public void onSearchSubscription() {
        loadToCenterPane("searchSubscription.fxml");
    }

    @FXML
    public void onAddSubscription() {
        loadToCenterPane("addSubscription.fxml");
    }

    @FXML
    public void onSearchVehicle() {
        loadToCenterPane("searchVehicle.fxml");
    }

    @FXML
    public void onSetToday() {
        loadToCenterPane("setToday.fxml");
    }

    @FXML
    public void onSetHourlyFee() {
        loadToCenterPane("setFee.fxml");
    }

    @FXML
    public void onSetCapacity() {
        loadToCenterPane("setCapacity.fxml");
    }

    //Used to load selected window
    private void loadToCenterPane(String fxmlToLoad) {
        AnchorPane paneToLoad = null;
        try {
            paneToLoad = FXMLLoader.load(this.getClass().getResource(fxmlToLoad));
        } catch (IOException e) {
            e.printStackTrace();
        }
        centerPane.getChildren().setAll(paneToLoad);
    }

    //Added shadows to windows
    public void dropShadow(Parent pane, double offsetX, double offsetY, double spread, Color color) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetY(offsetX);
        dropShadow.setOffsetX(offsetY);
        dropShadow.setSpread(spread);
        dropShadow.setColor(color);
        pane.setEffect(dropShadow);
    }

    //Allow user to drag application window
    static void drag(Parent mainFrame, Stage primaryStage) {
        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];
        mainFrame.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });
        mainFrame.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset[0]);
            primaryStage.setY(event.getScreenY() - yOffset[0]);
        });
    }

    @FXML
    public void onMinimizeButton() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void onCloseButton() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onOkayButton() {
        boolean isValid = true;
        if (checkPlateValidity(plateXML, vehicleExitsCheckBox)) {
            return;
        }
        String plateString = PlateFormatter.formatPlate(plateXML.getText().trim());
        ParkRecord parkRecord = Autopark.getInstance().isParked(plateString);
        Date exitDate = null;
        Time exitTime = null;
        try {
            exitDate = new Date(exitDateXML.getValue().getDayOfMonth(), exitDateXML.getValue().getMonthValue(), exitDateXML.getValue().getYear());
            exitTime = new Time(exitTimeXML.getValue().getHour(), exitTimeXML.getValue().getMinute());
        } catch (NullPointerException e) {
            isValid = false;
        }
        if (isValid) {
            isValid = Autopark.getInstance().vehicleExits(plateString, exitDate, exitTime);
            if (isValid) {
                System.out.println(Autopark.getInstance().getIncomeDaily());
                vehicleExitsCheckBox.setText("Araç çıkış yaptı.");
                vehicleExitsCheckBox.setSelected(true);
                getInstance().checkBoxAnimation(vehicleExitsCheckBox, null, null, plateXML, null, exitDateXML, null, exitTimeXML, Duration.seconds(1));
                logsTable.refresh();
            } else if ((parkRecord == null) || (parkRecord.getExitTime() != null)) {
                vehicleExitsCheckBox.setText("Araç otoparkta değil.");
                vehicleExitsCheckBox.setIndeterminate(true);
                getInstance().checkBoxAnimation(vehicleExitsCheckBox, null, null, plateXML, null, exitDateXML, null, exitTimeXML, Duration.seconds(1));
            } else if (parkRecord.getEnterDate().isAfterThan(exitDate)) {
                vehicleExitsCheckBox.setText("Çıkış tarihi, girişten önce.");
                vehicleExitsCheckBox.setIndeterminate(true);
                getInstance().checkBoxAnimation(vehicleExitsCheckBox, null, null, null, null, null, null, null, Duration.seconds(1));
            } else if (parkRecord.getEnterDate().isEqualsWith(exitDate)) {
                if ((parkRecord.getEnterTime().getHour() - exitTime.getHour()) > 0) {
                    vehicleExitsCheckBox.setText("Çıkış zamanı, girişten önce.");
                    vehicleExitsCheckBox.setIndeterminate(true);
                    getInstance().checkBoxAnimation(vehicleExitsCheckBox, null, null, null, null, null, null, null, Duration.seconds(1));
                } else if ((parkRecord.getEnterTime().getHour() - exitTime.getHour()) == 0) {
                    if ((parkRecord.getEnterTime().getMinute() - exitTime.getMinute()) > 0) {
                        vehicleExitsCheckBox.setText("Çıkış tarihi, girişten önce.");
                        vehicleExitsCheckBox.setIndeterminate(true);
                        getInstance().checkBoxAnimation(vehicleExitsCheckBox, null, null, null, null, null, null, null, Duration.seconds(1));
                    }
                }
            }
        } else
            VehicleEntersController.EntersExitsNullDateOrTime(exitDate, vehicleExitsCheckBox, getInstance());
    }
}

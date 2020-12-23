package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParkRecordModel {
    private static ObservableList<ParkRecordModel> parkRecordModels = FXCollections.observableArrayList();
    private String plate;
    private String enterD;
    private String enterT;
    private String exitD;
    private String exitT;
    private Double paid;

    public static ObservableList<ParkRecordModel> getParkRecordModels() {
        return parkRecordModels;
    }

    ParkRecordModel(String plate, String enterD, String enterT, String exitD, String exitT, Double paid) {
        this.plate = plate;
        this.enterD = enterD;
        this.enterT = enterT;
        this.exitD = exitD;
        this.exitT = exitT;
        this.paid = paid;
    }

    public static void setParkRecordModels(ObservableList<ParkRecordModel> parkRecordModels) {
        ParkRecordModel.parkRecordModels = parkRecordModels;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getEnterD() {
        return enterD;
    }

    public void setEnterD(String enterD) {
        this.enterD = enterD;
    }

    public String getEnterT() {
        return enterT;
    }

    public void setEnterT(String enterT) {
        this.enterT = enterT;
    }

    public String getExitD() {
        return exitD;
    }

    public void setExitD(String exitD) {
        this.exitD = exitD;
    }

    public String getExitT() {
        return exitT;
    }

    public void setExitT(String exitT) {
        this.exitT = exitT;
    }

    public static void add(ParkRecordModel parkRecordModel){
        parkRecordModels.add(parkRecordModel);
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }
}

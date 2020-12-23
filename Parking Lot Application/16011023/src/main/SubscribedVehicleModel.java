package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SubscribedVehicleModel {

    static ObservableList<SubscribedVehicleModel> subscribedVehicleModels = FXCollections.observableArrayList();

    private String plate;
    private String beginDate;
    private String endDate;
    private Double paid;

    SubscribedVehicleModel(){}

    static ObservableList<SubscribedVehicleModel> getSubscribedVehicleModels(){
        return subscribedVehicleModels;
    }

    static void add(SubscribedVehicleModel subscribedVehicleModel){
        subscribedVehicleModels.add(subscribedVehicleModel);
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Double getPaid() {
        return paid;
    }

    void setPaid(Double paid) {
        this.paid = paid;
    }
}

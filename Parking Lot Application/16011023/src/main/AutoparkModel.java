package main;

import javafx.beans.property.*;

public class AutoparkModel {
    private static AutoparkModel autoparkModel = new AutoparkModel();
    public static AutoparkModel getInstance(){
        return autoparkModel;
    }

    private DoubleProperty hourlyFee;
    private DoubleProperty dailyFee;
    private DoubleProperty income;
    private IntegerProperty capacity;
    private IntegerProperty occupancy;
    private StringProperty today;

    private AutoparkModel(){
        hourlyFee = new SimpleDoubleProperty(0);
        dailyFee = new SimpleDoubleProperty(0);
        income = new SimpleDoubleProperty(0);
        capacity = new SimpleIntegerProperty(0);
        occupancy = new SimpleIntegerProperty(0);
        today = new SimpleStringProperty("");
    }

    double getHourlyFee() {
        return hourlyFee.get();
    }

    DoubleProperty hourlyFeeProperty() {
        return hourlyFee;
    }

    void setHourlyFee(double hourlyFee) {
        this.hourlyFee.set(hourlyFee);
    }

    double getDailyFee() {
        return dailyFee.get();
    }

    DoubleProperty dailyFeeProperty() {
        return dailyFee;
    }

    void setDailyFee(double dailyFee) {
        this.dailyFee.set(dailyFee);
    }

    double getIncome() {
        return income.get();
    }

    DoubleProperty incomeProperty() {
        return income;
    }

    void setIncome(double income) {
        this.income.set(income);
    }

    int getCapacity() {
        return capacity.get();
    }

    IntegerProperty capacityProperty() {
        return capacity;
    }

    void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    int getOccupancy() {
        return occupancy.get();
    }

    IntegerProperty occupancyProperty() {
        return occupancy;
    }

    void setOccupancy(int occupancy) {
        this.occupancy.set(occupancy);
    }

    StringProperty todayProperty() {
        return today;
    }

    void setToday(String today) {
        this.today.set(today);
    }
}

package main;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AutoparkTest {
    private RegularVehicle regularVehicle1;
    private RegularVehicle regularVehicle2;
    private RegularVehicle regularVehicle3;

    @Before
    public void setUp() {
        Autopark.getInstance().setCapacity(1);
        Date.setToday(new Date(13,10,1997));
        regularVehicle1 = new RegularVehicle("34 ABC 123");
        regularVehicle2 = new RegularVehicle("27 DEF 457");
        regularVehicle3 = new RegularVehicle("54 SRC 789");
        Autopark.getInstance().vehicleEnters(regularVehicle2.getPlate(),Date.getToday(),new Time(14,45),false);

        Autopark.getInstance().vehicleEnters(regularVehicle3.getPlate(),Date.getToday(),new Time(11,5),false);
        Autopark.getInstance().vehicleExits(regularVehicle3.getPlate(),Date.getToday(),new Time(13,33));
    }

    @Test
    public void vehicleEntersTwice() {
        boolean mustBeFalse = Autopark.getInstance().vehicleEnters(regularVehicle2.getPlate(),Date.getToday(),new Time(15,56),false);
        assertFalse(mustBeFalse);
    }

    @Test
    public void notEnteredVehicleExits() {
        boolean mustBeFalse = Autopark.getInstance().vehicleExits(regularVehicle1.getPlate(),Date.getToday(),new Time(9,55));
        assertFalse(mustBeFalse);
    }

    @Test
    public void vehicleExitsAgain(){
        boolean mustBeFalse = Autopark.getInstance().vehicleExits(regularVehicle3.getPlate(),Date.getToday(),new Time(10,55));
        assertFalse(mustBeFalse);
    }

    @Test
    public void onCapacityFilled(){
        assertFalse(Autopark.getInstance().vehicleEnters("34 abc 12345",new Date(3,3,1990),new Time(5,5),false));
    }
}
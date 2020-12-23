package main;

public class ParkRecord {
	private Time enterTime;
	private Time exitTime;
	private Date enterDate;
	private Date exitDate;
	private Vehicle vehicle;

	//used for creating tableview consists of 5 strings which are; plate,entertime,exittime,enterdate,exitdate.
 	private ParkRecordModel parkRecordModel;

	// Constructor with 2 parameters. Exit time will be set afterwards.
	ParkRecord(Date enterDate, Time enterTime, Vehicle vehicle) throws NullPointerException{
		this.enterDate = enterDate;
		this.enterTime = enterTime;
		this.exitTime = null;
		this.vehicle = vehicle;

		//used for creating tableview consists of 5 strings which are; plate,entertime,exittime,enterdate,exitdate.
		parkRecordModel = new ParkRecordModel(PlateFormatter.printPlate(vehicle.getPlate()), enterDate.toString(), enterTime.toString(), "Park halinde", "",0.0);
		ParkRecordModel.add(parkRecordModel);
	}

	Date getEnterDate() {
		return enterDate;
	}

	void setExitDate(Date exitDate)
	{
		parkRecordModel.setExitD(exitDate.toString());
		this.exitDate = exitDate;
	}

	Time getEnterTime() {
		return enterTime;
	}

	Time getExitTime() {
		return exitTime;
	}

	void setExitTime(Time exitTime)
	{
		parkRecordModel.setExitT(exitTime.toString());
		this.exitTime = exitTime;
		if(!vehicle.isOfficial() && ((vehicle.getSubscription()==null) || vehicle.getSubscription().isValid())) {
			parkRecordModel.setPaid(getParkingDuration() * Autopark.getInstance().getHourlyFee());
		}
	}

	Vehicle getVehicle() {
		return vehicle;
	}

	int getParkingDuration() {
		return (exitDate.getDayDifference(enterDate)*24+enterTime.getHourDifference(exitTime));
	}
	@Override
	public String toString() {
		String arg;
		arg="Plaka:\t\t"+PlateFormatter.printPlate(vehicle.getPlate())+"\n";
		arg+="Giriş:\t\t"+enterTime.toString()+" - "+enterDate.toString()+"\n";
		if(exitTime==null){
			arg+="Durum:\tPark halinde\n\n";
			return arg;
		}
		arg+="Çıkış:\t\t"+exitTime.toString()+" - "+ exitDate.toString();
		return arg;
	}
}

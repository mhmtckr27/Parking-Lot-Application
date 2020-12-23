package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Autopark {
	private double hourlyFee;
	private double dailySubscriptionFee;
	private static double incomeDaily;
	private int capacity;
	private int currentOccupancy;
	private static Autopark autopark = new Autopark();
	private String name;
	//same as ArrayList but it's value is observed and any change will be on screen immediately.
	private ObservableList<ParkRecord> parkRecords;
	private ObservableList<SubscribedVehicle> subscribedVehicles;

	public Autopark(){
		incomeDaily = 0;
		subscribedVehicles = FXCollections.observableArrayList();
		parkRecords = FXCollections.observableArrayList();
	}

	int getCurrentOccupancy() {
		return currentOccupancy;
	}

	double getDailySubscriptionFee() {
		return dailySubscriptionFee;
	}

	void setDailySubscriptionFee(double dailySubscriptionFee) {
		AutoparkModel.getInstance().setDailyFee(dailySubscriptionFee);
		this.dailySubscriptionFee = dailySubscriptionFee;
	}

	public static Autopark getInstance() {
		return autopark;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		Autopark.getInstance().name = name;
	}

	double getHourlyFee() {
		return hourlyFee;
	}

	void setHourlyFee(double hourlyFee) {
		if (hourlyFee <= 0) {
			throw new CustomException("Saatlik ücret 0'dan büyük olmalıdır!");
		}
		AutoparkModel.getInstance().setHourlyFee(hourlyFee);
		Autopark.getInstance().hourlyFee = hourlyFee;
	}

	double getIncomeDaily() {
		return incomeDaily;
	}

	void setIncomeDaily(double incomeDaily) {
		AutoparkModel.getInstance().setIncome(incomeDaily);
		Autopark.incomeDaily = incomeDaily;
	}

	int getCapacity() {
		return capacity;
	}

	void setCapacity(int capacity) {
		if (capacity <= 0) {
			throw new CustomException(
					"Otopark kapasitesi 0'dan büyük olmalıdır!");
		}
		AutoparkModel.getInstance().setCapacity(capacity);
		Autopark.getInstance().capacity = capacity;
	}

	SubscribedVehicle searchVehicle(String plate)
			throws NullPointerException {
		if (plate == null) {
			throw new NullPointerException("Plaka bos olamaz!");
		}
		for (SubscribedVehicle sv : subscribedVehicles) {
			if (sv.getPlate().compareTo(plate) == 0) {
				return sv;
			}
		}
		return null;
	}
	
	ParkRecord isParked(String plate) throws NullPointerException {
		if (plate == null) {
			throw new NullPointerException("Plaka bos olamaz!");
		}
		ParkRecord toReturn = null;
		for (ParkRecord pr : parkRecords) {
			if(pr.getVehicle().getPlate().compareTo(plate)==0){
				toReturn=pr;
			}
		}
		return toReturn;
	}

	//I have handled unvalid entries at GUI so this method can be void.
	void addVehicle(SubscribedVehicle sv){
		System.out.println(sv.calculateTotalDays()+" "+incomeDaily);
		subscribedVehicles.add(sv);
		SubscribedVehicleModel subscribedVehicleModel = new SubscribedVehicleModel(/*sv.getPlate(),sv.getSubscription().getBegin().toString(),sv.getSubscription().getEnd().toString()*/);
		subscribedVehicleModel.setPlate(PlateFormatter.printPlate(sv.getPlate()));
		subscribedVehicleModel.setBeginDate(sv.getSubscription().getBegin().toString());
		subscribedVehicleModel.setEndDate(sv.getSubscription().getEnd().toString());
		subscribedVehicleModel.setPaid(sv.calculateTotalDays()*getDailySubscriptionFee());
		setIncomeDaily(sv.calculateTotalDays()*getDailySubscriptionFee());
		SubscribedVehicleModel.add(subscribedVehicleModel);
	}

	private void setCurrentOccupancy(int currentOccupancy) {
		AutoparkModel.getInstance().setOccupancy(currentOccupancy);
		this.currentOccupancy = currentOccupancy;
	}

	boolean vehicleEnters(String plate, Date enterDate, Time enterTime, boolean isOfficial) {
		if(isParked(plate)!=null) {
			if(isParked(plate).getExitTime()==null) {
				System.err.println(plate + " plakalı araç zaten park edilmiş!");
				return false;
			}
		}
		if(currentOccupancy==capacity) {
			System.err.println("Otopark kapasitesi doldu, daha fazla araç giremez!");
			return false;
		}
		setCurrentOccupancy(currentOccupancy+1);
		if(isOfficial) {
			OfficialVehicle ov=new OfficialVehicle(plate);
			ParkRecord pr=new ParkRecord(enterDate, enterTime, ov);
			parkRecords.add(pr);
			return true;
		}
		SubscribedVehicle sv= searchVehicle(plate);
		if(sv!=null) {
			ParkRecord pr = new ParkRecord(enterDate, enterTime, sv);
			parkRecords.add(pr);
			return true;
		}
		RegularVehicle rv=new RegularVehicle(plate);
		ParkRecord pr=new ParkRecord(enterDate, enterTime, rv);
		parkRecords.add(pr);
		return true;
	}
	
	boolean vehicleExits(String plate, Date exitDate, Time exitTime) throws NullPointerException{
		if(plate==null) {
			System.err.println("Plaka boş olamaz");
			return false;
		}
		ParkRecord pr=isParked(plate);
		if(pr==null) {
			System.err.println("Arac otoparka hiç giriş yapmadı!");
			return false;
		}
		if(pr.getExitTime()!=null){
			System.err.println("Arac otoparkta değil, daha önce çıkış yapmış");
			return false;
		}
		if(pr.getEnterDate().isAfterThan(exitDate)){
			System.err.println("Çıkış tarihi giriş tarihinden önce olamaz.");
			return false;
		}
		if(pr.getEnterDate().isEqualsWith(exitDate)){
			if((pr.getEnterTime().getHour()-exitTime.getHour())>0){
				System.err.println("Çıkış zamanı giriş zamanından önce olamaz.");
				return false;
			}else if((pr.getEnterTime().getHour()-exitTime.getHour())==0){
				if((pr.getEnterTime().getMinute()-exitTime.getMinute())>0){
					System.err.println("Çıkış zamanı giriş zamanından önce olamaz.");
					return false;
				}
			}
		}
		setCurrentOccupancy(getCurrentOccupancy()-1);
		if(pr.getVehicle().isOfficial()) {
			pr.setExitDate(exitDate);
			pr.setExitTime(exitTime);
			return true;
		}
		SubscribedVehicle sv=searchVehicle(plate);
		if(sv!=null) {
			if(sv.getSubscription().isValid()) {
				System.err.println("Üyelik geçersiz, park ücreti alınacak!");
				pr.setExitDate(exitDate);
				pr.setExitTime(exitTime);
				if(exitDate.isEqualsWith(Date.getToday())) {
					setIncomeDaily(incomeDaily + pr.getParkingDuration() * hourlyFee);
				}
				return true;
			}
			pr.setExitDate(exitDate);
			pr.setExitTime(exitTime);
			return true;
		}
		pr.setExitDate(exitDate);
		pr.setExitTime(exitTime);
		if(exitDate.isEqualsWith(Date.getToday())) {
			setIncomeDaily(incomeDaily + pr.getParkingDuration() * hourlyFee);
		}
		return true;
	}
	@Override
	public String toString() {
		StringBuilder arg;
		arg = new StringBuilder("=================================================\n\n");
		arg.append(currentOccupancy).append(" araç park halinde\n");
		arg.append(incomeDaily).append(" günlük gelir.\n\n");
		for(ParkRecord pr: parkRecords) {
			arg.append(pr.toString());
		}
		arg.append("=================================================\n\n");
		return arg.toString();
	}
}

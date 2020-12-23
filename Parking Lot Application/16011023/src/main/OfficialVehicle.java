package main;

public class OfficialVehicle implements Vehicle {
	private String plate;
	
	OfficialVehicle(String plate) throws NullPointerException{
		if(plate==null) {
			throw new NullPointerException("Plaka bo� olamaz!");
		}
		this.plate = plate;
	}

	@Override
	public String getPlate() {
		return plate;
	}

	@Override
	public boolean isOfficial() {
		return true;
	}

	@Override
	public Subscription getSubscription() {
		return null;
	}

}

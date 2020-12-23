package main;

public class RegularVehicle implements Vehicle {
	private String plate;

	RegularVehicle(String plate) throws NullPointerException {
		if (plate == null) {
			throw new NullPointerException("Plaka boş olamaz!");
		}
		this.plate = plate;
	}

	@Override
	public String getPlate() {
		return plate;
	}

	@Override
	public boolean isOfficial() {
		return false;
	}

	@Override
	public Subscription getSubscription() {
		return null;
	}

}

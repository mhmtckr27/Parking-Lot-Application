package main;

public class SubscribedVehicle implements Vehicle {
	private String plate;
	private Subscription subscription;

	SubscribedVehicle(String plate) throws NullPointerException {
		if (plate == null) {
			throw new NullPointerException("Plaka bo� olamaz!");
		}
		this.plate = plate.toUpperCase();
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
		return subscription;
	}

	void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	int calculateTotalDays(){
		return subscription.getEnd().getDayDifference(subscription.getBegin());
	}
}
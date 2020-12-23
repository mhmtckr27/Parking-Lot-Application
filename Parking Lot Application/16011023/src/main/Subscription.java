package main;

public class Subscription {
	private Date begin;
	private Date end;
	private SubscribedVehicle vehicle;

	Subscription(Date begin, Date end, String plate) {
		vehicle = new SubscribedVehicle(plate);
		this.begin = begin;
		this.end = end;
	}
	
	Date getEnd() {
		return end;
	}

	Date getBegin() {
		return begin;
	}

	public SubscribedVehicle getVehicle() {
		return vehicle;
	}

	boolean isValid() {
		if (begin.isBeforeThan(Date.getToday())
				|| begin.isEqualsWith(Date.getToday())) {
			return !end.isAfterThan(Date.getToday())
					&& !end.isEqualsWith(Date.getToday());
		}
		return true;
	}
}

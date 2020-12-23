package main;

public class Time {
	private int hour;
	private int minute;

	Time(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}

	int getHour() {
		return hour;
	}

	int getMinute() {
		return minute;
	}

	int getHourDifference(Time other) {
		int difference = (other.getHour() * 60 + other.getMinute())
				- (this.getHour() * 60 + this.getMinute());
		return difference/60;
	}

	@Override
	public String toString() {
		return ((hour<10 ? "0" + hour : ""+hour)
				+":"+(minute<10 ? "0" + minute : minute));
	}
}

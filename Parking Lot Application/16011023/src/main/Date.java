package main;

public class Date {
	private int day;
	private int month;
	private int year;
	private static Date today;

	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	private int getDay() {
		return day;
	}

	private int getMonth() {
		return month;
	}

	private int getYear() {
		return year;
	}

	static Date getToday() {
		return today;
	}

	static void setToday(Date today) {
		Date.today = today;
		AutoparkModel.getInstance().setToday(today.toString());
	}

	boolean isAfterThan(Date other) {
		if (this.year < other.year) {
			return false;
		}
		if (this.year > other.year) {
			return true;
		}
		if (this.month < other.month) {
			return false;
		}
		if (this.month > other.month) {
			return true;
		}
		if (this.day < other.day) {
			return false;
		}
		return this.day > other.day;
	}

	boolean isBeforeThan(Date other) {
		if (this.year > other.year) {
			return false;
		}
		if (this.year < other.year) {
			return true;
		}
		if (this.month > other.month) {
			return false;
		}
		if (this.month < other.month) {
			return true;
		}
		if (this.day > other.day) {
			return false;
		}
		return this.day < other.day;
	}

	boolean isEqualsWith(Date other) {
		if (other.year == this.year)
			if (other.month == this.month)
				return other.day == this.day;
		return false;
	}

	int getDayDifference(Date other){
		int difference = ((this.getYear()-other.getYear())*365)
						+((this.getMonth()-other.getMonth())*31)
						+this.getDay()-other.getDay();

		return ((difference < 0) ? 0 : difference);
	}

	@Override
	public String toString() {
		return day+"."+month+"."+year;
	}
}
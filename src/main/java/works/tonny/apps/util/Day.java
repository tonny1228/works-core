package works.tonny.apps.util;

import java.util.Date;

import org.llama.library.utils.CalendarUtil;
import org.llama.library.utils.LunarCalendar;

public class Day {

	private CalendarUtil cal;

	private LunarCalendar lc;

	private static final String[] DATE = { "日", "一", "二", "三", "四", "五", "六" };

	public Day(Date date) {
		cal = new CalendarUtil(date);
		lc = new LunarCalendar();
		lc.setTime(date);
	}

	public String getHoliday() {
		String holiday = "";
		if (Calendar.holiday.containsKey("n" + this.getNlMonth() + "-" + this.getNlDay())) {
			holiday += Calendar.holiday.get("n" + this.getNlMonth() + "-" + this.getNlDay());
		}
		if (Calendar.holiday.containsKey(this.getMonth() + "-" + this.getDay())) {
			holiday += " " + Calendar.holiday.get(this.getMonth() + "-" + this.getDay());
		}
		return holiday;
	}

	public Day nextDay() {
		cal.addDays(1);
		return this;
	}

	public Day(int year, int month, int day) {
		this(new Date(year, month, day));
	}

	public String getDateStr() {
		return cal.getDate();
	}

	public int getYear() {
		return cal.getYear();
	}

	public int getMonth() {
		return cal.getMonthInt();
	}

	public int getDay() {
		return cal.getDayOfMonth();
	}

	public int getNlYear() {
		return lc.getYear();
	}

	public int getNlMonth() {
		return lc.getMonth();
	}

	public int getNlDay() {
		return lc.getDay();
	}

	public String getNlYearName() {
		return lc.getYearName();
	}

	public String getNlMonthName() {
		return lc.getMonthName();
	}

	public String getNlDayName() {
		return lc.getDayName();
	}

	public Date getDate() {
		return cal.dateTime();
	}

	public String getNl() {
		return lc.getYear() + "-" + lc.getMonth() + "-" + lc.getDay();
	}

	public String getNlName() {
		return lc.getYearName() + "年" + lc.getMonthName() + lc.getDayName();
	}

	public int getWeekday() {
		return cal.getDayOfWeek();
	}

	public String getWeekdayName() {
		return DATE[getWeekday() - 1];
	}

	public String toString() {
		return this.getDateStr() + "/" + this.getWeekdayName() + "/" + this.getNlName();
	}
}

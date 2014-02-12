package diploma.webcad.core.util.date;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static Long WEEK_VALUE = (long) (1000 * 60 * 60 * 24 * 7);

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return Count of full days (24h) between two dates
	 */
	public static int getDaysDiff(Date date1, Date date2) {
		long day1 = date1.getTime();
		long day2 = date2.getTime();
		return Math.abs((int) ((day2 - day1) / 86400000)); //86400000ms == 24 hours
	}
	
	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return Count of full days (24h) between two dates plus one day, if the remainder is greater than 15 hours
	 */
	public static int getDaysDiffRound(Date date1, Date date2) {
		long day1 = date1.getTime();
		long day2 = date2.getTime();
		int intPart = Math.abs((int) ((day2 - day1) / 86400000)); //86400000ms == 24 hours
		int fracPart = Math.abs((int) (((day2 - day1) % 86400000) / 3600000)); //3600000ms = 1 hour
		if(fracPart < 15) return intPart;
		else return intPart + 1;
	}
	
	/*public static Date getCurrentDate() {
		return Calendar.getInstance(Locale.getDefault()).getTime();
	}*/
	
	public static String formatDate(Date date) {
		Format formatter = new SimpleDateFormat("dd-MM-yyyy");
		return formatter.format(date);
	}
	
	public static String formatDateTime(Date date) {
		Format formatter = new SimpleDateFormat("dd-MM-yyyy, hh:mm");
		return formatter.format(date);
	}
	
	public static String formatTime(Date date) {
		Format formatter = new SimpleDateFormat("hh:mm");
		return formatter.format(date);
	}

	public static boolean inRange(Date date, Date rangeStart, Date rangeEnd) {
		return date.getTime() >= rangeStart.getTime() && date.getTime() <= rangeEnd.getTime();
	}

	public static Date clearTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
}

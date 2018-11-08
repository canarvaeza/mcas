package mcas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatesManager {
	public static String getNewDate(String oldDate, int numbDays) {
		String newDate = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(oldDate));
			c.add(Calendar.DATE, numbDays);
			newDate = sdf.format(c.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}
}

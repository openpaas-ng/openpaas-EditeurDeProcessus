package org.linagora.utility;

import java.util.Calendar;
import java.util.Date;

public class DateManager {

	public static Date addHours(Date date, int hours){
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		calDate.add(Calendar.HOUR, hours);
		return calDate.getTime();
	}
}

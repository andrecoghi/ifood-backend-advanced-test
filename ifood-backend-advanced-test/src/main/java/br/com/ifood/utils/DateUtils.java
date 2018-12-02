package br.com.ifood.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateUtils {

	private static final Locale LOCALE = new Locale("pt", "BR");

	public static Date addSecondsTo(Date date, int seconds) {
		Calendar cal = Calendar.getInstance(LOCALE);

		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);

		return cal.getTime();
	}

}

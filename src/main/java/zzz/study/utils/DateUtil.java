package zzz.study.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private DateUtil() {}
	
	private static Calendar calendar = Calendar.getInstance();
	
	public static Date getDate(int year, int month, int day)
	{
		calendar.set(year, month-1, day);
		return calendar.getTime();
	}
	
	public static String toFormattedDate(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	public static String toFormattedDate(Date date, String pattern)
	{
		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static String localizedDate(int year, int month, int day)
	{
		return toFormattedDate(getDate(year, month, day));
	}
	
	public static String localizedDate(int year, int month, int day, String pattern)
	{
		return toFormattedDate(getDate(year, month, day), pattern);
	}

}

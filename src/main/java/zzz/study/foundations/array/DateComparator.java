package zzz.study.foundations.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class DateComparator implements Comparator {
	
	/**
	 * 对日期进行排列；越接近当前时间的，越排在前面。
	 * 
	 * 日期格式：year-month-day xx:xx:xx
	 */
	
	
	public int compare(Object obj1, Object obj2) {
		
		String date1 = (String)obj1;
		String date2 = (String)obj2;
		
		ArrayList<Integer> date1Arr = parseDate(date1);
		ArrayList<Integer> date2Arr = parseDate(date2);
		
		int i = 0;
		while (i < date1Arr.size()) {
			if (date1Arr.get(i) > date2Arr.get(i))
			    return 1;
			else if (date1Arr.get(i) < date2Arr.get(i))
				return -1;
			else
				i++;
		}
		
		return 0;  
		 
	}
	
	
	/**
	 * parseDate: 解析日期，从中提取出年月日时分秒
	 * 
	 * @param Date 所要解析的日期  & 日期格式： year-month-day xx:xx:xx
	 * @return 存储 年、月、日、时、分、秒 的数组
	 */
	
	public ArrayList<Integer> parseDate(String date)
	{
		ArrayList<Integer> dateTime = new ArrayList<Integer>();
		String[] get = date.split(" ");
		String[] getDate = get[0].split("-");
		String[] getTime = get[1].split(":");
		try {
			for (String s: getDate) {
				int target = Integer.parseInt(s);
				dateTime.add(target);
			}
			for (String s: getTime) {
				int target = Integer.parseInt(s);
				dateTime.add(target);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return dateTime;
		
	}
	 
   
	public static void main(String[] args) {
    	
    	// 解析日期
    	DateComparator dc = new DateComparator();
    	ArrayList dateTime = dc.parseDate("2010-10-07 9:03:07");
    	Iterator iter = dateTime.iterator();
    	while (iter.hasNext()) {
    		Integer i = (Integer)iter.next();
    		System.out.println(i);
    	}
    	
    	// 对日期排序
    	ArrayList<String> dateStrs = new ArrayList<String>();
    	dateStrs.add("2010-9-7 9:03:07");
    	dateStrs.add("2010-10-6 9:03:07");
    	dateStrs.add("2010-9-16 9:03:07");
    	dateStrs.add("2010-9-7 10:12:07");
    	
    	Collections.sort(dateStrs, new DateComparator());
    	
    	iter = dateStrs.iterator();
    	while (iter.hasNext()) {
    		String datestr = (String)iter.next();
    		System.out.println(datestr);
    	}
    	
    	
    }
}

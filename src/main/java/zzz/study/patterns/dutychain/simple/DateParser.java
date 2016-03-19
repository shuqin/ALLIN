package zzz.study.patterns.dutychain.simple;

public class DateParser implements Parseable {

	public boolean parse(String s) 
	{
		if (s.matches("[0-9]{4}[-/.][0-9]{2}[-/.][0-9]{2}")) {
			System.out.println(this.getClass().getName() + ":");
			System.out.println("I am suitable for handling this.");
			return true;
		}
		return false;
	}
}

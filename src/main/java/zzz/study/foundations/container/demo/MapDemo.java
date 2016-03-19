package zzz.study.foundations.container.demo;

import java.util.HashMap;
import java.util.Map;

import static zzz.study.utils.MapUtil.*;
import zzz.study.foundations.sharingclasses.Position;


public class MapDemo {

	public static void main(String[] args)
	{
		System.out.println("----------------- Map Usage -----------------");	
		System.out.println("*************** System Information *****************");
		
		Map<String, String> sysInfo = System.getenv();
		printMap(sysInfo);
		
		System.out.println("************ ��ӳ�䰴��ֵ��С�������� *************");
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("2", 2);
		map.put("6", 6);
		map.put("1", 1);
		map.put("3", 3);
		map.put("5", 5);
		print(sortMap(map));
		
		Map<String, Position> map2 = new HashMap<String, Position>();
		map2.put("13", new Position(2, 3));
		map2.put("5", new Position(1, 2));
		map2.put("17", new Position(4, 1));
		map2.put("8", new Position(2, 2));
		map2.put("25", new Position(3, 4));
		print(sortMap(map2));
		
		
	}
		
		
	
}



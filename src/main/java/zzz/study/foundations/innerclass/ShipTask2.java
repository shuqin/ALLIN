package zzz.study.foundations.innerclass;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import zzz.study.foundations.enums.Fruit;

public class ShipTask2 {
	
	private String startPlace;
	private String dest;
	private SortedMap<Fruit, String> freights;
	
	private class StartPlace {
		
		// 内部类对象自动拥有对其外部类成员的访问权
		
		StartPlace(String whereFrom) {
			startPlace = whereFrom;
		}
		
		String getWhereFrom() {
			return startPlace;
		}
		
	}
	
	private class Destination {
			
		Destination(String whereTo) {
			dest = whereTo;
		}
		
		String getWhereTo() {
			return dest;
		}
	}
	
	private class Freight {
			
		Freight() {
			if (freights == null) {
				freights = new TreeMap<Fruit, String>();
			}
		}
		
		void addFreight(Fruit fruit, String desc) {
			freights.put(fruit, desc);
		}
		
		public String toString() {
			String s = "货物 ------ ";
			Set keyset = freights.keySet();
			Iterator iter = keyset.iterator();
			while (iter.hasNext()) {
				Fruit f = (Fruit) iter.next();
				String desc = freights.get(f);
				s += "\n" + f.name() + " " + desc;
			}
			return s;
		}
	}
	
	class GetOuterThis {
		
		public String toString() {
			String s = "in GetOuterThis： ";
			return s + "\n" + ShipTask2.this;
			// 在内部类中，使用【外部类名.this】来访问其外部类对象。 
		}
	}
	
	// 外部类方法返回对内部类对象的引用
	
	public StartPlace getStartPlace(String whereFrom) {
		return new StartPlace(whereFrom);
	}
	
	public Destination getDest(String whereTo) {
		return new Destination(whereTo);
	}
	
	public Freight getFreight() {
		return new Freight();
	}
	
	public void shipTask() {
		Freight freights = new Freight();
		freights.addFreight(Fruit.BANANA, "500K");
		freights.addFreight(Fruit.APPLE, "300K");
		freights.addFreight(Fruit.PEAR, "100K");
		freights.addFreight(Fruit.GRAPE, "1000K");
		StartPlace startPlace = getStartPlace("Shanghai, China");
		Destination dest = getDest("Washington, USA");
		System.out.println("in shipTask():" + "\n" + this);
	}
	
	public String toString() {
		String shipTask = "运输任务：";
		shipTask += "\n" + startPlace + " ------ " + dest;
		shipTask += "\n" + this.new Freight();
		return shipTask;
	}

    public static void main(String[] args) {
    	ShipTask2 p = new ShipTask2();
    	p.shipTask();
    	
    	// 在外部类的非静态方法之外的任何位置创建某个内部类对象，
    	// 必须具体地指明这个对象的类型（全名限定）。
    	ShipTask2.Destination dest = p.getDest("Los Angeles, USA");
    	System.out.println("目标改变： " + dest.getWhereTo());
    	
    	// 使用【外部类对象.new】语法来创建其内部类对象
    	System.out.println(p.new GetOuterThis());
    }
}


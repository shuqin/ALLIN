package zzz.study.foundations.innerclass;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import zzz.study.foundations.enums.Fruit;

public class ShipTask3 {
	
	private String startPlace;
	private String dest;
	private SortedMap<Fruit, String> freights;
	
	private class StartPlace implements IStartOut {
			
		StartPlace(String whereFrom) {
			startPlace = whereFrom;
		}
		
		public String getWhereFrom() {
			return startPlace;
		}
		
	}
	
	private class Destination implements IDestination {
			
		Destination(String whereTo) {
			dest = whereTo;
		}
		
		public String getWhereTo() {
			return dest;
		}
	}
	
	private class Freight implements IFreight {
			
		Freight() {
			if (freights == null) {
				freights = new TreeMap<Fruit, String>();
			}
		}
		
		public void addFreight(Fruit fruit, String desc) {
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
			return s + "\n" + ShipTask3.this;
		}
	}
	
	// 外部类方法返回对内部类对象的引用
	
	public IStartOut getStartPlace(String whereFrom) {
		return new StartPlace(whereFrom);
	}
	
	public IDestination getDest(String whereTo) {
		return new Destination(whereTo);
	}
	
	public IFreight getFreight() {
		return new Freight();
	}
	
	public void shipTask() {
		Freight freights = new Freight();
		freights.addFreight(Fruit.BANANA, "500K");
		freights.addFreight(Fruit.APPLE, "300K");
		freights.addFreight(Fruit.PEAR, "100K");
		freights.addFreight(Fruit.GRAPE, "1000K");
		IStartOut startPlace = getStartPlace("Shanghai, China");
		IDestination dest = getDest("Washington, USA");
		System.out.println("in shipTask():" + "\n" + this);
	}
	
	public String toString() {
		String shipTask = "运输任务：";
		shipTask += "\n" + startPlace + " ------ " + dest;
		shipTask += "\n" + this.new Freight();
		return shipTask;
	}

    public static void main(String[] args) {
    	
    	ShipTask3 p = new ShipTask3();
    	p.shipTask();
    	
    	// 某个类的内部类声明为私有时，该内部类对象对外部其它类对象是不可见的。
    	IDestination dest = p.getDest("Los Angeles, USA");
    	System.out.println("目标改变： " + dest.getWhereTo());
    	
    	// 使用外部类对象及.new语法来创建其内部类对象
    	System.out.println(p.new GetOuterThis());
    }
}


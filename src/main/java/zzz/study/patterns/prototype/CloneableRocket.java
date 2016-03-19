package zzz.study.patterns.prototype;

import zzz.study.patterns.adapter.byobject.Rocket;

public class CloneableRocket extends Rocket implements Cloneable {
	
	public CloneableRocket(String name, double price, double apogee) {
		super(name, price, apogee);
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public boolean equals(Object obj) {
		
		if (obj == null) return false;
		
		if (this.getClass() != obj.getClass()) return false;
		
		if (obj instanceof CloneableRocket) {
			Rocket r = (Rocket)obj;
			if (!(this.getName().equals(r.getName())))
				return false;
			else if (r.getPrice() != r.getPrice())
				return false;
			else if (r.getApogee() != r.getApogee())
				return false;
		}
		
		return true;
	}
	
	public String toString() {
		return super.toString();
	}

}

package zzz.study.patterns.observer.curve2;

import java.util.Observable;

public class TPeak2 extends Observable {
	
	private double peakTime;
	
	public TPeak2() {
		peakTime = 0;
	}
	
	public TPeak2(double peakTime) {
		this.peakTime = peakTime;
	}
	
	public void setPeakTime(double peakTime) {
		this.peakTime = peakTime;
		setChanged();
		notifyObservers();
	}
	
	public double getPeakTime() {
		return peakTime;
	}
	
	

}

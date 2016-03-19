package zzz.study.patterns.observer.curve1;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TPeak implements ChangeListener {
	
	private double peakTime;
	
	public TPeak() {
		peakTime = 0;
	}
	
	/**
     * Construct a object that will represent the value of a slider.
     * 
     * @param JSlider
     *            the slider to observe
     */
	public TPeak(JSlider slider) {
		peakTime = 0;
        slider.addChangeListener(this);
	}
	
	public TPeak(double peakTime, JSlider slider) {
		this.peakTime = peakTime;
		slider.addChangeListener(this);
	}
	
	public void setPeakTime(double peakTime) {
		this.peakTime = peakTime;
	}
	
	public double getPeakTime() {
		return peakTime;
	}
	
	public void stateChanged(ChangeEvent e) {
		double sliderMax = ((JSlider)e.getSource()).getMaximum();
		double sliderMin = ((JSlider)e.getSource()).getMinimum();
		double val = ((JSlider)e.getSource()).getValue();
		double peakTime = (val - sliderMin) / (sliderMax - sliderMin);
		setPeakTime(peakTime);
	}

}

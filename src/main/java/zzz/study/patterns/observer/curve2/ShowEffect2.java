package zzz.study.patterns.observer.curve2;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import zzz.study.patterns.facade.UI;

public class ShowEffect2  {
	
	protected static TPeak2  tpeak2 = new TPeak2();
	protected static JSlider slider;
	protected static CurvePanel2  ratePanel;
	protected static CurvePanel2  thrustPanel;
	protected static ValueLabel2  tPeakValue2;
	
	
	public static void main(String[] args) {
		
	     UI.launch(new ShowEffect2().mainPanel(), "峰值影响");
	}
	
	public JSlider slider2() {
		if (slider == null) {			
			slider = new JSlider();
			slider.putClientProperty("JSlider.isFilled", Boolean.TRUE);
			slider.setValue(slider.getMinimum());	
			slider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					double sliderMax = ((JSlider)e.getSource()).getMaximum();
					double sliderMin = ((JSlider)e.getSource()).getMinimum();
					double val = ((JSlider)e.getSource()).getValue();
					double peakTime = (val - sliderMin) / (sliderMax - sliderMin);
					tpeak2.setPeakTime(peakTime);
				}
			});
		}
		return slider;
	}
	
    public JPanel createRateBurnPanel2() {
		
    	if (ratePanel == null) {
		     ratePanel = new CurvePanel2(1001, Functions2.getT2(), Functions2.getRate2(), tpeak2);		     
    	}  
    	ratePanel.setPreferredSize(new Dimension(300,200));	
		return ratePanel;
	}
	
	public JPanel createThrustPanel2() {
		
		if (thrustPanel == null) {
		     thrustPanel = new CurvePanel2(1001, Functions2.getT2(), Functions2.getThrust2(), tpeak2);     
    	}
		thrustPanel.setPreferredSize(new Dimension(300,200));		
		return thrustPanel;
	}

    public JPanel curvePanels2() {
    	
    	JPanel curvePanels = new JPanel();
		JPanel rateBurnPane = UI.createTitledPanel("燃烧速率", createRateBurnPanel2());
		JPanel thrustPane = UI.createTitledPanel("火箭推进力", createThrustPanel2());
		curvePanels.add(rateBurnPane);
		curvePanels.add(thrustPane);
		
		return curvePanels;
    }
    
    public JPanel controlPanel2() {
    	
    	JPanel controlPane = UI.createTitledPanel("时间控制", new JPanel());
		
		JLabel label = new JLabel("峰值时间");
		tPeakValue2 = new ValueLabel2(tpeak2); 
		slider = slider2();
		controlPane.add(label);
		controlPane.add(tPeakValue2);
		controlPane.add(slider);
		
		return controlPane;
    }
    
    public JPanel mainPanel() {
    	
    	JPanel mainPanel = new JPanel();
    	mainPanel.setLayout(new BorderLayout());
		mainPanel.add(curvePanels2(), BorderLayout.CENTER);
		mainPanel.add(controlPanel2(), BorderLayout.SOUTH);
		return mainPanel;
    }

}

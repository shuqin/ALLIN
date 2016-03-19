package zzz.study.patterns.facade;

import java.awt.Dimension;

import javax.swing.JFrame;

import zzz.study.patterns.decorator.func.Function;
import zzz.study.patterns.decorator.func.T;

public class FacadeShowFlight {
	
	public static void main(String[] args) {
		PlotPanel p = new PlotPanel(
				101, 
				new T(), 
				new FacadeShowFlight().new YFunction());
		p.setPreferredSize(new Dimension(300,200));
		
		JFrame frame = new JFrame("焰火飞行演示");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(UI.createTitledPanel("飞行轨迹", p));
		
		frame.pack();
		frame.setVisible(true);
	}
	
	private class YFunction extends Function {

		public YFunction() {
			 super(new Function[]{});
		}
		
		public double f(double t) {
			return 4*t*(1-t);
		}
	}

}

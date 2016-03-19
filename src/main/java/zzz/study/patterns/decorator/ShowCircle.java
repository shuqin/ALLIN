package zzz.study.patterns.decorator;


import java.awt.Dimension;

import javax.swing.JFrame;

import zzz.study.patterns.decorator.func.Arithmetic;
import zzz.study.patterns.decorator.func.Constant;
import zzz.study.patterns.decorator.func.Cos;
import zzz.study.patterns.decorator.func.Function;
import zzz.study.patterns.decorator.func.Sin;
import zzz.study.patterns.decorator.func.T;
import zzz.study.patterns.facade.UI;

public class ShowCircle {
	
	public static void main(String[] args) {
		
		Function theta = new Arithmetic('*', new T(), new Constant(2*Math.PI));
		Function theta2 = new Arithmetic('*', new T(), new Constant(2*Math.PI*5));
		Function x = new Arithmetic('+', new Cos(theta), new Sin(theta2));
		Function y = new Arithmetic('+', new Sin(theta), new Cos(theta2));
		
		FunPanel p = new FunPanel(1001);
		p.setPreferredSize(new Dimension(500,500));
		p.setXY(x, y);
		
		JFrame frame = new JFrame("画图演示");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(UI.createTitledPanel("循环圆", p));
		
		frame.pack();
		frame.setVisible(true);
	}

}

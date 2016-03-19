package zzz.study.patterns.adapter.byobject;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ShowRocketTable {
	
	public static void main(String[] args) {
		
		JTable table = new JTable(getRocketTable());
		table.setRowHeight(36);
		JScrollPane pane = new JScrollPane(table);
		pane.setPreferredSize(new java.awt.Dimension(300,100));
		display(pane, "火箭数据表格");
	}
	
	public static void display(Component c, String title) {
		JFrame f = new JFrame(title);
		f.getContentPane().add(c);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
	
	private static RocketTable getRocketTable() {
		Rocket r1 = new Rocket("挑战者号", 3.95, 50.2);
		Rocket r2 = new Rocket("阿波罗号", 29.0, 75.3);
		return new RocketTable(new Rocket[] {r1, r2});
	}

}

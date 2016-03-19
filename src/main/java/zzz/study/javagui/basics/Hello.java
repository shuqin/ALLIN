package zzz.study.javagui.basics;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Hello extends JFrame {
	
	JLabel label;
	static Hello hello;
	
	public Hello() {
		super("Hello, GUI");
		label = new JLabel("a label");
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		contentPane.add(label);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				hello = new Hello();
			}
		});
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				hello.label.setText("Hey, 变了!");
			}
		});
		
	}

}

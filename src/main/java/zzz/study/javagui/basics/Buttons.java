package zzz.study.javagui.basics;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import static zzz.study.javagui.basics.SwingConsole.*;

public class Buttons extends JFrame {
	
	private JButton b1 = new JButton("Button b1");
	private JButton b2 = new JButton("Button b2");
	private JTextField text = new JTextField(10); 
	private ActionListener bl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String content = ((JButton)e.getSource()).getText();
			text.setText(content);
		}
	};
	
	public Buttons() {
		
		b1.addActionListener(bl);
		b2.addActionListener(bl);
		setLayout(new FlowLayout());
		add(b1);
		add(b2);
		add(text);
	}
	
	public static void main(String[] args) {
		run(new Buttons(), 300, 200);
	}
	
	

}

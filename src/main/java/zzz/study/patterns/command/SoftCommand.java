package zzz.study.patterns.command;

import javax.swing.JTextField;

public class SoftCommand implements Command {

	public void setTextField(JTextField textfield) {
		
		textfield.setText("gentle Command that liable to accept.");
		
	}
	
	

}

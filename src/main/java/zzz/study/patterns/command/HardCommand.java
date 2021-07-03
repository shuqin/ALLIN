package zzz.study.patterns.command;

import javax.swing.*;

public class HardCommand implements Command {

    public void setTextField(JTextField textfield) {
        textfield.setText("Compelling command that i resist.");
    }

}

package zzz.study.patterns.command;

import javax.swing.*;

public class Receiver {

    private Command command;
    private JTextField textfield;

    public Receiver(Command command, JTextField textfield) {
        this.command = command;
        this.textfield = textfield;
    }

    public void execute() {
        command.setTextField(textfield);
    }

}

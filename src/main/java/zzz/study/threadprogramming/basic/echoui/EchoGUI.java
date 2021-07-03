package zzz.study.threadprogramming.basic.echoui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class EchoGUI extends JFrame {

    public EchoGUI(String name) {
        super(name);
    }

    public static void main(String[] args) {
        new EchoGUI("程序演示：字符回显").launch();

    }

    public JPanel inputpanel() {
        JPanel inputpanel = new JPanel();
        inputpanel.setLayout(new BorderLayout());
        inputpanel.setBorder(new TitledBorder("字符输入区："));
        JTextArea inputRegion = new JTextArea();
        inputpanel.add(inputRegion, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(inputRegion);
        inputpanel.add(scroll);
        return inputpanel;
    }

    public JPanel echopanel() {
        JPanel echopanel = new JPanel();
        echopanel.setLayout(new BorderLayout());
        echopanel.setBorder(new TitledBorder("回显区： "));
        JTextArea inputRegion = new JTextArea();
        echopanel.add(inputRegion, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(inputRegion);
        echopanel.add(scroll);
        return echopanel;
    }

    public JPanel tippanel() {
        JPanel tippanel = new JPanel();
        tippanel.setLayout(new BorderLayout());
        tippanel.setBorder(new TitledBorder("提示区： "));
        JTextArea inputRegion = new JTextArea();
        tippanel.add(inputRegion, BorderLayout.CENTER);
        JScrollPane scroll = new JScrollPane(inputRegion);
        tippanel.add(scroll);
        return tippanel;
    }

    public void launch() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(3, 1));
        JPanel inputpanel = inputpanel();
        JPanel echopanel = echopanel();
        JPanel tippanel = tippanel();
        contentPane.add(inputpanel);
        contentPane.add(echopanel);
        contentPane.add(tippanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

}

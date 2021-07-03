package zzz.study.patterns.command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client {

    private static JTextField textfield = null;

    public static void main(String[] args) {

        JPanel panel = new Client().mainBoard();
        Client.launch(panel, "命令模式");
    }

    public static JFrame launch(Component c, String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(c);

        frame.pack();
        frame.setVisible(true);
        return frame;
    }

    public JMenuBar menus() {
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("Command");

        JMenuItem menuItem = new JMenuItem("Soft Command..");
        menuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Receiver r = new Receiver(new SoftCommand(), textfield);
                r.execute();

            }

        });
        menu.add(menuItem);

        JMenuItem menuItem2 = new JMenuItem("Hard Command..");
        menuItem2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                Receiver r = new Receiver(new HardCommand(), textfield);
                r.execute();

            }

        });
        menu.add(menuItem2);
        mb.add(menu);

        return mb;

    }

    public JPanel mainBoard() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450, 300));
        panel.setLayout(new BorderLayout());
        JMenuBar mb = menus();
        panel.add(mb, BorderLayout.NORTH);
        if (textfield == null) {
            textfield = new JTextField("");
        }
        panel.add(textfield, BorderLayout.CENTER);

        return panel;

    }


}

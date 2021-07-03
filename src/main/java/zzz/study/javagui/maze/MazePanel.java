package zzz.study.javagui.maze;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * 迷宫面板： 按钮事件的观察者
 */
public class MazePanel extends JPanel implements Observer {

    private String title;
    private String text;
    private JTextArea infoArea;

    public MazePanel(String title, Font font) {
        this(title, "");
        infoArea.setFont(font);
    }

    public MazePanel(String title, String text) {
        this.title = title;
        this.text = text;
        infoArea = new JTextArea(text);
        //infoArea.setEnabled(false);
        setLayout(new BorderLayout());
        setBorder(new TitledBorder(title));
        add(infoArea, BorderLayout.CENTER);
        add(new JScrollPane(infoArea));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void update(Observable o, Object arg) {

        Maze m = (Maze) o;
        if (m.isCreatedFinished()) {
            m.solve();
            text = "" + m + "\n" + m.getSolution();
        } else {
            text = "" + m + "\n";
        }
        infoArea.setText(text);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                updateUI();
            }
        });

    }

}

package zzz.study.javagui.maze;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

/**
 * 监听按钮事件，并改变 Maze 对象（被观察者）
 */
public class Model implements ActionListener {

    private MazeGUI app;   // 用于从中获取数据的 GUI 界面

    public void actionPerformed(ActionEvent event) {

        JTextField inputRow = app.getInputRow();
        JTextField inputCol = app.getInputCol();
        JPanel mazePanel = app.getMazePanel();

        String rowStr = inputRow.getText();
        String colStr = inputCol.getText();
        String regex = "^\\s*[1-9][0-9]?\\s*$";
        if (rowStr.matches(regex) && colStr.matches(regex)) {
            int rows = Integer.parseInt(inputRow.getText());
            int cols = Integer.parseInt(inputCol.getText());
            final Maze maze = new Maze(rows, cols);
            maze.addObserver((Observer) mazePanel);

            new Thread(new Runnable() {
                public void run() {
                    if (maze.isCreatedFinished()) {
                        maze.reset();
                        maze.change();
                    }
                    maze.createMaze();
                }
            }).start();

        } else {
            JOptionPane.showMessageDialog(null, "对不起，您的输入有误， 请输入 [1-99] 之间的任意数字！", "警告", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void setGUI(MazeGUI app) {
        this.app = app;
    }

}

package zzz.study.patterns.facade;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ShowFlight extends JPanel {

    public static void main(String[] args) {
        ShowFlight flight = new ShowFlight();
        flight.setPreferredSize(new Dimension(300, 200));
        JPanel panel = createTitledPanel("飞行轨迹", flight);

        JFrame frame = new JFrame("焰火飞行演示");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static JPanel createTitledPanel(String title, JPanel in) {
        JPanel out = new JPanel();
        out.add(in);
        out.setBorder(createTitledBorder(title));
        return out;
    }

    public static TitledBorder createTitledBorder(String title) {
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createBevelBorder(BevelBorder.RAISED),
                title, TitledBorder.LEFT, TitledBorder.TOP);
        tb.setTitleColor(Color.black);
        tb.setTitleFont(getStandardFont());
        return tb;
    }

    public static Font getStandardFont() {
        return new Font("Dialog", Font.PLAIN, 18);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int nPoint = 101;
        double w = getWidth() - 1;
        double h = getHeight() - 1;
        int[] x = new int[nPoint];
        int[] y = new int[nPoint];
        for (int i = 0; i < nPoint; i++) {
            double t = ((double) i) / (nPoint - 1);
            x[i] = (int) (t * w);
            y[i] = (int) (4 * h * (t - 0.5) * (t - 0.5));
        }
        g.drawPolyline(x, y, nPoint);
    }
}

package zzz.study.patterns.observer.curve1;

import zzz.study.patterns.decorator.func.Function;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


public class CurvePanel extends JPanel implements ChangeListener {

    protected TPeak tpeak;
    protected Function xFunction;
    protected Function yFunction;
    private int points;
    private int[] xPoints;
    private int[] yPoints;

    public CurvePanel(int nPoint, Function xfunc, Function yfunc, JSlider slider) {
        if (tpeak == null)
            tpeak = new TPeak(slider);
        points = nPoint;
        xPoints = new int[points];
        yPoints = new int[points];
        xFunction = xfunc;
        yFunction = yfunc;
        slider.addChangeListener(this);
        setBackground(Color.WHITE);
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        double w = getWidth();
        double h = getHeight();

        for (int i = 0; i < points; i++) {
            double t = ((double) i) / (points - 1);
            xPoints[i] = (int) (xFunction.f(t) * w);
            yPoints[i] = (int) (h * (1 - yFunction.f(t - tpeak.getPeakTime())));
        }
        g.drawPolyline(xPoints, yPoints, points);
    }

    public void stateChanged(ChangeEvent e) {

        repaint();
    }

}

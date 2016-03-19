package zzz.study.patterns.observer.curve2;

import zzz.study.patterns.decorator.func.Function;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;


public class CurvePanel2 extends JPanel implements Observer {

    protected double tpeak2;
    protected Function xFunction;
    protected Function yFunction;
    private int points;
    private int[] xPoints;
    private int[] yPoints;

    public CurvePanel2(int nPoint, Function xfunc, Function yfunc, TPeak2 tpeak2) {
        points = nPoint;
        xPoints = new int[points];
        yPoints = new int[points];
        xFunction = xfunc;
        yFunction = yfunc;
        setBackground(Color.WHITE);
        tpeak2.addObserver(this);
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        double w = getWidth();
        double h = getHeight();

        for (int i = 0; i < points; i++) {
            double t = ((double) i) / (points - 1);
            xPoints[i] = (int) (xFunction.f(t) * w);
            yPoints[i] = (int) (h * (1 - yFunction.f(t - tpeak2)));
        }
        g.drawPolyline(xPoints, yPoints, points);
    }

    public void update(Observable observ, Object arg) {

        tpeak2 = ((TPeak2) observ).getPeakTime();
        repaint();
    }

}

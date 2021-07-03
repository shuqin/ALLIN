package zzz.study.patterns.observer.curve1;

import zzz.study.patterns.facade.UI;

import javax.swing.*;
import java.awt.*;

public class ShowEffect {

    protected static JSlider slider;
    protected static CurvePanel ratePanel;
    protected static CurvePanel thrustPanel;
    protected static ValueLabel tPeakValue;

    public static void main(String[] args) {

        UI.launch(new ShowEffect().mainPanel(), "峰值影响");
    }

    public JSlider slider() {
        if (slider == null) {
            slider = new JSlider();
            slider.putClientProperty("JSlider.isFilled", Boolean.TRUE);
            slider.setValue(slider.getMinimum());
        }
        return slider;
    }

    public JPanel createRateBurnPanel() {

        if (ratePanel == null) {
            ratePanel = new CurvePanel(1001, Functions.getT(), Functions.getRate(), slider());
        }
        ratePanel.setPreferredSize(new Dimension(300, 200));
        return ratePanel;
    }

    public JPanel createThrustPanel() {

        if (thrustPanel == null) {
            thrustPanel = new CurvePanel(1001, Functions.getT(), Functions.getThrust(), slider());
        }
        thrustPanel.setPreferredSize(new Dimension(300, 200));
        return thrustPanel;
    }

    public JPanel curvePanels() {

        JPanel curvePanels = new JPanel();
        JPanel rateBurnPane = UI.createTitledPanel("燃烧速率", createRateBurnPanel());
        JPanel thrustPane = UI.createTitledPanel("火箭推进力", createThrustPanel());
        curvePanels.add(rateBurnPane);
        curvePanels.add(thrustPane);

        return curvePanels;
    }

    public JPanel controlPanel() {

        JPanel controlPane = UI.createTitledPanel("时间控制", new JPanel());

        JLabel label = new JLabel("峰值时间");
        tPeakValue = new ValueLabel(slider());
        slider = slider();
        controlPane.add(label);
        controlPane.add(tPeakValue);
        controlPane.add(slider);

        return controlPane;
    }

    public JPanel mainPanel() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(curvePanels(), BorderLayout.CENTER);
        mainPanel.add(controlPanel(), BorderLayout.SOUTH);
        return mainPanel;
    }

}

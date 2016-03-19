package zzz.study.patterns.observer.curve1;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ValueLabel extends JLabel implements ChangeListener {

    private TPeak tPeak;

    /**
     * Construct a label that will show the value of a slider.
     *
     * @param JSlider the slider to observe
     */
    public ValueLabel(JSlider slider) {

        if (tPeak == null)
            tPeak = new TPeak(slider);
        setText(Double.toString(tPeak.getPeakTime()));
        slider.addChangeListener(this);
    }

    public ValueLabel(String text, JSlider slider) {
        if (tPeak == null)
            tPeak = new TPeak(slider);
        slider.addChangeListener(this);
        setText(text);
    }

    public void stateChanged(ChangeEvent e) {
        setText(Double.toString(tPeak.getPeakTime()));
    }

}

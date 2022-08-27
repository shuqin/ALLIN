package zzz.study.patterns.observer.curve2;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class ValueLabel2 extends JLabel implements Observer {

    public ValueLabel2(TPeak2 tpeak2) {
        tpeak2.addObserver(this);
    }

    public void update(Observable observ, Object arg) {

        setText(Double.toString(((TPeak2) observ).getPeakTime()));
    }

}

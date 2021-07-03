package zzz.study.patterns.state;

import java.util.Observable;

public class Door extends Observable {

    public final int CLOSED = -1;
    public final int OPENING = -2;
    public final int OPEN = -3;
    public final int CLOSING = -4;
    public final int STAYOPEN = -5;

    private int state = CLOSED;

    public Door() {
    }

    public String status() {
        switch (state) {
            case OPENING:
                return "正在打开...";
            case OPEN:
                return "已开启状态";
            case CLOSING:
                return "正在关闭...";
            case STAYOPEN:
                return "保持开启状态";
            default:
                return "已关闭状态";
        }
    }

    public void touch() throws Exception {
        switch (state) {
            case OPENING:
            case STAYOPEN:
                setState(CLOSING);
                break;
            case CLOSING:
            case CLOSED:
                setState(OPENING);
                break;
            case OPEN:
                setState(STAYOPEN);
                break;
            default:
                throw new Exception("无法发生！");
        }
    }

    public void complete(int state) {
        switch (state) {
            case CLOSING:
                setState(CLOSED);
                break;
            case OPENING:
                setState(OPEN);
                break;
            default:
                break;
        }
    }

    public void timeout() {
        setState(CLOSING);
    }

    private void setState(int state) {
        this.state = state;
        setChanged();
        notifyObservers();
    }


}

package zzz.study.patterns.builder;

public class ReservationException extends Exception {

    private String msg;

    public ReservationException(String msg) {
        super();
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}

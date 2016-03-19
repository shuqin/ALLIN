package zzz.study.patterns.builder;


public abstract class ReservationBuilder {

    protected final static int MIN_PERSONS = 25;
    protected final static int MIN_TOTAL_FEE = 1000;

    protected String date;
    protected int headcount;
    protected String city;
    protected double yuanPerHead;
    protected boolean hasSite;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHeadcount() {
        return headcount;
    }

    public void setHeadcount(int headcount) {
        this.headcount = headcount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getYuanPerHead() {
        return yuanPerHead;
    }

    public void setYuanPerHead(double yuanPerHead) {
        this.yuanPerHead = yuanPerHead;
    }

    public boolean isHasSite() {
        return hasSite;
    }

    public void setHasSite(boolean hasSite) {
        this.hasSite = hasSite;
    }

    public abstract Reservation build() throws ReservationException;

}

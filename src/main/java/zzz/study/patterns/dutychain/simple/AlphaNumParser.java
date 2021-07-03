package zzz.study.patterns.dutychain.simple;

public class AlphaNumParser implements Parseable {

    public boolean parse(String s) {
        if (s.matches("[a-zA-Z0-9]+")) {
            System.out.println(this.getClass().getName() + ":");
            System.out.println("I am suitable for handling this.");
            return true;
        }
        return false;
    }

}

package zzz.study.patterns.observer.realconfig.interact;

public class AApp extends Application {

    public AApp() {
        super.setId();
    }

    public Object haa(Config c) {
        System.out.println("haa: " + c.getConf());
        return c;
    }

    public Object hab(Config c) {
        System.out.println("hab: " + c.getConf());
        return c;
    }

    public Object hac(Config c) {
        System.out.println("hac: " + c.getConf());
        return c;
    }

}

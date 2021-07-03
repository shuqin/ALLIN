package zzz.study.patterns.observer.realconfig.more;

public class AApplication extends AbstractApplication {

    @Override
    public void handle(Object conf) {
        System.out.println("A Conf updated: " + conf);
    }

}

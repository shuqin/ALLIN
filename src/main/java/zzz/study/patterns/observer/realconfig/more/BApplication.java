package zzz.study.patterns.observer.realconfig.more;

public class BApplication extends AbstractApplication {

    @Override
    public void handle(Object conf) {
        Long num = (Long) conf;
        if (num != null && num > 0) {
            String info = String.format("factor(%d) = %d", num, factor(num));
            System.out.println(info);
        }
    }

    public Long factor(Long num) {
        if (num < 0) {
            return 0L;
        }
        if (num == 0) {
            return 1L;
        }
        return num * factor(num - 1);
    }
}

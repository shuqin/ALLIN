package zzz.study.patterns.observer.realconfig.interact;


import java.util.concurrent.TimeUnit;

public class ConfigRealUpdating {

    public static void main(String[] args) {

        // 中介者是全局管理者，是最先存在的
        ObserverMediator mediator = new ObserverMediator();

        // 这一步在配置平台实现, 可以采用注解的方式注入到应用中，配置仅与中介者交互
        Config aConfig = new Config("Haha", mediator);
        Config bConfig = new Config(-1L, mediator);
        Config cConfig = new Config(true, mediator);
        AApp a = new AApp();
        BApp b = new BApp();

        // 这一步可以通过应用启动时注册到分布式服务发现系统上来完成
        mediator.register(aConfig, new ConfigObserver(a, conf -> a.haa(conf)));
        mediator.register(bConfig, new ConfigObserver(a, conf -> a.hab(conf)));
        mediator.register(cConfig, new ConfigObserver(a, conf -> a.hac(conf)));

        mediator.register(aConfig, new ConfigObserver(b, conf -> b.hba(conf)));
        mediator.register(bConfig, new ConfigObserver(b, conf -> b.hbb(conf)));
        mediator.register(cConfig, new ConfigObserver(b, conf -> b.hbc(conf)));

        // 核心: 更新与通知
        aConfig.update("I am changed");
        sleep(2000);

        bConfig.update(9L);
        sleep(2000);

        mediator.unregister(cConfig, new ConfigObserver(b, conf -> b.hbc(conf)));

        cConfig.update(false);
        sleep(2000);
    }

    private static void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

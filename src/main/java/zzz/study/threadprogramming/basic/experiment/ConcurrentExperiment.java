package zzz.study.threadprogramming.basic.experiment;

/**
 * Created by lovesqcc on 16-2-15.
 */
public interface ConcurrentExperiment {

    /**
     * 执行一次并发实验,若成功返回 true, 若失败返回 false
     *
     * @return
     */
    public boolean perform();
}

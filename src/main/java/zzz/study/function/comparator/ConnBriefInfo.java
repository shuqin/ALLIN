package zzz.study.function.comparator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description TODO
 * @Date 2021/6/11 6:52 下午
 * @Created by qinshu
 */
@Setter
@Getter
@ToString
public class ConnBriefInfo implements ComparatorObject {

    public static final String DEFAULT_SORT_TIME = "firstTime";

    private Long visitCount;
    private String firstTime;
    private String lastTime;

    public ConnBriefInfo(Long visitCount, String firstTime, String lastTime) {
        this.visitCount = visitCount;
        this.firstTime = firstTime;
        this.lastTime = lastTime;
    }
}

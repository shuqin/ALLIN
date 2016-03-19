package zzz.study.patterns.templateMethod.express;

/**
 * Created by shuqin on 17/4/6.
 */
public interface Express {

    /**
     * 通用发货接口
     *
     * @param expressParam 发货参数
     * @return 发货包裹ID
     */
    int postExpress(ExpressParam expressParam);
}

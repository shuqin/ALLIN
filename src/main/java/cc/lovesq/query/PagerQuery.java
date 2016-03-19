package cc.lovesq.query;

import cc.lovesq.model.BaseObject;
import lombok.Data;

/**
 * @author YHJ create at 2014年3月3日 下午2:09:42
 * @Described：所有带分页的Pojo的基类 实现序列化
 */
@Data
public class PagerQuery extends BaseObject {

    private static final long serialVersionUID = 3567389263772243142L;

    /**
     * 开始的记录行
     **/
    private Integer firstRow = 0;

    /**
     * 默认一页20行
     **/
    private Integer pageSize = 20;

    /**
     * 当前页
     **/
    private int pageNum = 1;


}

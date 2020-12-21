package cc.lovesq.goodssnapshot;

import lombok.Data;

/**
 * 返回给前端的数据： List<GoodsServiceSnapshot>
 *
 */
@Data
public class GoodsServiceSnapshot {

    /** 快照信息的 key  */
    private String key;

    /** 中文标题 */
    private String title;

    /** 中文描述 */
    private String desc;


}

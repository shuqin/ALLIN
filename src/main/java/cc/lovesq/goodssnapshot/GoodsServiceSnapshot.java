package cc.lovesq.goodssnapshot;

/**
 * 返回给前端的数据： List<GoodsServiceSnapshot>
 *
 */
public class GoodsServiceSnapshot {

    /** 快照信息的 key  */
    private String key;

    /** 中文标题 */
    private String title;

    /** 中文描述 */
    private String desc;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

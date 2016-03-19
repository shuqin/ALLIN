package cc.lovesq.service;

import cc.lovesq.model.CreativeDO;
import cc.lovesq.query.CreativeQuery;

import java.util.List;

public interface CreativeService extends BaseService<CreativeDO> {

    /**
     * 根据创意ID获取创意详情
     *
     * @param creativeId 创意ID
     * @return 创意详情
     */
    CreativeDO get(Long creativeId);

    /**
     * 保存新增创意
     *
     * @param creative 要保存的创意对象
     */
    void save(CreativeDO creative);

    /**
     * 更新创意
     *
     * @param creative 要保存的创意对象
     */
    void update(CreativeDO creative);

    /**
     * 根据创意ID删除指定创意
     *
     * @param creativeId 创意ID
     */
    void delete(Long creativeId);

    /**
     * 根据指定条件查询创意
     *
     * @param query 查询条件
     */
    List<CreativeDO> search(CreativeQuery query);

    /**
     * 根据指定条件查询创意
     *
     * @param query 查询条件
     * @return 指定条件下的总记录数
     */
    Integer count(CreativeQuery query);

}

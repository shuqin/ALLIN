package cc.lovesq.service;

import java.util.List;

import cc.lovesq.pojo.CreativeDO;
import cc.lovesq.query.CreativeQuery;

public interface CreativeService {
	
	/**
	 * 根据创意ID获取创意详情
	 * @param creativeId  创意ID
	 * @return 创意详情
	 */
    public CreativeDO get(Long creativeId);

    /**
     * 保存新增创意
     * @param creative 要保存的创意对象
     * @return 
     */
    public void  save(CreativeDO creative);
    
    /**
     * 更新创意
     * @param creative 要保存的创意对象
     * @return 
     */
    public void update(CreativeDO creative);
    
    /**
	 * 根据创意ID删除指定创意
	 * @param creativeId  创意ID
	 * @return 
	 */
    public void delete(Long creativeId);

    /**
     * 	根据指定条件查询创意
     * @param query 查询条件
     * @param page  指定页码
     * @param pageSize 每页记录数
     */
    public List<CreativeDO> search(CreativeQuery query);
    
    /**
     * 	根据指定条件查询创意
     * @param query 查询条件
     * @return 指定条件下的总记录数
     */
    public Integer count(CreativeQuery query);
    
}

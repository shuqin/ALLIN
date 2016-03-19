package cc.lovesq.dao;

import java.util.List;

import cc.lovesq.pojo.CreativeDO;
import cc.lovesq.query.CreativeQuery;

public interface CreativeDAO {
	
	public CreativeDO findByCreativeId(Long creativeId);
	
	public Integer save(CreativeDO creative);
	
	public Integer update(CreativeDO creative);
	
	public Integer delete(Long creativeId);
	
	public List<CreativeDO> list(CreativeQuery query);
	
	public Integer count(CreativeQuery query);

}

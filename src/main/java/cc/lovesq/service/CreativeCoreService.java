package cc.lovesq.service;

import cc.lovesq.dao.CreativeDAO;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.List;

import cc.lovesq.pojo.CreativeDO;
import cc.lovesq.query.CreativeQuery;

@Component
public class CreativeCoreService { 

	@Resource
	private CreativeDAO creativeDAO;

	 public CreativeDO findByCreativeId(Long creativeId) {
		return creativeDAO.findByCreativeId(creativeId);
	}
	
	 public Integer save(CreativeDO creative) {
		return creativeDAO.save(creative);
	}
	
	 public Integer update(CreativeDO creative) {
		return creativeDAO.update(creative);
	}
	
	 public Integer delete(Long creativeId) {
		return creativeDAO.delete(creativeId);
	}
	
	 public List<CreativeDO> list(CreativeQuery query) {
		return creativeDAO.list(query);
	}
	
	 public Integer count(CreativeQuery query) {
		return creativeDAO.count(query);
	}

}

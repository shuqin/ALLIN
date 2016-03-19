package cc.lovesq.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.lovesq.dao.CreativeDAO;
import cc.lovesq.pojo.CreativeDO;
import cc.lovesq.query.CreativeQuery;
import cc.lovesq.service.CreativeService;

@Service("creativeService")
public class CreativeServiceImpl implements CreativeService {

	@Resource
	private CreativeDAO creativeDAO;
	
	public CreativeDO get(Long creativeId) {
		return creativeDAO.findByCreativeId(creativeId);
	}

	public void save(CreativeDO creative) {
		creativeDAO.save(creative);
	}

	public void update(CreativeDO creative) {
		creativeDAO.update(creative);
	}

	public void delete(Long creativeId) {
		creativeDAO.delete(creativeId);
	}

	public List<CreativeDO> search(CreativeQuery query) {
		return creativeDAO.list(query);
	}

	public Integer count(CreativeQuery query) {
		return creativeDAO.count(query);
	}

}

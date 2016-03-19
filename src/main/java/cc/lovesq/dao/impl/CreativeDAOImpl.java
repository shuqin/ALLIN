package cc.lovesq.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import cc.lovesq.dao.CreativeDAO;
import cc.lovesq.dao.util.SqlDaoBaseSupport;
import cc.lovesq.pojo.CreativeDO;
import cc.lovesq.query.CreativeQuery;

@Component("creativeDAO")
public class CreativeDAOImpl extends SqlDaoBaseSupport implements CreativeDAO {

	private String namespace = "Creative.";
	
	public CreativeDO findByCreativeId(Long creativeId) {
		return (CreativeDO) this.getSqlMapClientTemplateSupport().queryForObject(namespace+"selectByCreativeId", creativeId, null);
		
	}
	
	public Integer save(CreativeDO creative) {
		return (Integer) this.getSqlMapClientTemplateSupport().insert(namespace+"insert", creative, null);
	}

	public Integer update(CreativeDO creative) {
		return (Integer) this.getSqlMapClientTemplateSupport().update(namespace+"update", creative, null);
	}

	public Integer delete(Long creativeId) {
		return this.getSqlMapClientTemplateSupport().delete(namespace+"delete", creativeId, null);
	}

	public List<CreativeDO> list(CreativeQuery query) {
		return this.getSqlMapClientTemplateSupport().queryForList(namespace+"listByQuery", query, null);
	}

	public Integer count(CreativeQuery query) {
		return (Integer) this.getSqlMapClientTemplateSupport().queryForObject(namespace+"countByQuery", query, null);
		
	}


}

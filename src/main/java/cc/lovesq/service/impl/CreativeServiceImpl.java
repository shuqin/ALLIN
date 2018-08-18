package cc.lovesq.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.lovesq.dao.CreativeMapper;
import cc.lovesq.pojo.CreativeDO;
import cc.lovesq.query.CreativeQuery;
import cc.lovesq.service.CreativeService;

@Service("creativeService")
public class CreativeServiceImpl implements CreativeService {

  @Resource
  private CreativeMapper creativeMapper;

  public CreativeDO get(Long creativeId) {
    return creativeMapper.findByCreativeId(creativeId);
  }

  public void save(CreativeDO creative) {
    creativeMapper.insert(creative);
  }

  public void update(CreativeDO creative) {
    creativeMapper.update(creative);
  }

  public void delete(Long creativeId) {
    creativeMapper.delete(creativeId);
  }

  public List<CreativeDO> search(CreativeQuery query) {
    return creativeMapper.list(query);
  }

  public Integer count(CreativeQuery query) {
    return creativeMapper.count(query);
  }

}

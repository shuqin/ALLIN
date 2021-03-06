package cc.lovesq.service.impl;

import java.util.List;

import javax.annotation.Resource;

import cc.lovesq.annotations.Timecost;
import cc.lovesq.components.JedisLocalClient;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.lovesq.dao.CreativeMapper;
import cc.lovesq.pojo.CreativeDO;
import cc.lovesq.query.CreativeQuery;
import cc.lovesq.service.CreativeService;
import shared.utils.JsonUtil;

@Component("creativeService")
public class CreativeServiceImpl implements CreativeService {

  @Autowired
  private CreativeMapper creativeMapper;

  private static final String CREATIVE_KEY = "creative:";

  @Resource
  private JedisLocalClient jedisLocalClient;

  @Timecost
  public CreativeDO get(Long creativeId) {

    String creativeJson = jedisLocalClient.get(CREATIVE_KEY+creativeId);
    if (!StringUtils.isBlank(creativeJson)) {
      return JsonUtil.toObject(creativeJson, CreativeDO.class);
    }
    CreativeDO c = creativeMapper.findByCreativeId(creativeId);
    jedisLocalClient.set(CREATIVE_KEY+creativeId, JSON.toJSONString(c));
    return c;
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

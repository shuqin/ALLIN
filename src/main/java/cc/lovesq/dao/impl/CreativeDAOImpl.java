package cc.lovesq.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cc.lovesq.dao.CreativeDAO;
import cc.lovesq.pojo.CreativeDO;
import cc.lovesq.query.CreativeQuery;

@Component("creativeDAO")
public class CreativeDAOImpl implements CreativeDAO {

  private String namespace = "Creative.";

  public CreativeDO findByCreativeId(Long creativeId) {
    return null;

  }

  public Integer save(CreativeDO creative) {
    return 0;
  }

  public Integer update(CreativeDO creative) {
    return 0;
  }

  public Integer delete(Long creativeId) {
    return 0;
  }

  public List<CreativeDO> list(CreativeQuery query) {
    return new ArrayList<>();
  }

  public Integer count(CreativeQuery query) {
    return 0;
  }
}

package cc.lovesq.dao;

import org.springframework.stereotype.Component;

import java.util.List;

import cc.lovesq.pojo.CreativeDO;
import cc.lovesq.query.CreativeQuery;

@Component
public interface CreativeMapper {

  CreativeDO findByCreativeId(Long creativeId);

  Integer insert(CreativeDO creative);

  Integer update(CreativeDO creative);

  Integer delete(Long creativeId);

  List<CreativeDO> list(CreativeQuery query);

  Integer count(CreativeQuery query);

}

package cc.lovesq.dao;

import cc.lovesq.model.CreativeDO;
import cc.lovesq.query.CreativeQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CreativeMapper {

    CreativeDO findByCreativeId(Long creativeId);

    Integer insert(CreativeDO creative);

    Integer update(CreativeDO creative);

    Integer delete(Long creativeId);

    List<CreativeDO> list(CreativeQuery query);

    Integer count(CreativeQuery query);

}

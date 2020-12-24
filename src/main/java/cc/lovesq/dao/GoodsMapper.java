package cc.lovesq.dao;

import cc.lovesq.model.GoodsDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper {

  Integer insert(GoodsDO goodsDO);

  GoodsDO findByQuery(GoodsDO query);

}

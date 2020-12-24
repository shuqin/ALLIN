package cc.lovesq.dao;

import cc.lovesq.model.GoodsDO;
import cc.lovesq.query.GoodsQuery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper {

  Integer insert(GoodsDO goodsDO);

  GoodsDO findByQuery(GoodsQuery query);

}

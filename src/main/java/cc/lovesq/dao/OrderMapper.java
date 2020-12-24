package cc.lovesq.dao;

import cc.lovesq.model.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

  Integer insert(OrderDO orderDO);

  OrderDO findByQuery(OrderDO query);

}

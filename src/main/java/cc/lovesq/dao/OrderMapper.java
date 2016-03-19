package cc.lovesq.dao;

import cc.lovesq.model.OrderDO;
import cc.lovesq.query.OrderQuery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    Integer insert(OrderDO orderDO);

    OrderDO findByQuery(OrderQuery query);

}

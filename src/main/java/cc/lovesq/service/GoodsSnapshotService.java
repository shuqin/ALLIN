package cc.lovesq.service;

import cc.lovesq.goodssnapshot.GoodsServiceSnapshot;
import cc.lovesq.model.BookInfo;

import java.util.List;

public interface GoodsSnapshotService {

    boolean save(BookInfo goodsInfo);

    List<GoodsServiceSnapshot> query(String orderNo);
}

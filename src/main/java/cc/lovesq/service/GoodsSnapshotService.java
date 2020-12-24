package cc.lovesq.service;

import cc.lovesq.model.BookInfo;
import cc.lovesq.query.GoodsSnapshotQuery;
import cc.lovesq.result.goodsnapshot.GoodsSnapshot;

public interface GoodsSnapshotService {

    boolean save(BookInfo goodsInfo);

    GoodsSnapshot query(GoodsSnapshotQuery goodsSnapshotQuery);
}

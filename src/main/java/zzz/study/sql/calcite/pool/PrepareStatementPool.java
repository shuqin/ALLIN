package zzz.study.sql.calcite.pool;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import java.sql.PreparedStatement;

/**
 * TODO
 * Created by qinshu on 2021/7/17
 */
public class PrepareStatementPool {

    private static GenericKeyedObjectPool<String, PreparedStatement> objectPool;
    static {
        init();
    }

    public PrepareStatementPool() {

    }

    private static void init() {
        GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
        config.setMaxTotalPerKey(30);
        config.setBlockWhenExhausted(true);
        KeyedPooledObjectFactory factory = new PrepareStatementFactory();
        if (objectPool == null) {
            objectPool = new GenericKeyedObjectPool<>(factory, config);
        }
    }

    public static PreparedStatement borrow(String sql) {
        try {
            return objectPool.borrowObject(sql);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getCause());
        }

    }

    public static void returnObject(String sql, PreparedStatement p) {
        try {
            objectPool.returnObject(sql , p);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getCause());
        }

    }

}

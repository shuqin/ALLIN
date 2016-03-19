package zzz.study.sql.calcite.pool;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import zzz.study.sql.calcite.PreparedStatementWrapper;

import java.sql.PreparedStatement;

/**
 * TODO
 * Created by qinshu on 2021/7/17
 */
public class PrepareStatementFactory extends BaseKeyedPooledObjectFactory<String, PreparedStatement> {

    public PrepareStatementFactory() {
    }

    @Override
    public PreparedStatement create(String sql) throws Exception {
        return PreparedStatementWrapper.create(sql);
    }

    @Override
    public PooledObject<PreparedStatement> wrap(PreparedStatement preparedStatement) {
        return new DefaultPooledObject<>(preparedStatement);
    }

    @Override
    public void activateObject(String sql, PooledObject<PreparedStatement> p) throws Exception {
    }

    @Override
    public void passivateObject(String sql, PooledObject<PreparedStatement> p) throws Exception {
    }
}

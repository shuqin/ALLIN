package cc.lovesq.dao.util;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import cc.lovesq.constants.LogConstant;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.SessionScope;
import com.ibatis.sqlmap.engine.scope.StatementScope;

@SuppressWarnings("deprecation")
public class SqlMapClientTemplateSupport {

    private SqlMapClientTemplate sqlMapClientTemplate;
    
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMapClientTemplate;
    }

    public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
        this.sqlMapClientTemplate = sqlMapClientTemplate;
    }
    
    /**
     * 打印 SQL 语句日志
     * @param id  ibatis sqlStatement id
     * @param params sql params
     */
    public void logSQL(String id, Object params, String empId) {
		String sqlString = getSQL(id, params);
		LogConstant.info("(empId=" + empId + ", sql="+sqlString+")");
    }
    
    public void logSQL(String id, Object params, String empId, Logger logger) {
		String sqlString = getSQL(id, params);
		logger.info("(empId=" + empId + ", sql="+sqlString+")");
    }

    public String getSQL(String id,Object params){
    	
    	try {
    		SqlMapClientImpl sci = (SqlMapClientImpl)this.sqlMapClientTemplate.getSqlMapClient();  
        	MappedStatement ms = sci.getMappedStatement(id);  
        	          
        	Sql sql = ms.getSql();    
        	        
        	SessionScope sessionScope = new SessionScope();       
        	sessionScope.incrementRequestStackDepth();       
        	StatementScope statementScope = new StatementScope(sessionScope);       
        	ms.initRequest(statementScope);      
        	ms.getCacheKey(statementScope, params);  
        	
        	String sqlString = sql.getSql(statementScope, params);
        	Object[] sqlParam = sql.getParameterMap(statementScope, params).getParameterObjectValues(statementScope, params);  
        	
        	int sqlParamLen = sqlParam.length;
        	if (sqlParam != null && sqlParamLen > 0) {
        		for (int i=0; i < sqlParamLen; i++) {
        			if (sqlParam[i] instanceof Integer) {
        				sqlString = sqlString.replaceFirst("\\?", sqlParam[i].toString());
        			}
        			else if (sqlParam[i] instanceof String) {
        				sqlString = sqlString.replaceFirst("\\?", "'" + sqlParam[i].toString() + "'");
        			}
        		}
        	}
        	return sqlString;
    	} catch (Exception ex) {
    		LogConstant.error(ex.getMessage(), ex);
    		LogConstant.error("获取SQL 出错, id: " + id);
    	}
    	
    	return "[未能获取 SQL语句](id: " + id + ")";

    }
    
    public Object queryForObject(String statementName, Object parameterObject, String dataSource){
        return sqlMapClientTemplate.queryForObject(statementName, parameterObject);
    }
    
    @SuppressWarnings("rawtypes")
	public Map queryForMap(String statementName, Object parameterObject, String keyProp, String dataSource){
		return sqlMapClientTemplate.queryForMap(statementName, parameterObject, keyProp);
	}

    @SuppressWarnings({ "rawtypes" })
    public List queryForList(String statementName, String dataSource) throws DataAccessException {
        return queryForList(statementName, null, dataSource);
    }

    @SuppressWarnings("rawtypes")
    public List queryForList(final String statementName, final Object parameterObject, String dataSource){
        return sqlMapClientTemplate.execute(new SqlMapClientCallback<List>() {

            public List doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                return executor.queryForList(statementName, parameterObject);
            }
        });
    }

    /**
     * 批量插入
     */
    public Integer batchInsert(final String statementName, final Collection<? extends Object> parameterObjects,
                               String dataSource) throws DataAccessException {
        return batchAction(statementName, parameterObjects, new BatchAction() {

            public void doAction(final SqlMapExecutor executor, final String statementName, final Object parameterObject)
                                                                                                                         throws SQLException {
                executor.insert(statementName, parameterObject);
            }
        }, dataSource);
    }

    /**
     * 批量更新
     */
    public Integer batchUpdate(final String statementName, final Collection<? extends Object> parameterObjects,
                               String dataSource) throws DataAccessException {
        return batchAction(statementName, parameterObjects, new BatchAction() {

            public void doAction(final SqlMapExecutor executor, final String statementName, final Object parameterObject)
                                                                                                                         throws SQLException {
                executor.update(statementName, parameterObject);
            }
        }, dataSource);
    }

    /**
     * 批量删除
     */
    public Integer batchDelete(final String statementName, final Collection<? extends Object> parameterObjects,
                               String dataSource) throws DataAccessException {
        return batchAction(statementName, parameterObjects, new BatchAction() {

            public void doAction(final SqlMapExecutor executor, final String statementName, final Object parameterObject)
                                                                                                                         throws SQLException {
                executor.delete(statementName, parameterObject);
            }
        }, dataSource);
    }

    /**
     * 可以批量指定的动作
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Integer batchAction(final String statementName, final Collection<? extends Object> parameterObjects,
                                final BatchAction batchAction, String dataSource) throws DataAccessException {
        Object ret = sqlMapClientTemplate.execute(new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                if (parameterObjects == null || parameterObjects.isEmpty()) {
                    return new Integer(0);
                } else {
                    executor.startBatch();
                    for (Object parameterObject : parameterObjects) {
                        batchAction.doAction(executor, statementName, parameterObject);
                    }
                    return new Integer(executor.executeBatch());
                }
            }
        });

        if (ret instanceof Integer) {
            return (Integer) ret;
        } else {
            return new Integer(0);
        }
    }

    public int delete(String statementName, String dataSource) throws DataAccessException {
        return delete(statementName, null, dataSource);
    }

    public int delete(String statementName, Object parameterObject, String dataSource) throws DataAccessException {
        return sqlMapClientTemplate.delete(statementName, parameterObject);
    }

    public Object insert(String statementName, String dataSource) throws DataAccessException {
        return insert(statementName, null, dataSource);
    }

    public Object insert(String statementName, Object parameterObject, String dataSource) throws DataAccessException {
        if (dataSource == null) {
        	dataSource = "appDataSource";
        }
        return sqlMapClientTemplate.insert(statementName, parameterObject);
    }

    public int update(String statementName, String dataSource) throws DataAccessException {
        return update(statementName, null, dataSource);
    }

    public int update(String statementName, Object parameterObject, String dataSource) throws DataAccessException {
        return sqlMapClientTemplate.update(statementName, parameterObject);
    }
}

/**
 * 可以批量执行的动作接口
 */
interface BatchAction {

    public void doAction(final SqlMapExecutor executor, final String statementName, final Object parameterObject)
                                                                                                                 throws SQLException;
}


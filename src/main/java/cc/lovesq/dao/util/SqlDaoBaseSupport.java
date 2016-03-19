package cc.lovesq.dao.util;


public abstract class SqlDaoBaseSupport {

    private SqlMapClientTemplateSupport sqlMapClientTemplateSupport;

	public SqlMapClientTemplateSupport getSqlMapClientTemplateSupport() {
		return sqlMapClientTemplateSupport;
	}

	public void setSqlMapClientTemplateSupport(
			SqlMapClientTemplateSupport sqlMapClientTemplateSupport) {
		this.sqlMapClientTemplateSupport = sqlMapClientTemplateSupport;
	}

}


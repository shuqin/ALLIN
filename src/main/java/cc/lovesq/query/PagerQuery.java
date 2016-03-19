package cc.lovesq.query;

import cc.lovesq.pojo.BaseObject;

/**
 * @Described：所有带分页的Pojo的基类  实现序列化
 * @author YHJ create at 2014年3月3日 下午2:09:42
 * @ClassNmae com.aliyun.houyi.compass.v2.core.pojo.BaseObject
 */
public class PagerQuery extends BaseObject {
	
	/**
	 * 强烈建议采用静态序列化
	 * @FieldInfo BaseLimitObject.java：long
	 * @Author YHJ create at 2014年3月4日 下午2:52:27
	 */
	private static final long serialVersionUID = 3567389263772243142L;
	/** 开始的记录行 **/
	private Integer firstRow = 0;
	/** 默认一页20行 **/
	private Integer pageSize = 20;
	/** 当前页 **/
	private int pageNum = 1;
	
	public void setFirstRow(Integer firstRow) {
		this.firstRow = firstRow;
	}
	public Integer getFirstRow() {
		return firstRow;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageNum() {
		return pageNum;
	}


}

package cc.lovesq.controller;

import cc.lovesq.query.PagerQuery;


/**
 * @Described：基础控制层
 * @author YHJ create at 2014年3月4日 上午11:08:08
 * @ClassNmae com.aliyun.houyi.compass.v2.core.controller.BaseController
 */
public class BaseController {
	
	protected int totalCounts;//总记录条数
	protected int pageSize;//每页大小
	protected int totalPages;//总页数
	protected int pageNum; //当前的页

	protected boolean showFirst; //是否显示首页
	protected boolean showLast;  //是否显示尾页
	protected boolean showNext;  //是否显示下一页
	protected boolean showPrevious;  //是否显示上一页
	

	/**
	 * 初始化分页信息
	 * @param object
	 * @param totalCounts
	 * @Author YHJ create at 2014年3月4日 上午11:08:59
	 */
	protected void initPages(PagerQuery pagerQuery,int totalCounts){
		this.pageSize = pagerQuery.getPageSize();
		this.pageNum = pagerQuery.getPageNum();
		this.totalCounts = totalCounts;
		totalPages = (totalCounts%pageSize == 0) ? totalCounts/pageSize : (totalCounts/pageSize + 1);
		if(totalPages > 0){ //当totalPages <=0时 表明没有任何记录 所有上一页 下一页  首页 末页标签取默认值 不显示
			pageNum = (pageNum<=0)?1:pageNum;
			pageNum = (pageNum>totalPages)?totalPages:pageNum;
			showFirst =(pageNum == 1)?false:true;
			showLast =(pageNum == totalPages)?false:true;
			showPrevious = (pageNum == 1)?false:true;
			showNext = (pageNum == totalPages)?false:true;
			pagerQuery.setFirstRow(pageSize*(pageNum-1));
		}else{
			pageNum = 0;
		}
			
	}


	public int getTotalCounts() {
		return totalCounts;
	}


	public void setTotalCounts(int totalCounts) {
		this.totalCounts = totalCounts;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getTotalPages() {
		return totalPages;
	}


	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public boolean isShowFirst() {
		return showFirst;
	}


	public void setShowFirst(boolean showFirst) {
		this.showFirst = showFirst;
	}


	public boolean isShowLast() {
		return showLast;
	}


	public void setShowLast(boolean showLast) {
		this.showLast = showLast;
	}


	public boolean isShowNext() {
		return showNext;
	}


	public void setShowNext(boolean showNext) {
		this.showNext = showNext;
	}

	public boolean isShowPrevious() {
		return showPrevious;
	}

	public void setShowPrevious(boolean showPrevious) {
		this.showPrevious = showPrevious;
	}

}


package cc.lovesq.result;

import java.util.List;

import cc.lovesq.controller.BaseController;
/**
 * @Described：JsonResult for pagination
 * @author YHJ create at 2014年3月7日 下午3:52:55
 */
public class PagerJsonResult extends JsonResult {

	private static final long serialVersionUID = -6510732378035815288L;
	protected int pageSize;//每页大小
	protected int totalPages;//总页数
	protected int pageNum; //当前的页

	protected boolean showFirst; //是否显示首页
	protected boolean showLast;  //是否显示尾页
	protected boolean showNext;  //是否显示下一页
	protected boolean showPrevious;  //是否显示上一页

	public PagerJsonResult(BaseController baseController, List<?> list) {
		super(list);
		setTotal(baseController.getTotalCounts());
		setPageNum(baseController.getPageNum());
		setPageSize(baseController.getPageSize());
		setTotalPages(baseController.getTotalPages());
		setShowFirst(baseController.isShowFirst());
		setShowLast(baseController.isShowLast());
		setShowNext(baseController.isShowNext());
		setShowPrevious(baseController.isShowPrevious());
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


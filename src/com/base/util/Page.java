
package com.base.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页辅助类
 * 
 * @author huangw
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public class Page<T> {

	/** 总记录数 */
	private int totalCount = 0;

	/** 当前页 */
	private int currPageNo = 1;

	/** 每页记录数 */
	private int pageSize;

	/** 总页数 */
	private int totalPageCount =1;

	/** 显示页码数 */
	private int paginationSize;
	
	protected boolean autoCount = true;

	/** 显示页码 */
	private List<Integer> pageNos = new ArrayList<Integer>(0);

	/** 当前页数据 */
	private List<T> data = new ArrayList<T>(0);

	public Page() {
		this.totalCount = 0;
		this.currPageNo = 1;
		this.pageSize = 10;
		this.totalPageCount = 1;
		this.paginationSize = 7;
	}

	public Page(int pageSize) {
		this.totalCount = 0;
		this.currPageNo = 1;
		this.pageSize = pageSize;
		this.totalPageCount = 1;
		this.paginationSize = 7;
	}

	public Page(int currPageNo, int pageSize) {
		this.currPageNo = currPageNo;
		this.pageSize = pageSize;
		this.totalCount = 0;
		this.totalPageCount = 1;
		this.paginationSize = 7;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getStartIndex(){
		return ((currPageNo - 1) * pageSize);
	}
	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirst() {
		return ((currPageNo - 1) * pageSize) + 1;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrPageNo() {
		if (currPageNo < 1) {
			currPageNo = 1;
		}
		return currPageNo;
	}

	public void setCurrPageNo(int currPageNo) {
		if (currPageNo < 1) {
			currPageNo = 1;
		}
		this.currPageNo = currPageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPageCount() {
		if (this.getTotalCount() % this.getPageSize() == 0)
			totalPageCount = this.getTotalCount() / this.getPageSize()== 0?1:this.getTotalCount() / this.getPageSize();
		else
			totalPageCount = this.getTotalCount() / this.getPageSize() + 1;
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public List<Integer> getPageNos() {
		int begin = Math.max(1, currPageNo - paginationSize / 2);
		if((begin+paginationSize)>getTotalPageCount()){
			begin = getTotalPageCount() -paginationSize+1 < 1? 1: getTotalPageCount() -paginationSize+1 ;
		}
		int end = Math.min(getTotalPageCount(), begin + (paginationSize - 1));
		for (int i = begin; i <= end; i++) {
			pageNos.add(i);
		}
		return pageNos;
	}

	/**
	 * @param pageNos the pageNos to set
	 */
	public void setPageNos(List<Integer> pageNos) {
		this.pageNos = pageNos;
	}


	
	public List<T> getData() {
		return data;
	}

	
	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPaginationSize() {
		return paginationSize;
	}

	public void setPaginationSize(int paginationSize) {
		this.paginationSize = paginationSize;
	}

	
	public boolean isAutoCount() {
		return autoCount;
	}

	
	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}
	
	
}

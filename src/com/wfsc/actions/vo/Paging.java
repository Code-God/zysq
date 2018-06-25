package com.wfsc.actions.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ajax分页对象
 * 
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public class Paging implements Serializable {

	private static final long serialVersionUID = -1098367557407040050L;

	private int total = 0;

	private int page = 1;

	private int limit = 5;

	private int totalPage = 0;

	private List datas = new ArrayList();

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas = datas;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}

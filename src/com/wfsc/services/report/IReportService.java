package com.wfsc.services.report;

import java.util.Date;

import model.bo.auth.Org;


/**
 * 报表
 * @author aa
 *
 */
public interface IReportService {
	
	/**
	 * 生成用户注册数据报表
	 * @param reportType 报表类型 1周报表 2月报表 3年报表
	 * @param year 年份
	 * @param org 
	 * @return 报表数据
	 */
	public String generateUserReport(int reportType, int year, Org org);
	
	/**
	 * 生成指定月份商品Top10报表
	 * @param month 月份 1~12
	 * @return
	 */
	public String generateTopProductSale(int reportType, int year, int month);
	
	/**
	 * 生成一级分类商品满意度报表
	 * @return
	 */
	public String generateCategorySatisfaction();
	/**
	 * 生成财务报表
	 * @param sdate
	 * @param edate
	 * @param orgCode 
	 * @return
	 */
	public String generateOrderNumReport(Date sdate,Date edate, String orgCode);
	public String generateOrderMoneyReport(Date sdate,Date edate, String orgCode);
	
}

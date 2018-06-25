package com.wfsc.actions.report;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.wfsc.services.report.IReportService;
import com.wfsc.util.DateUtil;

@Controller("ReportAction")
@Scope("prototype")
public class ReportAction extends DispatchPagerAction {

	private static final long serialVersionUID = 1782296129610785080L;
	
	@Autowired
	private IReportService reportService;
	
	public String generateUserReport(){
		String typeStr = request.getParameter("type");
		String yearStr = request.getParameter("year");
		int year = 0;
		int type = 0;
		
		//默认生成年报表
		if(StringUtils.isEmpty(typeStr)){
			type = 3;
		}else{
			type = Integer.parseInt(typeStr);
		}
		
		if(type != 3){
			year = Integer.parseInt(yearStr);
		}
		
		String xmlData = reportService.generateUserReport(type, year, this.getCurrentOrgAdmin());
		request.setAttribute("xmlData", xmlData);
		request.setAttribute("year", year);
		request.setAttribute("type", type);
		return "userReport";
	}
	
	public String productSales(){
		String typeStr = request.getParameter("type");
		String yearStr = request.getParameter("year");
		String monthStr = request.getParameter("month");

		//默认展示当月的
		int type = 2;
		int year = DateUtil.getYear(new Date());
		int month = DateUtil.getMonth(new Date());
		
		if(StringUtils.isNotEmpty(typeStr)){
			type = Integer.parseInt(typeStr);
			year = Integer.parseInt(yearStr);
			month = Integer.parseInt(monthStr);
		}
		
		String xmlData = reportService.generateTopProductSale(type, year, month);
		request.setAttribute("xmlData", xmlData);
		request.setAttribute("year", year);
		request.setAttribute("type", type);
		request.setAttribute("month", month);
		
		return "saleVolume";
	}
	public String financeReport() throws ParseException{
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Date sdate = null;
		Date edate = null;
		if(StringUtils.isBlank(startTime)){
			Calendar c = Calendar.getInstance();
			sdate = DateUtil.getFirstDayOfYear(c.get(Calendar.YEAR));
		}else{
			String[] s = startTime.split("-");
			sdate = DateUtil.getFirstDayOfMonth(Integer.valueOf(s[0]).intValue(), Integer.valueOf(s[1]).intValue());
		}
		if(StringUtils.isBlank(endTime)){
			Calendar c = Calendar.getInstance();
			edate = DateUtil.getLastDayOfYear(c.get(Calendar.YEAR));
		}else{
			String[] e = endTime.split("-");
			edate = DateUtil.getLastDayOfMonth(Integer.valueOf(e[0]).intValue(), Integer.valueOf(e[1]).intValue());
		}
		String numXmlData = reportService.generateOrderNumReport(sdate, edate, this.getCurrentOrgCode());
		String moneyXmlData = reportService.generateOrderMoneyReport(sdate, edate, this.getCurrentOrgCode());
		request.setAttribute("numXmlData", numXmlData);
		request.setAttribute("moneyXmlData", moneyXmlData);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		return "financeReport";
	}
	
	public String satisfactionReport(){
		String xmlData = reportService.generateCategorySatisfaction();
		request.setAttribute("xmlData", xmlData);
		return "satisfactionReport";
	}

}

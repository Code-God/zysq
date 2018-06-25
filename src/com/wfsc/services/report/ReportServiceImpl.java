package com.wfsc.services.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.bo.auth.Org;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfsc.common.bo.product.Products;
import com.wfsc.daos.comment.CommentsDao;
import com.wfsc.daos.orders.OrdersDao;
import com.wfsc.daos.product.ProductCatDao;
import com.wfsc.daos.product.ProductSoldDao;
import com.wfsc.daos.product.ProductsDao;
import com.wfsc.daos.report.UserRegisterReportDao;
import com.wfsc.util.DateUtil;

@Service("reportService")
public class ReportServiceImpl implements IReportService {
	
	@Autowired
	private UserRegisterReportDao userRegisterReportDao;
	
	@Autowired
	private ProductSoldDao productSoldDao;
	
	@Autowired
	private ProductsDao productDao;
	
	@Autowired
	private ProductCatDao productCatDao;
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Autowired
	private CommentsDao commentsDao;

	@Override
	public String generateCategorySatisfaction() {
		String xmlData = "";
		Map<String,String> catMap = productCatDao.getTopPrdCatMap();
		Map<String, Integer> resultAll = commentsDao.getCommentsForReport(null);
		Map<String, Integer> resultVeryGood = commentsDao.getCommentsForReport(5);
		Map<String, Integer> resultGood = commentsDao.getCommentsForReport(4);
		Map<String, Integer> resultGeneral = commentsDao.getCommentsForReport(3);
		Map<String, Integer> resultBad = commentsDao.getCommentsForReport(2);
		Map<String, Integer> resultVeryBad = commentsDao.getCommentsForReport(1);
		Map<String,Map<String, Float>> map = new LinkedHashMap<String,Map<String, Float>>();
		if(MapUtils.isNotEmpty(resultAll)){
			Map<String, Float> dataVeryGood = new LinkedHashMap<String, Float>();
			Map<String, Float> dataGood = new LinkedHashMap<String, Float>();
			Map<String, Float> dataGeneral = new LinkedHashMap<String, Float>();
			Map<String, Float> dataBad = new LinkedHashMap<String, Float>();
			Map<String, Float> dataVeryBad = new LinkedHashMap<String, Float>();
			for(Map.Entry<String, Integer> entry : resultAll.entrySet()){
				String key = entry.getKey();
				Integer valueAll = entry.getValue();
				Integer valueVeryGood = resultVeryGood.get(key);
				Float rate = 0.0F;
				if(valueVeryGood!=null){
					rate = valueVeryGood.floatValue()/valueAll.floatValue()*100;
				}
				dataVeryGood.put(key, rate);
			}
			for(Map.Entry<String, Integer> entry : resultAll.entrySet()){
				String key = entry.getKey();
				Integer valueAll = entry.getValue();
				Integer valueGood = resultGood.get(key);
				Float rate = 0.0F;
				if(valueGood!=null){
					rate = valueGood.floatValue()/valueAll.floatValue()*100;
				}
				dataGood.put(key, rate);
			}
			for(Map.Entry<String, Integer> entry : resultAll.entrySet()){
				String key = entry.getKey();
				Integer valueAll = entry.getValue();
				Integer valueGeneral = resultGeneral.get(key);
				Float rate = 0.0F;
				if(valueGeneral!=null){
					rate = valueGeneral.floatValue()/valueAll.floatValue()*100;
				}
				dataGeneral.put(key, rate);
			}
			for(Map.Entry<String, Integer> entry : resultAll.entrySet()){
				String key = entry.getKey();
				Integer valueAll = entry.getValue();
				Integer valueBad = resultBad.get(key);
				Float rate = 0.0F;
				if(valueBad!=null){
					rate = valueBad.floatValue()/valueAll.floatValue()*100;
				}
				dataBad.put(key, rate);
			}
			for(Map.Entry<String, Integer> entry : resultAll.entrySet()){
				String key = entry.getKey();
				Integer valueAll = entry.getValue();
				Integer valueVeryBad = resultVeryBad.get(key);
				Float rate = 0.0F;
				if(valueVeryBad!=null){
					rate = valueVeryBad.floatValue()/valueAll.floatValue()*100;
				}
				dataVeryBad.put(key, rate);
			}
			map.put("非常满意", dataVeryGood);
			map.put("满意", dataGood);
			map.put("一般", dataGeneral);
			map.put("不满意", dataBad);
			map.put("非常不满意", dataVeryBad);
		}
		
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("caption", "满意度统计报表");
		attrs.put("xAxisName", "商品分类");
		attrs.put("yAxisName", "满意度");
		attrs.put("numberSuffix", "%");
		attrs.put("decimalPrecision", "2");
		attrs.put("yAxisMaxValue", "100");
		
		String header = buildHeader(attrs);
		String body = buildMuiltBody(map,catMap);
		String footer = buildFooter();
		xmlData = header + body + footer;
		return xmlData;
	}
	
	public String generateOrderNumReport(Date sdate,Date edate, String orgCode) {
		String xmlData = "";
		Map<String,Object> chartData = ordersDao.getOrdersNumForReport(sdate, edate, orgCode);
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("caption", "订单数量统计报表");
		attrs.put("xAxisName", "月份");
		attrs.put("yAxisName", "订单数量");
		String header = buildHeader(attrs);
		String body = buildSingleBody(chartData, null);
		String footer = buildFooter();
		xmlData = header + body + footer;
		return xmlData;
	}
	public String generateOrderMoneyReport(Date sdate,Date edate, String orgCode) {
		String xmlData = "";
		Map<String,Object> chartData = ordersDao.getOrdersMoneyForReport(sdate, edate, orgCode);
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("caption", "订单金额统计报表");
		attrs.put("xAxisName", "月份");
		attrs.put("yAxisName", "订单金额");
		String header = buildHeader(attrs);
		String body = buildSingleBody(chartData, null);
		String footer = buildFooter();
		xmlData = header + body + footer;
		return xmlData;
	}

	@Override
	public String generateTopProductSale(int reportType, int year, int month) {
		Date startTime = null;
		Date endTime = null;
		if(reportType == 2){
			startTime = DateUtil.getFirstDayOfMonth(year, month);
			endTime = DateUtil.getLastDayOfMonth(year, month);
		}else if(reportType == 1){
			startTime = DateUtil.getFirstDayOfYear(year);
			endTime = DateUtil.getLastDayOfYear(year);
		}
		//根据开始时间结束时间获取10条数据
		Map<String, Integer> result = productSoldDao.getTopProductSale(startTime, endTime, 10);
		Map<String, String> attrs = new HashMap<String, String>();
		String caption = year + "年" + month + "月份商品销量TOP 10";
		if(reportType == 1){
			caption = year + "年商品销量TOP 10";
		}
		attrs.put("caption", caption);
		attrs.put("xAxisName", "商品名称");
		attrs.put("yAxisName", "销售数量");
		String header = buildHeader(attrs);
		Map<String, Object> chartData = new LinkedHashMap<String, Object>();
		if(MapUtils.isNotEmpty(result)){
			for(Map.Entry<String, Integer> entry : result.entrySet()){
				String key = entry.getKey();
				
				//检测商品是否被删除，如果被删除，则直接放原有商品的编码
				Products product = productDao.findByCode(key);
				Integer value = entry.getValue();
				if(product != null){
					chartData.put(product.getName(), value.intValue());
				}else{
					chartData.put(key, value.intValue());
				}
			}
		}
		String body = buildSingleBody(chartData, null);
		String footer = buildFooter();
		String xmlData = header + body + footer;
		return xmlData;
	}

	@Override
	public String generateUserReport(int reportType, int year, Org org) {
		String xmlData = "";
		//年报表
		if(reportType == 3){
			Map<Integer, Integer> result = userRegisterReportDao.getYearData(org);
			Map<String, Object> chartData = new LinkedHashMap<String, Object>();
			if(MapUtils.isNotEmpty(result)){
				for(Map.Entry<Integer, Integer> entry : result.entrySet()){
					Integer key = entry.getKey();
					Integer value = entry.getValue();
					chartData.put(key.intValue() + "年", value);
				}
				Map<String, String> attrs = new HashMap<String, String>();
				attrs.put("caption", "会员年注册数量统计报表");
				attrs.put("xAxisName", "年份");
				attrs.put("yAxisName", "注册数量");
				String header = buildHeader(attrs);
				String body = buildSingleBody(chartData, null);
				String footer = buildFooter();
				xmlData = header + body + footer;
			}
		}else if(reportType == 2){
			//月报表数据
			Map<Integer, Integer> result = userRegisterReportDao.getMonthData(year, org);
			Map<String, Object> chartData = new LinkedHashMap<String, Object>();
			for(int i=1; i<=12; i++){
				if(MapUtils.isEmpty(result))
					result = new TreeMap<Integer, Integer>();
				
				if(!result.keySet().contains(i)){
					result.put(i, 0);
				}
			}
			if(MapUtils.isNotEmpty(result)){
				for(Map.Entry<Integer, Integer> entry : result.entrySet()){
					Integer key = entry.getKey();
					Integer value = entry.getValue();
					chartData.put(key.intValue() + "月份", value);
				}
				Map<String, String> attrs = new HashMap<String, String>();
				attrs.put("caption", year + "年会员月注册数量统计报表");
				attrs.put("xAxisName", "月份");
				attrs.put("yAxisName", "注册数量");
				String header = buildHeader(attrs);
				String body = buildSingleBody(chartData, null);
				String footer = buildFooter();
				xmlData = header + body + footer;
			}
		}else if(reportType == 1){
			//周报表数据
			Map<Integer, Integer> result = userRegisterReportDao.getWeekData(year, org);
			Map<String, Object> chartData = new LinkedHashMap<String, Object>();
			int weeks = DateUtil.getTotalWeekOfYear(year);
			for(int i=1; i<=weeks; i++){
				if(MapUtils.isEmpty(result))
					result = new TreeMap<Integer, Integer>();
				
				if(!result.keySet().contains(i)){
					result.put(i, 0);
				}
			}
			if(MapUtils.isNotEmpty(result)){
				for(Map.Entry<Integer, Integer> entry : result.entrySet()){
					Integer key = entry.getKey();
					Integer value = entry.getValue();
					chartData.put("第" + key.intValue() + "周", value);
				}
				Map<String, String> attrs = new HashMap<String, String>();
				attrs.put("caption", year + "年会员周注册数量统计报表");
				attrs.put("xAxisName", "周");
				attrs.put("yAxisName", "注册数量");
				attrs.put("labelStep ", "3");
				attrs.put("lineThickness ", "1");
				attrs.put("lineColor ", "28B779");
				attrs.put("labelDisplay ", "WRAP");
				String header = buildHeader(attrs);
				String body = buildSingleBody(chartData, null);
				String footer = buildFooter();
				xmlData = header + body + footer;
			}
		}
		
		return xmlData;
	}
	
	private String buildSingleBody(Map<String, Object> data, Map<String, String> colors){
		if(MapUtils.isEmpty(data)){
			return "";
		}
		
		if(MapUtils.isNotEmpty(colors) && data.size() != colors.size()){
			return "";
		}
		
		List<String> categories = new ArrayList<String>();
		categories.addAll(data.keySet());
		
		StringBuilder body = new StringBuilder();
		for (String category : categories) {
			String color = null;
			if (MapUtils.isNotEmpty(colors)) {
				color = colors.get(category);
			}
			body.append("<set label='" + category + "' value='" + data.get(category) + "' ");
			if (color != null)
				body.append("color='" + color + "'");
			body.append("/>");
		}
		return body.toString();
	}
	private String buildMuiltBody(Map<String,Map<String, Float>> data,Map<String,String> catMap){
		if(MapUtils.isEmpty(data)){
			return "";
		}
		StringBuilder body = new StringBuilder("<categories>");
		for(Map.Entry<String, String> catentry : catMap.entrySet()){
			String value = catentry.getValue();
			body.append("<category name='"+value+"'/>");
		}
		body.append("</categories>");
		for(Map.Entry<String,Map<String, Float>> entry : data.entrySet()){
			String key = entry.getKey();
			Map<String, Float> value = entry.getValue();
			body.append("<dataset seriesName='"+key+"'>");
			for(Map.Entry<String, String> catentry : catMap.entrySet()){
				Float val = 0.0F;
				String inkey = catentry.getKey();
				if(value.get(inkey)!=null){
					val = value.get(inkey);
				}
				body.append("<set value='"+val+"' />");
			}
			body.append("</dataset>");
		}
		return body.toString();
	}
	
	private String buildHeader(Map<String, String> attrs){
		Map<String, String> headerAttrs = new HashMap<String, String>();
		headerAttrs.put("bgAlpha", "100");
		headerAttrs.put("bgColor", "F9F9F9");
		headerAttrs.put("canvasBgColor", "F9F9F9");
		headerAttrs.put("canvasBgAlpha", "100");
		headerAttrs.put("plotFillRatio", "100");
		
		if(MapUtils.isNotEmpty(attrs)){
			headerAttrs.putAll(attrs);
		}
		
		StringBuilder header = new StringBuilder("<graph ");
		for (Map.Entry<String, String> entry : headerAttrs.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
				continue;
			}
			header.append(key + "='" + value + "' ");
		}
		header.append(">");
		return header.toString();
	}
	
	private String buildFooter(){
		return "</graph>";
	}

}

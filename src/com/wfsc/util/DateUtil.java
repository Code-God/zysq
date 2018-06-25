/*
 * Copyright 2006 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Ocean
 * Filename : DateUtil.java
 * Create   : rick@tekview.com, Nov 18, 2006/5:51:27 PM
 */
package com.wfsc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 处理日期相关的操作
 * 
 * @author Rick Chen
 * 
 */
public class DateUtil {

	private static SimpleDateFormat LONG_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat MID_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private static SimpleDateFormat SHORT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat SHORT_CN_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private static SimpleDateFormat DATE_MARK_SHORT = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat DATE_MARK_LONG = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 3天的毫秒数
	 */
	public static long THREE_DAY_MILLSEC = 3 * 24 * 3600 * 1000;

	/**
	 * 1天的毫秒数
	 */
	public static long ONE_DAY_MILLSEC = 24 * 3600 * 1000;

	/**
	 * 1小时的毫秒数
	 */
	public static long ONE_HOUR_MILLSEC = 3600 * 1000;

	/**
	 * 3小时的毫秒数
	 */
	public static long THREE_HOURS_MILLSEC = 3 * 3600 * 1000;

	/**
	 * 12小时的毫秒数
	 */
	public static long TWELVE_HOURS_MILLSEC = 12 * 3600 * 1000;
	
	public static Date getDate(String date){
		if(StringUtils.isEmpty(date)){
			return new Date();
		}
		try {
			return LONG_FORMAT.parse(date);
		} catch (ParseException e) {
		}
		return new Date();
	}
	
	public static Date getDateFromShort(String date){
		if(StringUtils.isEmpty(date)){
			return new Date();
		}
		try {
			return SHORT_FORMAT.parse(date);
		} catch (ParseException e) {
		}
		return new Date();
	}

	/**
	 * 返回当前日期完整字符串，格式为: yyyy-MM-dd hh:mm:ss
	 * 
	 * @return
	 */
	public static String getLongCurrentDate() {
		return new String(LONG_FORMAT.format(new Date()));
	}

	/**
	 * 给定日期(Date)，返回格式为: yyyy-MM-dd hh:mm:ss的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getLongDate(Date date) {
		if (null == date)
			return "";
		return new String(LONG_FORMAT.format(date));
	}

	/**
	 * 给定日期(long:ms)，返回格式为: yyyy-MM-dd hh:mm:ss的字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String getLongDate(long value) {
		return new String(LONG_FORMAT.format(new Date(value)));
	}
	
	/**
	 * 给定日期(long:ms)，返回格式为: yyyy-MM-dd hh:mm的字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String getMidFormatDate(long value) {
		return new String(MID_FORMAT.format(new Date(value)));
	}
	/**
	 * 返回当前日期简写字符串，格式为: yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getShortCurrentDate() {
		return new String(SHORT_FORMAT.format(new Date()));
	}
	
	/**
	 * 返回当前日期简写字符串，格式为: yyyy年MM月dd日
	 * 
	 * @return
	 */
	public static String getShortCNCurrentDate() {
		return new String(SHORT_CN_FORMAT.format(new Date()));
	}
	

	/**
	 * 给定日期(Date)，返回当前日期简写字符串，格式为: yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String getShortDate(Date date) {
		if (null == date)
			return getShortCurrentDate();
		return new String(SHORT_FORMAT.format(date));
	}

	/**
	 * 给定日期(long:ms)，返回当前日期简写字符串，格式为: yyyy-MM-dd
	 * 
	 * @param value
	 * @return
	 */
	public static String getShortDate(long value) {
		return new String(SHORT_FORMAT.format(new Date(value)));
	}

	/**
	 * 返回当前日期中等复杂程度的字符串，格式为: yyyy-MM-dd hh:mm
	 * 
	 * @return
	 */
	public static String getMidCurrentDate() {
		return new String(MID_FORMAT.format(new Date()));
	}
	
	/**
	 * 返回传入日期中等复杂程度的字符串，格式为: yyyy-MM-dd hh:mm
	 * 
	 * @return
	 */
	public static String getMidDateStr(long time) {
		return new String(MID_FORMAT.format(new Date(time)));
	}

	/**
	 * 给定日期(Date)，返回当前日期中等复杂程度的字符串，格式为: yyyy-MM-dd hh:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String getMidDate(Date date) {
		if (null == date)
			return getMidCurrentDate();
		return new String(MID_FORMAT.format(date));
	}

	/**
	 * 给定日期(long:ms)，返回当前日期中等复杂程度的字符串，格式为: yyyy-MM-dd hh:mm
	 * 
	 * @param value
	 * @return
	 */
	public static String getMidDate(long value) {
		return new String(MID_FORMAT.format(new Date(value)));
	}

	/**
	 * 返回时间长度，简短形式，格式为: *d*h*m
	 * 
	 * @param ms
	 * @return
	 */
	public static String getShortDisplayStrOfTime(long ms) {
		int oneSecond = 1000;
		int oneMinute = oneSecond * 60;
		int oneHour = oneMinute * 60;
		int oneDay = oneHour * 24;
		long day = ms / oneDay;
		long hour = (ms - day * oneDay) / oneHour;
		long minute = (ms - day * oneDay - hour * oneHour) / oneMinute;
		String strDay = day < 10 ? "" + day : "" + day;
		String strHour = hour < 10 ? "" + hour : "" + hour;
		String strMinute = minute < 10 ? "" + minute : "" + minute;
		//http://192.168.0.3:8080/browse/ISM-3036
		if(ms > 0 && minute == 0){
			strMinute = "1";
			minute = 1;
		}
		StringBuffer timeBuffer = new StringBuffer();
		if (day > 0) {
			timeBuffer.append(strDay);
			timeBuffer.append("天");
		}
		if (hour > 0) {
			timeBuffer.append(strHour);
			timeBuffer.append("小时");
		}
		if (minute > 0) {
			timeBuffer.append(strMinute);
			timeBuffer.append("分钟");
		}
		return timeBuffer.toString();
	}

	/**
	 * // 将毫秒数换算成x天x时x分x秒x毫秒
	 * 
	 * @param ms
	 * @return
	 */
	public static String getWellTimeFromMilliSecond(long ms) {
		int oneSecond = 1000;
		int oneMinute = oneSecond * 60;
		int oneHour = oneMinute * 60;
		int oneDay = oneHour * 24;
		long day = ms / oneDay;
		long hour = (ms - day * oneDay) / oneHour;
		long minute = (ms - day * oneDay - hour * oneHour) / oneMinute;
		long second = (ms - day * oneDay - hour * oneHour - minute * oneMinute) / oneSecond;
		long milliSecond = ms - day * oneDay - hour * oneHour - minute * oneMinute - second * oneSecond;
		String strDay = day < 10 ? "" + day : "" + day;
		String strHour = hour < 10 ? "" + hour : "" + hour;
		String strMinute = minute < 10 ? "" + minute : "" + minute;
		String strSecond = second < 10 ? "" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "" + strMilliSecond : "" + strMilliSecond;
		StringBuffer timeBuffer = new StringBuffer();
		timeBuffer.append(strDay);
		timeBuffer.append("天 ");
		timeBuffer.append(strHour);
		timeBuffer.append("小时 ");
		timeBuffer.append(strMinute);
		timeBuffer.append("分钟 ");
		timeBuffer.append(strSecond);
		timeBuffer.append("秒 ");
		timeBuffer.append(strMilliSecond);
		timeBuffer.append("毫秒 ");
		return timeBuffer.toString();
	}
	
	/**
	 * 
	 *把String格式为yyyy-MM-dd HH:mm的日期转化为yyyy-MM-dd HH:mm:ss的日期型
	 *
	 * @param dateStr
	 * @return
	 */
	public static Long getLongDateByMidformat(String dateStr){
		Date d;
		try {
			d =  MID_FORMAT.parse(dateStr);
			return Long.valueOf(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date().getTime();
	}
	
	public static Long getLongDate(String dateStr){
		Date d;
		try {
			d = SHORT_FORMAT.parse(dateStr);
			return Long.valueOf(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date().getTime();
	}
	/**
	 * 把String转化为yyyy-MM-dd HH:mm:ss
	 * @param dateStr
	 * @return
	 */
	public static Long getLongDateFormString(String dateStr){
		Date d;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			d = LONG_FORMAT.parse(dateStr);
			return Long.valueOf(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date().getTime();
	}
	/**
	 * 得到日期所在的周区间，例如将2008-04-09归入2008-04-07_2008-04-13这个周区间
	 * 
	 * @param dateString 时间字符串
	 * @return
	 */
	public static String getWeekFromDate(String dateString) {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = format.parse(dateString);
			Date mondy = new Date();
			Date sundy = new Date();
			int day = date.getDay();
			if (day == 0) {
				mondy.setTime(date.getTime() - 1000 * 60 * 60 * 24 * 6);
				sundy.setTime(date.getTime());
			} else {
				mondy.setTime(date.getTime() - 1000 * 60 * 60 * 24 * (day - 1));
				sundy.setTime(date.getTime() + 1000 * 60 * 60 * 24 * (7 - day));
			}
			sb.append(format.format(mondy));
			sb.append("_");
			sb.append(format.format(sundy));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/**
	 * 获得当前时间是星期几
	 * @param pTime - 可以传空
	 * @return
	 * @throws Exception
	 */
	public static int dayForWeek(String pTime) {
		Calendar c = Calendar.getInstance();
		if(StringUtils.isEmpty(pTime) || StringUtils.isBlank(pTime)){
			c.setTime(new Date());
		}else{
			try {
				c.setTime(SHORT_FORMAT.parse(pTime));
			} catch (ParseException e) {
				e.printStackTrace();
				return 0;
			}
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
	
	/**
	 *  
	 * @param ptime
	 * @return
	 */
	public static String getDayofWeek(String ptime){
		int i = dayForWeek(ptime);
		String result = "";
		switch (i) {
		case 1:
			result = "星期一";
			break;
		case 2:
			result = "星期二";
			break;
		case 3:
			result = "星期三";
			break;
		case 4:
			result = "星期四";
			break;
		case 5:
			result = "星期五";
			break;
		case 6:
			result = "星期六";
			break;
		case 7:
			result = "星期日";
			break;
		default:
			break;
		}
		return result;
	}


	/**
	 * // * 根据毫秒数得到秒数，用于在保存当前时间new Date().getTime()的时候保存整点的秒
	 * 
	 * @param ms
	 * @return
	 */
	public static long getSecondsFromMilliSecond(long ms) {
		long seconds = ms / 1000 * 1000;
		return seconds;
	}
	
	
	
	
	/**
	 * add by JONIM.XIA	
	 * @param dateString 日期字符串 yyyy-MM-dd
	 * @return 日期Long值
	 */
	public static Long convertStringDate2Long(String dateString) {
		Long datel = new Long(0);
		if (StringUtils.isNotEmpty(dateString)) {
			try {
				//SimpleDateFormat.getDateTimeInstance().
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(dateString);
				datel = date.getTime();
			} catch (Exception exp) {
			}
		}
		return datel;
	}
	
	/**
	 * add by JONIM.XIA	
	 * 日期长整型转换成字符型 yyyy-MM-dd
	 * @param dateLong 
	 * @return
	 */
	public static String convertLong2String(Long dateLong){
		String date = null;
		if(dateLong!=null){
			try {
				Date dt = new Date();
				dt.setTime(dateLong);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				date = sdf.format(dt);
			} catch (Exception exp) {
			}
		}
		return date;
	}
	
	/**
	 * add by JONIM.XIA	
	 * 日期长整型转换成字符型 
	 * @param dateLong yyyy-MM-dd HH:mm
	 * @return
	 */
	public static String convertLong2String(Long dateLong,String forMateType){
		String date = null;
		if(dateLong!=null){
			try {
				Date dt = new Date();
				dt.setTime(dateLong);
				if(forMateType==null)forMateType = "yyyy-MM-dd HH:mm";
				SimpleDateFormat sdf = new SimpleDateFormat(forMateType);
				date = sdf.format(dt);
			} catch (Exception exp) {
			}
		}
		return date;
	}
	
	/**
	 * add by JONIM.XIA	
	 * @param dateString 日期字符串
	 * @return 日期Long值
	 */
	public static Long convertStringDate2Long(String dateString,String forMateType) {
		Long datel = new Long(0);
		if (StringUtils.isNotEmpty(dateString)) {
			try {
				if(StringUtils.trimToNull(forMateType)==null)forMateType="yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat sdf = new SimpleDateFormat(forMateType);
				Date date = sdf.parse(dateString);
				datel = date.getTime();
			} catch (Exception exp) {
			}
		}
		return datel;
	}
	
	/**
	 * 指定时间所处的年份
	 * @param date
	 * @return
	 */
	public static int getYear(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * 指定时间所处的月份
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 指定时间所处的年的第几周
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 获取指定年份有多少周
	 * @param year
	 * @return
	 */
	public static int getTotalWeekOfYear(int year){
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.YEAR, year);
	    return c.getActualMaximum(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 指定月份第一天开始时间
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}
	
	/**
	 * 指定月份最后一天结束时间
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH,  cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}
	
	/**
	 * 指定年份的第一天开始时间
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfYear(int year){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}
	
	/**
	 * 指定年份的最后一天结束时间
	 * @param year
	 * @return
	 */
	public static Date getLastDayOfYear(int year){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH,  cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}
	
	/**
	 * 返回当前日期简写字符串，格式为: yyyyMMdd
	 * 
	 * @return
	 */
	public static String getShortDateMark() {
		return new String(DATE_MARK_SHORT.format(new Date()));
	}
	
	/**
	 * 返回当前日期简写字符串，格式为: yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getLongDateMark() {
		return new String(DATE_MARK_LONG.format(new Date()));
	}
	
	
	/**
	 * 返回指定日期简写字符串，格式为: yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getLongDateMark(Long time) {
		return new String(DATE_MARK_LONG.format(new Date(time)));
	}
	
	/**
	 * 返回当前日期简写字符串，格式为: yyyy年MM月dd日
	 * 
	 * @return
	 */
	public static String getShortCNDate(long time) {
		return new String(SHORT_CN_FORMAT.format(new Date(time)));
	}
	/**
	 * 获取某个月的所有日期列表
	 * @param year
	 * @param month
	 * @return
	 */
	public static List<String> getDayList(int year, int month){
		List<String> list = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);   
		cal.set(Calendar.MONTH, (month-1));//这里必须减1， 0-一月， 1-二月。。。。  
		int maxDay = cal.getActualMaximum(Calendar.DATE);
		for(int i=0; i< maxDay; i++){
			list.add(year + "-" + String.format("%02d", month) + "-" + String.format("%02d", (i+1)));
		}
		System.out.println(list);
		return list;
	}
	
	
	
	public static void main(String[] args){
//		System.out.println(getLastDayOfYear(2015));
//		
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR,2016);   
//		cal.set(Calendar.MONTH, 1);//2月   
//		int maxDate = cal.getActualMaximum(Calendar.DATE);
//		System.out.println(maxDate);
//		
//		String ss = String.format("%02d", 1);
//		System.out.print(ss);
		
//		getDayList(2016, 1);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 11);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		System.out.println(getLongDate(cal.getTime()));
		
	}
	
}

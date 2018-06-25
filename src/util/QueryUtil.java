package util;

import dao.QueryUtilImpl;

public class QueryUtil {
	/**
	 * add by jonim
	 * 
	 * @param hql
	 * @return
	 */
	public static String genOrderNumByString(String orderType) {
		return orderType + "-" + QueryUtilImpl.getInstance().getOrderNum(orderType);
	}

	/**
	 * add by jonim 给自定义表单专用
	 * 
	 * @param hql
	 * @return
	 */
	public static String genCreateFormByString(String orderType) {
		String num = orderType + "-" + QueryUtilImpl.getInstance().getOrderNum(orderType);
		if (num.indexOf("-") != -1) {
			num = num.replaceFirst("-", "_");
		}
		return num;
	}
}

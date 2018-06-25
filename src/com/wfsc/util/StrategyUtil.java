package com.wfsc.util;

import com.base.tools.Version;

/**
 * 网站整体策略UTIL ，这里的参数均来自配置文件
 *
 * @author jacky
 * @version 1.0
 * @since cupid 1.0
 */
public class StrategyUtil {
	/**
	 * 获取注册时送的金币数，可配置 
	 * @return
	 */
	public static int getRegGiftGold(){
		int gold = 20;
		String newProperty = Version.getInstance().getNewProperty("regGiftGold");
		if(newProperty != null){
			return Integer.valueOf(newProperty);
		}
		return gold;
	}
	/**
	 * 获取代理约见话费金币数，可配置 
	 * @return
	 */
	public static float getDatingFee() {
		int gold = 300;
		String newProperty = Version.getInstance().getNewProperty("datingFee");
		if(newProperty != null){
			return Integer.valueOf(newProperty);
		}
		return gold;
	}
	
	
	public static void main(String[] args){
		double s = 10.0d;
		//三天上涨，每天5%
		double d = s * Math.pow((1+0.05), 3); 
		System.out.println(d);
		//第四天跌7%
		double e = d * (1 - 0.05);
		System.out.println(e);
		
	}
	
}

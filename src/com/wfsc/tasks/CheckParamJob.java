/**
 * 
 */
package com.wfsc.tasks;

import java.util.Date;
import java.util.Map;

import model.vo.WxParamObj;

import org.apache.log4j.Logger;

import wx.cache.WxParamCache;
import wx.data.WxDataCommands;
import wx.data.WxDataHandler;

import com.base.job.SimpleTask;
import com.base.log.LogUtil;

/**
 * 
 * 从缓存里检查wx参数的时间戳，提前5分钟进行刷新，重新获取，更新时间戳
 * @author jacky
 * @version 1.0
 */
public class CheckParamJob extends SimpleTask {
	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	
	@Override
	public Object execute(Object param, Date nextFireTime) {
		int deadLine = 7000;//到期更新时间7000秒
		logger.info(".............................检查各微信公众号的WX参数是否到期.............................");
		//从WxParamCache里遍历每个参数，与当前时间相差超过7000秒时更新一下。
//		List<WxParamObj> allParams = WxParamCache.getInstance().getAllParams();
		Map<String, Map<String, WxParamObj>> fullCache = WxParamCache.getInstance().getFullCache();
		if(fullCache.size() == 0){
			logger.info("暂时没有需要更新的微信参数.....");
			return null;
		}
		//第一层循环，按分销商
		for (String orgCode : fullCache.keySet()) {
			logger.info("正在检查更新---------------" + orgCode);
			Map<String, WxParamObj> map = fullCache.get(orgCode);
			//第二层，按照缓存的参数来遍历
			for (String paras: map.keySet()) {
				WxParamObj wo = map.get(paras);
				//提前200秒更新， 一般js_ticket, token是7200到期
				if(System.currentTimeMillis() - wo.getTimestamp().longValue() >= deadLine * 1000){
					logger.info("------------------参数：" + paras + "已经快到期了，现在从服务器更新。------------------");
					//超过更新期限了，需要重新获取一遍，并且更新到缓存
					WxDataHandler handler = WxDataCommands.getInstance().getDataHandler(paras);
					handler.getValueAndUpdate2Cache(orgCode);
				}else{
					logger.info("------------------参数：" + paras + "尚未到期，无需从微信服务器更新。------------------");
				}
			}
		}
		logger.info("------------------现有缓存：" + fullCache.toString() + "。------------------");
		
		return null;
	}
}


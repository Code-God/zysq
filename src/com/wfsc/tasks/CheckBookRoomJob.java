/**
 * 
 */
package com.wfsc.tasks;

import java.util.Date;

import org.apache.log4j.Logger;

import com.base.ServerBeanFactory;
import com.base.job.SimpleTask;
import com.base.log.LogUtil;
import com.wfsc.services.product.IHotelService;

/**
 * 
 * 检查预订房间是否到期， 到期后，自动设置房间状态为空闲（0）
 * @author jacky
 * @version 1.0
 * @since hotel v1.0
 */
public class CheckBookRoomJob extends SimpleTask {
	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	@Override
	public Object execute(Object param, Date nextFireTime) {
		logger.info(".............................检查预订房间是否到期.............................下次执行时间：" + nextFireTime);
		this.execute();
		return null;
	}
	
	public void execute(){
		//查询当天的预订单，并检查是否已经超过预订保留时间（最晚到店时间）
		IHotelService service = (IHotelService) ServerBeanFactory.getBean("hotelService");
		service.checkAndUpdateBookRoom();
		
		
		//检查红包是否到期
		service.checkHongbaoExpire();
	}

}


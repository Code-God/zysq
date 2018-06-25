package com.wfsc.services.product;

import java.util.Map;

import model.Paging;

/**
 * 酒店service
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public interface IHotelService {

	/**
	 * 预定房间
	 * 
	 * @param param
	 * @return 0 - 不可预定， 1- 预定成功
	 */
	public int bookRoom(Map<String, String> param);

	/**
	 * 检查是否有逾期的预定，并更新状态
	 */
	public void checkAndUpdateBookRoom();

	/**
	 * 获取预订的记录，支持分页
	 * @param start
	 * @param intValue
	 * @param paramMap
	 * @return
	 */
	public Paging getMyBookedRecords(int start, int limit, Map<String, String> paramMap);

	/** 更新预订单状态 */
	public void updateBookRecord(Long id, int newStatus);

	/**
	 * 检查红包是否到期 
	 */
	public void checkHongbaoExpire();

}

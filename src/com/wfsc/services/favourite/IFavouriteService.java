package com.wfsc.services.favourite;

import java.util.List;

import com.wfsc.common.bo.account.Favourite;

/**
 * 我的收藏
 * @author Xiaojiapeng
 *
 */
public interface IFavouriteService {
	
	/**
	 * 查询我的收藏，支持分页
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Favourite> getFavouritesByUserId(long userId, int start, int limit);
	
	/**
	 * 查询我的收藏
	 * @param userId
	 * @return
	 */
	public List<Favourite> getFavouritesByUserId(long userId);
	
	/**
	 * 新增收藏
	 * @param favourite
	 * @return
	 */
	public Long add(Favourite favourite);
	
	/**
	 * 取消收藏
	 * @param userId 用户ID
	 * @param proId 产品ID
	 */
	public void deleteByUserIdAndProId(Long userId, Long proId);
	
	public Favourite getFavouriteByUserIdAndPrdId(long userId, long prdId);
}

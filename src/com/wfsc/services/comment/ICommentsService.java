package com.wfsc.services.comment;

import java.util.List;
import java.util.Map;

import model.bo.wxmall.Pj;

import com.base.util.Page;
import com.wfsc.common.bo.comment.Comments;

/**
 * 商品分类
 * 
 * @author Xiaojiapeng
 * 
 */
public interface ICommentsService {

	/**
	 * 根据商品编码获取评论
	 * 
	 * @param prdCode
	 * @return
	 */
	public List<Comments> queryByPrdCode(String prdCode);
	
	public Page<Pj> findForPage(Page<Pj> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(Comments products); 
	
	public Comments getCommentsById(Long id);
	
	public Page<Comments> findPage(Page<Comments> page, Map<String, Object> paramap);
	
	public int countByStarsAndPrdCode(Integer stars, String prdCode);
	
	/**
	 * 查看商品的评论，支持分页
	 * @param productCode 商品编码
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Comments> getCommentsByProductId(String productCode, int start, int limit);
	
	/**
	 * 查看我的评论，支持分页
	 * @param userId 用户id
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Comments> getCommentsByUserId(long userId, int start, int limit);
	
	public Comments getCommentsByDetailId(Long detailId);
}

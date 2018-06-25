package com.wfsc.services.account;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.bo.WxRulesImage;
import model.bo.drug.DrugUDRelation;

import com.base.exception.CupidRuntimeException;
import com.base.util.Page;
import com.wfsc.common.bo.user.User;
import com.wfsc.common.bo.user.WeiChat;


/**
 * 用户相关的service
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public interface IUserService {
	
	public boolean saveUser(User user);
	
	public boolean updateUser(User user);
	
	public boolean deleteUser(String email);
	
	public boolean deleteUserById(Long userId);
	
	public User getUserByEmail(String email);

	public User getUserById(Long userId);

	/** 检查昵称是否已经存在 */
	public boolean nickNameExists(String nickName);

	/** 检查邮件是否已经存在 */
	public boolean emailExists(String email);

	public void modifyPass(Long id, String newPass);
	
	public User login(String userName, String password, HttpServletRequest request) throws CupidRuntimeException;
	
	public Page<User> findForPage(Page<User> page, Map<String,Object> paramap);
	
	public void deleteByIds(List<Long> ids);
	
	public void saveOrUpdateEntity(User user); 
	
	public void disableAccount(String userId);
	public void enableAccount(String userId);
	public void disableUsers(String[] stopUserId);
	public void enableUsers(String[] startUserId);
	
	/**
	 * 注册新会员
	 * @param user
	 */
	public void registUser(User user);
	
	/**
	 * 会员激活
	 * @param email 待激活会员帐号
	 * @param activeCode 激活码
	 */
	public void activeUser(String email, String activeCode);
	

	/**
	 * 我要报名或我要推荐
	 * @param user  报名或推荐的用户信息
	 * @param images  病例附件
	 * @param tempFilePath 病例附件存放的临时目录
	 * @param saveFilePath 病例附件存放的目录
	 * @param drugUDRelation 用户和项目及推荐人关联信息
	 * @param singupType 用户报名方式  0： 我要报名   1：我要推荐 
	 * @return
	 */
	public boolean saveUser(User user, String[] images, String tempFilePath, String saveFilePath, DrugUDRelation drugUDRelation, String singupType) throws CupidRuntimeException;
	
	/**
	 * 更新用户的审核状态
	 * 
	 * @param userIds   用户编号
	 * @param auditStatus  审核结果状态
	 */
	public void updateAuditStatus(List<Long> userIds, int auditStatus);
	
	/**
	 * 根据手机号查找用户
	 * 
	 * @return
	 */
	public User getUserByMobile(String mobile);

	/**
	 * 通过openid查询用户信息
	 * @return
	 */
	public User getUserByOpenid(String openid);
	
	/**
	 * 通过用户id查询推荐的患者信息
	 * @param userid
	 * @return
	 */
	public List getRecommendByUserid(String userid);
	
	/**
	 * 通过用户id查询自己主动报名的信息（最多一条数据）
	 * @param userid
	 * @return
	 */
	public List getActiveEnteredUserid(String userid);
	
	/**
	 * 通过用户id查询是否有推荐
	 */
	
	public DrugUDRelation findDrugUDRelationByUId(String userid);
	
	
	/**
	 * 查询所有的邀请人
	 * @return
	 */
	public Page getAllInviterList(Page page, Map<String,Object> paramap);
	
	/**
	 * 通过用户id查询该邀请人邀请的所有人列表
	 * @return
	 */
	public Page getBeInvitedListByUserid(final Page page,int userid);
	
	/**
	 * 查询所有的用户信息
	 * @return
	 */
	public List<User> getAllUsers();
	
	public Long saveWeiChat(WeiChat weiChat);
	
}

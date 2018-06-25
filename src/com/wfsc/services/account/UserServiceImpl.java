package com.wfsc.services.account;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.bo.WxRulesImage;
import model.bo.drug.DrugMedicalHistory;
import model.bo.drug.DrugScoreLog;
import model.bo.drug.DrugUDRelation;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.drug.IDrugScoreLogService;

import com.base.ServerBeanFactory;
import com.base.exception.CupidRuntimeException;
import com.base.log.LogUtil;
import com.base.mail.Email;
import com.base.mail.EmailDispatcher;
import com.base.util.Page;
import com.wfsc.common.bo.report.UserRegisterReport;
import com.wfsc.common.bo.user.User;
import com.wfsc.common.bo.user.WeiChat;
import com.wfsc.daos.WeiChatDao;
import com.wfsc.daos.report.UserRegisterReportDao;
import com.wfsc.daos.user.UserDao;
import com.wfsc.util.CipherUtil;
import com.wfsc.util.DateUtil;
import com.wfsc.util.SysUtil;

import dao.drug.DrugMedicalHistoryDao;
import dao.drug.DrugScoreLogDao;
import dao.drug.DrugUDRelationDao;

/**
 * 用户相关的service
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

	protected static Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRegisterReportDao userRegisterReportDao;
	
	@Autowired
	private DrugMedicalHistoryDao drugMedicalHistoryDao;
	
	@Autowired
	private DrugUDRelationDao drugUDRelationDao;

	@Autowired
	private DrugScoreLogDao drugScoreLogDao;
	
	@Autowired
	private WeiChatDao weiChatDao;

	@Override
	public void modifyPass(Long userId, String newPass) {
		String password = CipherUtil.generatePassword(newPass);
		String sql = "update User set password = '" + password + "' where id= " + userId;
		userDao.getHibernateTemplate().bulkUpdate(sql);
	}

	@Override
	public boolean deleteUserById(Long userId) {
		try{
			this.userDao.deleteEntity(Long.valueOf(userId));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteUser(String email) {
		userDao.deleteUserByEmail(email);
		return true;
	}

	@Override
	public boolean emailExists(String email) {
		return userDao.isExistByEmail(email);
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUniqueEntityByOneProperty("email", email);
	}

	@Override
	public User getUserById(Long userId) {
		return userDao.getEntityById(userId);
	}

	@Override
	public boolean nickNameExists(String nickName) {
		return userDao.isExistByNickName(nickName);
	}

	@Override
	public boolean saveUser(User user) {
		userDao.saveEntity(user);
		return true;
	}

	@Override
	public boolean updateUser(User user) {
		userDao.updateEntity(user);
		return true;
	}

	@Override
	public User login(String userName, String password, HttpServletRequest request) throws CupidRuntimeException{
		User user = null;
		if(StringUtils.isNumeric(userName)){
			user = userDao.getUserByTelphone(userName);
		}else{
			user = userDao.getUserByEmail(userName);
		}
		//验证帐号是否存在
		if(user == null)
			throw new CupidRuntimeException("用户名不存在");
		//检查帐号状态
		int status = user.getStatus();
		if(status == 0){
			throw new CupidRuntimeException("该用户已被禁用");
		}else if(status == 9){
			throw new CupidRuntimeException("账号尚未激活");
		}
		//检查密码是否正确
		boolean valid = CipherUtil.validatePassword(user.getPassword(), password);
		if(!valid){
			throw new CupidRuntimeException("密码不正确");
		}
		//更新帐号状态
		user.setOnline(true);
		user.setLastLogin(new Date());
		String loginIp = request.getRemoteAddr();
		user.setLoginIp(loginIp);

		userDao.updateUser(user);

		return user;
	}

	public Page<User> findForPage(Page<User> page, Map<String,Object> paramap){
		return userDao.findForPage(page, paramap);
	}
	public void deleteByIds(List<Long> ids) {
		userDao.deletAllEntities(ids);
	}
	public void saveOrUpdateEntity(User u){
		userDao.saveOrUpdateEntity(u);
	}

	@Override
	public void disableAccount(String userId) {
		if (StringUtils.isEmpty(userId.trim()))
			return;
		User user = userDao.getEntityById(Long.valueOf(userId));
		user.setStatus(0);
		userDao.updateEntity(user);
	}

	@Override
	public void enableAccount(String userId) {
		if (StringUtils.isEmpty(userId.trim()))
			return;
		User user = userDao.getEntityById(Long.valueOf(userId));
		user.setStatus(1);
		userDao.updateEntity(user);
	}

	@Override
	public void disableUsers(String[] stopUserId) {
		for (int i = 0; i < stopUserId.length; i++) {
			disableAccount(stopUserId[i]);
		}
	}

	@Override
	public void enableUsers(String[] startUserId) {
		for (int i = 0; i < startUserId.length; i++) {
			enableAccount(startUserId[i]);
		}
	}

	@Override
	public void registUser(User user) {
		boolean exists = emailExists(user.getEmail());
		if(exists)
			throw new CupidRuntimeException("emailExists");
		boolean nickExists = nickNameExists(user.getNickName());
		if(nickExists)
			throw new CupidRuntimeException("nickexists");
		String password = user.getPassword();
		password = CipherUtil.generatePassword(password);
		user.setPassword(password);
		Date registeDate = new Date();
		user.setRegDate(registeDate);
		user.setStatus(9);
		String activeCode = SysUtil.createVerifyCode();
		user.setActiveCode(activeCode);
		//保存入库
		userDao.saveEntity(user);

		if(user.getEmail() != null){
			//保存入库成功后，发送邮件通知用户激活
			List<String> addresses =new ArrayList<String>();
			addresses.add(user.getEmail());
			String param = user.getEmail() + "=" + user.getActiveCode();
			Email email = new Email("register", "帐号激活", addresses, param);
			EmailDispatcher dispatcher = (EmailDispatcher) ServerBeanFactory.getBean("mailDispather");
			dispatcher.dispatchMail(email);
		}

		//统计报表
		int year = DateUtil.getYear(registeDate);
		int month = DateUtil.getMonth(registeDate);
		int week = DateUtil.getWeek(registeDate);
		UserRegisterReport report = null;
		synchronized (userRegisterReportDao) {
			report = userRegisterReportDao.getReportByYMW(year, month, week);
			if(report == null){
				report = new UserRegisterReport();
				report.setYear(year);
				report.setMonth(month);
				report.setWeek(week);
				report.setRegCount(1);
			}else{
				report.setRegCount(report.getRegCount() + 1);
			}
			userRegisterReportDao.saveOrUpdateEntity(report);
		}
	}

	@Override
	public void activeUser(String email, String activeCode) {
		if(StringUtils.isEmpty(email) || StringUtils.isEmpty(activeCode)){
			throw new CupidRuntimeException("激活请求不合法");
		}

		User user = userDao.getUserByEmail(email);
		if(user == null){
			throw new CupidRuntimeException("对不起，您激活的帐号不存在");
		}
		if(user.getStatus() == 0){
			throw new CupidRuntimeException("您的帐号已激活，请勿重复激活");
		}
		if(!StringUtils.equals(user.getActiveCode(), activeCode)){
			throw new CupidRuntimeException("激活失败");
		}
		user.setActiveCode(null);
		user.setStatus(1);
		userDao.updateEntity(user);
	}

	@Override
	public boolean saveUser(User user, String[] images, String tempFilePath, 
			String saveFilePath, DrugUDRelation drugUDRelation, String singupType) throws CupidRuntimeException {
		Long userId = null;
		// 根据手机号查找报名用户，不存在则新增，存在不需要重复添加
//		User dbUser = this.userDao.getUserByMobile(user.getMobilePhone());
//		if (dbUser == null) {
//			userId = userDao.saveEntity(user);
//		} else {
//			userId = dbUser.getId();
//		}
		if(user.getId()!=null){
			userId=user.getId();
			//修改用户
			userDao.updateUser(user);
		}else{
			userId=userDao.saveEntity(user);
		}
		drugUDRelation.setUserId(userId);
		drugUDRelation.setSignupDate(new Date());
		this.drugUDRelationDao.saveEntity(drugUDRelation);
		
		// 记录积分来源日志
		if (StringUtils.isNotBlank(singupType) && "0".equals(singupType)) { 
			// ******** 我要报名  ******* 
			// 记录基本分日志
			DrugScoreLog drugScoreLog = new DrugScoreLog();
			drugScoreLog.setOpenId(user.getOpenId());
			drugScoreLog.setAction(IDrugScoreLogService.ACTION_ADD);
			drugScoreLog.setOpdate(new Date());
			drugScoreLog.setScore(IDrugScoreLogService.SCORE_BASE);
			drugScoreLog.setSource(IDrugScoreLogService.SOURC_ACTION_BASE);
			this.drugScoreLogDao.saveEntity(drugScoreLog);
			user.setPoints(user.getPoints()+IDrugScoreLogService.SCORE_BASE);
			userDao.saveOrUpdateEntity(user);
			
			// 记录报名人成功报名后的积分日志
			DrugScoreLog drugScoreLog1 = new DrugScoreLog();
			drugScoreLog1.setOpenId(user.getOpenId());
			drugScoreLog1.setAction(IDrugScoreLogService.ACTION_ADD);
			drugScoreLog1.setOpdate(new Date());
			drugScoreLog1.setScore(IDrugScoreLogService.SCORE_SINGUP);
			drugScoreLog1.setSource(IDrugScoreLogService.SOURC_ACTION_SINGUP);
			this.drugScoreLogDao.saveEntity(drugScoreLog1);
			user.setPoints(user.getPoints()+IDrugScoreLogService.SCORE_SINGUP);
			userDao.saveOrUpdateEntity(user);
			
		} else {
			// ******** 我要推荐  *******
			// 记录推荐人成功推荐后的积分日志
			User referrerUser = this.userDao.getEntityById(drugUDRelation.getReferrerId());
			if (referrerUser != null) {
				DrugScoreLog drugScoreLog = new DrugScoreLog();
				drugScoreLog.setOpenId(referrerUser.getOpenId());
				drugScoreLog.setAction(IDrugScoreLogService.ACTION_ADD);
				drugScoreLog.setOpdate(new Date());
				drugScoreLog.setScore(IDrugScoreLogService.SCORE_RECOMMEND);
				drugScoreLog.setSource(IDrugScoreLogService.SOURC_ACTION_RECOMMEND);
				this.drugScoreLogDao.saveEntity(drugScoreLog);
				referrerUser.setPoints(referrerUser.getPoints()+IDrugScoreLogService.SCORE_RECOMMEND);
				userDao.saveOrUpdateEntity(referrerUser);
			}
		}
		
		if (images != null && images.length > 0) {
			try {
				File file = new File(saveFilePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				
				// 从临时文件获取用户上传的文件，并复制到病历目录中
				File tempFile = null;
				File newFile = null;
				DrugMedicalHistory dmh = null;
				for (int i = 0; i < images.length; i++) {
					String imgName = images[i];
					imgName = imgName.substring(imgName.indexOf("_"));
					//　临时文件
					tempFile = new File(tempFilePath + "/" + images[i]); 
					//　为保证文件不被覆盖，重新生成唯一的文件名：用户ID-当前时间毫秒数-上传文件名
					String newFileName = userId + imgName;
					newFile = new File(saveFilePath + newFileName);
					tempFile.renameTo(newFile);
					
					dmh = new DrugMedicalHistory();
					dmh.setUserId(userId);
					dmh.setMedicalHistoryPic(newFileName);
					this.drugMedicalHistoryDao.saveEntity(dmh);
				}
			} catch (Exception e) {
				throw new CupidRuntimeException(e.getMessage());
			}
		}

		return true;
	}
	/**
	 * 通过openid查询用户信息
	 * @return
	 */
	@Override
	public User getUserByOpenid(String openid) {
		return userDao.getUserByOpenid(openid);
	}
	/**
	 * 通过用户id查询推荐的患者信息
	 * @param userid
	 * @return
	 */
	public List getRecommendByUserid(String userid){
		return userDao.getRecommendByUserid(userid);
	}
	
	/**
	 * 通过用户id查询自己主动报名的信息（最多一条数据）
	 * @param userid
	 * @return
	 */
	public List getActiveEnteredUserid(String userid){
		return userDao.getActiveEnteredUserid(userid);
	}

	
	@Override
	public void updateAuditStatus(List<Long> userIds, int auditStatus) {
		this.userDao.updateAuditStatus(userIds, auditStatus);
	}
	
	@Override
	public User getUserByMobile(String mobile){
		return this.userDao.getUserByMobile(mobile);
	}
	/**
	 * 通过用户id查询是否有推荐
	 */

	@Override
	public  DrugUDRelation findDrugUDRelationByUId(String userid) {
		
		return drugUDRelationDao.findDrugUDRelationByUId(userid);
	}
	
	
	/**
	 * 查询所有的邀请人
	 * @return
	 */
	public Page getAllInviterList(Page page, Map<String,Object> paramap){
		return userDao.getAllInviterList(page,paramap);
	}
	
	/**
	 * 通过用户id查询该邀请人邀请的所有人列表
	 * @return
	 */
	public Page getBeInvitedListByUserid(final Page page,int userid){
		return userDao.getBeInvitedListByUserid(page, userid);
	}
	
	/**
	 * 查询所有的用户信息
	 * @return
	 */
	public List<User> getAllUsers(){
		return userDao.getAllUsers();
	}
	
	public Long saveWeiChat(WeiChat weiChat){
		return (Long)weiChatDao.saveEntity(weiChat);
	}
	
}

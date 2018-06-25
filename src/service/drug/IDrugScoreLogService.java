package service.drug;

import java.util.Map;

import model.bo.drug.DrugScoreLog;

import com.base.util.Page;

/**
 * 积分
 * @author Administrator
 *
 */
public interface IDrugScoreLogService {
	
	/**
	 * 操作类型：积分增加
	 */
	public static final int ACTION_ADD = 1;
	
	/**
	 * 操作类型：积分减少
	 */
	public static final int ACTION_DECREASE = 0;
	
	/**
	 * 积分动作：基本积分
	 */
	public static final String SOURC_ACTION_BASE = "0";
	
	/**
	 * 积分动作：推荐得分
	 */
	public static final String SOURC_ACTION_RECOMMEND  = "1";
	
	/**
	 * 积分动作：报名得分
	 */
	public static final String SOURC_ACTION_SINGUP = "2";
	
	/**
	 * 积分动作：医师认证审核通过得分
	 */
	public static final String SOURC_ACTION_PHYSICIAN_CERTIFICATION="3";
	
	/**
	 * 积分动作：完善个人信息得分
	 */
	public static final String SOURC_ACTION_PERFECT_INFO="4";
	
	
	/**
	 * 积分动作：后台人工审核通过得分
	 */
	public static final String SOURC_ACTION_AUDITING_INFO="5";
	
	/**
	 * 积分动作：推荐好友成功得分（扫描二维码）
	 */
	public static final String SOURC_ACTION_SCAN_CODE="6";
	
	/**
	 *积分动作：后台管理员兑换积分操作
	 */
	public static final String SOURC_ACTION_EXCHANGE_CODE="7";

	
	/**
	 * 分值：基本积分分值
	 */
	public static final int SCORE_BASE = 30;
	
	/**
	 * 分值：推荐得分分值
	 */
	public static final int SCORE_RECOMMEND  = 50;
	
	/**
	 * 分值：报名得分分值
	 */
	public static final int SCORE_SINGUP = 50;
	
	
	/**
	 * 分值：完善个人信息分值
	 */
	public static final int SCORE_PERFECT_INFO=10;
	
	
	/**
	 * 分值：医师认证审核通过分值
	 */
	public static final int SCORE_PHYSICIAN_CERTIFICATION=1000;
	
	
	/**
	 * 分值：推荐好友成功分值（扫描二维码）
	 */
	public static final int SCORE_SCAN_CODE=30;
	
	/**
	 * 新增或者修改积分
	 * 
	 * @param drugDiseaseitem
	 */
	public void saveOrUpdateDrugScore(DrugScoreLog drugScoreLog);

	/**
	 * 查看该积分类型的积分是否已经存在
	 * @return
	 */
	public boolean isExsit(String openid,String source);
	
	/**
	 * 通过openid查看积分总数
	 * @param openid
	 * @return
	 */
	public Integer getTotalScoreByOpenid(String openid);
	
	/**
	 *  通过openid 查看用户获得积分记录列表分页
	 * @param openid
	 * @return
	 */
	public Page<DrugScoreLog> findPageObtainForDrugScoreLog(Page<DrugScoreLog> page, Map<String,Object> paramap);
	
	/**
	 *  通过openid 查看用户兑换积分记录列表分页
	 * @param openid
	 * @return
	 */
	public Page<DrugScoreLog> findPageExchangeForDrugScoreLog(Page<DrugScoreLog> page, Map<String, Object> paramap);
}

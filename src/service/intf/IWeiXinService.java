package service.intf;

import model.bo.auth.Org;

/**
 * 微信用户相关的service
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public interface IWeiXinService {

	/**
	 * 解析普通消息，返回的是微信平台的消息格式XML
	 * 
	 * @param content
	 * @param content2
	 * @param toUserName
	 */
	public String intercept(String fromUserName, String toUserName, String content);

	/**
	 * 检查，是否是第一次此页面
	 * 
	 * @param sourceOpenId
	 * @param page
	 * @return true - 有效点击， false - 不是有效点击或自己刷新页面
	 * 
	 */
	public boolean isFirstVisit(String openId, String page);

	/**
	 * 检查，是否是朋友点击
	 * 
	 * @param sourceOpenId
	 * @param sesId - 会话ID， 防止当前用户刷新页面，自己中奖
	 * @return true - 有效点击， false - 不是有效点击或自己刷新页面
	 * 
	 */
	public boolean validClick(String sourceOpenId, String page, String sesId);

	public void createRecord(String sourceOpenId, String page, String id);

	public int getTotalTransfer();
	/**
	 * 用户关注后，自动生成数据库记录 
	 * @param openId
	 * @param fxOpenId - 分销商公众号的原始ID 
	 * @return
	 */
	public String createUser(String openId, String fxOpenId);

	/** 根据公众号的原始ID获取 */
	public Org getOrgBySourceId(String toUserName);
}

package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.WxGame;
import model.bo.WxRules;
import model.bo.auth.Org;
import model.bo.drug.DrugScoreLog;
import model.vo.WxUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.drug.IDrugScoreLogService;
import service.intf.IWeiXinService;
import actions.integ.weixin.WeiXinUtil;

import com.base.log.LogUtil;
import com.base.tools.Version;
import com.wfsc.common.bo.report.UserRegisterReport;
import com.wfsc.common.bo.user.User;
import com.wfsc.daos.report.UserRegisterReportDao;
import com.wfsc.daos.user.UserDao;
import com.wfsc.util.DateUtil;
import com.wfsc.util.SysUtil;

import dao.OrgDao;
import dao.WxRuleDao;
import dao.drug.DrugScoreLogDao;
import dao.weixin.WxGameDao;

@Service("weixinService")
public class WeiXinServiceImpl implements IWeiXinService {

	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	@Autowired
	private WxRuleDao wxRuleDao;

	@Autowired
	private WxGameDao wxGameDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRegisterReportDao userRegisterReportDao;
	
	@Autowired
	private OrgDao orgDao;
	
	@Autowired
	private DrugScoreLogDao drugScoreLogDao;

	public void setWxRuleDao(WxRuleDao wxRuleDao) {
		this.wxRuleDao = wxRuleDao;
	}

	@Override
	public String intercept(String toUserName, String fromUserName, String content) {
		List<WxRules> allEntities = wxRuleDao.getAllEntities();
		boolean isReturn = false;
		logger.info("关键字规则：" + allEntities.size());
		WxRules fitRule = new WxRules();
		for (WxRules wxRules : allEntities) {
			logger.info("关键字---" + wxRules.getKw());
			String[] split = StringUtils.split(wxRules.getKw(), "|");
			// 判断是否需要响应
			for (String keyword : split) {
				if (content.equalsIgnoreCase(keyword.trim())) {// 包含某个关键字
					logger.info(content + "-包含关键字.....:" + keyword);
					isReturn = true;
					break;
				}
			}
			if (isReturn) {
				fitRule.setRespType(wxRules.getRespType());
				fitRule.setTitle(wxRules.getTitle());
				fitRule.setTwdesc(wxRules.getTwdesc());
				fitRule.setPicUrl(wxRules.getPicUrl());
				fitRule.setRespContent(wxRules.getRespContent());
				break;
			}
		}
		// 通过上述的遍历，已经找到反馈规则
		if (isReturn) {
			// 判断回复类型
			if (fitRule.getRespType().equals(WxRules.NEWS)) {// 图文
				logger.info("需要响应.....图文");
				logger.info("title=" + fitRule.getTitle());
				logger.info("desc=" + fitRule.getTwdesc());
				logger.info("picUrl=" + fitRule.getPicUrl());
				logger.info("url=" + fitRule.getRespContent());
				JSONObject fromObject = JSONObject.fromObject(fitRule.getRespContent());
				List<Map<String, String>> picTexts = new ArrayList<Map<String, String>>();
				Map<String, String> m1 = new HashMap<String, String>();
				// Version.getInstance().getNewProperty("wx_cmdList")
				m1.put("title", fitRule.getTitle());
				m1.put("desc", fitRule.getTwdesc());
				m1.put("picUrl", fitRule.getPicUrl());
				m1.put("url", fromObject.getString("twUrl"));
				picTexts.add(m1);
				return WeiXinUtil.getResponsePicTextXml(toUserName, fromUserName, picTexts);
			} else if (fitRule.getRespType().equals(WxRules.MULTI)) {// 多图文
				logger.info("需要响应.....多图文");
				JSONArray fromObject = JSONArray.fromObject(fitRule.getRespContent());
				Object[] array = fromObject.toArray();
				List<Map<String, String>> picTexts = new ArrayList<Map<String, String>>();
				for (Object object : array) {
					JSONObject jo = (JSONObject) object;
					Map<String, String> m1 = new HashMap<String, String>();
					// Version.getInstance().getNewProperty("wx_cmdList")
					m1.put("title", jo.getString("title"));
					m1.put("desc", jo.getString("twdesc"));
					m1.put("picUrl", jo.getString("picUrl"));
					m1.put("url", jo.getString("twUrl"));
					picTexts.add(m1);
				}
				return WeiXinUtil.getResponsePicTextXml(toUserName, fromUserName, picTexts);
			} else if (fitRule.getRespType().equals(WxRules.TEXT)) {// 文本消息
				logger.info("需要响应.....文本");
				return WeiXinUtil.getResponseXml(toUserName, fromUserName, fitRule.getRespContent());
			}
		} else {
			// 自动回复
			// logger.info("普通的自动回复....." + content);
			// return WeiXinUtil.getResponseXml(toUserName, fromUserName, Version.getInstance().getNewProperty("welcome"));
		}
		return "";
	}

	public static void main(String[] args) {
		// String ss = "价格|4|多少钱|价钱|价位|竹叶青多少钱|碧潭飘雪多少钱";
		// String[] split = StringUtils.split(ss, "|");
		// for (String keyword : split) {
		// if("价格".equalsIgnoreCase(keyword.trim()) || "价格".indexOf(keyword.trim()) != -1){//包含某个关键字
		// System.out.print("---");
		// }
		// }
		float i = 50 * (1 - 5 / 100f);
		System.out.println(Math.floor(i));
		// Random r = new Random();
		// int bt25 = 0;//2奖概率
		// int bt28 = 0;//3奖概率
		// for(int i=0; i< 2000; i++){
		// int n = r.nextInt(30);
		// if(n >= 16 && n <= 18){
		// bt25 ++;
		// }
		// if(n >= 20){
		// bt28++;
		// }
		// if(n >= 29){
		// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
		// }
		// }
		// System.out.println(bt25/1000d + "|" + bt28/1000d);
	}

	@Override
	public boolean validClick(String sourceOpenId, String page, String sesId) {
		if (!isFirstVisit(sourceOpenId, page)) {
			WxGame item = wxGameDao.getUniqueEntityByPropNames(new String[] { "openId", "sesId" }, new Object[] {
					sourceOpenId, sesId });
			if (item == null) {
				// 点击次数+1
				WxGame wg = wxGameDao.getUniqueEntityByPropNames(new String[] { "openId", "page" }, new Object[] {
						sourceOpenId, page });
				wg.setVisit(wg.getVisit() + 1);
				wxGameDao.updateEntity(wg);
				return true;// 合法点击
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean isFirstVisit(String sourceOpenId, String page) {
		WxGame item = wxGameDao.getUniqueEntityByPropNames(new String[] { "openId", "page" }, new Object[] { sourceOpenId,
				page });
		if (item == null) {
			return true;
		} else {
			return false;
		}
	}

	public WxGameDao getWxGameDao() {
		return wxGameDao;
	}

	public void setWxGameDao(WxGameDao wxGameDao) {
		this.wxGameDao = wxGameDao;
	}

	@Override
	public void createRecord(String openId, String page, String sesId) {
		WxGame wx = new WxGame();
		wx.setOpenId(openId);
		wx.setPage(page);
		wx.setSesId(sesId);
		wxGameDao.saveEntity(wx);
	}

	@Override
	public int getTotalTransfer() {
		int baseCount = Integer.valueOf(Version.getInstance().getNewProperty("baseCount"));
		String sql = "select sum(visit) from WxGame";
		try {
			List find = this.wxGameDao.getHibernateTemplate().find(sql);
			return Integer.valueOf(find.get(0).toString()) + baseCount;
		} catch (Exception e) {
			e.printStackTrace();
			return baseCount;
		}
	}

	@Override
	public String createUser(String openId, String fxOpenId) {
		logger.info("fxOpenId=" + fxOpenId);
		
		Org org = orgDao.getUniqueEntityByOneProperty("wxID", fxOpenId);
		if(org == null){
			return null;
		}
		User u = new User();
		u.setOpenId(openId);
		
		
		User user = this.userDao.getUniqueEntityByOneProperty("openId", openId);
		if (user == null) {
			logger.info("自动关注，新用户..." + openId);
			try{
				//通过接口获取用户微信详细信息，并更新到数据库
				WxUser wxUserByOrg = WeiXinUtil.getWxUserByOrg(openId, org.getAppid(), org.getAppsecret());
				
				System.out.println("testetsttttttttttttttt-----------------nickname:"+wxUserByOrg.getNickName());
				//u.setCity(wxUserByOrg.getCity());
				u.setNickName(wxUserByOrg.getNickName());
				u.setSex(wxUserByOrg.getSex());
			//	u.setProvince(wxUserByOrg.getProvince());
				u.setCountry(wxUserByOrg.getCountry());
				u.setSubscribTime(wxUserByOrg.getSubscribeTime());
				u.setOrg(org);//属于哪个分销商
				u.setStatus(1);
				u.setSubstate(1);//默认值为1
				u.setEmail("abc@abc.com");
				u.setPassword(SysUtil.encodeBase64("11111111"));//默认密码
				Date date = new Date();
				u.setRegDate(date);
				//头像
				u.setHeadimgurl(wxUserByOrg.getHeadimgurl());

				//加入默认30积分
				DrugScoreLog  drugScoreLog=new DrugScoreLog();
				drugScoreLog.setOpenId(openId);
				drugScoreLog.setScore(IDrugScoreLogService.SCORE_BASE);
				drugScoreLog.setSource(IDrugScoreLogService.SOURC_ACTION_BASE);
				drugScoreLog.setAction(IDrugScoreLogService.ACTION_ADD);
				drugScoreLog.setOpdate(new Date());
				drugScoreLogDao.saveOrUpdateEntity(drugScoreLog);
				u.setPoints(IDrugScoreLogService.SCORE_BASE);
				
				this.userDao.saveEntity(u);
				
				//----------------------------统计报表--------------------------------
				int year = DateUtil.getYear(date);
				int month = DateUtil.getMonth(date);
				int week = DateUtil.getWeek(date);
				UserRegisterReport report = null;
				synchronized (userRegisterReportDao) {
					report = userRegisterReportDao.getReportByYMW(year, month, week);
					if(report == null){
						report = new UserRegisterReport();
						report.setYear(year);
						report.setMonth(month);
						report.setWeek(week);
						report.setRegCount(1);
						report.setOrgId(org.getId());//保存分销商ID
					}else{
						report.setRegCount(report.getRegCount() + 1);
					}
					userRegisterReportDao.saveOrUpdateEntity(report);
				}
				//-------------------------------------------------------------
				
			}catch(Exception e){
				e.printStackTrace();
			}
		} else {
			logger.info("老用户...重新关注..." + openId);
			//更新关注标记
			user.setSubscribTime(DateUtil.getLongCurrentDate());
			user.setSubstate(1);
			this.userDao.updateEntity(user);
			return user.getId().toString();
		}
		// 返回loginId
		return u.getId().toString();
	}

	public DrugScoreLogDao getDrugScoreLogDao() {
		return drugScoreLogDao;
	}

	public void setDrugScoreLogDao(DrugScoreLogDao drugScoreLogDao) {
		this.drugScoreLogDao = drugScoreLogDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	
	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	
	public void setUserRegisterReportDao(UserRegisterReportDao userRegisterReportDao) {
		this.userRegisterReportDao = userRegisterReportDao;
	}

	@Override
	public Org getOrgBySourceId(String toUserName) {
		return orgDao.getUniqueEntityByOneProperty("wxID", toUserName);
	}
}

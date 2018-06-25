package service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import model.GameDailyReport;
import model.GameReport;
import model.Paging;
import model.TwObj;
import model.bo.AboutUs;
import model.bo.Answers;
import model.bo.Columns;
import model.bo.Contacts;
import model.bo.Experts;
import model.bo.Feedback;
import model.bo.Gift;
import model.bo.GiftLog;
import model.bo.OnlineTests;
import model.bo.PublishedStudy;
import model.bo.PublishedTesting;
import model.bo.Question;
import model.bo.ShowCase;
import model.bo.StaticData;
import model.bo.TP;
import model.bo.Topics;
import model.bo.TouPiaoItem;
import model.bo.Training;
import model.bo.Vote;
import model.bo.VoteItem;
import model.bo.VoteRecord;
import model.bo.WxRules;
import model.bo.WxRulesImage;
import model.bo.act.HongBao;
import model.bo.act.UserHongBao;
import model.bo.auth.Org;
import model.bo.car.CarInfo;
import model.bo.car.CarInsurenceInfo;
import model.bo.car.CleaningCards;
import model.bo.drug.DrugScoreLog;
import model.bo.fenxiao.CashApply;
import model.bo.fenxiao.CommissionLog;
import model.bo.fenxiao.FxApplyConfig;
import model.bo.food.ConfigParam;
import model.bo.food.Spreader;
import model.bo.wxmall.Pj;
import model.bo.wxmall.WxMenu;
import model.vo.DataObject;
import model.vo.OrgObj;
import model.vo.ScanReport;
import model.vo.WxUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.drug.IDrugScoreLogService;
import service.intf.AdminService;
import sun.misc.BASE64Encoder;
import util.CommonUtil;
import util.SysUtil;
import actions.integ.weixin.WeiXinUtil;

import com.base.EnhancedHibernateDaoSupport;
import com.base.ServerBeanFactory;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.constants.CupidStrutsConstants;
import com.wfsc.common.bo.account.Coupon;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.order.OrdersDetail;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.report.UserRegisterReport;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.User;
import com.wfsc.daos.AdminDao;
import com.wfsc.daos.account.CleaningCardsDao;
import com.wfsc.daos.account.CouponDao;
import com.wfsc.daos.orders.OrdersDao;
import com.wfsc.daos.orders.OrdersDetailDao;
import com.wfsc.daos.product.ProductCatDao;
import com.wfsc.daos.product.ProductsDao;
import com.wfsc.daos.report.UserRegisterReportDao;
import com.wfsc.daos.user.UserDao;
import com.wfsc.services.system.ISystemLogService;
import com.wfsc.util.DateUtil;

import dao.AnswerDao;
import dao.ExpertsDao;
import dao.GiftLogDao;
import dao.GiftsDao;
import dao.OrgDao;
import dao.QuestionDao;
import dao.ShowCaseDao;
import dao.TopicDao;
import dao.WxMenuDao;
import dao.WxRuleDao;
import dao.WxRuleImageDao;
import dao.car.CarInfoDao;
import dao.car.CarInsurenceInfoDao;
import dao.fenxiao.CashApplyDao;
import dao.fenxiao.CommissionLogDao;
import dao.fenxiao.FxApplyConfigDao;
import dao.food.ConfigParamDao;
import dao.food.ShoppingCartDao;
import dao.hongbao.ContactsDao;
import dao.hongbao.HongBaoDao;
import dao.hongbao.UserHongBaoDao;
import dao.weixin.WxGameDao;
import dao.wxmall.PjDao;
import dao.wxmall.SpreadDao;
import dao.youzhen.AboutusDao;
import dao.youzhen.ColumnDao;
import dao.youzhen.FeedBackDao;
import dao.youzhen.OnlineTestsDao;
import dao.youzhen.PublishedStudyDao;
import dao.youzhen.PublishedTestingDao;
import dao.youzhen.StatisticDao;
import dao.youzhen.TPDao;
import dao.youzhen.TpitemDao;
import dao.youzhen.TrainingDao;
import dao.youzhen.VoteDao;
import dao.youzhen.VoteItemDao;
import dao.youzhen.VoteRecordDao;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private OrgDao orgDao;
	
	@Autowired
	private ConfigParamDao configParamDao;

	@Autowired
	private WxRuleDao wxRuleDao;

	@Autowired
	private ShowCaseDao showcaseDao;

	@Autowired
	private ExpertsDao expertsDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private TopicDao topicDao;

	@Autowired
	private GiftsDao giftDao;

	@Autowired
	private GiftLogDao giftLogDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ColumnDao columnDao;

	@Autowired
	private PublishedStudyDao publishedStudyDao;

	@Autowired
	private PublishedTestingDao publishedTestingDao;

	@Autowired
	private OnlineTestsDao onlineTestsDao;

	@Autowired
	private TrainingDao trainingDao;

	@Autowired
	private OrdersDao ordersDao;

	@Autowired
	private AboutusDao aboutusDao;
	
	@Autowired
	private HongBaoDao hongBaoDao;

	@Autowired
	private UserHongBaoDao userHongBaoDao;
	
	@Autowired
	private ContactsDao contactsDao;

	@Autowired
	private ISystemLogService systemLogService;
	
	@Autowired
	private IDrugScoreLogService drugScoreLogService;
	
	@Autowired
	private WxRuleImageDao wxRuleImageDao;
	
	private Logger logger = Logger.getLogger(LogUtil.SERVER);

	private Logger payLogger = Logger.getLogger(LogUtil.PAY);

	@Override
	public Admin login(String username, String pwd) {
		BASE64Encoder en = new BASE64Encoder();
		String encode = en.encode(pwd.getBytes());// 密码是经过base64编码的
		Admin u = adminDao.getUniqueEntityByPropNames(new String[] { "username", "password" }, new Object[] { username,
				encode });
		if (u != null) {
			u.setLastLoginDate(new Date());
			adminDao.updateEntity(u);
			// 加日志
			return u;
		} else {
			return null;
		}
	}

	@Override
	public void addAdmin(Admin user) {
		BASE64Encoder enc = new BASE64Encoder();
		String defaultPwd = "11111111";
		user.setPassword(enc.encode(defaultPwd.getBytes()));
		adminDao.saveEntity(user);
	}

	@Override
	public void updateAdmin(Admin user) {
		adminDao.updateEntity(user);
	}

	@Override
	public Admin getAdminById(String id) {
		return adminDao.getEntityById(Long.valueOf(id));
	}

	@Override
	public Map<String, Object> getAdminList(int start, int limit) {
		int totalCount = adminDao.count();
		Map<String, Object> result = new HashMap<String, Object>();
		List<Admin> list = new ArrayList<Admin>();
		try {
			list = adminDao.getPagingEntities(start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("total", totalCount);
		result.put("list", list);
		return result;
	}

	@Override
	public boolean batchDeleteAdmin(String ids) {
		try {
			List<Long> idList = new ArrayList<Long>();
			String[] split = ids.split(",");
			for (String id : split) {
				idList.add(Long.valueOf(id));
			}
			logger.info("删除的ID...." + idList.toString());
			adminDao.deletAllEntities(idList);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Map<String, Object> getKeywords(int start, int limit, Map<String, String> paramMap) {
		return wxRuleDao.queryRecord(start, limit, paramMap);
	}

	public void setWxRuleDao(WxRuleDao wxRuleDao) {
		this.wxRuleDao = wxRuleDao;
	}

	@Override
	public boolean addKeyword(HttpServletRequest request) {
		try {
			// 规则名
			String ruleName = request.getParameter("ruleName");
			// 关键字
			String keyword = request.getParameter("keywords");
			// 回复类型
			String respType = request.getParameter("respType");
			// 回复内容
			String respContent = request.getParameter("respContent");
			
			// 当前用户所属分销商编码
			String fxCode = request.getSession().getAttribute("orgCode").toString();
			
			// 图文消息的四个字段
			String title, desc, picUrl, twUrl;
			WxRules r = new WxRules();
			r.setRuleName(ruleName);
			r.setKw(keyword);
			r.setRespType(respType);
			r.setFxCode(fxCode);
			if ("text".equals(respType)) {
				// 文本消息，直接保存到此字段
				r.setRespContent(respContent);
			} else if ("news".equals(respType)) {
				// 单图文
				title = request.getParameter("title");
				desc = request.getParameter("desc");
				picUrl = request.getParameter("picUrl");
				twUrl = request.getParameter("twUrl");
				r.setTitle(title);
				r.setTwdesc(desc);
				r.setPicUrl(picUrl);
				r.setTwUrl(twUrl);
				// 图文消息， 以json格式保存到respContent字段
				TwObj tw = new TwObj(title, desc, picUrl, twUrl);
				JSONObject fromObject = JSONObject.fromObject(tw);
				r.setRespContent(fromObject.toString());
			} else if ("multi".equals(respType)) {// 多图文
				String[] titles = request.getParameterValues("title");
				String[] descs = request.getParameterValues("desc");
				String[] picUrls = request.getParameterValues("picUrl");
				String[] twUrls = request.getParameterValues("twUrl");
				int n = titles.length;
				List<TwObj> list = new ArrayList<TwObj>();
				for (int i = 0; i < n; i++) {
					TwObj tw = new TwObj(titles[i], descs[i], picUrls[i], twUrls[i]);
					list.add(tw);
				}
				JSONArray fromObject = JSONArray.fromObject(list);
				r.setRespContent(fromObject.toString());
			}
			wxRuleDao.saveEntity(r);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean addImageKeyword(HttpServletRequest request) {
		try {
			// 规则名
			String ruleName = request.getParameter("ruleName");
			// 关键字
			String keyword = request.getParameter("keywords");
			// 回复类型
			String respType = request.getParameter("respType");
			// 回复内容
			String respContent = request.getParameter("respContent");
			
			// 当前用户所属分销商编码
			String fxCode = request.getSession().getAttribute("orgCode").toString();
			
			// 图文消息的四个字段
			String title, desc, picUrl, twUrl;
			WxRulesImage r = new WxRulesImage();
			r.setRuleName(ruleName);
			r.setKw(keyword);
			r.setRespType(respType);
			r.setFxCode(fxCode);
			if ("text".equals(respType)) {
				// 文本消息，直接保存到此字段
				r.setRespContent(respContent);
			} else if ("news".equals(respType)) {
				// 单图文
				title = request.getParameter("title");
				desc = request.getParameter("desc");
				picUrl = request.getParameter("picUrl");
				twUrl = request.getParameter("twUrl");
				r.setTitle(title);
				r.setTwdesc(desc);
				r.setPicUrl(picUrl);
				r.setTwUrl(twUrl);
				// 图文消息， 以json格式保存到respContent字段
				TwObj tw = new TwObj(title, desc, picUrl, twUrl);
				JSONObject fromObject = JSONObject.fromObject(tw);
				r.setRespContent(fromObject.toString());
			} else if ("multi".equals(respType)) {// 多图文
				String[] titles = request.getParameterValues("title");
				String[] descs = request.getParameterValues("desc");
				String[] picUrls = request.getParameterValues("picUrl");
				String[] twUrls = request.getParameterValues("twUrl");
				int n = titles.length;
				List<TwObj> list = new ArrayList<TwObj>();
				for (int i = 0; i < n; i++) {
					TwObj tw = new TwObj(titles[i], descs[i], picUrls[i], twUrls[i]);
					list.add(tw);
				}
				JSONArray fromObject = JSONArray.fromObject(list);
				r.setRespContent(fromObject.toString());
			}
			wxRuleImageDao.saveEntity(r);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateKeyWord(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			// 规则名
			String ruleName = request.getParameter("ruleName");
			// 关键字
			String keyword = request.getParameter("keywords");
			// 回复类型
			String respType = request.getParameter("respType");
			// 回复内容
			String respContent = request.getParameter("respContent");
			String title, desc, picUrl, twUrl;
			WxRules r = wxRuleDao.getEntityById(Long.valueOf(id));
			if (r != null) {// 更新
				r.setKw(keyword);
				r.setRuleName(ruleName);
				r.setRespType(respType);
				if ("text".equals(respType)) {
					// 文本消息，直接保存到此字段
					r.setRespContent(respContent);
				} else if ("news".equals(respType)) {
					// 单图文
					title = request.getParameter("title");
					desc = request.getParameter("desc");
					picUrl = request.getParameter("picUrl");
					twUrl = request.getParameter("twUrl");
					r.setTitle(title);
					r.setTwdesc(desc);
					r.setPicUrl(picUrl);
					r.setTwUrl(twUrl);
					// 图文消息， 以json格式保存到respContent字段
					TwObj tw = new TwObj(title, desc, picUrl, twUrl);
					JSONObject fromObject = JSONObject.fromObject(tw);
					r.setRespContent(fromObject.toString());
				} else if ("multi".equals(respType)) {// 多图文
					String[] titles = request.getParameterValues("title");
					String[] descs = request.getParameterValues("desc");
					String[] picUrls = request.getParameterValues("picUrl");
					String[] twUrls = request.getParameterValues("twUrl");
					int n = titles.length;
					List<TwObj> list = new ArrayList<TwObj>();
					for (int i = 0; i < n; i++) {
						TwObj tw = new TwObj(titles[i], descs[i], picUrls[i], twUrls[i]);
						list.add(tw);
					}
					JSONArray fromObject = JSONArray.fromObject(list);
					r.setRespContent(fromObject.toString());
				}
				wxRuleDao.updateEntity(r);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean updateImageKeyWord(HttpServletRequest request) {
		try {
			String id = request.getParameter("id");
			// 规则名
			String ruleName = request.getParameter("ruleName");
			// 关键字
			String keyword = request.getParameter("keywords");
			// 回复类型
			String respType = request.getParameter("respType");
			// 回复内容
			String respContent = request.getParameter("respContent");
			String title, desc, picUrl, twUrl;
			WxRulesImage r = wxRuleImageDao.getEntityById(Long.valueOf(id));
			if (r != null) {// 更新
				r.setKw(keyword);
				r.setRuleName(ruleName);
				r.setRespType(respType);
				if ("text".equals(respType)) {
					// 文本消息，直接保存到此字段
					r.setRespContent(respContent);
				} else if ("news".equals(respType)) {
					// 单图文
					title = request.getParameter("title");
					desc = request.getParameter("desc");
					picUrl = request.getParameter("picUrl");
					twUrl = request.getParameter("twUrl");
					r.setTitle(title);
					r.setTwdesc(desc);
					r.setPicUrl(picUrl);
					r.setTwUrl(twUrl);
					// 图文消息， 以json格式保存到respContent字段
					TwObj tw = new TwObj(title, desc, picUrl, twUrl);
					JSONObject fromObject = JSONObject.fromObject(tw);
					r.setRespContent(fromObject.toString());
				} else if ("multi".equals(respType)) {// 多图文
					String[] titles = request.getParameterValues("title");
					String[] descs = request.getParameterValues("desc");
					String[] picUrls = request.getParameterValues("picUrl");
					String[] twUrls = request.getParameterValues("twUrl");
					int n = titles.length;
					List<TwObj> list = new ArrayList<TwObj>();
					for (int i = 0; i < n; i++) {
						TwObj tw = new TwObj(titles[i], descs[i], picUrls[i], twUrls[i]);
						list.add(tw);
					}
					JSONArray fromObject = JSONArray.fromObject(list);
					r.setRespContent(fromObject.toString());
				}
				wxRuleImageDao.updateEntity(r);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void deleteRule(Long id) {
		wxRuleDao.deleteEntity(id);
	}
	
	@Override
	public void deleteImageRule(Long id) {
		wxRuleImageDao.deleteEntity(id);
	}

	public static void main(String[] args) {
// List<TwObj> list = new ArrayList<TwObj>();
// for (int i = 0; i < 3; i++) {
// TwObj tw = new TwObj(i + "", i + "", i + "", i + "");
// list.add(tw);
// }
// JSONArray fromObject = JSONArray.fromObject(list);
// System.out.println(fromObject.toString());
// String json = "[{\"picUrl\":\"0\",\"title\":\"0\",\"twUrl\":\"0\",\"twdesc\":\"0\"}]";
// JSONArray f = JSONArray.fromObject(json);
// Object[] array = f.toArray();
		
//		float yj = (1610 * 2) / 100f;
//		System.out.println(yj);
		Calendar cl = Calendar.getInstance();
		int year = cl.get(Calendar.YEAR);
		int month = cl.get(Calendar.MONTH);
		int day = cl.get(Calendar.DAY_OF_MONTH);
		System.out.println(year + "-" + month +"-" + day);
		cl.set(year, month+1, day);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(cl.getTime()));
		
	}

	@Override
	public WxRules getRule(String id) {
		return wxRuleDao.getEntityById(Long.valueOf(id));
	}

	@Override
	public WxRulesImage getImageRule(String id) {
		return wxRuleImageDao.getEntityById(Long.valueOf(id));
	}
	
	// @Override
	// public List<Gift> getGiftPoolInfo() {
	// List<Gift> list = new ArrayList<Gift>();
	// String newProperty = Version.getInstance().getNewProperty("gifts");
	//		
	// String ct = Version.getInstance().getNewProperty("giftCount");
	//		
	// //礼品名称
	// String[] split = StringUtils.split(newProperty, "|");
	// //礼品数量
	// String[] giftCount = StringUtils.split(ct, "|");
	//		
	// //查已兑换
	// int dh1 = recordDao.countEntitiesByPropNames(new String[]{"giftType","isget"}, new Object[]{split[0],1});
	// //查未兑换
	// int wdh1 = recordDao.countEntitiesByPropNames(new String[]{"giftType","isget"}, new Object[]{split[0],0});
	// Gift g1 = new Gift(dh1, wdh1, Integer.valueOf(giftCount[0])-(dh1+wdh1));
	// list.add(g1);
	//		
	// //查已兑换
	// int dh2 = recordDao.countEntitiesByPropNames(new String[]{"giftType","isget"}, new Object[]{split[1],1});
	// //查未兑换
	// int wdh2 = recordDao.countEntitiesByPropNames(new String[]{"giftType","isget"}, new Object[]{split[1],0});
	// Gift g2 = new Gift(dh2, wdh2, Integer.valueOf(giftCount[1])-(dh2+wdh2));
	// list.add(g2);
	//		
	// //查已兑换
	// int dh3 = recordDao.countEntitiesByPropNames(new String[]{"giftType","isget"}, new Object[]{split[2],1});
	// //查未兑换
	// int wdh3 = recordDao.countEntitiesByPropNames(new String[]{"giftType","isget"}, new Object[]{split[2],0});
	// Gift g3 = new Gift(dh3, wdh3, Integer.valueOf(giftCount[2])-(dh3+wdh3));
	// list.add(g3);
	//		
	// return list;
	// }
	@Override
	public GameReport getGameReportData() {
		WxGameDao dao = (WxGameDao) ServerBeanFactory.getBean("wxGameDao");
		List findA = dao.getHibernateTemplate().find("SELECT SUM(visit) AS ct FROM WxGame WHERE page = 'a'");
		List findB = dao.getHibernateTemplate().find("SELECT SUM(visit) AS ct FROM WxGame WHERE page = 'b'");
		List findC = dao.getHibernateTemplate().find("SELECT SUM(visit) AS ct FROM WxGame WHERE page = 'c'");
		List findD = dao.getHibernateTemplate().find("SELECT SUM(visit) AS ct FROM WxGame WHERE page = 'd'");
		List findE = dao.getHibernateTemplate().find("SELECT SUM(visit) AS ct FROM WxGame WHERE page = 'e'");
		GameReport gr = new GameReport();
		if (findA == null || findA.isEmpty()) {
			gr.setA(0);
		} else {
			gr.setA(Integer.valueOf(findA.get(0).toString()));
		}
		if (findB == null || findB.isEmpty()) {
			gr.setB(0);
		} else {
			gr.setB(Integer.valueOf(findB.get(0).toString()));
		}
		if (findC == null || findC.isEmpty()) {
			gr.setC(0);
		} else {
			gr.setC(Integer.valueOf(findC.get(0).toString()));
		}
		if (findD == null || findD.isEmpty()) {
			gr.setD(0);
		} else {
			gr.setD(Integer.valueOf(findD.get(0).toString()));
		}
		if (findE == null || findE.isEmpty()) {
			gr.setE(0);
		} else {
			gr.setE(Integer.valueOf(findE.get(0).toString()));
		}
		return gr;
	}

	@Override
	public GameReport getGameUserReportData() {
		WxGameDao dao = (WxGameDao) ServerBeanFactory.getBean("wxGameDao");
		List findA = dao.getHibernateTemplate().find(
				"SELECT count(id) AS ct FROM ActRecord WHERE giftType = 'a' and isGet=3");
		List findB = dao.getHibernateTemplate().find(
				"SELECT count(id) AS ct FROM ActRecord WHERE giftType = 'b' and isGet=3");
		List findC = dao.getHibernateTemplate().find(
				"SELECT count(id) AS ct FROM ActRecord WHERE giftType = 'c' and isGet=3");
		List findD = dao.getHibernateTemplate().find(
				"SELECT count(id) AS ct FROM ActRecord WHERE giftType = 'd' and isGet=3");
		List findE = dao.getHibernateTemplate().find(
				"SELECT count(id) AS ct FROM ActRecord WHERE giftType = 'e' and isGet=3");
		GameReport gr = new GameReport();
		// 统计基数
		String s = Version.getInstance().getNewProperty("baseUser");
		int a = 100;
		int b = 100;
		int c = 100;
		int d = 100;
		int e = 100;
		if (s != null) {
			String string = StringUtils.split(s, "|")[0];
			a = Integer.valueOf(string);
			string = StringUtils.split(s, "|")[1];
			b = Integer.valueOf(string);
			string = StringUtils.split(s, "|")[2];
			c = Integer.valueOf(string);
			string = StringUtils.split(s, "|")[3];
			d = Integer.valueOf(string);
			string = StringUtils.split(s, "|")[4];
			e = Integer.valueOf(string);
		}
		if (findA == null || findA.isEmpty()) {
			gr.setA(0);
		} else {
			gr.setA(a + Integer.valueOf(findA.get(0).toString()));
		}
		if (findB == null || findB.isEmpty()) {
			gr.setB(0);
		} else {
			gr.setB(b + Integer.valueOf(findB.get(0).toString()));
		}
		if (findC == null || findC.isEmpty()) {
			gr.setC(0);
		} else {
			gr.setC(c + Integer.valueOf(findC.get(0).toString()));
		}
		if (findD == null || findD.isEmpty()) {
			gr.setD(0);
		} else {
			gr.setD(d + Integer.valueOf(findD.get(0).toString()));
		}
		if (findE == null || findE.isEmpty()) {
			gr.setE(0);
		} else {
			gr.setE(e + Integer.valueOf(findE.get(0).toString()));
		}
		return gr;
	}

	@Override
	public GameDailyReport getGameUserDailyReportData(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	@Override
	public Map<String, Object> getShowCases(int start, int limit, Map<String, String> paramMap) {
		return showcaseDao.queryRecord(start, limit, paramMap);
	}

	public void setShowcaseDao(ShowCaseDao showcaseDao) {
		this.showcaseDao = showcaseDao;
	}

	@Override
	public ShowCase addCase(Map<String, String> param) {
		ShowCase sc = new ShowCase();
		try {
			if (param.get("id") != null && !"0".equals(param.get("id"))) {
				sc = showcaseDao.getEntityById(Long.valueOf(param.get("id")));
			}
			sc.setTitle(param.get("title"));
			String columnId = param.get("columnId");
			if (columnId != null) {// 这里要判断一下，因为只有邮政咨询里的文章才有栏目之分
				Columns columns = this.columnDao.getEntityById(Long.valueOf(columnId));
				// 更新栏目
				sc.setColumns(columns);
			}
			sc.setAuthorName(param.get("authorName"));
			sc.setThecontent(param.get("content"));
			sc.setDocType(Integer.valueOf(param.get("docType")));
			if (param.get("id") != null && !"0".equals(param.get("id"))) {
				// 更新操作
				if (param.get("zan") != null) {
					sc.setZan(Integer.valueOf(param.get("zan")));
				}
				showcaseDao.updateEntity(sc);
			} else {
				// 新增。。。。。。。。
				sc.setCreateDate(DateUtil.getLongCurrentDate());
				sc.setZan(0);
				this.showcaseDao.saveEntity(sc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sc;
	}

	@Override
	public boolean delCase(String ids) {
		try {
			List<Long> idList = new ArrayList<Long>();
			String[] split = ids.split(",");
			for (String id : split) {
				idList.add(Long.valueOf(id));
			}
			logger.info("删除的ID...." + idList.toString());
			showcaseDao.deletAllEntities(idList);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delQuestion(String ids) {
		try {
			List<Long> idList = new ArrayList<Long>();
			String[] split = ids.split(",");
			for (String id : split) {
				idList.add(Long.valueOf(id));
			}
			logger.info("删除的ID...." + idList.toString());
			questionDao.deletAllEntities(idList);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delExperts(String ids) {
		try {
			List<Long> idList = new ArrayList<Long>();
			String[] split = ids.split(",");
			for (String id : split) {
				idList.add(Long.valueOf(id));
			}
			logger.info("删除的ID...." + idList.toString());
			expertsDao.deletAllEntities(idList);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void updateShowCase(ShowCase addCase) {
		showcaseDao.updateEntity(addCase);
	}

	@Override
	public void updateExpert(Experts ex) {
		expertsDao.updateEntity(ex);
	}

	@Override
	public Map<String, Object> getExperts(int start, int limit) {
		return expertsDao.queryRecord(start, limit);
	}

	public void setExpertsDao(ExpertsDao expertsDao) {
		this.expertsDao = expertsDao;
	}

	@Override
	public Experts addExpert(Map<String, String> param) {
		Experts expert = new Experts();
		try {
			if (param.get("id") != null && !"0".equals(param.get("id"))) {
				expert = this.expertsDao.getEntityById(Long.valueOf(param.get("id")));
			}
			expert.setExpName(param.get("expName"));
			expert.setExpDesc(param.get("expDesc"));
			if (param.get("id") != null && !"0".equals(param.get("id"))) {
				this.expertsDao.updateEntity(expert);
			} else {
				expert.setCreateDate(DateUtil.getLongCurrentDate());
				this.expertsDao.saveEntity(expert);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return expert;
	}

	// @Override
	// public GameDailyReport getGameUserDailyReportData(String targetDate) {
	// //深夜时间段的起始和结束
	// String dA1 = targetDate + " 00:00:00";
	// String dA2 = targetDate + " 08:00:00";
	// String sql1 = "SELECT count(id) AS ct FROM ActRecord WHERE windate >='" + dA1 + "' and windate <= '"+ dA2 + "'";
	// List findA = this.recordDao.getHibernateTemplate().find(sql1);
	// //上午时间段的起始和结束
	// String dB1 = targetDate + " 08:00:00";
	// String dB2 = targetDate + " 12:00:00";
	// String sql2 = "SELECT count(id) AS ct FROM ActRecord WHERE windate >='" + dB1 + "' and windate <= '"+ dB2 + "'";
	// List findB = this.recordDao.getHibernateTemplate().find(sql2);
	// //下午时间段的起始和结束
	// String dC1 = targetDate + " 12:01:00";
	// String dC2 = targetDate + " 18:00:00";
	// String sql3 = "SELECT count(id) AS ct FROM ActRecord WHERE windate >='" + dC1 + "' and windate <= '"+ dC2 + "'";
	// List findC = this.recordDao.getHibernateTemplate().find(sql3);
	// //下班-睡前时间段的起始和结束
	// String dD1 = targetDate + " 18:01:00";
	// String dD2 = targetDate + " 24:00:00";
	// String sql4 = "SELECT count(id) AS ct FROM ActRecord WHERE windate >='" + dD1 + "' and windate <= '"+ dD2 + "'";
	// List findD = this.recordDao.getHibernateTemplate().find(sql4);
	//		
	// GameDailyReport gdr = new GameDailyReport();
	// int a=0,b=0,c=0,d=0;//统计基数。。。
	// try{
	// String[] s = StringUtils.split(Version.getInstance().getNewProperty("userreport"), "|");
	// a = Integer.valueOf(s[0]);
	// b = Integer.valueOf(s[1]);
	// c = Integer.valueOf(s[2]);
	// d = Integer.valueOf(s[3]);
	// logger.info("a="+a + "|b=" + b + "|c=" +c +"|d="+d);
	// }catch(Exception e){
	// }
	//		
	//		
	// if(findA == null || findA.isEmpty()){
	// gdr.setA(0);
	// }else{
	// gdr.setA(a + Integer.valueOf(findA.get(0).toString()));
	// }
	//		
	// if(findB == null || findB.isEmpty()){
	// gdr.setB(0);
	// }else{
	// gdr.setB(b + Integer.valueOf(findB.get(0).toString()));
	// }
	//		
	// if(findC == null || findC.isEmpty()){
	// gdr.setC(0);
	// }else{
	// gdr.setC(c + Integer.valueOf(findC.get(0).toString()));
	// }
	//		
	// if(findD == null || findD.isEmpty()){
	// gdr.setD(0);
	// }else{
	// gdr.setD(d + Integer.valueOf(findD.get(0).toString()));
	// }
	//		
	// return gdr;
	// }
	@Override
	public List<Question> getQuestionsByDate(String date) {
		if (StringUtils.isEmpty(date)) {
			date = DateUtil.getShortCurrentDate().substring(0, 7);
		}
		String sql = "from Question where dateym like '" + date + "%'";
		List find = this.questionDao.getHibernateTemplate().find(sql);
		return find;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public void setAnswerDao(AnswerDao answerDao) {
		this.answerDao = answerDao;
	}

	@Override
	public void addQuestion(Question q) {
		q.setDateym(DateUtil.getLongCurrentDate());
		this.questionDao.saveEntity(q);
	}

	@Override
	public List<Topics> topics(int year) {
		return topicDao.getHibernateTemplate().find("from Topics where dateym like '" + year + "%'");
	}

	public void setTopicDao(TopicDao topicDao) {
		this.topicDao = topicDao;
	}

	@Override
	public void addTopic(Topics topic) {
		List find = topicDao.getHibernateTemplate().find("from Topics where dateym = '" + topic.getDateym() + "'");
		if (find.size() > 0) {
			Topics obj = (Topics) find.get(0);
			topicDao.deleteEntity(obj.getId());
		}
		this.topicDao.saveEntity(topic);
	}

	@Override
	public int saveAnswer(User user, String[] parameterValues, String snum) {
		// 分数
		int score = 0;
		// 单题得分
		int a = 100 / parameterValues.length;
		logger.info("单题分数==" + a);
		// parameterValues-1维数组 qid|答案
		int count = 0;
		for (String s : parameterValues) {
			logger.info("s==" + s);
			if (StringUtils.isNotBlank(s)) {
				count++;
			} else {
				continue;
			}
			Long qid = Long.valueOf(StringUtils.split(s, "|")[0]);
			String answer = StringUtils.split(s, "|")[1];
			// 通过answer里是否含有@符号来判断是否是多选题
			if (answer.indexOf("@") != -1) {
				// 是多选
				answer = answer.replaceAll("@", "|");
			}
			Answers as = new Answers();
			as.setAnswer(answer);
			as.setAnswerDate(DateUtil.getLongCurrentDate());
			as.setQid(qid);
			as.setFlag("T");// 在线考试
			as.setSnum(snum);
			if (user != null) {
				as.setOpenId(user.getOpenId());
				as.setUserName(user.getUsername());
			}
			answerDao.saveEntity(as);
			// 判断答案是否正确，计算得分
			Question q = questionDao.getEntityById(qid);
			if (q.getAnswer().equals(answer)) {
				score += a;
			}
		}
		return score;
	}

	@Override
	public List<GameReport> getReportList(Long topicId) {
		List<GameReport> l = new ArrayList<GameReport>();
		List<Question> list = this.questionDao.getEntitiesByOneProperty("topicId", topicId);
		for (Question q : list) {
			GameReport r = new GameReport();
			// 把统计sql的结果集转化成map（key-选项； value-人数）
			Map<String, String> map = new HashMap<String, String>();
			// 遍历问题，根据answer表的回答情况统计
			if (q.getFlag() == 0) {// 单选题
				String sql = "SELECT answer, COUNT(*) FROM Answers WHERE qid=" + q.getId() + " GROUP BY answer";
				List find = this.answerDao.getHibernateTemplate().find(sql);
				for (Object object : find) {
					Object[] o = (Object[]) object;
					map.put(o[0].toString(), o[1].toString());
				}
				r.setA(Integer.valueOf(map.get("A") == null ? "0" : map.get("A")));
				r.setB(Integer.valueOf(map.get("B") == null ? "0" : map.get("B")));
				r.setC(Integer.valueOf(map.get("C") == null ? "0" : map.get("C")));
				r.setD(Integer.valueOf(map.get("D") == null ? "0" : map.get("D")));
				r.setE(Integer.valueOf(map.get("E") == null ? "0" : map.get("E")));
				r.setF(Integer.valueOf(map.get("F") == null ? "0" : map.get("F")));
				l.add(r);
			} else if (q.getFlag() == 1) {// 多选
				String sqlA = "SELECT COUNT(*) FROM Answers WHERE qid=5 AND answer LIKE '%A%'";
				String sqlB = "SELECT COUNT(*) FROM Answers WHERE qid=5 AND answer LIKE '%B%'";
				String sqlC = "SELECT COUNT(*) FROM Answers WHERE qid=5 AND answer LIKE '%C%'";
				String sqlD = "SELECT COUNT(*) FROM Answers WHERE qid=5 AND answer LIKE '%D%'";
				String sqlE = "SELECT COUNT(*) FROM Answers WHERE qid=5 AND answer LIKE '%E%'";
				String sqlF = "SELECT COUNT(*) FROM Answers WHERE qid=5 AND answer LIKE '%F%'";
				List result = this.answerDao.getHibernateTemplate().find(sqlA);
				int a = Integer.valueOf(result.get(0).toString());
				result = this.answerDao.getHibernateTemplate().find(sqlB);
				int b = Integer.valueOf(result.get(0).toString());
				result = this.answerDao.getHibernateTemplate().find(sqlC);
				int c = Integer.valueOf(result.get(0).toString());
				result = this.answerDao.getHibernateTemplate().find(sqlD);
				int d = Integer.valueOf(result.get(0).toString());
				result = this.answerDao.getHibernateTemplate().find(sqlE);
				int e = Integer.valueOf(result.get(0).toString());
				result = this.answerDao.getHibernateTemplate().find(sqlF);
				int f = Integer.valueOf(result.get(0).toString());
				r.setA(a);
				r.setB(b);
				r.setC(c);
				r.setD(d);
				r.setE(e);
				r.setF(f);
				l.add(r);
			}
		}
		return l;
	}

	@Override
	public List<Question> getQuestionsByTopic(Long topicId) {
		String sql = "from Question where topicId=" + topicId + " and flag < 2";
		return this.questionDao.getHibernateTemplate().find(sql);
	}

	@Override
	public boolean delTopic(String ids) {
		try {
			List<Long> idList = new ArrayList<Long>();
			String[] split = ids.split(",");
			for (String id : split) {
				idList.add(Long.valueOf(id));
			}
			logger.info("删除的ID...." + idList.toString());
			topicDao.deletAllEntities(idList);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Gift> getGifts() {
		return this.giftDao.getAllEntities();
	}

	public void setGiftDao(GiftsDao giftDao) {
		this.giftDao = giftDao;
	}

	@Override
	public Gift addGift(Map<String, String> param) {
		Gift gift = new Gift();
		try {
			gift.setGiftName(param.get("giftName"));
			gift.setScore(Integer.valueOf(param.get("score")));
			gift.setGiftDesc(param.get("giftDesc"));;
			this.giftDao.saveEntity(gift);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return gift;
	}

	@Override
	public void updateGift(Gift gift) {
		this.giftDao.updateEntity(gift);
	}

	@Override
	public boolean delGift(String ids) {
		try {
			List<Long> idList = new ArrayList<Long>();
			String[] split = ids.split(",");
			for (String id : split) {
				idList.add(Long.valueOf(id));
			}
			logger.info("删除的ID...." + idList.toString());
			giftDao.deletAllEntities(idList);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public int getScoreById(Long uid) {
		User u = this.userDao.getEntityById(uid);
		return u.getScore();
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean duihuan(Long uid, Long giftId) {
		try {
			// 兑换扣分
			Gift gift = this.giftDao.getEntityById(giftId);
			User user = userDao.getEntityById(uid);
			if (gift.getScore() > user.getScore()) {
				logger.info("积分不够.....");
				return false;
			}
			user.setScore(user.getScore() - gift.getScore());
			userDao.updateEntity(user);
			// 记录兑换日志
			GiftLog log = new GiftLog();
			log.setGiftName(gift.getGiftName());
			log.setOpdate(DateUtil.getLongCurrentDate());
			log.setOperator(user.getNickName());
			log.setScore(gift.getScore());
			giftLogDao.saveEntity(log);
			logger.info("记录兑换记录完成.........." + gift.getGiftName() + "|" + user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return true;
	}

	@Override
	public boolean duihuanPrd(Long uid, Long prdId) {
// try {
// ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
// // 兑换扣分
// Products prd = dao.getEntityById(prdId);
// User user = userDao.getEntityById(uid);
// if (prd.getChargeScore() > user.getScore()) {
// logger.info("积分不够.....");
// return false;
// }
// user.setScore(user.getScore() - prd.getChargeScore());
// userDao.updateEntity(user);
// // 记录兑换日志
// GiftLog log = new GiftLog();
// log.setGiftName(prd.getTname());
// log.setOpdate(DateUtil.getLongCurrentDate());
// log.setOperator(user.getUsername());
// log.setScore(prd.getChargeScore());
// giftLogDao.saveEntity(log);
// logger.info("记录兑换记录完成.........." + prd.getTname() + "|兑换人：" + user.getUsername());
// } catch (Exception e) {
// e.printStackTrace();
// throw new RuntimeException();
// }
		return true;
	}

	public void setGiftLogDao(GiftLogDao giftLogDao) {
		this.giftLogDao = giftLogDao;
	}

	@Override
	public Map<String, Object> getGiftLogs(int start, int limit, Map<String, String> paramMap) {
		return giftLogDao.queryRecord(start, limit, paramMap);
	}

	@Override
	public boolean dhGift(String logId) {
		try {
			GiftLog l = giftLogDao.getEntityById(Long.valueOf(logId));
			l.setStatus(1);
			l.setMarkdate(DateUtil.getLongCurrentDate());
			giftLogDao.updateEntity(l);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public ShowCase getLastCase(String type) {
		List find = showcaseDao.getHibernateTemplate().find(
				"from ShowCase where docType=" + type + " order by createDate desc limit 0,1");
		if (find.size() > 0) {
			return (ShowCase) find.get(0);
		}
		return null;
	}

	@Override
	public Experts getLastExpert() {
		List find = this.expertsDao.getHibernateTemplate().find("from Experts order by id desc limit 0,1");
		if (find.size() > 0) {
			return (Experts) find.get(0);
		}
		return null;
	}

	@Override
	public ShowCase getArticle(String t, String id) {
		return this.showcaseDao.getEntityById(Long.valueOf(id));
	}

	@Override
	public Experts getExperts(Long uid) {
		return this.expertsDao.getEntityById(uid);
	}

	@Override
	public Map<String, Object> getUserList(int start, int limit, Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<User> list = new ArrayList<User>();
		String hql = "from User where 1=1 ";
		String countHql = "select count(id) from User where 1=1 ";
		String cond = " ";
		if (param != null && !param.isEmpty()) {
			if (param.get("keyword") != null && !StringUtils.isEmpty(param.get("keyword"))) {
				cond = " and (city like '%" + param.get("keyword") + "%' or country like '%" + param.get("keyword") + "%'";
				cond += " or telephone like '%" + param.get("keyword") + "%' or username like '%" + param.get("keyword")
						+ "%'";
			}
			
			// 所属分组
			if (param.get("orgId") != null && !StringUtils.isEmpty(param.get("orgId"))) {
				cond += " and org.id = " + param.get("orgId");
			}
			// 分销客标志
			if (param.get("flag") != null && !StringUtils.isEmpty(param.get("flag"))) {
				cond += " and flag = " + param.get("flag");
			}
			// 地区标志
			if (param.get("area") != null && !StringUtils.isEmpty(param.get("area"))) {
				//考虑到直辖市
				cond += " and (city = '" + param.get("area") + "' or province = '"+ param.get("area") +"')";
			}
		}
		hql += cond + " order by lastlogin desc";
		countHql += cond;
		List find = this.userDao.getHibernateTemplate().find(countHql);
		int totalCount = Integer.valueOf(find.get(0).toString());
		try {
			list = this.userDao.getPagingEntitiesByHql(hql, start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("total", totalCount);
		result.put("list", list);
		return result;
	}
	@Override
	public model.Paging getAuditOrgList(int page, int limit, Map<String, String> param) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Org> list = new ArrayList<Org>();
		String hql = "from Org where 1=1 ";
		String countHql = "select count(id) from Org where 1=1 ";
		String cond = " ";
		if (param != null && !param.isEmpty()) {
			
			if (param.get("orgCode") != null && !StringUtils.isEmpty(param.get("orgCode"))) {
				cond += " and parent.code = " + param.get("orgCode");
			}
			
			if (param.get("status") != null && !StringUtils.isEmpty(param.get("status"))) {
				cond += " and status = " + param.get("status");
			}
			
			// 其他条件。。。。。。
// // 所属分组
// if (param.get("orgId") != null && !StringUtils.isEmpty(param.get("orgId"))) {
// cond += " and org.id = " + param.get("orgId");
// }
		}
		hql += cond + " order by applyDate desc";
		countHql += cond;
		List find = this.orgDao.getHibernateTemplate().find(countHql);
		int totalCount = Integer.valueOf(find.get(0).toString());
		try {
			list = this.orgDao.getPagingEntitiesByHql(hql, start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
// result.put("total", totalCount);
// result.put("list", list);
		
		// 返回分页对象
		model.Paging pp = new model.Paging();
// List<Admin> resumeList = (List<Admin>) result.get("list");
		pp.setPage(Integer.valueOf(page));// 当前页码
		pp.setLimit(Integer.valueOf(limit));// 每页显示条数
		if (!list.isEmpty()) {
			pp.setTotal(totalCount);
			// 总页数
			if (totalCount % pp.getLimit() != 0) {
				pp.setTotalPage(totalCount / pp.getLimit() + 1);
			} else {
				pp.setTotalPage(totalCount / pp.getLimit());
			}
			logger.info("数据页数：" + pp.getTotalPage());
			pp.setDatas(list);
		}
		return pp;
	}

	@Override
	public boolean delUser(String id) {
		try {
			this.userDao.deleteEntity(Long.valueOf(id));
			return true;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public boolean disUser(String id) {
		try {
			User u = this.userDao.getEntityById(Long.valueOf(id));
			if (u != null) {
				u.setStatus(0);
				this.userDao.updateEntity(u);
			}
			return true;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public boolean enableUser(String id) {
		try {
			User u = this.userDao.getEntityById(Long.valueOf(id));
			if (u != null) {
				u.setStatus(1);
				this.userDao.updateEntity(u);
			}
			return true;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	@Override
	public User getUserById(Long uid) {
		return this.userDao.getEntityById(uid);
	}

	@Override
	public User updateUser(User u) {
		User entityById = this.userDao.getEntityById(u.getId());
		 entityById.setCity(u.getCity());
		 entityById.setCountry(u.getCountry());
		 entityById.setProvince(u.getProvince());
		// entityById.setCompany(u.getCompany());
		// entityById.setEmpnum(u.getEmpnum());
		// entityById.setJobtitle(u.getJobtitle());
		// entityById.setTelephone(u.getTelephone());
		entityById.setUsername(u.getUsername());
		if(u.getLastLogin() != null){
			entityById.setLastLogin(u.getLastLogin());
		}
		entityById.setSubstate(u.getSubstate());
		this.userDao.updateEntity(entityById);
		return entityById;
	}

	public ColumnDao getColumnDao() {
		return columnDao;
	}

	public void setColumnDao(ColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	@Override
	public List<Columns> getAllColumns(String model) {
		if (model == null) {
			model = "1";
		}
		return this.columnDao.getEntitiesByOneProperty("module", model);
	}

	@Override
	public void addColumn(String title, String model) {
		Columns cl = new Columns();
		cl.setTitle(title);
		cl.setModule(model);
		this.columnDao.saveEntity(cl);
	}

	@Override
	public void updateColumn(Long id, String title) {
		Columns cl = this.columnDao.getEntityById(id);
		cl.setTitle(title);
		this.columnDao.updateEntity(cl);
	}

	@Override
	public List<String> importUsers(List<Map<String, String>> dataList) {
		List<String> list = new ArrayList<String>();
		// 导入时，根据身份证判断是否重复，如果数据库已存在了，则更新此记录，以导入的为准。
		// 地市 县 工作单位 姓名 员工号 身份证号 岗位名称 联系电话
		int i = 1;
		for (Map<String, String> map : dataList) {
			String idCard = map.get("身份证号");
			String city = map.get("地市");
			String country = map.get("县");
			String company = map.get("工作单位");
			String username = map.get("姓名");
			String empnum = map.get("员工号");
			String jobtitle = map.get("岗位名称");
			String tel = map.get("联系电话");
			try {
				User u = this.userDao.getUniqueEntityByOneProperty("idCard", idCard);
				if (u != null) {// 更新
					logger.info("----更新用户记录------" + idCard + " : " + username + " : " + empnum);
					// u.setCity(city);
					// u.setCountry(country);
					// u.setCompany(company);
					u.setUsername(username);
					// u.setEmpnum(empnum);
					// u.setJobtitle(jobtitle);
					u.setTelephone(tel);
					this.userDao.updateEntity(u);
				} else {
					// 插入数据
					u = new User();
					// u.setIdCard(idCard);
					// u.setCity(city);
					// u.setCountry(country);
					// u.setCompany(company);
					u.setUsername(username);
					// u.setEmpnum(empnum);
					// u.setJobtitle(jobtitle);
					u.setTelephone(tel);
					this.userDao.saveEntity(u);
				}
			} catch (Exception e) {
				e.printStackTrace();
				list.add(i + "");// 记录错误行号
			}
			i++;
		}
		return list;
	}

	@Override
	public List<Columns> getColumns() {
		return this.columnDao.getAllEntities();
	}

	@Override
	public boolean clearQuestions() {
		try {
			this.questionDao.deleteAllEntities();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Map<String, Object> getQuestions(int start, int limit, Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Question> list = new ArrayList<Question>();
		String hql = "from Question where 1=1 ";
		String countHql = "select count(id) from Question where 1=1 ";
		String cond = " ";
		if (param != null && !param.isEmpty()) {
			if (param.get("keyword") != null && !StringUtils.isEmpty(param.get("keyword"))) {
				cond = " and qtitle like '%" + param.get("keyword") + "%' ";
			}
		}
		hql += cond + " order by id desc";
		countHql += cond;
		List find = this.questionDao.getHibernateTemplate().find(countHql);
		int totalCount = Integer.valueOf(find.get(0).toString());
		try {
			list = this.questionDao.getPagingEntitiesByHql(hql, start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("total", totalCount);
		result.put("list", list);
		return result;
	}

	@Override
	public List<PublishedStudy> getPublishedStudy() {
		return this.publishedStudyDao.getAllEntities();
	}

	@Override
	public List<PublishedTesting> getPublishedTesting() {
		return this.publishedTestingDao.getAllEntities();
	}

	public PublishedStudyDao getPublishedStudyDao() {
		return publishedStudyDao;
	}

	public void setPublishedStudyDao(PublishedStudyDao publishedStudyDao) {
		this.publishedStudyDao = publishedStudyDao;
	}

	@Override
	public boolean publishOnlineStudy(String ids) {
		try {
			// 清空数据库
			this.publishedStudyDao.deleteAllEntities();
			// 插入新的题目
			String[] split = StringUtils.split(ids, "|");
			for (String qid : split) {
				PublishedStudy ps = new PublishedStudy();
				if (StringUtils.isNotEmpty(qid)) {
					Question question = this.questionDao.getEntityById(Long.valueOf(qid));
					ps.setQuestion(question);
				}
				ps.setSeqno(0);
				ps.setPublishDate(DateUtil.getLongCurrentDate());
				this.publishedStudyDao.saveEntity(ps);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Question getQuestionsById(Long id) {
		return this.questionDao.getEntityById(id);
	}

	@Override
	public void updateQuestion(Question q) {
		Question quest = this.questionDao.getEntityById(q.getId());
		try {
			BeanUtils.copyProperties(quest, q);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		quest.setDateym(DateUtil.getLongCurrentDate());
		this.questionDao.updateEntity(quest);
	}

	public PublishedTestingDao getPublishedTestingDao() {
		return publishedTestingDao;
	}

	public void setPublishedTestingDao(PublishedTestingDao publishedTestingDao) {
		this.publishedTestingDao = publishedTestingDao;
	}

	@Override
	public boolean publishOnlineTesting(String ids, String testTitle, String testDesc, String testId) {
		try {
			// 如果testId不为空，则更新
			if (testId != null) {
				// 更新基础信息
				OnlineTests t = onlineTestsDao.getEntityById(Long.valueOf(testId));
				t.setTestTitle(testTitle);
				t.setTestDesc(testDesc);
				t.setPdate(DateUtil.getLongCurrentDate());
				onlineTestsDao.updateEntity(t);
				// 更新题目信息:找到之前的，删除，重新关联
				publishedTestingDao.deleteEntityByProperty("testId", Long.valueOf(testId));
				// 插入新的题目
				String[] split = StringUtils.split(ids, "|");
				for (String qid : split) {
					PublishedTesting pt = new PublishedTesting();
					if (StringUtils.isNotEmpty(qid)) {
						Question question = this.questionDao.getEntityById(Long.valueOf(qid));
						pt.setQuestion(question);
					}
					pt.setSeqno(0);
					pt.setTestId(t.getId());// 与考卷关联
					pt.setPublishDate(DateUtil.getLongCurrentDate());
					this.publishedTestingDao.saveEntity(pt);
				}
			} else {// 新增
				// 保存考试基本信息
				OnlineTests ol = new OnlineTests();
				ol.setTestTitle(testTitle);
				ol.setTestDesc(testDesc);
				ol.setPdate(DateUtil.getLongCurrentDate());
				long tid = onlineTestsDao.saveEntity(ol);
				// 插入新的题目
				String[] split = StringUtils.split(ids, "|");
				for (String qid : split) {
					PublishedTesting pt = new PublishedTesting();
					if (StringUtils.isNotEmpty(qid)) {
						Question question = this.questionDao.getEntityById(Long.valueOf(qid));
						pt.setQuestion(question);
					}
					pt.setSeqno(0);
					pt.setTestId(tid);// 与考卷关联
					pt.setPublishDate(DateUtil.getLongCurrentDate());
					this.publishedTestingDao.saveEntity(pt);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User getUserByOpenId(String openId) {
		return this.userDao.getUniqueEntityByOneProperty("openId", openId);
	}

	@Override
	public int getTestCount(Long id) {
		return publishedTestingDao.getEntitiesByOneProperty("testId", id).size();
	}

	@Override
	public Vote getVoteDetail(String id) {
		VoteDao vDao = (VoteDao) ServerBeanFactory.getBean("voteDao");
		VoteItemDao viDao = (VoteItemDao) ServerBeanFactory.getBean("voteItemDao");
		Vote vote = vDao.getEntityById(Long.valueOf(id));
		// 设置该问卷的题目
		List<VoteItem> list = viDao.getEntitiesByOneProperty("vote.id", Long.valueOf(id));
		if (list != null) {
			vote.setItems(list);
		}
		return vote;
	}

	@Override
	public ShowCase getYzInfoDetail(Long id) {
		return showcaseDao.getEntityById(id);
	}

	@Override
	public void setVoteStatus(Long id, String status) {
		VoteDao vDao = (VoteDao) ServerBeanFactory.getBean("voteDao");
		vDao.getHibernateTemplate().bulkUpdate("update Vote set vstatus=" + status + " where id=" + id);
		// 设置其余的状态
		if ("0".equals(status)) {
			vDao.getHibernateTemplate().bulkUpdate("update Vote set vstatus=1 where id <>" + id);
		} else {
			vDao.getHibernateTemplate().bulkUpdate("update Vote set vstatus=0 where id <>" + id);
		}
	}

	@Override
	public int getVoteCount() {
		VoteDao vDao = (VoteDao) ServerBeanFactory.getBean("voteDao");
		// 查找当前启用的问卷的题目数
		Vote v = vDao.getUniqueEntityByOneProperty("vstatus", 1);
		VoteItemDao viDao = (VoteItemDao) ServerBeanFactory.getBean("voteItemDao");
		return viDao.getEntityByHql("from VoteItem where vote.id = " + v.getId()).size();
	}

	@Override
	public Vote getActiveVote() {
		VoteDao vDao = (VoteDao) ServerBeanFactory.getBean("voteDao");
		VoteItemDao viDao = (VoteItemDao) ServerBeanFactory.getBean("voteItemDao");
		Vote v = vDao.getUniqueEntityByOneProperty("vstatus", 1);
		List<VoteItem> list = viDao.getEntityByHql("from VoteItem where vote.id = " + v.getId());
		v.setItems(list);
		return v;
	}

	@Override
	public int saveVote(User currentUser, String[] parameterValues, String empType, String jobType, String voteId) {
		VoteRecordDao viDao = (VoteRecordDao) ServerBeanFactory.getBean("voteRecordDao");
		VoteDao vDao = (VoteDao) ServerBeanFactory.getBean("voteDao");
		VoteRecord vr = null;
		for (String string : parameterValues) {
			String[] split = StringUtils.split(string, "|");
			vr = new VoteRecord();
			vr.setEmpType(empType);
			vr.setJobType(jobType);
			vr.setUsername(currentUser.getUsername());
			vr.setVoteItemId(Long.valueOf(split[0]));
			vr.setAnswer(split[1]);
			Vote v = vDao.getEntityById(Long.valueOf(voteId));
			if (v != null) {
				vr.setVote(v);
			}
			viDao.saveEntity(vr);
		}
		return 0;
	}

	@Override
	public boolean checkVoter(String voteId) {
		VoteRecordDao vrDao = (VoteRecordDao) ServerBeanFactory.getBean("voteRecordDao");
		List<VoteRecord> list = vrDao.getEntitiesByOneProperty("vote.id", Long.valueOf(voteId));
		if (!list.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, String>> getVoteReportData(Long voteId) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		VoteItemDao viDao = (VoteItemDao) ServerBeanFactory.getBean("voteItemDao");
		VoteRecordDao vrDao = (VoteRecordDao) ServerBeanFactory.getBean("voteRecordDao");
		List<VoteItem> vis = viDao.getEntitiesByOneProperty("vote.id", voteId);
		Map<String, String> map = null;
		for (VoteItem voteItem : vis) {
			String sql = "SELECT voteItemId, answer, COUNT(answer) FROM  VoteRecord WHERE vid=" + voteId
					+ " AND voteItemid=" + voteItem.getId() + " GROUP BY answer";
			List find = vrDao.getHibernateTemplate().find(sql);
			logger.info("find.size== " + find.size());
			map = new HashMap<String, String>();
			map.put("voteContent", voteItem.getItitle() + "|" + voteItem.getIcontent());// 题目的标题+答案
			map.put("A", "0");
			map.put("B", "0");
			map.put("C", "0");
			map.put("D", "0");
			map.put("E", "0");
			for (Object object : find) {
				Object[] objs = (Object[]) object;
				// 组装答案以及对应人数
				map.put(objs[1].toString(), "<font color=red>" + objs[2].toString() + "</font>");
			}
			list.add(map);
		}
		return list;
	}

	@Override
	public Training getTraining(Long id) {
		return trainingDao.getEntityById(id);
	}

	@Override
	public Training updateTraining(String title, String tcontent, String id) {
		Training t = null;
		if (StringUtils.isNotEmpty(id) && SysUtil.IsNumber(id)) {// 更新
			t = trainingDao.getEntityById(Long.valueOf(id));
			t.setTcontent(tcontent);
			t.setTtitle(title);
			trainingDao.updateEntity(t);
		} else {// 新增
			t = new Training(title, tcontent);
			t.setPdate(DateUtil.getLongCurrentDate());
			trainingDao.saveEntity(t);
		}
		return t;
	}

	@Override
	public Map<String, Object> getFeedbackList(int start, int limit) {
		FeedBackDao fbDao = (FeedBackDao) ServerBeanFactory.getBean("feedBackDao");
		int totalCount = fbDao.count();
		Map<String, Object> result = new HashMap<String, Object>();
		List<Feedback> list = new ArrayList<Feedback>();
		try {
			list = fbDao.getPagingEntities(start, limit);
			for (Feedback feedback : list) {
				User uu = this.userDao.getUniqueEntityByOneProperty("username", feedback.getUsername());
				if (uu != null) {
					feedback.setTelephone(uu.getTelephone());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("total", totalCount);
		result.put("list", list);
		return result;
	}

	/**
	 * @param trainingDao the trainingDao to set
	 */
	public void setTrainingDao(TrainingDao trainingDao) {
		this.trainingDao = trainingDao;
	}

	@Override
	public int getOnlineStudyCount() {
		return publishedStudyDao.count();
	}

	@Override
	public int saveDemoTestResult(User user, String[] parameterValues, String snum) {
		// 分数
		int score = 0;
		// 单题得分
		int a = 100 / parameterValues.length;
		logger.info("单题分数==" + a);
		// parameterValues-1维数组 qid|答案
		int count = 0;
		for (String s : parameterValues) {
			logger.info("s==" + s);
			if (StringUtils.isNotBlank(s)) {
				count++;
			} else {
				continue;
			}
			Long qid = Long.valueOf(StringUtils.split(s, "|")[0]);
			String answer = StringUtils.split(s, "|")[1];
			// 通过answer里是否含有@符号来判断是否是多选题
			if (answer.indexOf("@") != -1) {
				// 是多选
				answer = answer.replaceAll("@", "|");
			}
			Answers as = new Answers();
			as.setAnswer(answer);
			as.setAnswerDate(DateUtil.getLongCurrentDate());
			as.setQid(qid);
			as.setFlag("S");// 在线辅导
			as.setSnum(snum);
			if (user != null) {
				as.setOpenId(user.getOpenId());
				as.setUserName(user.getUsername());
			}
			answerDao.saveEntity(as);
			// 判断答案是否正确，计算得分
			Question q = questionDao.getEntityById(qid);
			if (q.getAnswer().equals(answer)) {
				score += a;
			}
		}
		return score;
	}

	@Override
	public void setTPStatus(Long id, String status) {
		TPDao vDao = (TPDao) ServerBeanFactory.getBean("tpDao");
		vDao.getHibernateTemplate().bulkUpdate("update TP set vstatus=" + status + " where id=" + id);
		// 设置其余的状态
		if ("0".equals(status)) {
			vDao.getHibernateTemplate().bulkUpdate("update TP set vstatus=1 where id <>" + id);
		} else {
			vDao.getHibernateTemplate().bulkUpdate("update TP set vstatus=0 where id <>" + id);
		}
	}

	@Override
	public void saveTP(Map<String, String> param, String[] parameterValues) {
		TPDao vDao = (TPDao) ServerBeanFactory.getBean("tpDao");
		TpitemDao tiDao = (TpitemDao) ServerBeanFactory.getBean("tpitemDao");
		TP tp = new TP();
		tp.setTtitle(param.get("tptitle"));
		tp.setTdesc(param.get("tdesc"));
		tp.setStype(Integer.valueOf(param.get("stype")));
		tp.setSlimit(Integer.valueOf(param.get("slimit")));
		tp.setPublishdate(DateUtil.getLongCurrentDate());
		tp.setVstatus(0);// 默认不启用
		vDao.saveEntity(tp);
		// 保存所有选项。。。
		for (String string : parameterValues) {
			TouPiaoItem tpi = new TouPiaoItem();
			tpi.setContent(string);
			tpi.setTp(tp);
			tiDao.saveEntity(tpi);
		}
	}

	@Override
	public TP getTP() {
		TPDao vDao = (TPDao) ServerBeanFactory.getBean("tpDao");
		TpitemDao tiDao = (TpitemDao) ServerBeanFactory.getBean("tpitemDao");
		TP tp = vDao.getUniqueEntityByOneProperty("vstatus", 1);
		if (tp != null) {
			List<TouPiaoItem> items = tiDao.getEntitiesByOneProperty("tp.id", tp.getId());
			tp.setItems(items);
		}
		return tp;
	}

	@Override
	public TP getTPById(Long id) {
		TpitemDao tiDao = (TpitemDao) ServerBeanFactory.getBean("tpitemDao");
		TPDao vDao = (TPDao) ServerBeanFactory.getBean("tpDao");
		TP tp = vDao.getEntityById(id);
		List<TouPiaoItem> items = tiDao.getEntitiesByOneProperty("tp.id", tp.getId());
		tp.setItems(items);
		return tp;
	}

	@Override
	public void updateTP(Long tpId, Map<String, String> param, String[] parameterValues) {
		TPDao vDao = (TPDao) ServerBeanFactory.getBean("tpDao");
		TpitemDao tiDao = (TpitemDao) ServerBeanFactory.getBean("tpitemDao");
		TP tp = vDao.getEntityById(tpId);
		if (tp != null) {
			tp.setTtitle(param.get("tptitle"));
			tp.setTdesc(param.get("tdesc"));
			tp.setStype(Integer.valueOf(param.get("stype")));
			tp.setSlimit(Integer.valueOf(param.get("slimit")));
			tp.setPublishdate(DateUtil.getLongCurrentDate());
			vDao.updateEntity(tp);
		}
		// 删除所有选项，重新添加
		tiDao.getHibernateTemplate().bulkUpdate("delete from TouPiaoItem where tp.id =" + tpId);
		// 重新添加所有选项。。。
		for (String string : parameterValues) {
			TouPiaoItem tpi = new TouPiaoItem();
			tpi.setContent(string);
			tpi.setTp(tp);
			tiDao.saveEntity(tpi);
		}
	}

	@Override
	public void updateTPCount(String optId) {
		TpitemDao tiDao = (TpitemDao) ServerBeanFactory.getBean("tpitemDao");
		TouPiaoItem tpi = tiDao.getEntityById(Long.valueOf(optId));
		tpi.setCount(tpi.getCount() + 1);
		tiDao.updateEntity(tpi);
	}

	@Override
	public void updateTPCount(String[] optId) {
		TpitemDao tiDao = (TpitemDao) ServerBeanFactory.getBean("tpitemDao");
		for (String id : optId) {
			TouPiaoItem tpi = tiDao.getEntityById(Long.valueOf(id));
			tpi.setCount(tpi.getCount() + 1);
			tiDao.updateEntity(tpi);
		}
	}

	@Override
	public boolean delFeedBack(String id) {
		try {
			FeedBackDao fbDao = (FeedBackDao) ServerBeanFactory.getBean("feedBackDao");
			fbDao.deleteEntity(Long.valueOf(id));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void updateStatistics(String destPage) {
		StatisticDao dao = (StatisticDao) ServerBeanFactory.getBean("statisticDao");
		String mname = "";
		if (destPage.equals("bizStudy.jsp-1")) {
			mname = "业务学习";
		} else if (destPage.equals("bizStudy.jsp-2")) {
			mname = "生活常识";
		} else if (destPage.indexOf("onlineDemoStudyIndex") != -1) {
			mname = "在线辅导";
		} else if (destPage.indexOf("onlineVoteIndex") != -1) {
			mname = "在线调查";
		} else if (destPage.indexOf("onlineTestIndex") != -1) {
			mname = "在线考试";
		} else if (destPage.indexOf("onlineTP") != -1) {
			mname = "在线投票";
		} else if (destPage.indexOf("training") != -1) {
			mname = "培训信息";
		} else if (destPage.indexOf("contactUs") != -1) {
			mname = "联系我们";
		} else if (destPage.indexOf("feedback") != -1) {
			mname = "意见反馈";
		} else if (destPage.indexOf("yzindex") != -1) {
			mname = "邮政资讯";
		} else {
			mname = "其他";
		}
		logger.info("SSSSSSSSSSSSSSSSSS---" + destPage);
		StaticData objInDB = dao.getUniqueEntityByOneProperty("menuName", mname);
		if (objInDB != null) {// 更新
			objInDB.setMenuName(mname);
			objInDB.setTotal(objInDB.getTotal() + 1);
			dao.updateEntity(objInDB);
		} else {
			StaticData sd = new StaticData();
			sd.setMenuName(mname);
			sd.setTotal(sd.getTotal() + 1);
			// 插入
			dao.saveEntity(sd);
		}
	}

	@Override
	public ScanReport getVisitReportData() {
		StatisticDao dao = (StatisticDao) ServerBeanFactory.getBean("statisticDao");
		ScanReport sr = new ScanReport();
		// 查出总共有多少分类
		List cats = dao.getHibernateTemplate().find("select menuName, total from StaticData");
		List<DataObject> list = new ArrayList<DataObject>();
		for (Object object : cats) {
			Object[] s = (Object[]) object;
			DataObject doo = new DataObject();
			doo.setKey(s[0].toString());
			doo.setValue(s[1].toString());
			list.add(doo);
		}
		sr.setDatas(list);
		return sr;
	}

	@Override
	public AboutUs updateAboutUs(String title, String tcontent) {
		aboutusDao.deleteAllEntities();
		AboutUs t = new AboutUs(title, tcontent);
		t.setPdate(DateUtil.getLongCurrentDate());
		aboutusDao.saveEntity(t);
		return t;
	}

	@Override
	public AboutUs getAboutus() {
		List<AboutUs> allEntities = aboutusDao.getAllEntities();
		if (allEntities.isEmpty()) {
			return new AboutUs("", "");
		}
		return allEntities.get(0);
	}

	/**
	 * @param aboutusDao the aboutusDao to set
	 */
	public void setAboutusDao(AboutusDao aboutusDao) {
		this.aboutusDao = aboutusDao;
	}

	@Override
	public Map<String, Object> getTrainingList(int start, int limit) {
		int totalCount = trainingDao.count();
		Map<String, Object> result = new HashMap<String, Object>();
		List<Training> list = new ArrayList<Training>();
		try {
			list = trainingDao.getPagingEntities(start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 排序
		Collections.sort(list);
		result.put("total", totalCount);
		result.put("list", list);
		return result;
	}

	@Override
	public void delTraining(Long id) {
		trainingDao.deleteEntity(id);
	}

	@Override
	public void delColumn(String id) {
		// 删除文章
		showcaseDao.deleteEntityByProperty("columns.id", Long.valueOf(id));
		columnDao.deleteEntity(Long.valueOf(id));
	}

	/**
	 * @param onlineTestsDao the onlineTestsDao to set
	 */
	public void setOnlineTestsDao(OnlineTestsDao onlineTestsDao) {
		this.onlineTestsDao = onlineTestsDao;
	}

	@Override
	public OnlineTests getOnlineTest() {
		return onlineTestsDao.getAllEntities().get(0);
	}

	@Override
	public void exportFeedBack(List<Feedback> list, ServletOutputStream outputStream) throws Exception {
		// 简历模版的列
		List<String> sheetColumn = CommonUtil.getFeedBackColumns();
		// 定义Excel
		HSSFWorkbook workbook = new HSSFWorkbook();// 初始化一个workbook
		HSSFCellStyle style = workbook.createCellStyle(); // 样式对象
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
		/** 字体begin */
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);// HSSFColor.VIOLET.index //字体颜色
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体增粗
		// 把字体应用到当前的样式
		style.setFont(font);
		// 设置sheet名称
		HSSFSheet sheet = workbook.createSheet("企业招聘信息列表"); // 创建一个表
		int columnCount = sheetColumn.size();
		HSSFRow headerRow = sheet.createRow(0);// 创建第一行表头
		for (int index = 0; index < columnCount; index++) {
			// 设置表头单元格,添加到Excel中
			HSSFCell cell = headerRow.createCell(index);
			cell.setCellValue(new HSSFRichTextString(sheetColumn.get(index)));
			cell.setCellStyle(style);
		}
		int rows = list.size();
		// 序号 姓名 电话 反馈内容 反馈时间
		for (int row = 1; row <= rows; row++) {
			HSSFRow dataRow = sheet.createRow(row);// 创建数据行
			Feedback c = list.get(row - 1);
			int colIndex = 0;
			// 序号
			dataRow.createCell(colIndex).setCellValue(new HSSFRichTextString(row + ""));
			colIndex++;
			// 姓名
			dataRow.createCell(colIndex).setCellValue(new HSSFRichTextString(c.getUsername()));
			colIndex++;
			// 电话
			dataRow.createCell(colIndex).setCellValue(new HSSFRichTextString(c.getTelephone()));
			colIndex++;
			// 反馈内容
			dataRow.createCell(colIndex).setCellValue(new HSSFRichTextString(c.getFcontent()));
			colIndex++;
			// 反馈时间
			dataRow.createCell(colIndex).setCellValue(new HSSFRichTextString(c.getFdate()));
			colIndex++;
		}
		workbook.write(outputStream);
		outputStream.flush();
	}

	@Override
	public List<OnlineTests> getAllTests() {
		return this.onlineTestsDao.getAllEntities();
	}

	@Override
	public OnlineTests getOnlineTest(Long id) {
		return this.onlineTestsDao.getEntityById(id);
	}

	@Override
	public List<PublishedTesting> getOnlineTests(Long testId) {
		return this.publishedTestingDao.getEntitiesByOneProperty("testId", testId);
	}

	@Override
	public boolean delTest(Long testId) {
		try {
			// 删除该试卷下的所有题目
			publishedTestingDao.deleteEntityByProperty("testId", testId);
			// 删除试卷
			this.onlineTestsDao.deleteEntity(testId);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

// @Override
// public Long addProduct(Map<String, String> param) {
// String tname = param.get("tname");
// String tips = param.get("tips");
// String guige = param.get("guige");
// String tdesc = param.get("tdesc");
// String tprice = param.get("tprice");
// String price = param.get("price");
// String stock = param.get("stock");
// String picUrl = param.get("picUrl");
// String discount = param.get("discount");
// String chargeScore = param.get("chargeScore");
// Products fp = new Products();
// fp.setTname(tname);
// fp.setTips(tips);
// fp.setGuige(guige);
// fp.setTdesc(tdesc);
// fp.setTprice(Float.valueOf(tprice));
// fp.setPrice(Float.valueOf(price));// 原价
// fp.setStock(Integer.valueOf(stock));// 库存数量
// fp.setTpicUrl(picUrl);
// fp.setDiscount(Float.valueOf(discount));
// fp.setChargeScore(Integer.valueOf(chargeScore));// 兑换积分
// fp.setTstatus(0);// 默认是下架
// fp.setPublishDate(DateUtil.getLongCurrentDate());// 发布时间
// ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
// return dao.saveEntity(fp);
// }

// @Override
// public void putOnSale(String id, int Status) {
// ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
// Products entity = dao.getEntityById(Long.valueOf(id));
// entity.setTstatus(Status);// 上下架
// dao.updateEntity(entity);
// }
//
// @Override
// public Map<String, Object> getProductList(int start, int limit, Map<String, String> paramMap) {
// ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
// return dao.queryRecord(start, limit, paramMap);
// }

	@Override
	public boolean delFoodCat(String id) {
		try {
			ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
			dao.deleteEntity(Long.valueOf(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Products getProductById(Long id) {
		ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
		return dao.getEntityById(id);
	}

	@Override
	public void updateFoodProduct(Products fp, String fileName) {
// ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
// Products entityById = dao.getEntityById(fp.getId());
// entityById.setTname(fp.getTname());
// entityById.setTips(fp.getTips());
// entityById.setGuige(fp.getGuige());
// entityById.setTdesc(fp.getTdesc());
// entityById.setStock(fp.getStock());
// entityById.setChargeScore(fp.getChargeScore());
// entityById.setTprice(fp.getTprice());
// entityById.setPrice(fp.getPrice());// 原价
// entityById.setDiscount(fp.getDiscount());
// if (!"".equals(fileName) && fileName != null) {
// // 需要更新图片
// entityById.setTpicUrl(fileName);
// }
// dao.updateEntity(entityById);
	}

	@Override
	public Products getProductByGuige(String productCat) {
		ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
		return dao.getUniqueEntityByOneProperty("guige", productCat);
	}

	@Override
	public Map<String, Object> getAllProductList(int start, int limit, Map<String, String> paramMap) {
		// paramMap -- 预留的查询参数
		paramMap.put("tstatus", "1");// 只查询上架的
		
		// //根据pid查到分类code
		if (paramMap.get("pid") != null && !paramMap.get("pid").equals("0") && !StringUtils.isEmpty(paramMap.get("pid"))) {
			 Long pid = Long.valueOf(paramMap.get("pid"));
			ProductCatDao dao = (ProductCatDao) ServerBeanFactory.getBean("productCatDao");
			paramMap.put("prdCatCode", dao.getEntityById(pid).getCode());
		}
		
		ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = dao.queryRecord(start, limit, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, Object> getMyOrders(int start, int limit, Map<String, String> param) {
		// paramMap -- 预留的查询参数
		OrdersDao dao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
		Map<String, Object> result = new HashMap<String, Object>();
		List<Orders> list = new ArrayList<Orders>();
		String hql = "from Orders where 1=1 ";
		String countHql = "select count(id) from Orders where 1=1 ";
		String cond = " ";
		if (param != null && !param.isEmpty()) {
			String userId = param.get("userId");
			String flag = param.get("flag");
			String category = param.get("category");
			if (userId != null && !StringUtils.isEmpty(userId)) {
				cond += " and user.id = " + userId;
			}
			if (category != null && !StringUtils.isEmpty(category)) {
				cond += " and category = " + category;
			}
			if (flag != null && !StringUtils.isEmpty(flag)) {
				if(!"all".equals(flag)){// 如果是all的话，就说明不用过滤，查询所有订单
					cond += " and status = " + flag;
				}
			}
		}
		hql += cond + " order by id desc";
		countHql += cond;
		List find = dao.getHibernateTemplate().find(countHql);
		int totalCount = Integer.valueOf(find.get(0).toString());
		try {
			list = dao.getPagingEntitiesByHql(hql, start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("total", totalCount);
		result.put("list", list);
		return result;
	}

	@Override
	public void updateOrderPayStatus(Map<String, String> params) {
		try{
			// -------------业务处理--------------------------------------------
			// 第一步：更新订单状态
			OrdersDao dao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
			OrdersDetailDao detailDao = (OrdersDetailDao) ServerBeanFactory.getBean("ordersDetailDao");
			String orderNo = params.get("orderNum");
	// String agentId = params.get("agentId");
			// 订单总金额
			Long totalFee = Long.valueOf(params.get("totalFee"));
			
			Orders order = dao.getUniqueEntityByOneProperty("orderNo", orderNo);
			if(order == null){// 说明订单不存在
				return;
			}
			// 通过订单知道该订单的支付者ID
			Long userId = order.getUser().getId();
			
			String openId = order.getUser().getOpenId();
			
			// 通过订单知道该订单的分销客ID
			Long agentId = order.getFxpersonId();
			
			// 通过订单知道该订单所属分销商
			String orgCode = order.getFxCode();
			payLogger.info("此订单所属分销商........." + orgCode);
			// 防止万一微信重复发送的处理
			if (order.getTransId() != null) {
				return;
			}
			if (order != null) {
				order.setBankType(params.get("bank"));
				order.setStatus(Integer.valueOf(params.get("status")));
				order.setTransId(params.get("transId"));
				// 记录支付时间
				order.setChargeDate(new Date());
				dao.updateEntity(order);
				
				//更新积分-- 2015-08-30, RMB与积分的比例，默认1:1
				String scoreRate = (Version.getInstance().getNewProperty("scoreRate") == null ? "1" : Version.getInstance().getNewProperty("scoreRate"));
				int score = (int) (totalFee * Integer.valueOf(scoreRate));
				User user = this.userDao.getEntityById(order.getUser().getId());
				user.setScore(user.getScore() + score);
				this.userDao.updateEntity(user);
				logger.info("[score][userId="+user.getId() + "]的积分更新完成...." + score);
			}
			// 第二步：更新 销量信息
			payLogger.info("更新销量 .........");
			ProductsDao prddao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
			List<OrdersDetail> list = detailDao.getEntitiesByOneProperty("orderNo", orderNo);
			for (OrdersDetail od : list) {
				Products prduct = prddao.getUniqueEntityByOneProperty("prdCode", od.getPrdCode());
				prduct.setScount(prduct.getScount() + od.getPrdCount());
				od.setProduct(prduct);
				prddao.updateEntity(prduct);
			}
			payLogger.info("更新销量 结束.........agentId====" + agentId+ "| orderNo--" + orderNo);
			
			
//			ProductCatDao pcDao = (ProductCatDao) ServerBeanFactory.getBean("productCatDao");
			CouponDao couponDao = (CouponDao) ServerBeanFactory.getBean("couponDao");
			//第三步：（ 可选） 如果是优惠券类型产品， 需要生产优惠券编码
			for (OrdersDetail od : list) {
				Products prd = prddao.getUniqueEntityByOneProperty("prdCode", od.getPrdCode());
//				ProductCat prdCat = pcDao.getUniqueEntityByOneProperty("code", prd.getPrdCatCode());
				//查找orderDetail，确定是几张优惠券
				int ccount = od.getPrdCount();
				for(int i=0; i< ccount; i++){
//				if("优惠券".equals(prdCat.getName())){
					//生成优惠券记录到特定表：wf_coupon
					Coupon cp = new Coupon();
					cp.setCouponCode(SysUtil.getRandomCode(8).toUpperCase()+"-"+SysUtil.getRandomNum(3));
					cp.setGenerateDate(DateUtil.getLongCurrentDate());
					cp.setCouponMoney(prd.getPrdDisPrice());
					cp.setCouponName(prd.getName());
					cp.setCouponType(Coupon.TYPE_MONEY);//优惠券类型：普通现金抵扣
					cp.setExpireDays(prd.getExpireDays());
					cp.setPrdCode(prd.getPrdCode());
					cp.setServiceId(prd.getServiceId());//该优惠券适用的服务商ID
					cp.setStatus(Coupon.STATE_NEW);//刚生成的状态，未使用
					cp.setUserid(userId);
					couponDao.saveEntity(cp);
					payLogger.info("时间："+ DateUtil.getLongCurrentDate() + ": 已经保存优惠券....");
//				}else if("洗车卡".equals(prdCat.getName())){
//					//生成洗车卡记录
//					this.createCleaningCards(prd, order.getUser());
//				}
				}
			}
			logger.info("开始清理购物车 .....");
			User user = this.userDao.getEntityById(userId);
			//清除购物车
			ShoppingCartDao scdao = (ShoppingCartDao) ServerBeanFactory.getBean("shoppingCartDao");
			// 清除购物车(只清除用户选择结账的)
			for (OrdersDetail od : list) {
				logger.info("user==" + user);
				logger.info("od.getProduct()==" + od.getProduct());
				scdao.getHibernateTemplate().bulkUpdate("delete from ShoppingCart where user.id = ? and product.id = ?", user.getId(), od.getProduct().getId());
			}
			logger.info("购物车清除完毕.....");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		// 如果是推广的订单，需要计算推广者的佣金：：：分销客佣金=订单金额*分销客佣金比例
//		if (orderNo.startsWith("T-") && agentId != null) {
//			payLogger.info("开始计算佣金.........");
//			// 1..........拿到此分销客的佣金比例
//			Org orgBycode = this.getOrgBycode(orgCode);
//			int personCommission = orgBycode.getPersonCommission();
//			
//			// 2...........根据订单金额，计算应得佣金，四舍五入;totalFee单位是分。
//			float yj = (totalFee * personCommission) / 100f;
//			
//			// 3...........保存佣金记录到佣金日志表,这个记录表方便将来做统计
//			try{
//				CommissionLogDao comDao = (CommissionLogDao) ServerBeanFactory.getBean("commissionLogDao");
//				CommissionLog cl = new CommissionLog();
//				cl.setAgentId(Long.valueOf(agentId));
//				cl.setCreateTime(DateUtil.getLongCurrentDate());
//				cl.setOrderNo(orderNo);
//				cl.setPayerId(userId);
//				cl.setOrgCode(orgCode);
//				cl.setYj(yj);
//				comDao.saveEntity(cl);
//				
//				payLogger.info("佣金计算完毕,此次分销客应得："+ yj +"分。");
//			}catch(Exception e){
//				e.printStackTrace();
//				payLogger.info("佣金计算异常。");
//			}
//		}
	}

	/**
	 * 生成洗车卡号 
	 * @param prd
	 * @param userId - 购买者ID
	 */
	public void createCleaningCards(Products prd, User user) {
		CleaningCardsDao ccDao = (CleaningCardsDao) ServerBeanFactory.getBean("cleaningCardsDao");
		CleaningCards cc = new CleaningCards();
		//洗车卡号
		cc.setCardCode("C"+DateUtil.getShortDateMark() + SysUtil.getRandomCode(5).toUpperCase());
		cc.setCardType(prd.getCardType());
		//根据卡类型设置过期时间
		cc.setExpireDate(this.getExpireDate(prd.getCardType()));
		cc.setGenerateDate(DateUtil.getLongCurrentDate());
		cc.setTotalPoints(this.getCleaningPoints(prd.getCardType()));
		cc.setLeftPoints(cc.getTotalPoints());//剩余次数与总数相等
		cc.setOpenId(user.getOpenId());
		cc.setServiceId(prd.getServiceId());
		logger.info("in createCleaningCards, serviceId=" + prd.getServiceId());
		cc.setStatus(CleaningCards.STATE_NEW);
		cc.setUserName(user.getUsername());
		ccDao.saveEntity(cc);
	}

	/**u
	 * 根据洗车卡类型，计算剩余次数； 只有次卡可以，月卡，年卡不计算次数，直接返回0 
	 * @param cardType
	 * @return
	 */
	private Integer getCleaningPoints(String cardType) {
		if(cardType.equals(Coupon.TYPE_CLEAN_10)){
			return 10;
		}else if(cardType.equals(Coupon.TYPE_CLEAN_20)){
			return 20;
		}else if(cardType.equals(Coupon.TYPE_CLEAN_30)){
			return 30;
		}else if(cardType.equals(Coupon.TYPE_CLEAN_50)){
			return 50;
		}else if(cardType.equals(Coupon.TYPE_CLEAN_100)){
			return 100;
		}else if(cardType.equals(Coupon.TYPE_CLEAN_1M)){
			return 0;
		}else if(cardType.equals(Coupon.TYPE_CLEAN_3M)){
			return 0;
		}else if(cardType.equals(Coupon.TYPE_CLEAN_6M)){
			return 0;
		}else if(cardType.equals(Coupon.TYPE_CLEAN_12M)){
			return 0;
		}
		return 0;
	}

	private String getExpireDate(String cardType) {
		String date = "";
		Calendar cl = Calendar.getInstance();
		int year = cl.get(Calendar.YEAR);
		int month = cl.get(Calendar.MONTH);
		int day = cl.get(Calendar.DAY_OF_MONTH);
		
		if(cardType.equals(Coupon.TYPE_CLEAN_10)){
			cl.set(year, month, day + 10);
		}else if(cardType.equals(Coupon.TYPE_CLEAN_20)){
			cl.set(year, month, day + 20);
		}else if(cardType.equals(Coupon.TYPE_CLEAN_30)){
			cl.set(year, month, day + 30);
		}else if(cardType.equals(Coupon.TYPE_CLEAN_50)){
			cl.set(year, month, day + 50);
		}else if(cardType.equals(Coupon.TYPE_CLEAN_100)){
			cl.set(year, month, day + 100);
		}else if(cardType.equals(Coupon.TYPE_CLEAN_1M)){
			cl.set(year, month+1, day);
		}else if(cardType.equals(Coupon.TYPE_CLEAN_3M)){
			cl.set(year, month+3, day);
		}else if(cardType.equals(Coupon.TYPE_CLEAN_6M)){
			cl.set(year, month+6, day);
		}else if(cardType.equals(Coupon.TYPE_CLEAN_12M)){
			cl.set(year, month+12, day);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = sdf.format(cl.getTime());
		logger.info(cardType + "类型，洗车卡到期时间："+ date);
		return date;
	}

	@Override
	public List<ConfigParam> getConfigParams() {
		ConfigParamDao dao = (ConfigParamDao) ServerBeanFactory.getBean("configParamDao");
		return dao.getAllEntities();
	}

	@Override
	public void updateConfigParam(String key, String value) {
		ConfigParamDao dao = (ConfigParamDao) ServerBeanFactory.getBean("configParamDao");
		dao.getHibernateTemplate().bulkUpdate("update ConfigParam set cvalue='" + value + "' where ckey='" + key + "'");
	}

	@Override
	public void updateConfigParam(Map<String, String> paramMap) {
		ConfigParamDao dao = (ConfigParamDao) ServerBeanFactory.getBean("configParamDao");
		Set<String> keySet = paramMap.keySet();
		for (String key : keySet) {
			dao.getHibernateTemplate().bulkUpdate(
					"update ConfigParam set cvalue='" + paramMap.get(key) + "' where ckey='" + key + "'");
		}
	}

	@Override
	public Map<String, Object> getPagingObjList(String daoBeanName, int start, int limit, Map<String, String> param) {
		EnhancedHibernateDaoSupport dao = (EnhancedHibernateDaoSupport) ServerBeanFactory.getBean(daoBeanName);
		Map<String, Object> result = new HashMap<String, Object>();
		List<Orders> list = new ArrayList<Orders>();
		String hql = "from Orders where 1=1 ";
		String countHql = "select count(id) from Order where 1=1 ";
		String cond = " ";
		if (param != null && !param.isEmpty()) {
			if (param.get("startDate") != null && !StringUtils.isEmpty(param.get("startDate"))) {
				cond += " and orderDate >= '" + param.get("startDate") + "' ";
			}
			if (param.get("endDate") != null && !StringUtils.isEmpty(param.get("endDate"))) {
				cond += " and orderDate <= '" + param.get("endDate") + "' ";
			}
			if (param.get("vname") != null && !StringUtils.isEmpty(param.get("vname"))) {
				cond += " and vname = '" + param.get("vname") + "' ";
			}
		}
		if (param.get("vip") != null && !StringUtils.isEmpty(param.get("vip"))) {
			// 查的是推广大V的订单，订单的vname必须不能为空
			cond += " and vname is not null and vname <> ''";
		}
		hql += cond + " order by id desc";
		countHql += cond;
		List find = dao.getHibernateTemplate().find(countHql);
		int totalCount = Integer.valueOf(find.get(0).toString());
		try {
			list = dao.getPagingEntitiesByHql(hql, start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("total", totalCount);
		result.put("list", list);
		return result;
	}

	@Override
	public User getUserByNameAndPwd(String username, String oldpwd) {
		return userDao
				.getUniqueEntityByPropNames(new String[] { "username", "password" }, new Object[] { username, SysUtil.encodeBase64(oldpwd) });
	}

	@Override
	public void saveUser(User u) {
		User userByTelphone = userDao.getUserByTelphone(u.getTelephone());
		UserRegisterReportDao userRegisterReportDao = (UserRegisterReportDao) ServerBeanFactory.getBean("userRegisterReportDao");
		if(userByTelphone == null){
			userDao.saveEntity(u);
			
			// 统计报表
			int year = DateUtil.getYear(u.getRegDate());
			int month = DateUtil.getMonth(u.getRegDate());
			int week = DateUtil.getWeek(u.getRegDate());
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
	}

	@Override
	public int addOrUpdateVip(String vipname, Long vid, String tgGuige) {
		SpreadDao dao = (SpreadDao) ServerBeanFactory.getBean("spreadDao");
		// 生成推广链接，这个链接其实是一个授权链接
		String appId = Version.getInstance().getNewProperty("APPID");
		String redirectUrl = "";
		try {
			redirectUrl = URLEncoder.encode(Version.getInstance().getNewProperty("auth_redirect_url"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (tgGuige == null) {
			tgGuige = "2L";
		}
		// 参数,用分号间隔
		String param = vipname + ";" + tgGuige;
		String spreadUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri="
				+ redirectUrl + "&response_type=code&scope=snsapi_userinfo&state=" + param + "#wechat_redirect";
		if (vid.longValue() > 0) {
			// 更新操作
			Spreader v = dao.getEntityById(vid);
			if (v != null) {
				v.setWxName(vipname);
				v.setSpUrl(spreadUrl);
				dao.updateEntity(v);
			}
		} else {
			// Spreader u = dao.getUniqueEntityByOneProperty("wxName", vipname);
			// if(u != null){
			// return 0;//已存在
			// }else{
			Spreader s = new Spreader();
			s.setWxName(vipname);
			s.setScore(0);
			s.setSpUrl(spreadUrl);// 推广链接
			dao.saveEntity(s);
			// }
		}
		return 1;
	}

	@Override
	public Spreader getSpreadVip(String vname) {
		SpreadDao dao = (SpreadDao) ServerBeanFactory.getBean("spreadDao");
		return dao.getUniqueEntityByOneProperty("wxName", vname);
	}

	@Override
	public List<Spreader> getAllVip() {
		SpreadDao dao = (SpreadDao) ServerBeanFactory.getBean("spreadDao");
		return dao.getAllEntities();
	}

	@Override
	public int delVip(Long id) {
		try {
			SpreadDao dao = (SpreadDao) ServerBeanFactory.getBean("spreadDao");
			dao.deleteEntity(id);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public Map<String, Object> loadPj(int start, int limit, Map<String, String> paramMap) {
		PjDao dao = (PjDao) ServerBeanFactory.getBean("pjDao");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = dao.queryRecord(start, limit, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Paging getPjList(int page, int limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		model.Paging pp = new model.Paging();
		PjDao dao = (PjDao) ServerBeanFactory.getBean("pjDao");
		ProductsDao prdDao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
		OrdersDao ordersDao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
		Map<String, Object> map = dao.queryRecord(start, limit, paramMap);
		// -----------------拼装分页对象 -------------------------------------
		List<Pj> dataList = (List<Pj>) map.get("list");
		// 填充非持久属性
		for (Pj pj : dataList) {
			if (prdDao.getEntityById(pj.getPrdid()) != null) {
				pj.setPrdName(prdDao.getEntityById(pj.getPrdid()).getName());
			} else {
				pj.setPrdName("{该商品[id:" + pj.getPrdid() + "]已删除}");
			}
			if(ordersDao.getEntityById(pj.getOrderId()) != null){
				pj.setOrderNum(ordersDao.getEntityById(pj.getOrderId()).getOrderNo());
			}
		}
		int total = Integer.valueOf(map.get("total").toString());
		pp.setPage(Integer.valueOf(page));// 当前页码
		pp.setLimit(Integer.valueOf(limit));// 每页显示条数
		if (!dataList.isEmpty()) {
			pp.setTotal(total);
			// 总页数
			if (total % pp.getLimit() != 0) {
				pp.setTotalPage(total / pp.getLimit() + 1);
			} else {
				pp.setTotalPage(total / pp.getLimit());
			}
			logger.info("数据页数：" + pp.getTotalPage());
			pp.setDatas(dataList);
		}
		return pp;
	}

	@Override
	public boolean delPj(Long id) {
		try {
			PjDao dao = (PjDao) ServerBeanFactory.getBean("pjDao");
			dao.deleteEntity(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Spreader getSpreaderByName(String username) {
		SpreadDao dao = (SpreadDao) ServerBeanFactory.getBean("spreadDao");
		return dao.getUniqueEntityByOneProperty("wxName", username);
	}

	@Override
	public float getDeliverFee(String orgCode) {
		//这个平台不是多租户， 统一从全局参数里获取
//		OrgDao dao = (OrgDao) ServerBeanFactory.getBean("orgDao");
//		Org org = dao.getUniqueEntityByOneProperty("code", orgCode);
//// ConfigParam cp = cpDao.getUniqueEntityByOneProperty("ckey", "YUN_FEE");
//		if (org != null) {
//			return Float.valueOf(org.getDeleverFee()/100f);
//		}
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		return Float.valueOf(service.getConfigParam("YUN_FEE"));
	}

	@Override
	public String[] loadVipIndexInfo(String vname) {
		ConfigParamDao cpDao = (ConfigParamDao) ServerBeanFactory.getBean("configParamDao");
		OrdersDao OrdersDao = (OrdersDao) ServerBeanFactory.getBean("OrdersDao");
		ConfigParam cp = cpDao.getUniqueEntityByOneProperty("ckey", "VIP_DISCOUNT");
		String cvalue = "0.1";
		// 折扣率
		if (cp != null) {
			cvalue = cp.getCvalue();
		} else {
			cvalue = "0.1";// 默认10%
		}
		int score = 0;
		List<Orders> orders = OrdersDao.getEntitiesByOneProperty("vname", vname);
		for (Orders order : orders) {
			// 已经支付的单子 订单状态 0-未支付， 1-已支付， 2-支付失败， 3-已取消
			if (order.getStatus() == 1) {
				score += order.getFee() * Float.valueOf(cvalue);
			}
		}
		String[] sarr = new String[2];
		sarr[0] = cvalue;
		sarr[1] = score + "";
		return sarr;
	}

	@Override
	public void updateProductPic(Long prdId, String fullPath) {
		ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
		Products entityById = dao.getEntityById(prdId);
// entityById.setTpicUrl(fullPath);
		entityById.setPicUrl1(fullPath);
		
		dao.updateEntity(entityById);
	}

	@Override
	public Long addProduct(Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getProductList(int start, int limit, Map<String, String> param) {
		ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
		Map<String, Object> result = new HashMap<String, Object>();
		List<Products> list = new ArrayList<Products>();
		String hql = "from Products where 1=1 ";
		String countHql = "select count(id) from Products where 1=1 ";
		String cond = " ";
		
		// 根据商家代码，找出该商家的产品分类编码集合
		// 属于该分销商的分类code
		ProductCatDao catDao = (ProductCatDao) ServerBeanFactory.getBean("productCatDao");
		PjDao pjDao = (PjDao) ServerBeanFactory.getBean("pjDao");
		
		List<ProductCat> cats = catDao.getEntitiesByOneProperty("fxCode", param.get("orgCode"));
		if(!cats.isEmpty()){
			StringBuffer sb = new StringBuffer("'");
			int i=0;
			for (ProductCat productCat : cats) {
				if(i == cats.size() - 1){// 最后一个元素
					sb.append(productCat.getCode() +"'");
				}else{
					sb.append(productCat.getCode() + "','");
				}
				i++;
			}
			cond += " and prdCatCode in ("+ sb.toString() +")";
		}else{
			// 该商家没有建立分类， 当然要返回空
			result.put("total", 0);
			result.put("list", list);
			return result;
		}
		// 通过产品分类集合构造子查询，过滤商品
		
		if (param != null && !param.isEmpty()) {
// // 分销商编码
			if (param.get("keyword") != null && !StringUtils.isEmpty(param.get("keyword"))) {
				cond += " and name like '%" + param.get("keyword") + "%'";
			}
		}
		hql += cond + " order by createDate desc";
		countHql += cond;
		List find = dao.getHibernateTemplate().find(countHql);
		int totalCount = Integer.valueOf(find.get(0).toString());
		try {
			list = dao.getPagingEntitiesByHql(hql, start, limit);
			// 增加评价数
			for (Object object : list) {
				Products p = (Products) object;
				p.setPjcount(pjDao.countEntitiesByPropNames("prdid", p.getId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("total", totalCount);
		result.put("list", list);
		
		return result;
	}

	@Override
	public void putOnSale(String id, int status) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public List<OrgObj> loadOrg() {
		List<OrgObj> result = new ArrayList<OrgObj>();
		OrgDao dao = (OrgDao) ServerBeanFactory.getBean("orgDao");
		List<Org> allEntities = dao.getAllEntities();
		OrgObj oo = null;
		for (Org org : allEntities) {
			oo = new OrgObj();
			oo.setId(org.getId());
			if (org.getParent() == null) {
				oo.setParent("#");
			} else {
				oo.setParent(org.getParent().getId().toString());
			}
			oo.setText(org.getOrgname());
			oo.setOrgCode(org.getCode());
			result.add(oo);
		}
		return result;
	}
	
	
	
	@Override
	public List<OrgObj> loadOrg(Long userid, int status) {
		List<OrgObj> result = new ArrayList<OrgObj>();
		OrgDao dao = (OrgDao) ServerBeanFactory.getBean("orgDao");
		
		// 先找出所属分销机构的code
		Org chargeOrg = dao.getUniqueEntityByOneProperty("charger.id", userid);
		
		// 查找自己管辖范围内的分销商
		String sql = "";
		if(userid == 0){// 说明是管理员，加载所有的
			sql = "from Org where  status = " + status + " order by applyDate desc";
		}else if(userid == -1){
			// 普通管理员
			sql = "from Org where  status = " + status + " and pid is null order by applyDate desc";
		}else{
// sql = "from Org where (code like '"+ chargeOrg.getCode() +"%' or code='000') and status = " + status + " order by
// applyDate desc";
			sql = "from Org where (code like '"+ chargeOrg.getCode() +"%') and status = " + status + " order by applyDate desc";
		}
		
		List<Org> allEntities = dao.getHibernateTemplate().find(sql);
		OrgObj oo = null;
		for (Org org : allEntities) {
			oo = new OrgObj();
			oo.setId(org.getId());
			if(userid == 0){// 说明是管理员，加载所有的
				if(org.getParent() == null ){
					oo.setParent("#");// JSTREE的规范： 顶级节点（其实就是当前登录管理员所属的分销商），需要设置parent为#
				}else{
					oo.setParent(org.getParent().getId().toString());
				}
			}else{
				if(org.getParent() == null || org.getCode().equals(chargeOrg.getCode())){
					oo.setParent("#");// JSTREE的规范： 顶级节点（其实就是当前登录管理员所属的分销商），需要设置parent为#
				}else{
					oo.setParent(org.getParent().getId().toString());
				}
			}
			
// if (org.getParent() == null) {
// oo.setParent("#");
//				
// //如果当前用户不是总分销商，那么就禁用该节点
// if(chargeOrg != null && chargeOrg.getCode().length() > org.getCode().length()){
// oo.setState(new State(true, true));
// }
//				
// } else {
// oo.setParent(org.getParent().getId().toString());
// }
			oo.setText(org.getOrgname());
			if(org.getParent() != null){
				oo.setParentName(org.getParent().getOrgname());
			}
			oo.setOrgCode(org.getCode());
			oo.setOrgname(org.getOrgname());
			oo.setApplyDate(org.getApplyDate());
			oo.setTelephone(org.getTelephone());
			oo.setCommission(org.getCommission());
			result.add(oo);
		}
		return result;
	}
	@Override
	public List<OrgObj> loadMenu(Long orgId) {
		List<OrgObj> result = new ArrayList<OrgObj>();
		WxMenuDao dao = (WxMenuDao) ServerBeanFactory.getBean("wxMenuDao");
		
		// 查找自己管辖范围内的分销商
		String sql = "from WxMenu where orgId=" + orgId;
		
		List<WxMenu> allEntities = dao.getHibernateTemplate().find(sql);
		
//		if(allEntities.size() > 0){//测试
		if(allEntities.size() == 0){
			synMenuFromWx(orgId, result, dao);
			//再查询一次
			allEntities = dao.getHibernateTemplate().find(sql);
		}
		OrgObj oo = null;
		for (WxMenu wxMenu : allEntities) {
			oo = new OrgObj();
			oo.setId(wxMenu.getId());
			if(wxMenu.getParent() == null){
				oo.setParent("#");// JSTREE的规范： 顶级节点需要设置parent为#
			}else{
				oo.setParent(wxMenu.getParent().getId().toString());
			}
			
			oo.setText(wxMenu.getName());
			oo.setMtype(wxMenu.getType());
			oo.setKey(wxMenu.getKey());
			oo.setUrl(wxMenu.getUrl());
			if(wxMenu.getParent() != null){
				oo.setParentName(wxMenu.getParent().getName());
			}
			result.add(oo);
		}
		
		return result;
	}

	private void synMenuFromWx(Long orgId, List<OrgObj> result, WxMenuDao dao) {
		List<WxMenu> bos = new ArrayList<WxMenu>();
		
		Org org = this.getOrgById(orgId);
		//从公众平台同步
		String token = WeiXinUtil.getToken(org.getAppid(), org.getAppsecret());
		// 查询最新的菜单
		String mstr = WeiXinUtil.getLastMenu(token);
		JSONObject fo = JSONObject.fromObject(mstr);
		logger.info(fo.toString());
		JSONArray array = fo.getJSONArray("button");
		if(array.size() == 0){
			return;
		}
		for (Object object : array) {//遍历一级菜单
			JSONObject jo = (JSONObject) object;
			//一级菜单的类型
			Object mtype = jo.get("type");
			WxMenu topM = new WxMenu();
			topM.setParent(null);
			if(mtype == null){//说明有二级菜单
				//不管这个一级菜单有没有子菜单，都需要保存到数据库
				topM.setName(jo.getString("name"));
				topM.setKey("");
				topM.setOrgId(orgId);
				topM.setType(null);
				topM.setUrl(null);
			}else{//没有二级菜单
				if(mtype.toString().equals("click")){
					topM.setKey(jo.getString("key"));
				}else if(mtype.toString().equals("view")){
					topM.setUrl(jo.getString("url"));
				}
				topM.setName(jo.getString("name"));
				topM.setOrgId(orgId);
				topM.setType(mtype.toString());
			}
			bos.add(topM);
		}
		
		Map<String, WxMenu> topMenuMap = new LinkedHashMap<String, WxMenu>();
		//一级菜单写入数据库， 放入map供后续调用
		for (WxMenu wm : bos) {
			dao.saveEntity(wm);
			topMenuMap.put(wm.getName(), wm);
		}
		bos.clear();
		logger.info("一级菜单保存完毕....");
		
		//
		//------------- 处理二级菜单 ---------------
		for (Object object : array) {//遍历一级菜单
			JSONObject jo = (JSONObject) object;
			//一级菜单的类型
			Object mtype = jo.get("type");
			//一级菜单的名称
			String name = jo.getString("name");
			if(mtype == null){//说明有二级菜单,这里只处理二级菜单
				JSONArray subMenus = jo.getJSONArray("sub_button");
				for (Object sm : subMenus) {
					JSONObject subJo = (JSONObject) sm;
					String submType = subJo.getString("type");
					WxMenu subM = new WxMenu();
					if(submType.toString().equals("click")){
						subM.setKey(subJo.getString("key"));
					}else if(submType.toString().equals("view")){
						subM.setUrl(subJo.getString("url"));
					}
					subM.setName(subJo.getString("name"));
					subM.setOrgId(orgId);
					subM.setParent(topMenuMap.get(name));//必须先保存一级菜单
					subM.setType(submType.toString());
					
					bos.add(subM);
				}
			}
		}
		
		//写入数据库
		for (WxMenu wm : bos) {
			dao.saveEntity(wm);
		}
		bos.clear();
		logger.info("二级菜单保存完毕....");
		
//		dao.getSessionFactory().getCurrentSession().flush();
	}
	
	@Override
	public List<OrgObj> loadPrdCat(String orgCode) {
		List<OrgObj> result = new ArrayList<OrgObj>();
		ProductCatDao dao = (ProductCatDao) ServerBeanFactory.getBean("productCatDao");
		
		// 先找出所属分销机构的分类
		List<ProductCat> allEntities = dao.getEntitiesByOneProperty("fxCode", orgCode);
		OrgObj oo = null;
		for (ProductCat pc : allEntities) {
			oo = new OrgObj();
			oo.setId(pc.getId());
			if(pc.getParentId() == 0 ){
				oo.setParent("#");// JSTREE的规范： 顶级节点（其实就是当前登录管理员所属的分销商），需要设置parent为#
			}else{
				oo.setParent(pc.getParentId().toString());
			}
			
			oo.setText(pc.getName());
			if(pc.getParentId() != 0){
				oo.setParentName(dao.getUniqueEntityByOneProperty("id", pc.getParentId()).getName());
			}
			oo.setOrgCode(pc.getCode());
			oo.setOrgname(pc.getName());
			oo.setPicUrl(pc.getPicUrl());
			result.add(oo);
		}
		return result;
	}
	public List<OrgObj> loadDaili(Long pid) {
		List<OrgObj> result = new ArrayList<OrgObj>();
		AdminDao dao = (AdminDao) ServerBeanFactory.getBean("adminDao");
		
		// 先找出此平台下的所有管理员
		List<Admin> allEntities = dao.getEntitiesByOneProperty("pid", pid);
		OrgObj oo = null;
		for (Admin admin : allEntities) {
			//只过滤出代理商，服务商去掉
			if(admin.getRoleString().indexOf("代理商") == -1){
				continue;
			}
			oo = new OrgObj();
			oo.setId(admin.getId());
			oo.setParent("#");// JSTREE的规范： 顶级节点（其实就是当前登录管理员所属的分销商），需要设置parent为#
			
			oo.setText(admin.getCompany());
			oo.setOrgCode("");
			oo.setOrgname("");
			oo.setPicUrl("");
			result.add(oo);
		}
		return result;
	}

	@Override
	public Org getCharger(Long orgId) {
		OrgDao dao = (OrgDao) ServerBeanFactory.getBean("orgDao");
		return dao.getEntityById(orgId);
	}

	@Override
	public boolean updateCharger(Long orgId, Long chargerId) {
		try {
			OrgDao dao = (OrgDao) ServerBeanFactory.getBean("orgDao");
			
			//检查该管理员是否已经是别的地区管理员
			List<Org> list = dao.getEntitiesByOneProperty("charger.id", chargerId);
			
			if(!list.isEmpty()){
				return false;
			}
			
			Org org = dao.getEntityById(orgId);
			// 负责人是管理员
			Admin u = this.adminDao.getEntityById(chargerId);
			if (u != null) {
				org.setCharger(u);
			}
			dao.updateEntity(org);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public synchronized boolean addSubOrg(Long pid, String name) {
		try {
			OrgDao dao = (OrgDao) ServerBeanFactory.getBean("orgDao");
			Org org = dao.getEntityById(pid);
			Org neworg = new Org();
			neworg.setOrgname(name);
			neworg.setParent(org);
			// 当前节点的code=父节点的code+（当前级别兄弟节点数量+1）的格式化
			List<Org> silblings = dao.getEntitiesByOneProperty("parent.id", org.getId());
			String code = org.getCode() + CommonUtil.formateCode(silblings.size() + 1, CupidStrutsConstants.LV_CODE_LENGTH);
			neworg.setCode(code);
			neworg.setStatus(1);// 管理员你后台新增的，默认就是通过审核的
			dao.saveEntity(neworg);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public synchronized long addSubCat(Long pid, String name, String orgCode) {
		try {
			ProductCatDao dao = (ProductCatDao) ServerBeanFactory.getBean("productCatDao");
			// 父节点
			ProductCat cat = dao.getEntityById(pid);
			
			ProductCat newCat = new ProductCat();
			newCat.setName(name);
			newCat.setParentId(cat.getId());
			// 当前节点的code=父节点的code+（当前级别兄弟节点数量+1）的格式化
			List<ProductCat> silblings = dao.getEntitiesByOneProperty("parentId", cat.getId());
			String code = cat.getCode() + CommonUtil.formateCode(silblings.size() + 1, 3);
			newCat.setCode(code);
			newCat.setFxCode(orgCode);
			return dao.saveEntity(newCat);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	@Override
	public synchronized Org addSubOrg(String parentOrgCode, Map<String, String> param) {
		try {
			
			String username = param.get("username");
			String telephone = param.get("telephone");
			String email = param.get("email");
			String openId = param.get("openId");
			
			OrgDao dao = (OrgDao) ServerBeanFactory.getBean("orgDao");
			Org orgInDb = dao.getUniqueEntityByOneProperty("telephone", telephone);
			if(orgInDb != null){
				logger.info("申请分销商，更新记录...");
				// 更新
				orgInDb.setOrgname(username);
// orgInDb.setEmail(email);
				dao.updateEntity(orgInDb);
				return orgInDb;
			}else{
				// 新增
				Org org = dao.getByCode(parentOrgCode);
				Org neworg = new Org();
				neworg.setOrgname(username);
				neworg.setTelephone(telephone);
				neworg.setEmail(email);
				neworg.setParent(org);
				neworg.setApplyDate(DateUtil.getLongCurrentDate());
				// 当前节点的code=父节点的code+（当前级别兄弟节点数量+1）的格式化
				List<Org> silblings = dao.getEntitiesByOneProperty("parent.id", org.getId());
				String code = org.getCode() + CommonUtil.formateCode(silblings.size() + 1, CupidStrutsConstants.LV_CODE_LENGTH);
				neworg.setCode(code);
				neworg.setStatus(0);// 需要审核
				dao.saveEntity(neworg);
				return neworg;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delOrg(Long orgId) {
		try {
			OrgDao dao = (OrgDao) ServerBeanFactory.getBean("orgDao");
			dao.deleteEntity(orgId);
			
			
			//还要将用户表中的记录一并删除
			this.userDao.deleteEntityByProperty("org.id", orgId);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean delCat(Long catId) {
		try {
			ProductCatDao dao = (ProductCatDao) ServerBeanFactory.getBean("productCatDao");
			// 是否有子分类
			List<ProductCat> subs = dao.getEntitiesByOneProperty("parentId", catId);
			if(subs != null && subs.size() > 0){
				return false;
			}
			// 是否有商品挂在该分类
			ProductsDao pdao =  (ProductsDao) ServerBeanFactory.getBean("productsDao");
			List<Products> prds = pdao.getEntitiesByOneProperty("prdCatCode", dao.getEntityById(catId).getCode());
			if(prds != null && prds.size() > 0){
				return false;
			}
			dao.deleteEntity(catId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean modOrg(Long id, String orgName) {
		try {
			OrgDao dao = (OrgDao) ServerBeanFactory.getBean("orgDao");
			Org org = dao.getEntityById(id);
			org.setOrgname(orgName);
			dao.updateEntity(org);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<OrgObj> load2LevelOrg() {
		List<OrgObj> result = new ArrayList<OrgObj>();
		OrgDao dao = (OrgDao) ServerBeanFactory.getBean("orgDao");
		List<Org> tmpList = dao.getAllEntities();
		List<Org> list = new ArrayList<Org>();
		// 过滤掉orgCode长度超过12的
		for (Org o : tmpList) {
			if (o.getCode().length() <= CupidStrutsConstants.LV_CODE_LENGTH * 2) {
				list.add(o);
			}
		}
		OrgObj oo = null;
		for (Org org : list) {
			oo = new OrgObj();
			oo.setId(org.getId());
			if (org.getParent() == null) {
				oo.setParent("#");
			} else {
				oo.setParent(org.getParent().getId().toString());
			}
			oo.setText(org.getOrgname());
			oo.setOrgCode(org.getCode());
			result.add(oo);
		}
		return result;
	}

	
	public OrgDao getOrgDao() {
		return orgDao;
	}

	
	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	@Override
	public boolean auditOrgStatus(Long orgId, int i) {
		try{
			Org org = orgDao.getEntityById(orgId);
			org.setStatus(i);
			orgDao.updateEntity(org);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Org getOrgBycode(String orgCode) {
		return orgDao.getByCode(orgCode);
	}

	@Override
	public boolean addTopOrg(String topOrgName, String telephone, String email, String city) {
		try{
			Org org = new Org();
			org.setOrgname(topOrgName);
			org.setApplyDate(DateUtil.getLongCurrentDate());
			org.setStatus(1);
			org.setParent(null);
			org.setCity(city);
			List<Org> silblings = orgDao.getHibernateTemplate().find("from Org where parent.id is null order by id desc");
			if(silblings.size() == 0){//没数据
				org.setCode("000001");
			}else{
				String code = CommonUtil.formateCode(Integer.valueOf(silblings.get(0).getCode()) + 1, CupidStrutsConstants.LV_CODE_LENGTH);
				org.setCode(code);
			}
			org.setTelephone(telephone);
			org.setEmail(email);
			orgDao.saveEntity(org);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public Long addTopPrdcat(String topCatName, String orgCode) {
		ProductCatDao dao = (ProductCatDao) ServerBeanFactory.getBean("productCatDao");
		try{
			ProductCat cat = new ProductCat();
			cat.setName(topCatName);
			cat.setParentId(0L);
			List<ProductCat> silblings = dao.getHibernateTemplate().find("from ProductCat where parentId = 0 order by id desc");
			//编码生成机制，这里不能用同级别节点的数量来作为增量基数，因为之前的节点可能已经被删除，这样编码就会有重复
			String code = CommonUtil.formateCode(Integer.valueOf(silblings.get(0).getCode()) + 1, 3); //<----- 正确的写法
//			String code = CommonUtil.formateCode(silblings.size() + 1, 3);  <------- 错误的写法。
			cat.setCode(code);
			cat.setFxCode(orgCode);
			return dao.saveEntity(cat);
		}catch(Exception e){
			e.printStackTrace();
			return -1L;
		}
	}

	@Override
	public Org getOrgByUserId(Long id) {
		return orgDao.getUniqueEntityByOneProperty("charger.id", id);
	}

	@Override
	public boolean updateCommission(Long orgId, int commission, int personCommission) {
		try{
			Org org = this.getOrgById(orgId);
			org.setCommission(commission);
			org.setPersonCommission(personCommission);
			orgDao.updateEntity(org);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String getConfigParam(String key) {
		ConfigParam config = this.configParamDao.getUniqueEntityByOneProperty("ckey", key);
		if(config != null){
			return config.getCvalue();
		}
		return "";
	}

	
	public void setConfigParamDao(ConfigParamDao configParamDao) {
		this.configParamDao = configParamDao;
	}

	@Override
	public Org getOrgById(Long orgId) {
		return orgDao.getEntityById(orgId);
	}

	@Override
	public boolean updateWxconfig(Long orgId, String appid, String appsecret, String wxID, String kfAccount) {
		try{
			Org org = this.getOrgById(orgId);
			org.setAppid(appid);
			org.setAppsecret(appsecret);
			org.setWxID(wxID);
			org.setKfAccount(kfAccount);
			orgDao.updateEntity(org);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean updateWxMallexpire(Long orgId, String mallexpire) {
		try{
			Org org = this.getOrgById(orgId);
			if("".equals(mallexpire)){
				Calendar cal = Calendar.getInstance();
				cal.set(2099, 1, 1);// 几乎无限
				org.setWxmallexpire(DateUtil.getShortDate(cal.getTime()));
			}else{
				org.setWxmallexpire(mallexpire);
			}
			orgDao.updateEntity(org);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public User applyFxPerson(String openId, String currentFxCode) {
		User user = this.userDao.getUniqueEntityByOneProperty("openId", openId);
		//从分销客设置表里查询是否设置过
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		FxApplyConfig fac =  service.getFxApplyConfig(currentFxCode);
		if(fac.getFxkApplyCondition() == 1){//需要有订单才能申请
			//从订单表里查询，是否有消费过， 没消费过，不允许申请分销客
			if(this.ordersDao.getEntitiesByPropNames(new String[]{"fxpersonId","status"}, new Object[]{user.getId(), 1}).size() == 0){
				return null;
			}
		}
		
		user.setRegDate(new Date());
		user.setFlag(1);// 分销客标志
		this.userDao.updateEntity(user);
		return user;
	}

	/**
	 * 当flag=1 获得该分销客累计佣金 当flag=0 获得该分销客当前佣金
	 * 
	 * @param user
	 * @param flag - 1 累计佣金 0 - 当前可提佣金
	 * @return
	 */
	@Override
	public Long getYj(User user, int flag) {
		
		CommissionLogDao clDao = (CommissionLogDao) ServerBeanFactory.getBean("commissionLogDao");
		CashApplyDao caDao = (CashApplyDao) ServerBeanFactory.getBean("cashApplyDao");
		
		Long yj = 0L;
		// 具体算法：
		String hql ="";
		// flag - 1 累计佣金 0 - 当前可提佣金
		if(flag == 0){
			// 计算当前可提佣金： 从累计佣金减去已经提取的。 已经提取的从提现记录表里去查询
			Long total = 0L;
			Long cashApply = 0L;
			List<CommissionLog> list = clDao.getEntitiesByOneProperty("agentId", user.getId());
			for (CommissionLog commissionLog : list) {
				total += commissionLog.getYj().longValue();
			}
			logger.info("所有佣金：total yj--" + total);
			// 计算已经提现并处理完成的
// List<CashApply> caList = caDao.getEntitiesByPropNames(new String[]{"userId","flag"}, new Object[]{user.getId(), 1});
			List<CashApply> caList =caDao.getHibernateTemplate().find("from CashApply where userId=" + user.getId() + " and flag  <> 2" );
			for (CashApply ca : caList) {
				cashApply += ca.getApplyFee();
			}
			logger.info("所有已经提现、或者已经发起提现的佣金：total cash apply--" + cashApply);
			yj = total - cashApply;
			logger.info("user " + user.getId() + " | " +user.getNickName() + "的可提现佣金为：" + yj + "分");
// hql = "from Orders where fxpersonId=" +user.getId() + " and clear=0 and status in (1,2,3)" ;
		}else if(flag == 1){
			// 计算累计佣金： 从佣金日志表里统计累加
			List<CommissionLog> list = clDao.getEntitiesByOneProperty("agentId", user.getId());
			for (CommissionLog commissionLog : list) {
				yj += commissionLog.getYj().longValue();
			}
// hql = "from Orders where fxpersonId=" +user.getId() + " and status in (1,2,3)" ;
			logger.info("user " + user.getId() + " | " + user.getNickName() + "的累计佣金为：" + yj + "分");
		}
// List<Orders> list = this.ordersDao.getHibernateTemplate().find(hql);
//		
// for (Orders orders : list) {
// logger.info(orders.getFeePrice());
// yj += (orders.getFeePrice() * personCommission)/100;
// }
		return yj;
	}
	/**
	 * 当flag=1 获得该[分销商]累计佣金 当flag=0 获得该[分销商]当前佣金
	 * 
	 * @param user
	 * @param flag - 1 累计佣金 0 - 当前可提佣金
	 * @return
	 */
	@Override
	public Long getFxYj(Org org, int flag) {
		
		CommissionLogDao clDao = (CommissionLogDao) ServerBeanFactory.getBean("commissionLogDao");
		CashApplyDao caDao = (CashApplyDao) ServerBeanFactory.getBean("cashApplyDao");
		
		Long yj = 0L;
		// 具体算法：
		String hql ="";
		// flag - 1 累计佣金 0 - 当前可提佣金
		if(flag == 0){
			// 计算当前可提佣金： 从累计佣金减去已经提取的。 已经提取的从提现记录表里去查询
			Long total = 0L;
			Long cashApply = 0L;
			
			List<Orders> list = ordersDao.getEntitiesByOneProperty("fxCode", org.getCode());
			for (Orders orders : list) {
				total += orders.getFeePrice();
			}
			
			logger.info("所有佣金：total yj--" + total);
			// 计算已经提现并处理完成的
			// atype : 0 - 分销客， 1-分销商
			List<CashApply> caList =caDao.getHibernateTemplate().find("from CashApply where atype=1 and orgCode=" + org.getCode() + " and flag  <> 2" );
			for (CashApply ca : caList) {
				cashApply += ca.getApplyFee();
			}
			logger.info("所有已经提现、或者已经发起提现的佣金：total cash apply--" + cashApply);
			yj = total - cashApply;
			logger.info("user " + org.getOrgname()  + "的可提现佣金为：" + yj + "分");
		}else if(flag == 1){
			// 计算累计佣金： 由于是分销商，和分销客不同，从订单表里统计即可
			List<Orders> list = ordersDao.getEntitiesByOneProperty("fxCode", org.getCode());
			for (Orders orders : list) {
				yj += orders.getFeePrice();
			}
			logger.info("user " + org.getOrgname()  + "的累计佣金为：" + yj + "分");
		}
		return yj;
	}

	
	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}

	@Override
	public boolean updateShareConfig(Map<String, String> params, String currentOrgCode) {
		try{
			
			String shareDesc = params.get("shareDesc");
			String shareLogo = params.get("shareLogo");
			String shareTitle = params.get("shareTitle");
			String attHintUrl = params.get("attHintUrl");
			
			Org org = this.getOrgBycode(currentOrgCode);
			org.setShareDesc(shareDesc);
			org.setShareLogo(shareLogo);
			org.setShareTitle(shareTitle);
			org.setAttHintUrl(attHintUrl);
			orgDao.updateEntity(org);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public Map<String, String> getFxkInfo(User user) {
		Map<String, String> map = new HashMap<String, String>();
		// 可提佣金：未结算的佣金，即订单的clear不为1的
		map.put("yj", this.getYj(user, 0)+"");
		// 累计佣金：历史以来所有的佣金
		map.put("allyj", this.getYj(user, 1)+"");
		// 分销订单数，当前未结算的
		String hql = "from Orders where fxpersonId=" +user.getId() + " " ;
		List<Orders> list = this.ordersDao.getHibernateTemplate().find(hql);
		map.put("orderSize", list.size()+"");
		
		// 我的客户，根据用户表的PID来查找
		String sql = "SELECT count(*) FROM User  where pid = " + user.getId();
		List find = this.ordersDao.getHibernateTemplate().find(sql);
		// 去重计数
		map.put("customers", find.get(0)+"");
		
// map.put("subcnt", set.size()+"");
		return map;
	}

	@Override
	public Long saveWxCustomer(WxUser wxUserByOrg, Org org, String agentId,String inviter_id) {
		System.out.println("-----------------saveopenid:"+wxUserByOrg.getOpenid());
		User u = new User();
		String openId = wxUserByOrg.getOpenid();
		u.setOpenId(openId);
		User user = this.userDao.getUniqueEntityByOneProperty("openId", openId);
		if (user == null) {
			System.out.println("-----------------aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			logger.info("saveWxCustomer，openId===..." + openId);
			//u.setCity(wxUserByOrg.getCity());
			u.setNickName((wxUserByOrg.getNickName() + "").toString());
			
			//System.out.println("-----------------nickname:"+u.getNickName());
			u.setSex(wxUserByOrg.getSex());
			//u.setProvince(wxUserByOrg.getProvince());
			u.setCountry(wxUserByOrg.getCountry());
			u.setOpenId(openId);
			// u.setSubscribTime(wxUserByOrg.getSubscribeTime());
			if(org!=null){
				u.setOrg(org);// 属于哪个分销商
			}
			u.setStatus(1);
			u.setEmail("abc@abc.com");
			u.setPassword(SysUtil.encodeBase64("11111111"));// 默认密码
			u.setRegDate(new Date());
			//u.setBirthDate(new Date());
			// 头像
			u.setHeadimgurl(wxUserByOrg.getHeadimgurl());
			if(agentId != null){
				u.setPid(Long.valueOf(agentId));
			}
			if(inviter_id!=null&&!inviter_id.equals("")){
				u.setInviterId(Integer.valueOf(inviter_id));
			}
			Long id = 0L;
			boolean illegal = false;
			try{
				id = this.userDao.saveEntity(u);
				System.out.println("-----------------ccccccccccccccccccccccccccccccccccc");
				// 通过接口获取用户微信详细信息，并更新到数据库
			}catch(Throwable e){
				System.out.println("-----------------保存异常");
				e.printStackTrace();
				illegal = true;
				logger.info("无法识别的昵称");
			}
			if(illegal){
				System.out.println("-----------------bbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
				logger.info("处理昵称......");
				u = new User();
				u.setId(0L);
				//u.setCity(wxUserByOrg.getCity());
				u.setNickName((wxUserByOrg.getNickName() + "").toString());
				u.setSex(wxUserByOrg.getSex());
				//u.setProvince(wxUserByOrg.getProvince());
				//u.setCountry(wxUserByOrg.getCountry());
				u.setOpenId(openId);
				if(org!=null){
					u.setOrg(org);// 属于哪个分销商
				}
				u.setStatus(1);
				u.setEmail("abc@abc.com");
				u.setPassword(SysUtil.encodeBase64("11111111"));// 默认密码
				u.setRegDate(new Date());
				// 头像
				u.setHeadimgurl(wxUserByOrg.getHeadimgurl());
				if(agentId != null){
					u.setPid(Long.valueOf(agentId));
				}
				if(inviter_id!=null&&!inviter_id.equals("")){
					u.setInviterId(Integer.valueOf(inviter_id));
				}
				u.setNickName("N/A");
				id = this.userDao.saveEntity(u);
				System.out.println("-----------------ddddddddddddddddddddddddddddd");
			}
			//首次进入首页
			DrugScoreLog score=new DrugScoreLog();
			//判断是否已经添加过积分
			boolean isexist=drugScoreLogService.isExsit(openId, "0");		
			if(!isexist){
				//不存在时，基础分添加
				score.setOpenId(u.getOpenId());
				score.setAction(IDrugScoreLogService.ACTION_ADD);
				score.setScore(IDrugScoreLogService.SCORE_BASE);
				score.setSource(IDrugScoreLogService.SOURC_ACTION_BASE);
				score.setOpdate(new Date());
				drugScoreLogService.saveOrUpdateDrugScore(score);
				u.setPoints(IDrugScoreLogService.SCORE_BASE);
				
			}
			
			//通过微信扫描成为的用户，给邀请人加分
			User inviter=this.getUserById(Long.valueOf(inviter_id));
			if(inviter!=null){
				//自己扫描自己二维码不加分
				if(!inviter.getOpenId().equals(openId)){
					//推荐人id存在，说明推荐好友成功，给推荐人增加积分30
					score.setOpenId(inviter.getOpenId());
					score.setAction(IDrugScoreLogService.ACTION_ADD);
					score.setScore(IDrugScoreLogService.SCORE_SCAN_CODE);
					score.setSource(IDrugScoreLogService.SOURC_ACTION_SCAN_CODE);
					score.setOpdate(new Date());
					drugScoreLogService.saveOrUpdateDrugScore(score);
					inviter.setPoints(inviter.getPoints()+IDrugScoreLogService.SCORE_SCAN_CODE);
				}
			}
			
			return id;
		} else {
			logger.info("已经是关注用户，无需保存...更新昵称，头像即可" + openId);
			user.setNickName((wxUserByOrg.getNickName() + "").toString());
			// 头像
			user.setHeadimgurl(wxUserByOrg.getHeadimgurl());
			if(StringUtils.isNotEmpty(wxUserByOrg.getCity())){
				//user.setCity(wxUserByOrg.getCity());
			}
			if(StringUtils.isNotEmpty(wxUserByOrg.getProvince())){
				//user.setProvince(wxUserByOrg.getProvince());
			}
			if(StringUtils.isNotEmpty(wxUserByOrg.getCountry())){
				user.setCountry(wxUserByOrg.getCountry());
			}
			
			this.userDao.saveEntity(user);
			return user.getId();
		}
	}

	@Override
	public boolean updateOrg(Org org) {
		try{
			this.orgDao.updateEntity(org);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean delAudit(Long orgId) {
		try{
			this.orgDao.deleteEntity(orgId);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean modCat(Long id, String orgName) {
		try {
			ProductCatDao dao = (ProductCatDao) ServerBeanFactory.getBean("productCatDao");
			ProductCat pc = dao.getEntityById(id);
			pc.setName(orgName);
			dao.updateEntity(pc);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void updatePayParam(String orgId, String mchId, String payKey) {
		try{
			Org org = this.orgDao.getEntityById(Long.valueOf(orgId));
			org.setMchId(mchId);
			org.setPayKey(payKey);
			this.orgDao.updateEntity(org);
			// 记录日志
			SystemLog systemLog = new SystemLog(SystemLog.MODULE_SYSTEM, org.getCharger().getUsername(), "修改支付参数：  商户ID：" + mchId + ", 支付秘钥：" + payKey);
			systemLogService.saveSystemLog(systemLog);
		}catch(Exception e){
			logger.info("更新支付参数失败..." + e.getMessage());
		}
	}

	
	public void setSystemLogService(ISystemLogService systemLogService) {
		this.systemLogService = systemLogService;
	}
	

	public void setDrugScoreLogService(IDrugScoreLogService drugScoreLogService) {
		this.drugScoreLogService = drugScoreLogService;
	}

	@Override
	public void cashApply(CashApply ca) {
		CashApplyDao dao = (CashApplyDao) ServerBeanFactory.getBean("cashApplyDao");
		dao.saveEntity(ca);
	}

	@Override
	public void updateIndexShow(Org org, String indexShow) {
		Org o = this.orgDao.getEntityById(org.getId());
		o.setIndexShow(Integer.valueOf(indexShow));
		this.orgDao.updateEntity(o);
	}

	@Override
	public Map<String, Object> getCashApplyRecordList(int start, int limit, Map<String, String> param) {
		CashApplyDao dao = (CashApplyDao) ServerBeanFactory.getBean("cashApplyDao");
		Map<String, Object> result = new HashMap<String, Object>();
		List<CashApply> list = new ArrayList<CashApply>();
		String hql = "from CashApply where 1=1 ";
		String countHql = "select count(id) from CashApply where 1=1 ";
		String cond = " ";
		if (param != null && !param.isEmpty()) {
// // 分销商编码
			if (param.get("orgCode") != null && !StringUtils.isEmpty(param.get("orgCode"))) {
				cond += " and orgCode = '" + param.get("orgCode") + "'";
			}
// // 分销商编码
			if (param.get("atype") != null && !StringUtils.isEmpty(param.get("atype"))) {
				cond += " and atype = " + param.get("atype") ;
			}
			// 总销商编码
			if (param.get("topOrgCode") != null && !StringUtils.isEmpty(param.get("topOrgCode"))) {
				cond += " and topOrgCode = " + param.get("topOrgCode") ;
			}
		}
		hql += cond + " order by applyDate desc";
		countHql += cond;
		List find = dao.getHibernateTemplate().find(countHql);
		int totalCount = Integer.valueOf(find.get(0).toString());
		try {
			list = dao.getPagingEntitiesByHql(hql, start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("total", totalCount);
		result.put("list", list);
		return result;
	}

	@Override
	public boolean handleCashApply(Long id, int targetStatus) {
		try{
			CashApplyDao dao = (CashApplyDao) ServerBeanFactory.getBean("cashApplyDao");
			dao.getHibernateTemplate().bulkUpdate("update CashApply set flag=" + targetStatus);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean cancelApply(Long id) {
		try{
			CashApplyDao dao = (CashApplyDao) ServerBeanFactory.getBean("cashApplyDao");
			dao.deleteEntity(id);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateDeliverFee(Long orgId, Integer deliverFee, Integer baoyou) {
		try{
			Org org = this.getOrgById(orgId);
			if(!"".equals(deliverFee) && deliverFee != null){
				org.setDeleverFee(100 * Integer.valueOf(deliverFee));// 保存时，转化成分
				org.setBaoyou(100 * Integer.valueOf(baoyou));// 保存时，转化成分
				orgDao.updateEntity(org);
			} 
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public FxApplyConfig getFxApplyConfig(String orgCode) {
		FxApplyConfigDao dao = (FxApplyConfigDao) ServerBeanFactory.getBean("fxApplyConfigDao");
		return dao.getUniqueEntityByOneProperty("orgCode", orgCode);
	}

	@Override
	public boolean saveOrUpdateFxApplyConfig(FxApplyConfig fac) {
		try{
			FxApplyConfigDao dao = (FxApplyConfigDao) ServerBeanFactory.getBean("fxApplyConfigDao");
			if(fac.getId() != null ){
				FxApplyConfig k = dao.getEntityById(fac.getId());
				fac.setOrgCode(k.getOrgCode());
				if(fac.getFxkDescPic() != null){
					k.setFxkDescPic(fac.getFxkDescPic());
				}
				if(fac.getFxsDescPic() != null){
					k.setFxsDescPic(fac.getFxsDescPic());
				}
				//分销客申请门槛
				k.setFxkApplyCondition(fac.getFxkApplyCondition());
				k.setFxkApplyTxt(fac.getFxkApplyTxt());
				k.setFxsApplyTxt(fac.getFxsApplyTxt());
				k.setClearFxkPic(fac.getClearFxkPic());
				k.setClearFxsPic(fac.getClearFxsPic());
				// 拷贝数据库里的数据
				BeanUtils.copyProperties(fac, k);
				if(fac.getFxkDescPic() != null){
					k.setFxkDescPic(fac.getFxkDescPic());
				}
				if(fac.getFxsDescPic() != null){
					k.setFxsDescPic(fac.getFxsDescPic());
				}
				
				if(fac.getClearFxkPic() == 1){
					k.setFxkDescPic("");
				}
				if(fac.getClearFxsPic() == 1){
					k.setFxsDescPic("");
				}
				
				// 更新
				dao.updateEntity(k);
			}else{
				// 新增
				dao.saveEntity(fac);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean generatePj(Integer count, String prdId) {
		try{
			String email = "99999@99999.99999";
			Long userId = null;
			Long orderId = null;
			// ---------虚拟用户
			User u = this.userDao.getUniqueEntityByOneProperty("email", email);
			if(u == null){
				// 新建一个虚拟用户
				u = new User();
				u.setEmail(email);
				u.setPassword("");
				u.setRegDate(new Date());
				u.setOrg(this.orgDao.getByCode("000"));
				u.setStatus(1);
				userId = userDao.saveEntity(u);
			}
			// --------虚拟订单
			String dummyOrderNo = "dummy-99999";
			Orders order = this.ordersDao.getUniqueEntityByOneProperty("orderNo", dummyOrderNo);
			if(order == null){
				// 新建一个虚拟订单
				order = new Orders();
				order.setFxCode("000");
				orderId = this.ordersDao.saveEntity(order);
			}
			
			
			// 生成评价数量，时间段随机选择最近3个月，姓名从姓名库中随机生成
			PjDao pjDao = (PjDao) ServerBeanFactory.getBean("pjDao");
			Pj pj = null;
			for (int i=0; i<count; i++) {
				pj = new Pj();
				pj.setContent(CommonUtil.randomPjDesc());// 随机生成
				pj.setOrderId(orderId);// 虚拟订单
				pj.setPrdid(Long.valueOf(prdId));
				pj.setPjer(u);// 虚拟用户
				pj.setPjDate(CommonUtil.randomDate());
				pj.setPjerName(CommonUtil.randomNickName());// 随机生成
				pj.setScore(CommonUtil.randomScore());// 随机4星，5星
				pjDao.saveEntity(pj);
			}
			logger.info("虚拟评价已经生成完毕....");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int countOrg() {
		return this.orgDao.countByHql("from Org where LENGTH(code) = " + CupidStrutsConstants.LV_CODE_LENGTH);
	}

	@Override
	public boolean addTopMenu(Long orgId, String menuName, String mtype, String key, String url) {
		try{
			WxMenuDao dao = (WxMenuDao) ServerBeanFactory.getBean("wxMenuDao");
			// 是否超过3个一级菜单？
			List find = dao.getHibernateTemplate().find("from WxMenu where orgId=" + orgId + " and pid is null");
			if(find.size() < 3){
				// 可以继续增加一级菜单
				WxMenu m = new WxMenu();
				m.setOrgId(orgId);
				m.setType(mtype);
				m.setName(menuName);
				if("0".equals(mtype)){// 一级菜单有子菜单
					
				}else{
					m.setKey(key);
					m.setUrl(url);
				}
				dao.saveEntity(m);
			}else{
				// 一级菜单已经满了
				return false;
			}
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean modWxMenu(Long orgId, Map<String, String> mparams) {
		try {
			WxMenuDao dao = (WxMenuDao) ServerBeanFactory.getBean("wxMenuDao");
			String dbId = mparams.get("dbId");
			String menuName = mparams.get("menuName");
			String mtype = mparams.get("mtype");
			String key = mparams.get("key");
			String url = mparams.get("url");
			WxMenu menu = dao.getEntityById(Long.valueOf(dbId));
			if(menu != null){
				menu.setKey(key);
				menu.setUrl(url);
				menu.setType(mtype);
				menu.setName(menuName);
				dao.updateEntity(menu);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addSubMenu(Long pid, Map<String, String> params) {
		try {
			WxMenuDao dao = (WxMenuDao) ServerBeanFactory.getBean("wxMenuDao");
			// 二级菜单不得超过7个
			List<WxMenu> childs = dao.getEntitiesByOneProperty("parent.id", pid);
			if(childs.size() < 5){
				String name = params.get("name");
				String mtype = params.get("mtype");
				String key = params.get("key");
				String url = params.get("url");
				String orgId = params.get("orgId");
				WxMenu parent = dao.getEntityById(pid);
				WxMenu wxMenu = new WxMenu();
				wxMenu.setName(name);
				wxMenu.setType(mtype);
				wxMenu.setParent(parent);
				wxMenu.setKey(key);
				wxMenu.setUrl(url);
				wxMenu.setOrgId(Long.valueOf(orgId));
				
				dao.saveEntity(wxMenu);
				return true;
			}else{
				logger.info("无法增加更多的二级菜单。");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean publish2Wx(Org org) {
		String token = WeiXinUtil.getToken(org.getAppid(), org.getAppsecret());
		// POST菜单https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
		if (token != null) {
			String menuJson = parseMenuJson(org.getId());
			logger.info("发布到微信公众号：" + menuJson);
			return WeiXinUtil.postMenu(menuJson, token); 
		}
		return false;
	}

	private String parseMenuJson(Long orgId) {
		Map<String, List<WxMenu>> menuMap = new HashMap<String, List<WxMenu>>();
		Map<String, WxMenu> topMenuMap = new LinkedHashMap<String, WxMenu>();
		
		WxMenuDao dao = (WxMenuDao) ServerBeanFactory.getBean("wxMenuDao");
		List<WxMenu> list = dao.getEntitiesByOneProperty("orgId", orgId);
		StringBuffer sb = new StringBuffer("{ \"button\":[");
		//组合成map
		for (WxMenu wxMenu : list) {
			if(wxMenu.getParent() == null){//说明是一级菜单
				menuMap.put(wxMenu.getName(), new ArrayList<WxMenu>());
				topMenuMap.put(wxMenu.getName(), wxMenu);
			}
		}
		//组合二级菜单和一级菜单的映射关系
		for (WxMenu wxMenu : list) {
			if(wxMenu.getParent() != null){
				menuMap.get(wxMenu.getParent().getName()).add(wxMenu);
			}
		}
		int i=0;
		//开始构造，从一级菜单开始
		for (WxMenu m : topMenuMap.values()) {
			if(m.getType() != null && !"0".equals(m.getType()) && !"".equals(m.getType())){//一级菜单无子菜单
				sb.append("{").append("\n");
				sb.append("\"name\":\"" + m.getName() + "\",").append("\n");
				sb.append("\"type\":\"" + m.getType() + "\",").append("\n");
				if("click".equals(m.getType())){
					sb.append("\"key\":\""+ m.getKey() +"\"").append("\n");
				}else if("view".equals(m.getType())){
					sb.append("\"url\":\""+ m.getUrl() +"\"").append("\n");
				}
				sb.append("}");
			}else{//一级菜单有子菜单
				sb.append("{").append("\n");
				sb.append("\"name\":\""+ m.getName() +"\",").append("\n");
				sb.append("\"sub_button\":[").append("\n");
				
				//--------列出所有二级菜单----------
				List<WxMenu> childs = menuMap.get(m.getName());
				int j = 0;
				for (WxMenu sm : childs) {
					sb.append("{").append("\n");
					
					sb.append("\"type\":\"" + sm.getType() + "\",").append("\n");
					sb.append("\"name\":\"" + sm.getName() + "\",").append("\n");
					if("click".equals(sm.getType())){
						sb.append("\"key\":\""+ sm.getKey() +"\"").append("\n");
					}else if("view".equals(sm.getType())){
						sb.append("\"url\":\""+ sm.getUrl() +"\"").append("\n");
					}
					
					sb.append("}").append("\n");
					j++;
					if(j < childs.size()){
						//子菜单之间的逗号
						sb.append(",");
					}
				}
				
				sb.append("]}").append("\n");
			}
			i ++;
			//一级菜单之间的逗号(,)
			if(i < topMenuMap.keySet().size()){
				sb.append(",");
			}
		}
		
		sb.append("]}");
		return sb.toString();
	}

	@Override
	public boolean delWxMenu(Long dbId) {
		try {
			WxMenuDao dao = (WxMenuDao) ServerBeanFactory.getBean("wxMenuDao");
			dao.deleteEntity(dbId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Products getProductByCode(String prdCode) {
		ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
		return dao.getUniqueEntityByOneProperty("prdCode", prdCode);
	}

	@Override
	public Gift getGiftById(Long giftId) {
		return giftDao.getEntityById(giftId);
	}

	@Override
	public List<ProductCat> getTopCat(String orgCode) {
		ProductCatDao dao = (ProductCatDao) ServerBeanFactory.getBean("productCatDao");
		return dao.getHibernateTemplate().find("from ProductCat where fxCode = '"+ orgCode +"' and parentId=0");
	}
	@Override
	public List<ProductCat> getSubCat(String orgCode, String pid) {
		ProductCatDao dao = (ProductCatDao) ServerBeanFactory.getBean("productCatDao");
		return dao.getHibernateTemplate().find("from ProductCat where fxCode = '"+ orgCode +"' and parentId=" + Long.valueOf(pid));
	}

	@Override
	public int sendBless(Long orgId, String openId ) {
		Org org = this.orgDao.getEntityById(orgId);
		String templateId = org.getTemplateMsgId();
		List<String> msgs = new ArrayList<String>();
		msgs.add("");
		boolean b = WeiXinUtil.sendTemplateMsg(openId, org.getAppid(), org.getAppsecret(), templateId, msgs);
		if(b){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public Paging loadHongbao(int page, int limit, Map<String, String> param) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		//分页对象
		model.Paging pp = new model.Paging();
		Map<String, Object> map = hongBaoDao.queryRecord(start, limit, param);
		// -----------------拼装分页对象 -------------------------------------
		List<HongBao> dataList = (List<HongBao>) map.get("list");
		int total = Integer.valueOf(map.get("total").toString());
		pp.setPage(Integer.valueOf(page));// 当前页码
		pp.setLimit(Integer.valueOf(limit));// 每页显示条数
		if (!dataList.isEmpty()) {
			pp.setTotal(total);
			// 总页数
			if (total % pp.getLimit() != 0) {
				pp.setTotalPage(total / pp.getLimit() + 1);
			} else {
				pp.setTotalPage(total / pp.getLimit());
			}
			logger.info("数据页数：" + pp.getTotalPage());
			pp.setDatas(dataList);
		}
		return pp;
	}

	
	public HongBaoDao getHongBaoDao() {
		return hongBaoDao;
	}

	
	public void setHongBaoDao(HongBaoDao hongBaoDao) {
		this.hongBaoDao = hongBaoDao;
	}

	@Override
	public boolean addHongbao(Long orgId, Map<String, String> params) {
		String thetype = params.get("thetype");
		String hbvalue = params.get("hbvalue");
		String num = params.get("num");
		String deduct = params.get("deduct");
		String expireDate = params.get("expireDate");
		try{
			HongBao hb = new HongBao();
			hb.setOrgId(orgId);//所属公司
			if("0".equals(thetype)){
				//红包
				hb.setHbvalue(Long.valueOf(hbvalue));
				hb.setNum(Integer.valueOf(num));
			}else{
				//优惠券
				hb.setDeduct(Float.valueOf(deduct));
				hb.setNum(0);
			}
			//卡券类型
			hb.setThetype(Integer.valueOf(thetype));
			hb.setCreateDate(DateUtil.getLongCurrentDate());
			hb.setExpireDate(expireDate);
			hb.setStatus(0);
			hb.setUuid(SysUtil.getRandomCode(16));
			hongBaoDao.saveEntity(hb);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delHongbao(Long id) {
		try{
			hongBaoDao.deleteEntity(id);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public int takeHongbao(Long orgId, String openId, String uuid) {
		logger.info("拿红包---openId=" + openId + "   uuid=" + uuid);
		//根据uuid和openId， 将红包数据和用户绑定， 并更新红包库存
		try{
			//拿到红包信息	
			HongBao hb = hongBaoDao.getUniqueEntityByOneProperty("uuid", uuid);
			//检查库存
			if(hb.getNum() == hb.getNumused()){
				//已经领完了
				return 9;
			}
			
			User user = userDao.getUniqueEntityByOneProperty("openId", openId);
			//判断是否已经领取过了
			if(userHongBaoDao.getUniqueEntityByOneProperty("userId", user.getId()) != null){
				//已经领取过了
				return 2;
			}
			UserHongBao uhb = new UserHongBao();
			uhb.setUserId(user.getId());
			uhb.setHbuuid(uuid);
			uhb.setOrgId(orgId);
			//红包
			uhb.setThetype(HongBao.TYPE_HB);
			uhb.setGetDate(DateUtil.getLongCurrentDate());
			uhb.setStatus(0);//0-未使用  1-已使用
			Long id = userHongBaoDao.saveEntity(uhb);
			
			if(id > 0){
				//领用数+1
				hb.setNumused(hb.getNumused() + 1);
				hongBaoDao.updateEntity(hb);
				logger.info("更新红包库存完毕....");
			}
			return 1;//ok
		}catch(Exception e){
			e.printStackTrace();
			return 0;//fail
		}
	}

	
	public UserHongBaoDao getUserHongBaoDao() {
		return userHongBaoDao;
	}

	
	public void setUserHongBaoDao(UserHongBaoDao userHongBaoDao) {
		this.userHongBaoDao = userHongBaoDao;
	}

	@Override
	public Paging getMyHongBaoList(int page, int limit, Map<String, String> param) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		//分页对象
		model.Paging pp = new model.Paging();
		Map<String, Object> map = userHongBaoDao.queryRecord(start, limit, param);
		// -----------------拼装分页对象 -------------------------------------
		List<UserHongBao> dataList = (List<UserHongBao>) map.get("list");
		int total = Integer.valueOf(map.get("total").toString());
		pp.setPage(Integer.valueOf(page));// 当前页码
		pp.setLimit(Integer.valueOf(limit));// 每页显示条数
		if (!dataList.isEmpty()) {
			pp.setTotal(total);
			// 总页数
			if (total % pp.getLimit() != 0) {
				pp.setTotalPage(total / pp.getLimit() + 1);
			} else {
				pp.setTotalPage(total / pp.getLimit());
			}
			logger.info("数据页数：" + pp.getTotalPage());
			
			//带入关联红包对象
			for (UserHongBao uhb : dataList) {
				uhb.setHb(this.hongBaoDao.getUniqueEntityByOneProperty("uuid", uhb.getHbuuid()));
			}
			
			pp.setDatas(dataList);
		}
		return pp;
	}

	@Override
	public Contacts getContacts(Long orgId, int locId) {
		return contactsDao.getUniqueEntityByPropNames(new String[]{"orgId","locId"}, new Object[]{orgId, locId});
	}

	
	public ContactsDao getContactsDao() {
		return contactsDao;
	}

	
	public void setContactsDao(ContactsDao contactsDao) {
		this.contactsDao = contactsDao;
	}

	@Override
	public boolean updateContacts(Long orgId,  Map<String, String> param) {
		try{
			int locId = Integer.valueOf(param.get("locId"));
			Contacts c = this.getContacts(orgId, locId);
			if(c != null){
				c.setCtitle(param.get("ctitle"));
				c.setAreaCode(param.get("areaCode"));
				c.setTelCode(param.get("telCode"));
				contactsDao.updateEntity(c);
			}else{
				//新增
				c = new Contacts();
				c.setOrgId(orgId);
				c.setLocId(locId);
				c.setCtitle(param.get("ctitle"));
				c.setAreaCode(param.get("areaCode"));
				c.setTelCode(param.get("telCode"));
				contactsDao.saveEntity(c);
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public void saveCarInfo(CarInfo carInfo) {
		try{
			CarInfoDao cDao = (CarInfoDao) ServerBeanFactory.getBean("carInfoDao");
			CarInsurenceInfoDao ciDao = (CarInsurenceInfoDao) ServerBeanFactory.getBean("carInsInfoDao");
			//保存车辆相关信息
			carInfo.setSubmitDate(DateUtil.getLongCurrentDate());
			cDao.saveEntity(carInfo);
			logger.info("车辆信息保存完成。。。。");
			
			//先保存该车辆保险信息
//			ciDao.saveEntity(entity)
			CarInsurenceInfo cii = new CarInsurenceInfo();
			BeanUtils.copyProperties(cii, carInfo.getCiInfo());
			cii.setCarInfo(carInfo);
			ciDao.saveEntity(cii);
			logger.info("车辆保险方案保存完成。。。。");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public Long getOrdersFee(Long id) {
		try{
			Long fee = 0L;
			OrdersDao cDao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
			List<Orders> entitiesByOneProperty = cDao.getEntitiesByOneProperty("user.id", id);
			for (Orders order : entitiesByOneProperty) {
				fee += order.getFeePrice();
			}
			return fee;
		}catch(Exception e){
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public Paging getAreaOrderList(int page, int limit, Map<String, String> param) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		//分页对象
		model.Paging pp = new model.Paging();
		Map<String, Object> map = ordersDao.queryAreaRecord(start, limit, param);
		// -----------------拼装分页对象 -------------------------------------
		List<UserHongBao> dataList = (List<UserHongBao>) map.get("list");
		int total = Integer.valueOf(map.get("total").toString());
		pp.setPage(Integer.valueOf(page));// 当前页码
		pp.setLimit(Integer.valueOf(limit));// 每页显示条数
		if (!dataList.isEmpty()) {
			pp.setTotal(total);
			// 总页数
			if (total % pp.getLimit() != 0) {
				pp.setTotalPage(total / pp.getLimit() + 1);
			} else {
				pp.setTotalPage(total / pp.getLimit());
			}
			logger.info("数据页数：" + pp.getTotalPage());
			
			
			pp.setDatas(dataList);
		}
		return pp;
	}

	@Override
	public Paging getFaxianList(int page, int limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		//分页对象
		model.Paging pp = new model.Paging();
		Map<String, Object> map = showcaseDao.queryRecord(start, limit, paramMap);
		// -----------------拼装分页对象 -------------------------------------
		List<ShowCase> dataList = (List<ShowCase>) map.get("list");
		int total = Integer.valueOf(map.get("total").toString());
		pp.setPage(Integer.valueOf(page));// 当前页码
		pp.setLimit(Integer.valueOf(limit));// 每页显示条数
		if (!dataList.isEmpty()) {
			pp.setTotal(total);
			// 总页数
			if (total % pp.getLimit() != 0) {
				pp.setTotalPage(total / pp.getLimit() + 1);
			} else {
				pp.setTotalPage(total / pp.getLimit());
			}
			logger.info("数据页数：" + pp.getTotalPage());
			
			
			pp.setDatas(dataList);
		}
		return pp;
	}

	@Override
	public void delCoupon(Long id) {
		CouponDao couponDao = (CouponDao) ServerBeanFactory.getBean("couponDao");
		couponDao.deleteEntity(id);
	}
	
	@Test
	public void testOne(){
		System.out.print("....");
	}
	
	public List<WxRulesImage> getAllWxRulesImage() {
		List<WxRulesImage> result = wxRuleImageDao.getAllWxRulesImage();
		return result;
	}
	
	@Override
	public Map<String, Object> getImageKeywords(int start, int limit, Map<String, String> paramMap) {
		return wxRuleImageDao.queryRecord(start, limit, paramMap);
	}
	
}
	

	

package service.intf;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import model.GameDailyReport;
import model.GameReport;
import model.Paging;
import model.bo.AboutUs;
import model.bo.Columns;
import model.bo.Contacts;
import model.bo.Experts;
import model.bo.Feedback;
import model.bo.Gift;
import model.bo.OnlineTests;
import model.bo.PublishedStudy;
import model.bo.PublishedTesting;
import model.bo.Question;
import model.bo.ShowCase;
import model.bo.TP;
import model.bo.Topics;
import model.bo.Training;
import model.bo.Vote;
import model.bo.WxRules;
import model.bo.WxRulesImage;
import model.bo.auth.Org;
import model.bo.car.CarInfo;
import model.bo.fenxiao.CashApply;
import model.bo.fenxiao.FxApplyConfig;
import model.bo.food.ConfigParam;
import model.bo.food.Spreader;
import model.vo.OrgObj;
import model.vo.ScanReport;
import model.vo.WxUser;

import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.User;

public interface AdminService {

	/**
	 * 登录
	 * 
	 * @param username
	 * @param pwd
	 * @return
	 */
	public Admin login(String username, String pwd);

	/**
	 * 列表数据分页 key - list, value - List<Resume> key - total, value - 记录总数
	 * 
	 * @param type - 权限类型：管理员，高级操作员，中级操作员，普通操作员
	 * @param paramMap - 查询参数
	 */
//	public Map<String, Object> getRecordList(int start, int limit, String type, Map<String, String> paramMap);

	public Admin getAdminById(String id);

	public Map<String, Object> getAdminList(int start, int intValue);

	public boolean batchDeleteAdmin(String ids);

	public void updateAdmin(Admin user);

	public void addAdmin(Admin user);

	public boolean addKeyword(HttpServletRequest request);
	
	public boolean addImageKeyword(HttpServletRequest request);

	public boolean updateKeyWord(HttpServletRequest request);
	
	public boolean updateImageKeyWord(HttpServletRequest request);
	
	public void deleteRule(Long valueOf);
	
	public void deleteImageRule(Long valueOf);

	public WxRules getRule(String id);
	
	public WxRulesImage getImageRule(String id);

	public GameReport getGameReportData();

	public GameReport getGameUserReportData();
	/**
	 * 统计每日的参与量分布图
	 * @param date
	 * @return
	 */
	public GameDailyReport getGameUserDailyReportData(String date);

	/**
	 * 查找 
	 * @param start
	 * @param intValue
	 * @param type
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getShowCases(int start, int intValue, Map<String, String> paramMap);

	/**
	 * 新增文档 
	 * @param param
	 */
	public ShowCase addCase(Map<String, String> param);
	/**
	 * 删除文档
	 * @param ids
	 * @return
	 */
	public boolean delCase(String ids);
	/**
	 * 更新 
	 * @param addCase
	 */
	public void updateShowCase(ShowCase addCase);

	public Map<String, Object> getExperts(int start, int intValue);

	public Experts addExpert(Map<String, String> param);

	public void updateExpert(Experts expert);

	public boolean delExperts(String ids);

	/**
	 * 获得问题列表，按时间 
	 * @param date - YYYY-MM
	 * @return
	 */
	public List<Question> getQuestionsByDate(String date);

	
	public void addQuestion(Question q);

	public List<Topics> topics(int year);

	public void addTopic(Topics q);

	public boolean delQuestion(String ids);

	public int saveAnswer(User user, String[] parameterValues, String snum);

	public List<GameReport> getReportList(Long valueOf);

	public List<Question> getQuestionsByTopic(Long valueOf);

	public boolean delTopic(String ids);

	public List<Gift> getGifts();

	public Gift addGift(Map<String, String> param);

	public void updateGift(Gift gift);

	public boolean delGift(String ids);

	public int getScoreById(Long uid);

	public boolean duihuan(Long uid, Long giftId);

	public Map<String, Object> getGiftLogs(int start, int intValue, Map<String, String> paramMap);

	public boolean dhGift(String giftId);

	public ShowCase getLastCase(String type);

	public Experts getLastExpert();

	public ShowCase getArticle(String t, String id);

	public Experts getExperts(Long uid);

	public Map<String, Object> getUserList(int start, int intValue, Map<String, String> param);
	
	public Map<String, Object> getCashApplyRecordList(int start, int intValue, Map<String, String> param);

	public boolean delUser(String id);

	public boolean disUser(String id);

	public boolean enableUser(String id);

	public User getUserById(Long valueOf);

	public User updateUser(User u);
	/** ·获取所有模块 
	 * @param model */
	public List<Columns> getAllColumns(String model);

	public void addColumn(String title, String model);

	public void updateColumn(Long id, String title);

	/**
	 * 导入用户，如果用户身份证号已存在，则更新
	 * 请填写对该方法的说明，方便日后维护，如果你不写，以后自己忘记了也很难读懂，后面接手你代码的人也会骂你
	 * @param dataList
	 * @return
	 */
	public List<String> importUsers(List<Map<String, String>> dataList);

	public List<Columns> getColumns();

	public boolean clearQuestions();

	public Map<String, Object> getQuestions(int start, int intValue, Map<String, String> param);

	/**
	 * 获得当前已发布的在线辅导题，如果没有，则返回空集合 
	 * @return
	 */
	public List<PublishedStudy> getPublishedStudy();
	/**
	 * 发布在线辅导题 
	 * @param ids
	 * @return
	 */
	public boolean publishOnlineStudy(String ids);

	public Question getQuestionsById(Long valueOf);

	public void updateQuestion(Question q);
	/**
	 * 查询已发布的在线考试题目 
	 * @return
	 */
	public List<PublishedTesting> getPublishedTesting();
	/**
	 * 发布在线考试题 
	 * @param ids
	 * @param testDesc 
	 * @param testTitle 
	 * @param testId 
	 * @return
	 */
	public boolean publishOnlineTesting(String ids, String testTitle, String testDesc, String testId);

	public User getUserByOpenId(String openId);

	public int getTestCount(Long testId);

	public Vote getVoteDetail(String id);

	public ShowCase getYzInfoDetail(Long valueOf);

	public void setVoteStatus(Long valueOf, String status);

	public int getVoteCount();

	public Vote getActiveVote();

	public int saveVote(User currentUser, String[] parameterValues, String empType, String jobType, String voteId);

	public boolean checkVoter(String username);
	/**
	 * 调查报告
	 * @param valueOf
	 * @return
	 */
	public List<Map<String, String>> getVoteReportData(Long valueOf);

	public Training getTraining(Long id);

	public Training updateTraining(String title, String tcontent, String id);

	public Map<String, Object> getFeedbackList(int start, int intValue);

	public int getOnlineStudyCount();

	public int saveDemoTestResult(User currentUser, String[] parameterValues, String snum);

	public void setTPStatus(Long valueOf, String status);

	public void saveTP(Map<String, String> param, String[] parameterValues);
	/** 获得已发布的投票 */
	public TP getTP();

	public void updateTP(Long tpId, Map<String, String> param, String[] parameterValues);

	public TP getTPById(Long tpId);

	/** 更新投票的数量 
	 * @param slimit */
	public void updateTPCount( String optId);
	public void updateTPCount( String[] optIds);

	public boolean delFeedBack(String id);

	public void updateStatistics(String destPage);

	public ScanReport getVisitReportData();

	public AboutUs updateAboutUs(String title, String tcontent);

	public AboutUs getAboutus();

	public Map<String, Object> getTrainingList(int start, int intValue);

	public void delTraining(Long valueOf);

	public void delColumn(String id);

	public OnlineTests getOnlineTest();

	public void exportFeedBack(List<Feedback> list, ServletOutputStream outputStream) throws Exception;
	/** 获取所有测试 */
	public List<OnlineTests> getAllTests();
	
	public OnlineTests getOnlineTest(Long id);
	
	/** 根据考卷ID获取该考卷的所有题目 */
	public List<PublishedTesting> getOnlineTests(Long testId);

	public boolean delTest(Long testId);

	public Long addProduct(Map<String, String> param);
	/**
	 * 产品上架 
	 * @param id
	 * @param status 
	 */
	public void putOnSale(String id, int status);

	/**
	 * 套餐列表 
	 * @param start
	 * @param intValue
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getProductList(int start, int intValue, Map<String, String> paramMap);

	/**
	 * 根据ID删除套餐 
	 * @param id
	 * @return
	 */
	public boolean delFoodCat(String id);

	public void updateFoodProduct(Products fp, String fileName);

	/**
	 * 根据产品规格 
	 * @param productCat
	 * @return
	 */
	public Products getProductByGuige(String productCat);

	/**
	 *  分页查询所有产品
	 * @param start
	 * @param intValue
	 * @param paramMap  -- 预留的查询参数， 比如产品分类
	 * @return
	 */
	public Map<String, Object> getAllProductList(int start, int intValue, Map<String, String> paramMap);
	
	/**
	 * 根据商品ID获得商品
	 * @param prdId
	 * @return
	 */
	public Products getProductById(Long prdId);

	
	public Map<String, Object> getMyOrders(int start, int limit, Map<String, String> paramMap);
	
	/**
	 * 更新订单状态 - 支付成功
	 * @param orderNum
	 * @param bank
	 * @param transId
	 * @param status
	 */
	public void updateOrderPayStatus(Map<String, String> params);

	/**
	 * 
	 * @return
	 */
	public List<ConfigParam> getConfigParams();

	public void updateConfigParam(String string, String dispPrice);
	public void updateConfigParam(Map<String,String> paramMap);
	/**
	 * 通用分页查询业务类 
	 * @param dao - 需要查询的dao
	 * @param start
	 * @param limit
	 * @param paramMap 
	 * @return
	 */
	Map<String, Object> getPagingObjList(String daoBeanname, int start, int limit, Map<String, String> paramMap);

	public User getUserByNameAndPwd(String username, String oldpwd);

	public void saveUser(User u);

	public int addOrUpdateVip(String vipname, Long id, String tgGuige);

	public Spreader getSpreadVip(String vname);

	public List<Spreader> getAllVip();

	public int delVip(Long id);

	/**
	 * 加载评价 
	 * @param start
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> loadPj(int start, int limit, Map<String, String> paramMap);

	
	public Paging getPjList(int start, int intValue, Map<String, String> paramMap);

	public boolean delPj(Long id);

	/** 根据大V微信号查找 */
	public Spreader getSpreaderByName(String username);

	/**
	 * 获得运费，根据商家自己的设置 
	 * @param orgCode
	 * @return
	 */
	public float getDeliverFee(String orgCode);

	/**
	 * 
	 * @param vname
	 * @return  [0] - 提成比例   【1】 - 当前积分
	 */
	public String[] loadVipIndexInfo(String vname);

	public void updateProductPic(Long prdId, String fullPath);

	public boolean duihuanPrd(Long userId, Long prdId);

	public List<OrgObj> loadOrg();

	public List<OrgObj> load2LevelOrg();

	public boolean addSubOrg(Long pid, String name);

	public boolean delOrg(Long valueOf);

	public boolean modOrg(Long valueOf, String orgName);

	public Org getCharger(Long valueOf);

	public boolean updateCharger(Long valueOf, Long valueOf2);

	/**
	 * 查询待审核的经销商列表 
	 * @param start
	 * @param intValue
	 * @param param
	 * @return
	 */
	public Paging getAuditOrgList(int start, int intValue, Map<String, String> param);

	/**
	 * 根据userId查找其负责的分销商，往下查找
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<OrgObj> loadOrg(Long userId, int status);

	/**
	 * 改变org分销商的状态
	 * @param valueOf
	 * @param i
	 * @return
	 */
	public boolean auditOrgStatus(Long orgId, int i);

	public Org getOrgBycode(String orgCode);

	public Org addSubOrg(String orgCode, Map<String, String> param);

	public boolean addTopOrg(String topOrgName, String telephone, String email, String city);
	/** 根据用户ID查找所属的分销商 */
	public Org getOrgByUserId(Long id);

	public boolean updateCommission(Long orgId, int commission, int personCommission);
	/** 根据参数key去查找参数值 */
	public String getConfigParam(String wxServerCtx);
	
	public Org getOrgById(Long orgId);

	/**
	 * 更新分销商公众号的appid和appsecret 
	 * @param orgId
	 * @param appid
	 * @param appsecret
	 * @param wxID 
	 * @param kfAccount 
	 * @return
	 */
	public boolean updateWxconfig(Long orgId, String appid, String appsecret, String wxID, String kfAccount);
	/**
	 * 更新微商城有效期
	 * @param orgId
	 * @param mallexpire
	 * @return
	 */
	public boolean updateWxMallexpire(Long orgId, String mallexpire);

	/**
	 * 申请成为分销客 
	 * @param openId
	 * @param currentFxCode - 前台用户访问的分销商编码
	 */
	public User applyFxPerson(String openId, String currentFxCode);

	public Map<String, Object> getKeywords(int start, int intValue, Map<String, String> paramMap);

	/**
	 当flag=1 获得该分销客累计佣金
	 * 当flag=0 获得该分销客当前佣金
	 * @param user
	 * @param flag - 1 累计佣金   0 - 当前可提佣金
	 * @return
	 */
	public Long getYj(User user, int flag);

	/**
	 * 更新转发时的配置
	 * @param params -需更新的参数
	 * @return
	 */
	public boolean updateShareConfig(Map<String, String> params, String currentOrgCode);

	public Map<String, String> getFxkInfo(User currentUser);

	/**
	 * 保存分销客户
	 * 直接保存到用户表
	 * 和用户关注该微信号时的逻辑一样，如果下次该用户在此关注公众号时，就作为老用户
	 * @param wxUserByOrg
	 * @param org 
	 * @param agentId - 分销客的ID
	 */
	public Long saveWxCustomer(WxUser wxUserByOrg, Org org, String agentId,String inviter_id);

	public boolean updateOrg(Org orgBycode);
	/**
	 * 删除审核记录 
	 * @param valueOf
	 * @return
	 */
	public boolean delAudit(Long valueOf);

	/**
	 * 根据当前分销商查找所属分类 
	 * @param orgCode
	 * @return
	 */
	public List<OrgObj> loadPrdCat(String orgCode);

	/**
	 * 新增一级分类
	 * @param name
	 * @param orgCode 
	 * @return
	 */
	public Long addTopPrdcat(String name, String orgCode);

	/**
	 * 修改分类
	 * @param valueOf
	 * @param orgName
	 * @return
	 */
	public boolean modCat(Long valueOf, String orgName);

	public boolean delCat(Long id);

	public long addSubCat(Long pid, String name, String currentOrgCode);

	
	public void updatePayParam(String orgId, String mchId, String payKey);

	public void cashApply(CashApply ca);

	public void updateIndexShow(Org org, String indexShow);

	/**
	 * 分销商的佣金
	 * @param org - 当前分销商管理员
	 * @param i - 1-累计佣金  0-当前可提佣金
	 * @return
	 */
	public Long getFxYj(Org org, int i);

	/**
	 * 
	 * @param id
	 * @param targetStatus 目标状态：1 - 已处理
	 */
	public boolean handleCashApply(Long id, int targetStatus);

	/**
	 * 分销商取消提现：从提现记录表里删除该记录 
	 * @param valueOf
	 * @return
	 */
	public boolean cancelApply(Long valueOf);

	/**
	 *  更新运费
	 * @param orgId
	 * @param deliverFee
	 * @return
	 */
	public boolean updateDeliverFee(Long orgId, Integer deliverFee, Integer baoyou);

	public FxApplyConfig getFxApplyConfig(String currentOrgCode);

	public boolean saveOrUpdateFxApplyConfig(FxApplyConfig fac);

	/**
	 * 管理工具： 自动生成评价 
	 * @param integer
	 * @param prdId
	 * @return
	 */
	public boolean generatePj(Integer integer, String prdId);

	public int countOrg();

	public List<OrgObj> loadMenu(Long orgId);

	public boolean addTopMenu(Long orgId, String menuName, String mtype, String key, String url);

	public boolean modWxMenu(Long orgId, Map<String, String> mparams);

	public boolean addSubMenu(Long orgId, Map<String, String> params);

	public boolean publish2Wx(Org orgId);

	public boolean delWxMenu(Long dbId);

	public Products getProductByCode(String prdCode);

	public Gift getGiftById(Long giftId);

	public List<ProductCat> getTopCat(String orgCode);

	public List<ProductCat> getSubCat(String orgCode, String pid);

	/** 模板消息发送祝福 */
	public int sendBless(Long orgId, String openId);

	/**
	 * 红包列表 
	 * @param orgId
	 * @return
	 */
	public Paging loadHongbao(int page, int limit, Map<String, String> params);

	/**
	 * 新增红包、优惠券
	 * @param orgId
	 * @param params
	 * @return
	 */
	public boolean addHongbao(Long orgId, Map<String, String> params);

	/**
	 * 删除红包
	 * @param valueOf
	 * @return
	 */
	public boolean delHongbao(Long valueOf);

	/**
	 * 获得红包 
	 * @param orgId 
	 * @param openId
	 * @param uuid
	 * @return
	 */
	public int takeHongbao(Long orgId, String openId, String uuid);

	
	public Paging getMyHongBaoList(int page, int limit, Map<String, String> paramMap);

	public Contacts getContacts(Long orgId, int locId);

	public boolean updateContacts(Long orgId, Map<String, String> param);

	public void saveCarInfo(CarInfo carInfo);

	/**
	 * 根据用户ID，查询该用户的所有订单总额 
	 * @param id
	 * @return
	 */
	public Long getOrdersFee(Long id);

	public List<OrgObj> loadDaili(Long pid);

	public Paging getAreaOrderList(int page, int limit, Map<String, String> paramMap);

	/**
	 * 获取发现列表
	 * @param page
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	public Paging getFaxianList(int page, int limit, Map<String, String> paramMap);

	public void delCoupon(Long id);
	
	public List<WxRulesImage> getAllWxRulesImage();
	
	public Map<String, Object> getImageKeywords(int start, int limit, Map<String, String> paramMap);
	
}

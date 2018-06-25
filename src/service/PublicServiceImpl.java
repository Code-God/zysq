package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.bo.Experts;
import model.bo.Feedback;
import model.bo.LogRecord;
import model.bo.ShowCase;
import model.bo.ZanLog;
import model.bo.auth.Org;
import model.bo.fenxiao.OneProduct;
import model.bo.food.ShoppingCart;
import model.bo.wxmall.Pj;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.intf.AdminService;
import service.intf.PublicService;

import com.base.ServerBeanFactory;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.wfsc.common.bo.account.Address;
import com.wfsc.common.bo.ad.AdvConfig;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.order.OrdersDetail;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.common.bo.user.User;
import com.wfsc.daos.AdminDao;
import com.wfsc.daos.account.AddressDao;
import com.wfsc.daos.ad.AdDao;
import com.wfsc.daos.orders.OrdersDao;
import com.wfsc.daos.orders.OrdersDetailDao;
import com.wfsc.daos.product.ProductsDao;
import com.wfsc.daos.user.UserDao;
import com.wfsc.services.orders.IOrdersService;
import com.wfsc.services.productcat.IProductCatService;
import com.wfsc.util.DateUtil;

import dao.ExpertsDao;
import dao.LogRecordDao;
import dao.OrgDao;
import dao.ShowCaseDao;
import dao.ZanLogDao;
import dao.fenxiao.OneProductDao;
import dao.food.ShoppingCartDao;
import dao.wxmall.PjDao;
import dao.youzhen.FeedBackDao;

@Service("publicService")
public class PublicServiceImpl implements PublicService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ZanLogDao zanLogDao;

	@Autowired
	private LogRecordDao logRecordDao;

	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	@Override
	public User login(Map<String, String> param) {
		// 初次登陆时，如果用户名和身份证正确，更新下openId即可，这就算绑定成功了，以后不需要再登录了
		try {
			String openId = param.get("openId");
			String idCard = param.get("idcard");
			User user = this.userDao.getUniqueEntityByOneProperty("idCard", idCard);
			if (user != null) {
				user.setOpenId(openId);
				this.userDao.updateEntity(user);// 更新成功
			} else {
				// 该用户不存在
				return null;
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean zan(String sesId, User currentUser, String targetId) {
		// 先检查是否已经点赞
		String loginId = "";
		if (currentUser == null) {
			loginId = "SES-" + sesId;
		} else {
			loginId = currentUser.getId() + "";
		}
		ZanLog log = zanLogDao.getUniqueEntityByPropNames(new String[] { "loginId", "targetId" }, new Object[] { loginId,
				targetId });
		if (log != null) {
			return false;// 已经赞过了
		}
		try {
			ZanLog zl = new ZanLog();
			if (currentUser != null) {// 未登录的用户也可以点赞
				zl.setLoginId(loginId);
			} else {
				// 未登录的，就记录sessionId
				zl.setLoginId("SES-" + sesId);
			}
			zl.setTargetId(targetId);
			zl.setZtime(DateUtil.getLongCurrentDate());
			this.zanLogDao.saveEntity(zl);
			// 增加赞的计数
			ShowCaseDao scDao = (ShowCaseDao) ServerBeanFactory.getBean("showCaseDao");
			ShowCase theCase = scDao.getEntityById(Long.valueOf(targetId));
			theCase.setZan(theCase.getZan() + 1);
			scDao.updateEntity(theCase);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void setZanLogDao(ZanLogDao zanLogDao) {
		this.zanLogDao = zanLogDao;
	}

	@Override
	public int sign(User currentUser) {
		try {
			// 查看今天是否已经签到过了
			String sql = "from LogRecord where  actType=" + LogRecord.ACT_SIGN + "and loginId='" + currentUser.getId()
					+ "' and actTime like '" + DateUtil.getShortCurrentDate() + "%'";
			List find = logRecordDao.getHibernateTemplate().find(sql);
			if (find.size() > 0) {
				return 2;// 已经签到过了
			}
			// 签到一次获取积分，并记录到历史表
			User u = userDao.getEntityById(currentUser.getId());
			int add = Integer.valueOf(Version.getInstance().getNewProperty("signScore"));
			u.setScore(u.getScore() + add);
			userDao.updateEntity(u);
			logger.info("签到成功...获得积分:" + add);
			LogRecord lr = new LogRecord();
			lr.setActType(LogRecord.ACT_SIGN);
			lr.setLoginId(u.getId() + "");
			lr.setActTime(DateUtil.getLongCurrentDate());
			lr.setUserName(u.getUsername());
			this.logRecordDao.saveEntity(lr);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void setLogRecordDao(LogRecordDao logRecordDao) {
		this.logRecordDao = logRecordDao;
	}

	public static void main(String[] args) {
		int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int m = Calendar.getInstance().get(Calendar.MINUTE);
		System.out.println(i + "|" + m);
	}

	/**
	 * #抢沙发起始时间：几点 // sofaStartHour=12 // #抢沙发起始时间：几分 // sofaStartMinue=0 // // #抢沙发结束时间：几点 // sofaEndHour=12 // #抢沙发结束时间：几分 //
	 * sofaEndMinue=5
	 */
	@Override
	public int sofa(User currentUser) {
		// 抢沙发：中午十二点到十二点零五分，期间前五名为抢沙发有效
		//	
		try {
			// 如果已经抢过沙发了，返回-1
			// 查看今天是否已经抢过了
			String sql = "from LogRecord where actType=" + LogRecord.ACT_SOFA + "and loginId='" + currentUser.getId()
					+ "' and actTime like '" + DateUtil.getShortCurrentDate() + "%'";
			List find = logRecordDao.getHibernateTemplate().find(sql);
			if (find.size() > 0) {
				return -1;// 已经抢过了
			}
			// 返回当天12点以后的名次
			int rank = logRecordDao.getSofaAndReturnRank(currentUser.getId().toString(), currentUser.getUsername());
			return rank;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<User> getHeroList() {
		// 取分数最高的前十
		String sql = "from User where score > 0 order by score desc limit 0, 10";
		return this.userDao.getHibernateTemplate().find(sql);
	}

	@Override
	public User getUserByOpenId(String openId) {
		return userDao.getUniqueEntityByOneProperty("openId", openId);
	}

	@Override
	public ShowCase getShowCase(String caseId) {
		ShowCaseDao scDao = (ShowCaseDao) ServerBeanFactory.getBean("showCaseDao");
		return scDao.getEntityById(Long.valueOf(caseId));
	}

	@Override
	public Experts getExpert(String id) {
		ExpertsDao dao = (ExpertsDao) ServerBeanFactory.getBean("expertsDao");
		return dao.getEntityById(Long.valueOf(id));
	}

	@Override
	public void updateUser(User u) {
		if (u != null) {
			userDao.updateEntity(u);
		}
	}

	@Override
	public void submitFeedback(String name, String content) {
		FeedBackDao dao = (FeedBackDao) ServerBeanFactory.getBean("feedBackDao");
		Feedback fb = new Feedback(name, content);
		fb.setFdate(DateUtil.getLongCurrentDate());
		dao.saveEntity(fb);
	}

	@Override
	public void doRegisterBind(Map<String, String> paramMap) {
		String openId = paramMap.get("openId");
		String email = paramMap.get("email");
		String telephone = paramMap.get("telephone");
		String nickName = paramMap.get("nickName");
		User obj = this.userDao.getUniqueEntityByOneProperty("openId", openId);
		if (obj == null) {
			User u = new User();
			u.setEmail(email);
			u.setUsername(nickName);
			u.setOpenId(openId);
			u.setTelephone(telephone);
			this.userDao.saveEntity(u);
		} else {
			logger.info("已存在，无需注册..");
			throw new RuntimeException("已经绑定过了！");
		}
	}

	@Override
	public void updateAddress(Map<String, String> param) {
		AddressDao dao = (AddressDao) ServerBeanFactory.getBean("addressDao");
		String label = param.get("label");
		String accepter = param.get("accepter");
		String telephone = param.get("telephone");
		String address = param.get("address");
		String postcode = param.get("postcode");
		String isdefault = param.get("isdefault");
		Long userid = Long.valueOf(param.get("userId"));
		Long uaId = Long.valueOf(param.get("uaId"));
		// 如果是默认地址，则需要更改其他地址的默认状态
		Address ua = dao.getEntityById(uaId);
		int flag = 0;
		if (isdefault == null) {// 说明不是默认地址
			flag = 0;
		} else {
			flag = 1;
			// 取出该用户所有地址，修改其他地址状态
			List<Address> uas = dao.getEntitiesByOneProperty("user.id", userid);
			for (Address userAddress : uas) {
				userAddress.setIsDefault(0);
				dao.updateEntity(userAddress);
			}
		}
		ua.setUsername(accepter);
		ua.setUserid(userDao.getEntityById(userid).getId());
		ua.setDetailAddr(address);
		ua.setAlias(label);
		ua.setZipcode(postcode);
		ua.setTel(telephone);
		ua.setIsDefault(flag);// 是否默认地址
		dao.updateEntity(ua);
	}

	@Override
	public long[] add2Cart(Map<String, String> param) {
		try {
			ProductsDao prdDao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
			ShoppingCartDao dao = (ShoppingCartDao) ServerBeanFactory.getBean("shoppingCartDao");
			UserDao userDao = (UserDao) ServerBeanFactory.getBean("userDao");
			String userId = param.get("userId");
			String prdId = param.get("prdId");
			String count = (param.get("count") == null ? "1" : param.get("count"));
			Products product = prdDao.getEntityById(Long.valueOf(prdId));
			User user = userDao.getEntityById(Long.valueOf(userId));
			
			Long scId = 0L;
			//查看是否有合并的商品
			ShoppingCart item = dao.getUniqueEntityByPropNames(new String[]{"user.id","product.id"}, new Object[]{user.getId(), Long.valueOf(prdId)});
			if(item != null){
				//需要合并
				item.setScount(Integer.valueOf(count) + item.getScount());
				dao.updateEntity(item);
				scId = item.getId();
			}else{
				ShoppingCart sc = new ShoppingCart();
				sc.setScount(Integer.valueOf(count));
				sc.setProduct(product);
				sc.setUser(user);
				scId = dao.saveEntity(sc);
			}
			int num = dao.countEntitiesByOrCondition("user.id", Long.valueOf(userId));
			return new long[]{num, scId};
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ShoppingCart> getShoppingCartPrdList(Long userId) {
		ShoppingCartDao dao = (ShoppingCartDao) ServerBeanFactory.getBean("shoppingCartDao");
		return dao.getEntitiesByOneProperty("user.id", userId);
	}

	@Override
	public int delfromCart(List<Long> idList, Long userId) {
		ShoppingCartDao dao = (ShoppingCartDao) ServerBeanFactory.getBean("shoppingCartDao");
		dao.deletAllEntities(idList);
		return dao.countEntitiesByOrCondition("user.id", Long.valueOf(userId));
	}

	@Override
	public List<Address> getUserAdd(Long userId) {
		AddressDao dao = (AddressDao) ServerBeanFactory.getBean("addressDao");
		return dao.getEntitiesByOneProperty("user.id", userId);
	}

	@Override
	public void saveUserAddress(Map<String, String> param) {
		AddressDao dao = (AddressDao) ServerBeanFactory.getBean("addressDao");
		UserDao userDao = (UserDao) ServerBeanFactory.getBean("userDao");
		String label = param.get("label");
		String accepter = param.get("accepter");
		String telephone = param.get("telephone");
		String address = param.get("address");
		String postcode = param.get("postcode");
		String isdefault = param.get("isdefault");
		Long userid = Long.valueOf(param.get("userId"));
		// 如果是默认地址，则需要更改其他地址的默认状态
		Address ua = new Address();
		int flag = 0;
		if (isdefault == null) {// 说明不是默认地址
			flag = 0;
		} else {
			flag = 1;
			// 取出该用户所有地址，修改其他地址状态
			List<Address> uas = dao.getEntitiesByOneProperty("userid", userid);
			for (Address userAddress : uas) {
				userAddress.setIsDefault(0);
				dao.updateEntity(userAddress);
			}
		}
		ua.setUsername(accepter);
		ua.setUserid(userDao.getEntityById(userid).getId());
		ua.setDetailAddr(address);
		ua.setAlias(label);
		ua.setZipcode(postcode);
		ua.setTel(telephone);
		ua.setIsDefault(flag);// 是否默认地址
		
//		ua.setAccepter(accepter);
//		ua.setUser(userDao.getEntityById(userid));
//		ua.setAddress(address);
//		ua.setLabel(label);
//		ua.setPostcode(postcode);
//		ua.setTelephone(telephone);
//		ua.setIsdefault(flag);// 是否默认地址
		dao.saveEntity(ua);
	}

	@Override
	public void delUa(Long id) {
		AddressDao dao = (AddressDao) ServerBeanFactory.getBean("addressDao");
		dao.deleteEntity(id);
	}

	@Override
	public Address loadUa(Long id) {
		AddressDao dao = (AddressDao) ServerBeanFactory.getBean("addressDao");
		return dao.getEntityById(id);
	}

	@Override
	public void updateCart4Count(Map<String, String> param) {
		ShoppingCartDao dao = (ShoppingCartDao) ServerBeanFactory.getBean("shoppingCartDao");
		Set<String> keySet = param.keySet();
		for (String s : keySet) {
			logger.info("in updateCart4Count:::" + s);
			ShoppingCart sc = dao.getEntityById(Long.valueOf(s));
			if(sc != null){
				sc.setScount(Integer.valueOf(param.get(s)));
				dao.updateEntity(sc);
			}
		}
	}

	@Override
	public List<Address> loadAllUserAddress(Long userId) {
		AddressDao dao = (AddressDao) ServerBeanFactory.getBean("addressDao");
		return dao.getEntitiesByOneProperty("userid", userId);
	}

	@Override
	public float getOrderFee(Long userId, String[] scids) {
		float fee = 0.0f;
		ShoppingCartDao dao = (ShoppingCartDao) ServerBeanFactory.getBean("shoppingCartDao");
		List<ShoppingCart> list = dao.getEntitiesByOneProperty("user.id", userId);
		for (ShoppingCart sc : list) {
			for (String id : scids) {
				if(Long.valueOf(id).longValue() == sc.getId().longValue()){
					fee += (sc.getScount() * sc.getProduct().getPrdDisPrice());
				}
			}
		}
		return fee;
	}

	@Override
	public int getOrderFeeByOpenId(String openId) {
		int fee = 0;
		ShoppingCartDao dao = (ShoppingCartDao) ServerBeanFactory.getBean("shoppingCartDao");
		List<ShoppingCart> list = dao.getEntitiesByOneProperty("user.openId", openId);
		for (ShoppingCart sc : list) {
			fee += (sc.getScount() * sc.getProduct().getPrdPrice()) * 100;
		}
		logger.info("==订单金额：" + fee + " 分==");
		return fee;
	}

	@Override
	public Orders saveOrder(Map<String, String> param) {
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		IOrdersService orderService = (IOrdersService) ServerBeanFactory.getBean("ordersService");
		OrdersDao dao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
		OrdersDetailDao detailDao = (OrdersDetailDao) ServerBeanFactory.getBean("ordersDetailDao");
		UserDao userDao = (UserDao) ServerBeanFactory.getBean("userDao");
		ProductsDao prdDao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
		Orders order = new Orders();
		//如果当前用户是从分销客转发的链接进入，那么session中必然有agentId（分销客的openId），根据这个参数就能判断是分销的订单，分销的订单前缀为T0000
		logger.info("==非推广订单...fxCode=" + param.get("fxCode"));
		// 当前用户ID
		Long userId = Long.valueOf(param.get("userId"));
		order.setAddress(param.get("destlocation"));
		order.setBuyerMsg(param.get("buyerMsg"));// 买家留言
		// 设置运费, 运费可以由总销商自行设置
		order.setTransFee(service.getDeliverFee(param.get("fxCode")));
//			分销商code
		order.setFxCode(param.get("fxCode"));
//			String orderNum = DateUtil.getShortDateMark() + "-"
//					+ String.format("%05d", QueryUtilImpl.getInstance().getOrderNum("order"));
		String orderNum = "";
		
		//---------- 保存订单时  用来区分是否是分销客的用户-------------------
		if (param.get("agentId") != null) {
			String orderPrefix = "T-";
			orderNum = orderPrefix +  orderService.generateCodeByType("orderNo");
			order.setVname(param.get("agentId"));
//			//分销客ID
			order.setFxpersonId(Long.valueOf(param.get("agentId")));
			logger.info("推广/非注册==生成订单号：" + orderNum);
		}else{
			//直接用户。。。。
			orderNum = orderService.generateCodeByType("orderNo");
			logger.info("直接用户订单， 生成订单号：" + orderNum);
		}
		
		//用户从购物车里选择的条目
		String scIds = param.get("scIds");
		String[] scids = StringUtils.split(scIds, ",");
		
		order.setOrderNo(orderNum);
		order.setOdate(new Date());
		order.setStatus(0);// 订单状态0 – 未支付， 1-已支付，2-已发货，3-已完成 9-废弃
		// 从购物车统计金额
		float finalFee = this.getOrderFee(userId, scids) + order.getTransFee();
		if(param.get("hbvalue") != null){
			finalFee = (finalFee - Float.valueOf(param.get("hbvalue")));
		}
		order.setFee(finalFee);// 订单金额
		// order.setUserName(userName)
		User user = userDao.getEntityById(userId);
		order.setUser(user);
		order.setUserName(user.getUsername());
		order.setInvoiceTitle(param.get("invoiceTitle"));
		dao.saveEntity(order);
		logger.info("订单保存完毕.....");
		List<OrdersDetail> dlist = new ArrayList<OrdersDetail>();
		// 根据购物车，生成订单明细表记录
		List<ShoppingCart> list = this.getShoppingCartPrdList(userId);
		
		
		for (ShoppingCart sc : list) {
			for(int k=0; k<scids.length ; k++){
				if(sc.getId().longValue() == Long.valueOf(scids[k]).longValue()){//只处理用户选择的结账
					OrdersDetail od = new OrdersDetail();
					od.setPrdCount(sc.getScount());
					od.setOrderNo(order.getOrderNo());
					od.setPrdCode(sc.getProduct().getPrdCode());
					od.setProdName(sc.getProduct().getName());
					od.setPrdPrice(sc.getProduct().getDisPrice());
					od.setTotalPrice(sc.getProduct().getDisPrice() * sc.getScount());
					od.setPrdImage(sc.getProduct().getPicUrl1());
					detailDao.saveEntity(od);
					dlist.add(od);
					// ------更新库存（如果用户取消订单，库存还需要恢复）------------
					int i = sc.getProduct().getStock() - od.getPrdCount();
					sc.getProduct().setStock(i > 0 ? i : 0);
					prdDao.updateEntity(sc.getProduct());
					
//					// 清除购物车(只清除用户选择结账的)
//					scdao.deleteEntity(Long.valueOf(scids[k]));
//					logger.info("购物车清除完毕.....");
				}
			}
		}
		// 非持久属性，用来在页面上显示的
		order.setOrdersDetail(dlist);
		logger.info("订单明细保存完毕.....");
		
		// 如果是需要保存地址
		if ("1".equals(param.get("saveflag"))) {
			logger.info("保存新的默认地址.....");
			param.put("isdefault", "1");
			param.put("label", "我的默认地址");
			this.saveUserAddress(param);
		}
		return order;
	}

	@Override
	public Orders getOrder(Long orderId) {
		OrdersDao dao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
		OrdersDetailDao detailDao = (OrdersDetailDao) ServerBeanFactory.getBean("ordersDetailDao");
		ProductsDao prdDao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
		Orders order = dao.getEntityById(orderId);
		if(order == null){
			return null;
		}
//		List<OrdersDetail> ds = detailDao.getEntitiesByOneProperty("order.id", orderId);
		List<OrdersDetail> ds = detailDao.getEntitiesByOneProperty("orderNo", order.getOrderNo());
		for (OrdersDetail ordersDetail : ds) {
			ordersDetail.setProduct(prdDao.getUniqueEntityByOneProperty("prdCode", ordersDetail.getPrdCode()));
		}
		order.setOrdersDetail(ds);
		return order;
	}

	@Override
	public void cancelOrder(Long orderId) {
//		ProductsDao prdDao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
		OrdersDao dao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
		// 订单状态 0-未支付， 1-已支付， 2-支付失败， 3-已取消 */
		dao.getHibernateTemplate().bulkUpdate("update Orders set status=9 where id=" + orderId);
		// 恢复库存
//		OrdersDetailDao detailDao = (OrdersDetailDao) ServerBeanFactory.getBean("ordersDetailDao");
//		List<OrdersDetail> odList = detailDao.getEntitiesByOneProperty("order.id", orderId);
//		for (OrdersDetail od : odList) {
//			od.getProduct().setStock(od.getOcount() + od.getProduct().getStock());
//			prdDao.updateEntity(od.getProduct());
//		}
	}

	@Override
	public void orderFail(String orderNum) {
		OrdersDao dao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
		// 订单状态 订单状态 0 – 未支付， 1-已支付，2-已发货，3-已完成 4-失败 9-废弃 */
		dao.getHibernateTemplate().bulkUpdate("update Order set ostatus=4 where orderNum='" + orderNum + "'");
	}

	@Override
	public void pj(Map<String, String> paramMap) {
		try {
			String orderId = paramMap.get("orderId");
			//商品编号
			String prdCode = paramMap.get("prdCode");
			String odId = paramMap.get("odId");
			String orgId = paramMap.get("orgId");
			String pjscore = paramMap.get("pjscore");
			String pjContent = paramMap.get("pjContent");
			String userId = paramMap.get("userId");
			
			ProductsDao prdDao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
			Products prd = prdDao.getUniqueEntityByOneProperty("prdCode", prdCode);
			
			PjDao pjDao = (PjDao) ServerBeanFactory.getBean("pjDao");
			User user = this.userDao.getEntityById(Long.valueOf(userId));
			Pj pj = new Pj();
			pj.setContent(pjContent);
			pj.setPjDate(DateUtil.getLongCurrentDate());
			pj.setOrderId(Long.valueOf(orderId));
			pj.setPjer(user);
			pj.setPrdid(prd.getId());
			pj.setOrgId(Long.valueOf(orgId));//增加公司ID， 便于将来统计和查询，减少关联查询开销
			pj.setScore(Integer.valueOf(pjscore));
			pjDao.saveEntity(pj);
			// 更新订单明细表里的评价标记
			OrdersDetailDao detailDao = (OrdersDetailDao) ServerBeanFactory.getBean("ordersDetailDao");
			OrdersDetail od = detailDao.getEntityById(Long.valueOf(odId));
			od.setIsComment(1);
			detailDao.updateEntity(od);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存失败..." + e.getMessage());
		}
	}

	@Override
	public int getOrderFeeByOrderNum(String orderNum) {
		OrdersDao dao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
		OrgDao orgDao =(OrgDao) ServerBeanFactory.getBean("orgDao");
		Orders o = dao.getUniqueEntityByOneProperty("orderNo", orderNum);
		
		//去掉包邮的费用，如果满足的话
		Org org = orgDao.getUniqueEntityByOneProperty("code", o.getFxCode());
		if((o.getFeePrice()-o.getTransFeePrice()) > org.getBaoyou().intValue()){
			logger.info("订单号：" + o.getOrderNo() + "符合包邮..");
			//符合包邮，去掉邮费
			return (o.getFeePrice().intValue() - o.getTransFeePrice().intValue());
		}
		return o.getFeePrice().intValue();// 转化成分
	}

	@Override
	public List<ProductCat> loadSubCategory(String fxCode, String pid) {
		IProductCatService productCatService = (IProductCatService) ServerBeanFactory.getBean("productCatService");
		//根据分类所属公司，查找只属于该公司的分类, 每个分类只与某个分销商的顶级分销代码相关联， 如001, 002, 003等
		return productCatService.getAllProductCatByOrg(fxCode.substring(0, 3), pid);
	}

	@Override
	public boolean confirmGet(String orderId) {
		try{
			OrdersDao dao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
			Orders order = this.getOrder(Long.valueOf(orderId));
			if(order != null){
				order.setGetDate(DateUtil.getLongCurrentDate());
				order.setStatus(3);//设置为已收货  订单状态 0 – 未支付， 1-已支付，2-已发货，3-已完成 9-废弃*/
				dao.updateEntity(order);
			}else{
				return false;//该订单不存在
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public OneProduct getOneProductInfo(Long orgId) {
		OneProductDao dao = (OneProductDao) ServerBeanFactory.getBean("onePrdDao");
		return dao.getUniqueEntityByOneProperty("orgId", orgId);
	}

	@Override
	public List<AdvConfig>  getRecommendedList(Long orgId) {
		AdDao dao = (AdDao) ServerBeanFactory.getBean("adDao");
		return dao.getEntitiesByPropNames(new String[]{"orgId", "adType"}, new Object[]{orgId, 1});
	}

	@Override
	public Org getOrgByCity(String city) {
//		//根据城市找到管理员， 
//		AdminDao dao = (AdminDao) ServerBeanFactory.getBean("adminDao");
//		Admin admin = dao.getUniqueEntityByOneProperty("area", city);
//		//根据管理员找到所属的代理商（系统里的分销商）
		OrgDao orgDao =(OrgDao) ServerBeanFactory.getBean("orgDao");
//		if(admin == null){//此地区还未设置管理员
//			
//		}
		return orgDao.getEntityById(Long.valueOf(Version.getInstance().getNewProperty("testOrgId")));
	}
}

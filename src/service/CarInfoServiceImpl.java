package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Paging;
import model.bo.car.CarInfo;
import model.bo.car.CarInsurenceInfo;
import model.bo.car.CleaningCards;
import model.bo.car.CleaningInfo;
import model.bo.car.CleaningLogs;
import model.bo.car.RescueInfo;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import service.intf.ICarInfoService;

import com.base.ServerBeanFactory;
import com.base.log.LogUtil;
import com.wfsc.common.bo.account.Coupon;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.daos.AdminDao;
import com.wfsc.daos.account.CleaningCardsDao;
import com.wfsc.daos.account.CleaningLogDao;
import com.wfsc.daos.account.CouponDao;
import com.wfsc.daos.orders.OrdersDao;
import com.wfsc.daos.user.UserDao;
import com.wfsc.services.orders.IOrdersService;
import com.wfsc.util.DateUtil;

import dao.car.CarInfoDao;
import dao.car.CarInsurenceInfoDao;
import dao.car.CarLibDao;
import dao.car.CleaningInfoDao;
import dao.car.RescueInfoDao;

@Service("carService")
public class CarInfoServiceImpl implements ICarInfoService {

	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	@Override
	public String saveOrUpdateCarInfo(CarInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllCarBrands() {
		List<String> result = new ArrayList<String>();
		CarLibDao dao = (CarLibDao) ServerBeanFactory.getBean("carLibDao");
		List find = dao.getHibernateTemplate().find(
				"SELECT  alphabeta, brandName FROM CarLib GROUP BY brandName ORDER BY alphabeta ASC");
		for (Object object : find) {
			Object[] s = (Object[]) object;
			result.add(s[0].toString() + "-" + s[1].toString());
		}
		//
		return result;
	}

	@Override
	public List<String> loadAllCarTypes(String brandName) {
		List<String> result = new ArrayList<String>();
		CarLibDao dao = (CarLibDao) ServerBeanFactory.getBean("carLibDao");
		List find = dao.getHibernateTemplate().find(
				"SELECT carYear, carTypeName, cc FROM CarLib WHERE brandName='" + brandName + "'");
		for (Object object : find) {
			Object[] s = (Object[]) object;
			result.add(s[0].toString() + "-" + s[1].toString() + "-" + s[2].toString());
		}
		return result;
	}

	@Override
	public Paging loadRescueList(int page, int limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		RescueInfoDao dao = (RescueInfoDao) ServerBeanFactory.getBean("rescueDao");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = dao.queryRecord(start, limit, paramMap);
			// 分页对象
			int total = Integer.valueOf(map.get("total").toString());
			List<RescueInfo> dataList = (List<RescueInfo>) map.get("list");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pp;
	}

	@Override
	public boolean addRescueInfo(Map<String, String> params) {
		try {
			RescueInfoDao dao = (RescueInfoDao) ServerBeanFactory.getBean("rescueDao");
			RescueInfo ri = new RescueInfo();
			ri.setAddress(params.get("address"));
			ri.setArea(params.get("area"));
			ri.setCharger(params.get("charger"));
			ri.setFacName(params.get("facName"));
			ri.setPrice(Long.valueOf(params.get("price")) * 100);
			ri.setServiceContent(params.get("serviceContent"));
			ri.setTelephone(params.get("telephone"));
			dao.saveEntity(ri);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void delRescue(Long id) {
		RescueInfoDao dao = (RescueInfoDao) ServerBeanFactory.getBean("rescueDao");
		dao.deleteEntity(id);
	}

	@Override
	public void delCleaning(Long id) {
		CleaningInfoDao dao = (CleaningInfoDao) ServerBeanFactory.getBean("cleaningDao");
		dao.deleteEntity(id);
	}

	@Override
	public Paging loadCleaningList(int page, int limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		CleaningInfoDao dao = (CleaningInfoDao) ServerBeanFactory.getBean("cleaningDao");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = dao.queryRecord(start, limit, paramMap);
			// 分页对象
			int total = Integer.valueOf(map.get("total").toString());
			List<CleaningInfo> dataList = (List<CleaningInfo>) map.get("list");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pp;
	}

	@Override
	public boolean addCleaningInfo(Map<String, String> params) {
		try {
			CleaningInfoDao dao = (CleaningInfoDao) ServerBeanFactory.getBean("cleaningDao");
			CleaningInfo ri = new CleaningInfo();
			ri.setAddress(params.get("address"));
			ri.setArea(params.get("area"));
			ri.setCharger(params.get("charger"));
			ri.setFacName(params.get("facName"));
			ri.setPrice(Long.valueOf(params.get("price")) * 100);
			ri.setServiceContent(params.get("serviceContent"));
			ri.setTelephone(params.get("telephone"));
			dao.saveEntity(ri);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Paging loadCouponList(int page, int limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		CouponDao dao = (CouponDao) ServerBeanFactory.getBean("couponDao");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = dao.queryRecord(start, limit, paramMap);
			// 分页对象
			int total = Integer.valueOf(map.get("total").toString());
			List<CleaningInfo> dataList = (List<CleaningInfo>) map.get("list");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pp;
	}

	@Override
	public void consumeCoupon(Long cid) {
		try{
			//核销优惠券， 1.修改状态， 2.更新核销日期
			CouponDao dao = (CouponDao) ServerBeanFactory.getBean("couponDao");
			Coupon c = dao.getEntityById(cid);
			c.setStatus(1);
			c.setConsumeDate(DateUtil.getLongCurrentDate());
			dao.updateEntity(c);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List<Admin> getAllService() {
		AdminDao dao = (AdminDao) ServerBeanFactory.getBean("adminDao");
		return dao.getHibernateTemplate().find("from Admin where company is not null");
	}

	@Override
	public Paging cleaningCardsList(int page, int limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		CleaningCardsDao dao = (CleaningCardsDao) ServerBeanFactory.getBean("cleaningCardsDao");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = dao.queryRecord(start, limit, paramMap);
			// 分页对象
			int total = Integer.valueOf(map.get("total").toString());
			List<CleaningInfo> dataList = (List<CleaningInfo>) map.get("list");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pp;
	}

	@Override
	public String getServiceName(Long serviceId) {
		AdminDao dao = (AdminDao) ServerBeanFactory.getBean("adminDao");
		Admin admin = dao.getEntityById(serviceId);
		if(admin != null){
			return admin.getCompany();
		}
		return null;
	}

	@Override
	public int consumeCleaning(Long cardId) {
		CleaningLogDao dao = (CleaningLogDao) ServerBeanFactory.getBean("cleaningLogDao");
		CleaningCardsDao ccdao = (CleaningCardsDao) ServerBeanFactory.getBean("cleaningCardsDao");
		try{
			//修改card的剩余次数等状态（如果是次卡的话）
			CleaningCards cc = ccdao.getEntityById(cardId);
			if(!cc.getCardType().split("_")[1].endsWith("M")){
				//说明是次卡，需更新洗车记录
				if(cc.getLeftPoints() == 0){
					return 0;//已经用完了，无法核销。
				}
				if(cc.getLeftPoints() - 1 == 0){//最后一次，需修改状态
					cc.setStatus(1);
				}
				cc.setLeftPoints(cc.getLeftPoints() - 1);
				ccdao.updateEntity(cc);
				logger.info("洗车卡核销完毕....");
			}
			//记录洗车日志
			CleaningLogs clog = new CleaningLogs();
			clog.setCardCode(cc.getCardCode());
			clog.setOpenId(cc.getOpenId());
			clog.setTheDate(DateUtil.getLongCurrentDate());
			clog.setUserName(cc.getUserName());
			dao.saveEntity(clog);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 2;
		}
	}

	@Override
	public Paging loadCleaningLogList(int page, int limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		CleaningLogDao dao = (CleaningLogDao) ServerBeanFactory.getBean("cleaningLogDao");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = dao.queryRecord(start, limit, paramMap);
			// 分页对象
			int total = Integer.valueOf(map.get("total").toString());
			List<CleaningInfo> dataList = (List<CleaningInfo>) map.get("list");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pp;
	}

	@Override
	public List<Admin> getLocalServiceNames(Long daliId) {
		List<Admin> results = new ArrayList<Admin>();
		AdminDao dao = (AdminDao) ServerBeanFactory.getBean("adminDao");
		Admin entityById = dao.getEntityById(daliId);
		List<Admin> list = dao.getEntitiesByOneProperty("area", entityById.getArea());
		if(!list.isEmpty()){
			for (Admin admin : list) {
				if(admin.getRoleString().indexOf("服务商") != -1){
					results.add(admin);
				}
			}
		}else{
			return null;
		}
		return results;
	}

	@Override
	public Paging loadHebaoList(int page, int limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		// 分页对象
		model.Paging pp = new model.Paging();
		CarInfoDao dao = (CarInfoDao) ServerBeanFactory.getBean("carInfoDao");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = dao.queryRecord(start, limit, paramMap);
			// 分页对象
			int total = Integer.valueOf(map.get("total").toString());
			List<CleaningInfo> dataList = (List<CleaningInfo>) map.get("list");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pp;
	}

	@Override
	public void delHebao(Long id) {
		try{
			CarInfoDao dao = (CarInfoDao) ServerBeanFactory.getBean("carInfoDao");
			dao.deleteEntity(id);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean submitHebaoPrice(String dbId, String price, String orgCode) {
		try{
			//第2步： 更新车辆信息、保险信息以及状态
			CarInfoDao dao = (CarInfoDao) ServerBeanFactory.getBean("carInfoDao");
			UserDao userDao = (UserDao) ServerBeanFactory.getBean("userDao");
			CarInfo ci = dao.getEntityById(Long.valueOf(dbId));
			
			IOrdersService orderService = (IOrdersService) ServerBeanFactory.getBean("ordersService");
			//第一步：生成车险核保订单
			String orderNo = orderService.generateCodeByType("orderNo");
			Orders order = new Orders();
			order.setOrderNo(orderNo);
			order.setUser(userDao.getUniqueEntityByOneProperty("openId", ci.getOpenId()));
			order.setOdate(new Date());
			order.setCategory(1);//车险类型
			order.setCtype(Orders.CTYPE_RMB);
			order.setFeePrice(Long.valueOf(Long.valueOf(price) * 100));
			order.setFee(Float.valueOf(Long.valueOf(price)));
			order.setFxCode(orgCode);//代理商所加盟的公司orgCode
			order.setInvoiceTitle("车险");
			OrdersDao orderDao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
			orderDao.saveEntity(order);
			logger.info("车险订单生成....");
			
			ci.setPrice(Long.valueOf(price) * 100);
			ci.setFeedbackDate(DateUtil.getLongCurrentDate());
			//修改核保状态
			ci.setFlag(1);
			ci.setOrderId(order.getId());
			dao.updateEntity(ci);
			logger.info("车辆核保信息保存完毕...");
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public CarInfo getHebaoDetail(Long dbId) {
		try{
			CarInfoDao dao = (CarInfoDao) ServerBeanFactory.getBean("carInfoDao");
			CarInsurenceInfoDao ciiDao = (CarInsurenceInfoDao) ServerBeanFactory.getBean("carInsInfoDao");
			CarInfo ci = dao.getEntityById(Long.valueOf(dbId));
			//填充保险信息
			CarInsurenceInfo ciiInfo = ciiDao.getUniqueEntityByOneProperty("carInfo.id", dbId);
			ci.setCiInfo(ciiInfo);
			return ci;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Paging getMyCarInsurence(int page, int limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		model.Paging pp = new model.Paging();
		CarInfoDao dao = (CarInfoDao) ServerBeanFactory.getBean("carInfoDao");
		CarInsurenceInfoDao ciiDao = (CarInsurenceInfoDao) ServerBeanFactory.getBean("carInsInfoDao");
		Map<String, Object> map = dao.queryRecord(start, limit, paramMap);
		// -----------------拼装分页对象 -------------------------------------
		List<CarInfo> dataList = (List<CarInfo>) map.get("list");
		// 填充非持久属性
		for (CarInfo ci : dataList) {
			CarInsurenceInfo ciiInfo = ciiDao.getUniqueEntityByOneProperty("carInfo.id", ci.getId());
			if (ciiInfo != null) {
				ci.setCiInfo(ciiInfo);
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
	public void cancelCarInsurenceOrder(Long dbId) {
		try{
			CarInfoDao dao = (CarInfoDao) ServerBeanFactory.getBean("carInfoDao");
			dao.deleteEntity(dbId);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public CarInfo viewCarInsurenceOrder(Long dbId) {
		try{
			CarInfoDao dao = (CarInfoDao) ServerBeanFactory.getBean("carInfoDao");
			CarInfo entityById = dao.getEntityById(dbId);
			if(entityById != null){
				//填充保险购买方案
				CarInsurenceInfoDao ciiDao = (CarInsurenceInfoDao) ServerBeanFactory.getBean("carInsInfoDao");
				CarInsurenceInfo carIns = ciiDao.getUniqueEntityByOneProperty("carInfo.id", entityById.getId());
				if(carIns != null){
					entityById.setCiInfo(carIns);
				}
			}
			return entityById;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public CarInfo getCarInfoByOrderNo(Long orderId) {
		try{
			CarInfoDao dao = (CarInfoDao) ServerBeanFactory.getBean("carInfoDao");
			return dao.getUniqueEntityByOneProperty("orderId", orderId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Coupon> getMyCoupon(String openId) {
		CouponDao cdao = (CouponDao) ServerBeanFactory.getBean("couponDao");
		return cdao.getEntitiesByOneProperty("openId", openId);
	}

	@Override
	public Paging getMyDiyongquanList(Integer page, Integer limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		model.Paging pp = new model.Paging();
		CouponDao dao = (CouponDao) ServerBeanFactory.getBean("couponDao");
		Map<String, Object> map = dao.queryRecord(start, limit, paramMap);
		// -----------------拼装分页对象 -------------------------------------
		List<Coupon> dataList = (List<Coupon>) map.get("list");
		 
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
	public Paging getMyCleaningCardsList(Integer page, Integer limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		model.Paging pp = new model.Paging();
		CleaningCardsDao dao = (CleaningCardsDao) ServerBeanFactory.getBean("cleaningCardsDao");
		AdminDao adminDao = (AdminDao) ServerBeanFactory.getBean("adminDao");
		Map<String, Object> map = dao.queryRecord(start, limit, paramMap);
		// -----------------拼装分页对象 -------------------------------------
		List<CleaningCards> dataList = (List<CleaningCards>) map.get("list");
		 
		int total = Integer.valueOf(map.get("total").toString());
		pp.setPage(Integer.valueOf(page));// 当前页码
		pp.setLimit(Integer.valueOf(limit));// 每页显示条数
		if (!dataList.isEmpty()) {
			
			for (CleaningCards cc : dataList) {
				//附加服务商名称
				Admin admin = adminDao.getEntityById(cc.getId());
				if(admin != null){
					cc.setServiceName(admin.getCompany());
				}
			}
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
	public Paging getMyWashLogs(Integer page, Integer limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		model.Paging pp = new model.Paging();
		CleaningLogDao dao = (CleaningLogDao) ServerBeanFactory.getBean("cleaningLogDao");
		CleaningCardsDao ccDao = (CleaningCardsDao) ServerBeanFactory.getBean("cleaningCardsDao");
		AdminDao adminDao = (AdminDao) ServerBeanFactory.getBean("adminDao");
		Map<String, Object> map = dao.queryRecord(start, limit, paramMap);
		// -----------------拼装分页对象 -------------------------------------
		List<CleaningLogs> dataList = (List<CleaningLogs>) map.get("list");
		 
		int total = Integer.valueOf(map.get("total").toString());
		pp.setPage(Integer.valueOf(page));// 当前页码
		pp.setLimit(Integer.valueOf(limit));// 每页显示条数
		if (!dataList.isEmpty()) {
			
			for (CleaningLogs cc : dataList) {
				CleaningCards ccc = ccDao.getUniqueEntityByOneProperty("cardCode", cc.getCardCode());
				//附加服务商名称
				Admin admin = adminDao.getEntityById(ccc.getServiceId());
				if(admin != null){
					cc.setServiceName(admin.getCompany());
				}
			}
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
	public Paging getRescueList(Integer page, Integer limit, Map<String, String> paramMap) {
		// 起始记录
		int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
		model.Paging pp = new model.Paging();
		RescueInfoDao dao = (RescueInfoDao) ServerBeanFactory.getBean("rescueDao");
		Map<String, Object> map = dao.queryRecord(start, limit, paramMap);
		// -----------------拼装分页对象 -------------------------------------
		List<RescueInfo> dataList = (List<RescueInfo>) map.get("list");
		 
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
	public List<Admin> getServiceByPid(Long pid) {
		AdminDao adminDao = (AdminDao) ServerBeanFactory.getBean("adminDao");
		return adminDao.getEntitiesByOneProperty("pid", pid);
	}
}

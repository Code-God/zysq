package service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.auth.Org;
import model.bo.fenxiao.CashApply;
import model.bo.fenxiao.OneProduct;
import model.bo.fenxiao.PrdSpec;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.intf.AdminService;
import service.intf.IFenxiaoService;

import com.base.ServerBeanFactory;
import com.wfsc.actions.vo.YjInfo;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.user.User;
import com.wfsc.daos.orders.OrdersDao;
import com.wfsc.daos.product.ProductsDao;
import com.wfsc.daos.user.UserDao;

import dao.OrgDao;
import dao.fenxiao.CashApplyDao;
import dao.fenxiao.CommissionLogDao;
import dao.fenxiao.OneProductDao;
import dao.fenxiao.PrdSpecDao;

@Service("fenxiaoService")
public class FenxiaoServiceImpl implements IFenxiaoService {

	private Logger logger = Logger.getLogger(AdminServiceImpl.class);
	@Autowired
	private OrdersDao ordersDao;
	
	@Override
	public List<Orders> getFxkOrders(User fxUser) {
		String hql = "from Orders where fxpersonId=" +fxUser.getId() + " and  status in (1,2,3)" ;
		List<Orders> list = this.ordersDao.getHibernateTemplate().find(hql);
		return list;
	}
	
	@Override
	public Map<String, Object> getMyFxkOrders(int start, int limit, Map<String, String> param) {
		// paramMap -- 预留的查询参数
		String fxkUserId = param.get("fxkUserId");
		String flag = param.get("flag");
		OrdersDao dao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
		Map<String, Object> result = new HashMap<String, Object>();
		List<Orders> list = new ArrayList<Orders>();
		String hql = "from Orders where 1=1 ";
		String countHql = "select count(id) from Orders where 1=1 ";
		String cond = " ";
		if (param != null && !param.isEmpty()) {
			if (fxkUserId != null && !StringUtils.isEmpty(fxkUserId)) {
				cond += " and fxpersonId = " + fxkUserId;
			}
			if (flag != null && !StringUtils.isEmpty(flag)) {
				if(!"all".equals(flag)){//如果是all的话，就说明不用过滤，查询所有订单
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

	
	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}

	@Override
	public Map<String, Object> getMyFxkCustomers(int start, int limit, Map<String, String> param) {
		OrdersDao orderDao = (OrdersDao) ServerBeanFactory.getBean("ordersDao");
		UserDao dao = (UserDao) ServerBeanFactory.getBean("userDao");
		// paramMap -- 预留的查询参数
		//当前分销客的ID
		String pid = param.get("pid");
		Map<String, Object> result = new HashMap<String, Object>();
		List<User> list = new ArrayList<User>();
		String hql = " from User where 1=1 ";
		String countHql = "select count(id) from User where 1=1 ";
		String cond = " ";
		if (param != null && !param.isEmpty()) {
			if (pid != null && !StringUtils.isEmpty(pid)) {
				cond += " and pid = " + pid;
			}
		}
		hql += cond + " order by id desc";
		countHql += cond;
		List find = dao.getHibernateTemplate().find(countHql);
		int totalCount = Integer.valueOf(find.get(0).toString());
		try {
			list = dao.getPagingEntitiesByHql(hql, start, limit);
			
			//加载订单数
			for (User user : list) {
				int c = orderDao.countByHql("from Orders where user.id = " + user.getId());
				user.setOrders(c);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("total", totalCount);
		result.put("list", list);
		return result;
	}

	@Override
	public YjInfo getMyYjInfo(String currentOrgCode) {
		CommissionLogDao clDao = (CommissionLogDao) ServerBeanFactory.getBean("commissionLogDao");
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		CashApplyDao caDao = (CashApplyDao) ServerBeanFactory.getBean("cashApplyDao");
		
		OrgDao orgDao = (OrgDao) ServerBeanFactory.getBean("orgDao");
		YjInfo info = new YjInfo();
		List<Orders> list = ordersDao.getEntitiesByOneProperty("fxCode", currentOrgCode);
		//订单数量
		info.setOrderSize(list.size());
		//佣金比例
		int commission = orgDao.getUniqueEntityByOneProperty("code", currentOrgCode).getCommission();
		info.setCommission(commission);
		//佣金总计:所有订单的金额合计后乘以佣金比例
		Long fee = 0L;
		for (Orders o : list) {
			fee += o.getFeePrice();
		}
		//累计佣金
		long total = (fee * commission) / 100;
		info.setYjfee(total);
		
		//计算可提佣金： 累计减去已经提现的
		Long cashApply = 0L;
		List<CashApply> caList =caDao.getHibernateTemplate().find("from CashApply where atype=1 and orgCode=" + currentOrgCode + " and flag  <> 2" );
		for (CashApply ca : caList) {
			cashApply += ca.getApplyFee();
		}
		logger.info("所有已经提现、或者已经发起提现的佣金：total cash apply--" + cashApply);
		info.setAvfee(total-cashApply);
		
		return info;
	}

	@Override
	public Org getOrgByWxId(String toUserName) {
		OrgDao orgDao = (OrgDao) ServerBeanFactory.getBean("orgDao");
		return orgDao.getUniqueEntityByOneProperty("wxID", toUserName);
	}

	@Override
	public boolean clearOnePrdPic(Long id, Integer picIndex) throws Exception {
		try{
			OneProductDao dao = (OneProductDao) ServerBeanFactory.getBean("onePrdDao");
			OneProduct obj = dao.getEntityById(id);
			if(obj != null){
				PropertyDescriptor pd = new PropertyDescriptor("pic"+picIndex, obj.getClass());
				Method method = pd.getWriteMethod();
				Object o = method.invoke(obj, new String(""));
				dao.updateEntity(obj);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<PrdSpec> loadPrdSpec(String prdCatId) {
		PrdSpecDao dao = (PrdSpecDao) ServerBeanFactory.getBean("prdSpecDao");
		return dao.getEntitiesByOneProperty("prdCatId", Long.valueOf(prdCatId));
	}

	@Override
	public Long addPrdSpec(String catId, String specName) {
		try{
			PrdSpecDao dao = (PrdSpecDao) ServerBeanFactory.getBean("prdSpecDao");
			PrdSpec ps = new PrdSpec();
			ps.setPrdCatId(Long.valueOf(catId));
			ps.setSpecName(specName);
			return dao.saveEntity(ps);
		}catch(Exception e){
			return 0L;
		}
	}

	@Override
	public boolean delPrdSpec(Long specId) {
		try{
			PrdSpecDao dao = (PrdSpecDao) ServerBeanFactory.getBean("prdSpecDao");
			dao.deleteEntity(Long.valueOf(specId));
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean clearPrdPic(Long id, Integer index) {
		try{
			ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
			Products obj = dao.getEntityById(id);
			if(obj != null){
				//picUrl5
				PropertyDescriptor pd = new PropertyDescriptor("picUrl"+index, obj.getClass());
				Method method = pd.getWriteMethod();
				Object o = method.invoke(obj, new String(""));
				dao.updateEntity(obj);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}

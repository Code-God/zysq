package service.intf;

import java.util.List;
import java.util.Map;

import model.Paging;
import model.bo.car.CarInfo;

import com.wfsc.common.bo.account.Coupon;
import com.wfsc.common.bo.user.Admin;

/**
 * 微信用户相关的service
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public interface ICarInfoService {

	/**
	 * 提交车辆信息
	 * @return
	 */
	public String saveOrUpdateCarInfo(CarInfo info);
	
	/**
	 * 获取汽车品牌列表
	 * @return
	 */
	public List<String> getAllCarBrands();

	/**
	 * 获取某个品牌下所有车型 
	 * @param brandName
	 * @return
	 */
	public List<String> loadAllCarTypes(String brandName);

	/**
	 * 加载救援服务
	 * @param start
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	public Paging loadRescueList(int page, int limit, Map<String, String> paramMap);

	public boolean addRescueInfo(Map<String, String> params);

	public void delRescue(Long id);

	
	public Paging loadCleaningList(int page, int limit, Map<String, String> paramMap);

	public boolean addCleaningInfo(Map<String, String> params);

	public void delCleaning(Long id);

	public Paging loadCouponList(int page, int limit, Map<String, String> paramMap);

	public void consumeCoupon(Long cid);

	public List<Admin> getAllService();

	public Paging cleaningCardsList(int page, int limit, Map<String, String> paramMap);

	public String getServiceName(Long serviceId);

	/** 核销洗车卡 */
	public int consumeCleaning(Long cardId);

	public Paging loadCleaningLogList(int page, int limit, Map<String, String> paramMap);

	/**
	 * 查询此代理商负责区域的所有服务商的信息 
	 * @param daliId
	 * @return
	 */
	public List<Admin> getLocalServiceNames(Long daliId);

	
	public Paging loadHebaoList(int page, int limit, Map<String, String> paramMap);

	public void delHebao(Long id);

	public boolean submitHebaoPrice(String dbId, String price, String orgCode);

	public CarInfo getHebaoDetail(Long dbId);

	public Paging getMyCarInsurence(int page, int limit, Map<String, String> paramMap);

	public void cancelCarInsurenceOrder(Long dbId);

	public CarInfo viewCarInsurenceOrder(Long dbId);

	public CarInfo getCarInfoByOrderNo(Long orderNo);

	public List<Coupon> getMyCoupon(String openId);

	/**
	 * 个人中心---我的抵用券 
	 * @param page
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	public Paging getMyDiyongquanList(Integer page, Integer limit, Map<String, String> paramMap);

	/**
	 * 个人中心---洗车卡
	 * @param page
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	public Paging getMyCleaningCardsList(Integer page, Integer limit, Map<String, String> paramMap);

	public Paging getMyWashLogs(Integer page, Integer limit, Map<String, String> paramMap);

	public Paging getRescueList(Integer page, Integer limit, Map<String, String> paramMap);

	public List<Admin> getServiceByPid(Long pid);
	
}

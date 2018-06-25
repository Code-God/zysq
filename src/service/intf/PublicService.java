package service.intf;

import java.util.List;
import java.util.Map;

import model.bo.Experts;
import model.bo.ShowCase;
import model.bo.auth.Org;
import model.bo.fenxiao.OneProduct;
import model.bo.food.ShoppingCart;

import com.wfsc.common.bo.account.Address;
import com.wfsc.common.bo.ad.AdvConfig;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.user.User;

public interface PublicService {

	/**
	 * 登录
	 * 
	 * @param username
	 * @param pwd
	 * @return
	 */
	public User login(Map<String, String> param);

	/**
	 * 点赞 
	 * @param currentUser
	 * @param targetId - 赞的目标文章ID
	 * @return
	 */
	public boolean zan(String sesId, User currentUser, String targetId);
	/**
	 * 签到 
	 * @param currentUser
	 * @return 0-签到失败   1-成功   2-已签到过了
	 */
	public int sign(User currentUser);
	/**
	 * 抢沙发 
	 * @param currentUser
	 * @return 0-失败   1-成功   -1-抢过沙发了
	 */
	public int sofa(User currentUser);
	/**
	 * 英雄榜 
	 * @return
	 */
	public List<User> getHeroList();
	
	public User getUserByOpenId(String openId);

	public ShowCase getShowCase(String caseId);

	public Experts getExpert(String id);

	public void updateUser(User u);

	/**
	 * 提交反馈意见 
	 * @param name
	 * @param content
	 */
	public void submitFeedback(String name, String content);

	/**
	 * 注册绑定 
	 * @param paramMap
	 */
	public void doRegisterBind(Map<String, String> paramMap);

	/**
	 * 新增或更新配送地址 
	 * @param param
	 */
	public void updateAddress(Map<String, String> param);

	/**
	 * 选购商品到购物车 
	 * @param param
	 * @return [0] - num  [1] - scId
	 */
	public long[] add2Cart(Map<String, String> param);

	
	public List<ShoppingCart> getShoppingCartPrdList(Long userId);
	/**
	 * 从购物车里删除 
	 * @param idList
	 * @param userId 
	 */
	public int delfromCart(List<Long> idList, Long userId);
	/**
	 * 获取用户配送地址 
	 * @param userId
	 * @return
	 */
	public List<Address> getUserAdd(Long userId);

	public void saveUserAddress(Map<String, String> param);

	public void delUa(Long valueOf);

	public Address loadUa(Long valueOf);
	
	/**
	 * 更新购物车每个商品条目对应的数量 
	 * @param param
	 */
	public void updateCart4Count(Map<String, String> param);
	
	/**
	 * 加载所有用户地址 
	 * @return
	 */
	public List<Address> loadAllUserAddress(Long userId);

	/**
	 * 保存订单 
	 * @param param
	 */
	public Orders saveOrder(Map<String, String> param);

	/**
	 * 统计该用户购物车里的金额
	 * @param userId
	 * @return
	 */
	public float getOrderFee(Long userId, String[] scIds);

	public Orders getOrder(Long orderId);

	/**
	 * 取消订单 
	 * @param orderId
	 */
	public void cancelOrder(Long orderId);

	/** 返回订单总金额： 单位：分 */
	public int getOrderFeeByOpenId(String openId);

	public void orderFail(String orderNum);

	public void pj(Map<String, String> paramMap);

	public int getOrderFeeByOrderNum(String orderNum);

	public List<ProductCat> loadSubCategory(String fxCode, String pid);

	public boolean confirmGet(String orderId);

	/**
	 * 获取当前总销商的爆款信息 
	 * @param fxCode
	 * @return
	 */
	public OneProduct getOneProductInfo(Long orgId);

	public List<AdvConfig> getRecommendedList(Long orgId);

	public Org getOrgByCity(String city);
	
}

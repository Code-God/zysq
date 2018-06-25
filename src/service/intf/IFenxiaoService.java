package service.intf;

import java.util.List;
import java.util.Map;

import model.bo.auth.Org;
import model.bo.fenxiao.PrdSpec;

import com.wfsc.actions.vo.YjInfo;
import com.wfsc.common.bo.order.Orders;
import com.wfsc.common.bo.user.User;

public interface IFenxiaoService {

	 /**
	  * 查找分销客推广的订单
	  * @param currentUser
	  * @return
	  */
	public List<Orders> getFxkOrders(User fxUser);

	/**
	 * 分销客订单 
	 * @param start
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getMyFxkOrders(int start, int limit, Map<String, String> paramMap);
	
	/**
	 * 分销客户 
	 * @param start
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> getMyFxkCustomers(int start, int limit, Map<String, String> paramMap);

	
	public YjInfo getMyYjInfo(String currentOrgCode);

	/**
	 * 根据微信原始ID获取对应的分销商对象 
	 * @param toUserName
	 * @return
	 */
	public Org getOrgByWxId(String toUserName);

	/**
	 * 清除爆款图片
	 * @param valueOf
	 * @param valueOf2
	 * @return
	 * @throws Exception 
	 */
	public boolean clearOnePrdPic(Long id, Integer picIndex) throws Exception;

	/**
	 * 根据分类ID查询产品 规格
	 * @param prdCatId
	 * @return
	 */
	public List<PrdSpec> loadPrdSpec(String prdCatId);

	public Long addPrdSpec(String catId, String specName);

	public boolean delPrdSpec(Long specId);

	public boolean clearPrdPic(Long id, Integer index);
}

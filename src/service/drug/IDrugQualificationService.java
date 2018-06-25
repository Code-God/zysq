package service.drug;

import java.util.Map;

import model.bo.drug.DrugQualification;

import com.base.util.Page;
import com.wfsc.common.bo.user.WeiChat;

/**
 * 医疗专业认证service
 * @author Administrator
 *
 */
public interface IDrugQualificationService {
	/**
	 * 通过用户id查看医疗认证信息
	 */
	public DrugQualification getQualificationByUserId(String userid);
	/**
	 * 保存或者修改认证信息
	 */
	public void saveOrUpdateQualification(DrugQualification qualification);
	
	/**
	 * 通过认证id查看医疗认证信息
	 */
	public DrugQualification getQualificationById(Long id);
	
	/**
	 * 查看医师认证列表
	 * @param page
	 * @param paramap
	 * @return
	 */
	public Page<DrugQualification> findPageForQualification(Page<DrugQualification> page, Map<String,Object> paramap);
	
	
	/**
	 * 通过认证id查询认证信息
	 * @param id
	 * @return
	 */
	public Object findQualificationById(String id);
	
	/**
	 * 查看消息列表
	 * @param page
	 * @param paramap
	 * @return
	 */
	public Page<Object> findPageForNews(Page<Object> page, Map<String,Object> paramap);
	
	/**
	 * 通过id查询消息信息
	 * @param id
	 * @return
	 */
	public Object findNewsById(String id);
	
	
	public void updateNewsById(WeiChat r);
	
	public WeiChat getNewsEntityById(Long id);
	
}

package service;

import java.util.Map;

import model.bo.drug.DrugQualification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.drug.IDrugQualificationService;

import com.base.util.Page;
import com.wfsc.common.bo.user.WeiChat;
import com.wfsc.daos.WeiChatDao;

import dao.drug.DrugQualificationDao;
/**
 * 医疗专业认证serviceImpl
 * @author Administrator
 *
 */
@Service("drugQualificationService")
public class DrugQualificationServiceImpl implements IDrugQualificationService {
	//医疗专业认证dao
	@Autowired
	private DrugQualificationDao drugQualificationDao;
	
	@Autowired
	private WeiChatDao weiChatDao;

	/**
	 * 保存或者修改认证信息
	 */
	public void saveOrUpdateQualification(DrugQualification qualification){
		drugQualificationDao.saveOrUpdateEntity(qualification);
	}
	
	/**
	 * 通过用户id查看医疗认证信息
	 */
	public DrugQualification getQualificationByUserId(String userid){
		return drugQualificationDao.getQualificationByUserId(userid);
	}
	
	/**
	 * 通过认证id查看医疗认证信息
	 */
	public DrugQualification getQualificationById(Long id){
		return drugQualificationDao.getEntityById(id);
	}
	
	/**
	 * 查看医师认证列表
	 * @param page
	 * @param paramap
	 * @return
	 */
	public Page<DrugQualification> findPageForQualification(Page<DrugQualification> page, Map<String,Object> paramap){
		return drugQualificationDao.findPageForQualification(page, paramap);
	}
	
	/**
	 * 查看医师认证列表
	 * @param page
	 * @param paramap
	 * @return
	 */
	public  Page<Object> findPageForNews(Page<Object> page, Map<String,Object> paramap){
		return weiChatDao.findPageForNews(page, paramap);
	}
	
	
	
	/**
	 * 通过认证id查询认证信息
	 * @param id
	 * @return
	 */
	public Object findQualificationById(String id){
		return drugQualificationDao.findQualificationById(id);
	}
	
	public Object findNewsById(String id){
		return weiChatDao.findNewsById(id);
	}
	
	public void setDrugQualificationDao(DrugQualificationDao drugQualificationDao) {
		this.drugQualificationDao = drugQualificationDao;
	}
	
	/**
	 * 审核
	 */
	public void updateNewsById(WeiChat r){
		weiChatDao.updateEntity(r);
	}
	
	public WeiChat getNewsEntityById(Long id){
		return weiChatDao.getEntityById(id);
	}
	
}

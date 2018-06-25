package service.drug;

import java.io.File;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugDiseaseDict;
import model.bo.drug.DrugDiseaseItem;
import model.bo.drug.DrugDrugs;
import model.bo.drug.DrugItemClassification;
import model.bo.drug.DrugResearchCenter;

import com.base.util.Page;
import com.wfsc.common.bo.user.User;

import model.bo.drug.DrugProjectConter;

public interface IDrugProjectService {

	/**
	 * 新增或者修改疾病类型
	 * 
	 * @param drugDiseaseitem
	 */
	public void saveOrUpdateDrugDiseaseItem(DrugDiseaseItem drugDiseaseitem,File file);

	/**
	 * 通过id删除疾病类型
	 * 
	 * @param id
	 */
	public void deleteDrugDiseaseItem(Long id);


	/**
	 * 通过id查看疾病类型
	 * @param id
	 */
	public DrugDiseaseItem getDrugDiseaseById(Long id);
	
	/**
	 * 通过id查看疾病报名人数
	 * @param id
	 */
	public int getDrugDiseaseCountById(Long id);
	
	/**
	 * 通过条件查询疾病信息
	 * 
	 * @param page
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	public Page<DrugDiseaseItem> findPageForDisease(Page<DrugDiseaseItem> page,
			Map<String, Object> paramMap);
	
	public Page findPageForCenter(Page page, Map<String, Object> paramMap);
	
	/**
	 * 查看所有疾病信息列表
	 * @return
	 */
	public List<DrugDiseaseItem> getAllDisease();
	
	/**
	 * 通过条件查询药物分页信息
	 * 
	 * @param page
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	public Page<DrugDrugs> findPageForDrugs(Page<DrugDrugs> page,
			Map<String, Object> paramMap);
	
	/**
	 * 通过id查看药物信息
	 * @param id
	 * @return
	 */
	public DrugDrugs getDrugById(Long id);
	
	/**
	 * 保存或者修改药物信息
	 * @param drug
	 */
	public void saveOrUpdateDrug(DrugDrugs drug);
	
	/**
	 * 通过id删除药物信息
	 * @param id
	 */
	public void delDrugById(Long id);
	
	/**
	 * 新增或者修改项目类型
	 * 
	 * @param drugDiseaseitem
	 */
	public void saveOrUpdateDrugItemClassification(DrugItemClassification drugDiseaseitem);

	/**
	 * 通过id删除项目类型
	 * 
	 * @param id
	 */
	public void deleteDrugItemClassification(Long id);


	/**
	 * 通过id查看项目类型
	 * @param id
	 */
	public DrugItemClassification getDrugItemClassificationById(Long id);
	
	/**
	 * 通过条件分页查询项目类型
	 * 
	 * @param page
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	public Page<DrugItemClassification> findPageForDrugItemClassification(Page<DrugItemClassification> page,
			Map<String, Object> paramMap);
	
	/**
	 * 查看所有疾病信息列表
	 * @return
	 */
	public List<DrugItemClassification> getAllDrugItemClassification();
	/**
	 * 查看与人有关的项目
	 * @return
	 */
	public List getRelationDrugProjects(String userid);
	/**
	 *  前台通过是否推荐展示图片
	 * @return
	 */
	
	public List showDiseaseImg();
	
	/**
	 * 查看项目下的疾病信息
	 */
	public  List<DrugDiseaseItem> getClassificationinDisease(String id);
	/**
	 *  通过药品名称查询项目
	 */
	public List  getDiseaseItemByName(String name);
	/**
	 * 通过药品名称查询外部项目
	 */
	public List<DrugDiseaseDict> getDrugDiseaseDictByName(String key,int pageIndex);
	/**
	 * 通过id查询捷信项目详细
	 * @param id
	 * @return
	 */
	public DrugDiseaseDict findDiseaseDictById(Long id);
	
	/**
	 * 通过登记号查询机构
	 * @param ctrId 
	 * @return
	 */
	public List<DrugResearchCenter>getResearchCenterByCtrId(String ctrId);
	
	/**
	 * 查询捷信项目列表分页
	 */
	
	public Page<DrugDiseaseDict> findPageForDiseaseDict(Page<DrugDiseaseDict> page, Map<String,Object> paramap);
	
	/**
	 * 新增或者修改捷信项目
	 */
	public void saveOrUpdateDiseaseDict(DrugDiseaseDict drugDiseaseDict);
	/**
	 * 
	 */
	/**
	 * 通过id删除捷信项目
	 * 
	 * @param id
	 */
	public void deleteDiseaseDict(Long id);
	
	/**
	 * 查询报名人员
	 */
	
	public Page<Object> findPageForRelation(final Page<Object> page, Map<String,Object> paramap);
	

	/**
	 * 查询推荐人员列表
	 */
	
	 public Page<Object> findPageForRecommendedPerson(final Page<Object> page,Map<String,Object> paramap);
	 
	 
	 /**
	  * 根据推荐人id获取用户列表
	  */
	 
	 public Page<Object> findPageForUsers(final Page<Object> page,Map<String,Object> paramap);
	 
	 /**
	  * 根据用户id和项目id查找用户是否报名过该项目
	  * @param userId
	  * @param diseaseId
	  * @return
	  */
	 public boolean isExist(String userId,String diseaseId);
	 
	 /**
	  * 查询项目中心列表
	  * @param page
	  * @param paramap
	  * @return
	  */
	  public Page<DrugProjectConter> findPageForProjectConter(Page<DrugProjectConter> page, Map<String,Object> paramap);
	 
	 /**
	  * 新增或者修改项目中心
	  * @param drugProjectConter
	  */
	 public void saveOrUpdateDrugProjectConter(DrugProjectConter drugProjectConter);
	 
	 /**
	  * 通过id查看项目中心
	  * @param id
	  * @return
	  */
	 public DrugProjectConter getProjectConterById(Long id);
	 
	 /**
		 * 通过id删除项目中心
		 * 
		 * @param id
		 */
		public void deleteDrugProjectConterItem(Long id);
		
		/**
		 * 通过疾病id查看项目中心
		 * @param diseaseId
		 * @return
		 */
		
		List<DrugProjectConter> getProjectConterByDiseaseid(String diseaseId);
	 
}

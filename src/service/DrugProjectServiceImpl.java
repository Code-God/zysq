package service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugBanner;
import model.bo.drug.DrugDiseaseDict;
import model.bo.drug.DrugDiseaseItem;
import model.bo.drug.DrugDrugs;
import model.bo.drug.DrugItemClassification;
import model.bo.drug.DrugProjectConter;
import model.bo.drug.DrugResearchCenter;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.drug.IDrugProjectService;
import util.UploadUtil;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.user.User;

import dao.drug.DrugDiseaseDictDao;
import dao.drug.DrugDiseaseItemDao;
import dao.drug.DrugDrugsDao;
import dao.drug.DrugItemClassificationDao;
import dao.drug.DrugProjectConterDao;
import dao.drug.DrugUDRelationDao;
@Service("drugProjectService")
public class DrugProjectServiceImpl implements IDrugProjectService {

	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	
	//疾病类型dao
	@Autowired
	private DrugDiseaseItemDao drugDiseaseItemDao;
	
	//药物dao
	@Autowired
	private DrugDrugsDao drugDrugDao;
	
	//项目类型
	@Autowired
	private DrugItemClassificationDao drugItemClassificationDao;
	
	//捷信项目dao
	
	@Autowired
	private DrugDiseaseDictDao drugDiseaseDictDao;
	
	//报名dao
	@Autowired
	private DrugUDRelationDao drugUDRelationDao;

	//项目中心dao
	@Autowired
	private DrugProjectConterDao drugProjectConterDao;
	

	/**
	 * 新增或者修改疾病类型
	 * @param drugDiseaseitem
	 */
	@Override
	public void saveOrUpdateDrugDiseaseItem(DrugDiseaseItem drugDiseaseitem,File file){
		String picpath;//图片路径
		DrugDiseaseItem olditem = null;
		if (drugDiseaseitem!=null) {
			olditem=drugDiseaseItemDao.getEntityById(drugDiseaseitem.getId());
		}
		if (file!=null && olditem!=null) {
			UploadUtil.deletePic(drugDiseaseitem.getImgpath());
			picpath=UploadUtil.upLoadImage(file, "itempic");
			if (!StringUtils.isEmpty(picpath)) {
				olditem.setImgpath(picpath);
			}
		}else{
			UploadUtil.deletePic(drugDiseaseitem.getImgpath());
			picpath=UploadUtil.upLoadImage(file, "itempic");
			if (!StringUtils.isEmpty(picpath)) {
				drugDiseaseitem.setImgpath(picpath);
			}
		}
		if(drugDiseaseitem.getId()==null){
			drugDiseaseitem.setDiseaseName(drugDiseaseitem.getDiseaseName());
			drugDiseaseitem.setMedicineName(drugDiseaseitem.getMedicineName());
			drugDiseaseitem.setClassificationId(drugDiseaseitem.getClassificationId());
			drugDiseaseitem.setDiseaseProfile(drugDiseaseitem.getDiseaseProfile());
			drugDiseaseitem.setIsshow(drugDiseaseitem.getIsshow());
			drugDiseaseitem.setDiseaseIntroduction(drugDiseaseitem.getDiseaseIntroduction());
			drugDiseaseitem.setUpdateTime(new Date());
			drugDiseaseitem.setChosenCondition(drugDiseaseitem.getChosenCondition());
			drugDiseaseitem.setSearchKey(drugDiseaseitem.getSearchKey());
			drugDiseaseitem.setProjectId(drugDiseaseitem.getProjectId());
			drugDiseaseitem.setProjectName(drugDiseaseitem.getProjectName());
			drugDiseaseItemDao.saveOrUpdateEntity(drugDiseaseitem);
		}else{
		olditem.setDiseaseName(drugDiseaseitem.getDiseaseName());
		olditem.setMedicineName(drugDiseaseitem.getMedicineName());
		olditem.setClassificationId(drugDiseaseitem.getClassificationId());
		olditem.setDiseaseProfile(drugDiseaseitem.getDiseaseProfile());
		olditem.setIsshow(drugDiseaseitem.getIsshow());
		olditem.setDiseaseIntroduction(drugDiseaseitem.getDiseaseIntroduction());
		olditem.setUpdateTime(new Date());
		olditem.setChosenCondition(drugDiseaseitem.getChosenCondition());
		olditem.setSearchKey(drugDiseaseitem.getSearchKey());
		olditem.setProjectId(drugDiseaseitem.getProjectId());
		olditem.setProjectName(drugDiseaseitem.getProjectName());
		drugDiseaseItemDao.saveOrUpdateEntity(olditem);
		}
		

	}
	
	/**
	 * 通过id删除疾病类型
	 * @param id
	 */
	@Override
	public void deleteDrugDiseaseItem(Long id){
		drugDiseaseItemDao.deleteEntity(id);
	}
	
	/**
	 * 通过条件查询疾病信息
	 * @param page
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	@Override
	public Page<DrugDiseaseItem> findPageForDisease(Page<DrugDiseaseItem> page, Map<String, Object> paramMap) {
		Page<DrugDiseaseItem> pagelist= drugDiseaseItemDao.findPageForDisease(page, paramMap);		
		List<DrugDiseaseItem> diseaselist=pagelist.getData();
		DrugItemClassification itemclass=null;
		for(int i=0;i<diseaselist.size();i++){
			String id=diseaselist.get(i).getClassificationId();
			if(id!=null && !id.equals("")){
			 itemclass=drugItemClassificationDao.getEntityById(Long.valueOf(diseaselist.get(i).getClassificationId()));
			}
			System.out.println(itemclass+"uuuuuuuuuuuuuuuuuuuuu");
			if(itemclass!=null){
				diseaselist.get(i).setClassificationName(itemclass.getClassificationName());
			}
		}
		return pagelist;
		
		
		
	}	
	
	public Page findPageForCenter(Page page, Map<String, Object> paramMap) {
		Page pagelist= drugDiseaseItemDao.findPageForCenter(page, paramMap);
		return pagelist;
	}	

	/**
	 * 通过id查看疾病类型
	 * @param id
	 */
	@Override
	public DrugDiseaseItem getDrugDiseaseById(Long id){
		return drugDiseaseItemDao.getEntityById(id);
	}
	
	/**
	 * 通过id查看疾病报名人数
	 * @param id
	 */
	@Override
	public int getDrugDiseaseCountById(Long id)
	{
		return drugUDRelationDao.countEntitiesByPropNames("diseaseId", id);
	}
	
	
	/**
	 * 查看所有疾病信息列表
	 * @return
	 */
	public List<DrugDiseaseItem> getAllDisease(){
		return drugDiseaseItemDao.getAllDisease();
	}
	
	/**
	 * 通过条件查询药物分页信息
	 * 
	 * @param page
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	public Page<DrugDrugs> findPageForDrugs(Page<DrugDrugs> page,
			Map<String, Object> paramMap){
		Page<DrugDrugs> pagelist=drugDrugDao.findPageForDrugs(page, paramMap); 
		List<DrugDrugs> druglist=pagelist.getData();
		for(int i=0;i<druglist.size();i++){
			DrugDiseaseItem disease=drugDiseaseItemDao.getEntityById(Long.valueOf(druglist.get(i).getDiseaseId()));
			if(disease!=null){
			druglist.get(i).setDiseaseName(disease.getDiseaseName());
			}
		}
		return pagelist;
	}
	
	/**
	 * 通过id查看药物信息
	 */
	@Override
	public DrugDrugs getDrugById(Long id) {
		return drugDrugDao.getEntityById(id);
	}
	
	/**
	 * 保存或者修改药物信息
	 */
	@Override
	public void saveOrUpdateDrug(DrugDrugs drug) {
		drugDrugDao.saveOrUpdateEntity(drug);
		
	}

	/**
	 * 通过id删除药物信息
	 */
	@Override
	public void delDrugById(Long id) {
		drugDrugDao.deleteEntity(id);
		
	}
	
	/**
	 * 新增或者修改项目类型
	 * 
	 * @param drugDiseaseitem
	 */
	@Override
	public void saveOrUpdateDrugItemClassification(
			DrugItemClassification drugDiseaseitem) {
			drugItemClassificationDao.saveOrUpdateEntity(drugDiseaseitem);
	}
	
	/**
	 * 通过id删除项目类型
	 * 
	 * @param id
	 */
	@Override
	public void deleteDrugItemClassification(Long id) {
			drugItemClassificationDao.deleteEntity(id);
	}


	/**
	 * 通过id查看项目类型
	 * @param id
	 */
	@Override
	public DrugItemClassification getDrugItemClassificationById(Long id) {
		return drugItemClassificationDao.getEntityById(id);
	}

	/**
	 * 通过条件分页查询项目类型
	 * 
	 * @param page
	 * @param limit
	 * @param paramMap
	 * @return
	 */
	@Override
	public Page<DrugItemClassification> findPageForDrugItemClassification(
			Page<DrugItemClassification> page, Map<String, Object> paramMap) {
		return drugItemClassificationDao.findPageForDisease(page, paramMap);
	}

	/**
	 * 查看所有疾病信息列表
	 * @return
	 */
	@Override
	public List<DrugItemClassification> getAllDrugItemClassification() {
		return drugItemClassificationDao.getAllDrugItemClassification();
	}
	
	/**
	 * 查看与用户有关的项目(推荐过的项目，或者报名的项目)
	 */
	@Override
	public List getRelationDrugProjects(String userid) {
		return drugDiseaseItemDao.getRelationDrugProjects(userid);		
	}
	/**
	 *  前台通过是否推荐展示图片
	 */

	@Override
	public List showDiseaseImg() {
		
		return this.drugDiseaseItemDao.showDiseaseImg();
	}
	/**
	 * 查看项目下的疾病信息
	 */
	
	@Override
	public  List<DrugDiseaseItem> getClassificationinDisease(String id) {
		
		return this.drugDiseaseItemDao.getClassificationinDisease(id);
	}
	/**
	 * 通过药品名称查询项目
	 */

	@Override
	public List  getDiseaseItemByName(String name) {
		
		return drugDiseaseItemDao.getDiseaseItemByName(name);
	}
	/**
	 * 通过药品名称查询外部项目
	 */


	@Override
	public List<DrugDiseaseDict> getDrugDiseaseDictByName(String key,int pageIndex) {
		return this.drugDiseaseItemDao.getDrugDiseaseDictByName(key,pageIndex);
	}
	/**
	 *  通过id查询捷信项目详细
	 */

	@Override
	public DrugDiseaseDict findDiseaseDictById(Long id) {
		
		return this.drugDiseaseItemDao.findDiseaseDictById(id);
	}
	/**
	 * 通过登记号查询机构
	 */
	@Override
	public List<DrugResearchCenter> getResearchCenterByCtrId(String ctrId) {
		return this.drugDiseaseItemDao.getResearchCenterByCtrId(ctrId);
	}
	
	/**
	 * 查询捷信项目列表分页
	 */

	@Override
	public Page<DrugDiseaseDict> findPageForDiseaseDict(
			Page<DrugDiseaseDict> page, Map<String, Object> paramap) {
		Page<DrugDiseaseDict> pagelist=drugDiseaseDictDao.findPageForDiseaseDict(page, paramap);
		List<DrugDiseaseDict> druglist=pagelist.getData();
		for(int i=0;i<druglist.size();i++){
			DrugDiseaseDict disease=drugDiseaseDictDao.getEntityById(Long.valueOf(druglist.get(i).getId()));
			if(disease!=null){
			druglist.get(i).setDicDiseaseName(disease.getDicDiseaseName());
			}
		}
		return pagelist;
	}
	/**
	 * 新增或者修改捷信项目
	 */

	@Override
	public void saveOrUpdateDiseaseDict(DrugDiseaseDict drugDiseaseDict) {
		
		drugDiseaseDictDao.saveOrUpdateEntity(drugDiseaseDict);
		
	}
	/**
	 *  通过id删除捷信项目
	 */

	@Override
	public void deleteDiseaseDict(Long id) {
		drugDiseaseDictDao.deleteEntity(id);
		
	}
	/**
	 * 查询所有报名人员
	 */

	@Override
	public Page<Object> findPageForRelation(Page<Object> page, Map<String,Object> paramap) {
		
		return drugUDRelationDao.findPageForRelation(page,paramap);
	}

	/**
	 * 查询推荐人员列表
	 */

	@Override
	public Page<Object> findPageForRecommendedPerson(Page<Object> page,
			Map<String, Object> paramap) {
		
		return drugUDRelationDao.findPageForRecommendedPerson(page, paramap);
	}
	/**
	 * 根据推荐人id获取用户列表
	 */

	@Override
	public Page<Object> findPageForUsers(Page<Object> page,
			Map<String, Object> paramap) {
		
		return drugUDRelationDao.findPageForUsers(page, paramap);
	}
	
	/**
	 * 根据用户id和项目id查找用户是否报名过该项目
	 */

	@Override
	public boolean isExist(String userId, String diseaseId) {
		
		return drugUDRelationDao.isExist(userId, diseaseId);
	}
	

	@Override
	public Page<DrugProjectConter> findPageForProjectConter(Page<DrugProjectConter> page, Map<String, Object> paramap) {
		
		Page<DrugProjectConter> pagelist= drugProjectConterDao.findPageForProjectConter(page, paramap);		
		List<DrugProjectConter> ProjectConterlist=pagelist.getData();
		DrugDiseaseItem diseaseItem=null;
		for(int i=0;i<ProjectConterlist.size();i++){
			String id=ProjectConterlist.get(i).getDiseaseId();
			if(id!=null && !id.equals("")){
				diseaseItem=drugDiseaseItemDao.getEntityById(Long.valueOf(ProjectConterlist.get(i).getDiseaseId()));
			}
			if(diseaseItem!=null){
				ProjectConterlist.get(i).setDiseaseName(diseaseItem.getDiseaseName());
			}
		}
		return pagelist;
	}

	
	/**
	 * 新增或者修改项目中心
	 */

	@Override
	public void saveOrUpdateDrugProjectConter(DrugProjectConter drugProjectConter) {
		
		 drugProjectConterDao.saveOrUpdateEntity(drugProjectConter);
	}
	
	/**
	 * 通过id查询项目中心
	 */

	@Override
	public DrugProjectConter getProjectConterById(Long id) {
		return drugProjectConterDao.getEntityById(id);
	}
	/**
	 * 通过id删除项目中心
	 */
	@Override
	public void deleteDrugProjectConterItem(Long id) {
		
		drugProjectConterDao.deleteEntity(id);
		
	}
	/**
	 * 通过疾病id查看项目中心
	 * @param diseaseId
	 * @return
	 */
	
	public List<DrugProjectConter> getProjectConterByDiseaseid(String diseaseId){
		
		return drugProjectConterDao.getProjectConterByDiseaseid(diseaseId);
	}

	public void setDrugDiseaseItemDao(DrugDiseaseItemDao drugDiseaseItemDao) {
		this.drugDiseaseItemDao = drugDiseaseItemDao;
	}

	public DrugDrugsDao getDrugDrugDao() {
		return drugDrugDao;
	}

	public void setDrugDrugDao(DrugDrugsDao drugDrugDao) {
		this.drugDrugDao = drugDrugDao;
	}

	public DrugItemClassificationDao getDrugItemClassificationDao() {
		return drugItemClassificationDao;
	}

	public void setDrugItemClassificationDao(
			DrugItemClassificationDao drugItemClassificationDao) {
		this.drugItemClassificationDao = drugItemClassificationDao;
	}

	public DrugDiseaseDictDao getDrugDiseaseDictDao() {
		return drugDiseaseDictDao;
	}

	public void setDrugDiseaseDictDao(DrugDiseaseDictDao drugDiseaseDictDao) {
		this.drugDiseaseDictDao = drugDiseaseDictDao;
	}

	public DrugDiseaseItemDao getDrugDiseaseItemDao() {
		return drugDiseaseItemDao;
	}


	public DrugUDRelationDao getDrugUDRelationDao() {
		return drugUDRelationDao;
	}

	public void setDrugUDRelationDao(DrugUDRelationDao drugUDRelationDao) {
		this.drugUDRelationDao = drugUDRelationDao;
	}
	public DrugProjectConterDao getDrugProjectConterDao() {
		return drugProjectConterDao;
	}

	public void setDrugProjectConterDao(DrugProjectConterDao drugProjectConterDao) {
		this.drugProjectConterDao = drugProjectConterDao;
	}



	
}

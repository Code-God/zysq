package model.bo.drug;

/**
 * 捷信项目表
 * 
 * @author Administrator
 *  @hibernate.class dynamic-insert="true" dynamic-update="true" table="drug_disease_dict"
 *                  lazy="false"
 */
public class DrugDiseaseDict implements java.io.Serializable {
	private Long id;//主键
	private String ctrId;//登记号
	private String dicDiseaseName;//疾病名称(项目)
	private String dicDiseaseProfile;//疾病概要
	private String dicDiseaseIntroduction;//疾病简介
	private String publishDate;//发布日期
	private String adaptation;//适应症
	private String generalTitle;//试验通俗题目
	private String dicMedicineName;//药物名称
	private String dicMedicineType;//药物类型
	private String designPurpose;//试验目的
	private String designStage;//试验分期
	private String designType;//设计类型
	private String randomize;//随机化
	private String blindMethod;//盲法
	private String subjectConditionIn;//入选标准
	private String subjectConditionout;//排除标准
	private String groupComparedMedicine;//对照药
	private String isJsure;//是否为捷信项目
	private String sponsorinfo;//申办者名称
	private int state;//状态  0、未开始 1、正在进行 2、已结束
	
	private String searchKey;//搜索关键字

	/**
	 * @hibernate.id column="id" generator-class="native" type="long"
	 *               unsaved-value="0"
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property type="string" column="ctr_id"
	 * @return
	 */
	public String getCtrId() {
		return ctrId;
	}

	public void setCtrId(String ctrId) {
		this.ctrId = ctrId;
	}

	/**
	 * @hibernate.property type="string" column="publish_date" update="false"
	 * @return
	 */

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	/**
	 * @hibernate.property type="string" column="adaptation"
	 * @return
	 */

	public String getAdaptation() {
		return adaptation;
	}

	public void setAdaptation(String adaptation) {
		this.adaptation = adaptation;
	}

	/**
	 * @hibernate.property type="string" column="general_title"
	 * @return
	 */

	public String getGeneralTitle() {
		return generalTitle;
	}

	public void setGeneralTitle(String generalTitle) {
		this.generalTitle = generalTitle;
	}

	/**
	 * @hibernate.property type="string" column="dic_medicine_name"
	 * @return
	 */

	public String getDicMedicineName() {
		return dicMedicineName;
	}

	public void setDicMedicineName(String dicMedicineName) {
		this.dicMedicineName = dicMedicineName;
	}

	/**
	 * @hibernate.property type="string" column="dic_medicine_type"
	 * @return
	 */
	public String getDicMedicineType() {
		return dicMedicineType;
	}

	public void setDicMedicineType(String dicMedicineType) {
		this.dicMedicineType = dicMedicineType;
	}

	/**
	 * @hibernate.property type="string" column="design_purpose"
	 * @return
	 */

	public String getDesignPurpose() {
		return designPurpose;
	}

	public void setDesignPurpose(String designPurpose) {
		this.designPurpose = designPurpose;
	}

	/**
	 * @hibernate.property type="string" column="design_stage"
	 * @return
	 */

	public String getDesignStage() {
		return designStage;
	}

	public void setDesignStage(String designStage) {
		this.designStage = designStage;
	}

	/**
	 * @hibernate.property type="string" column="design_type"
	 * @return
	 */

	public String getDesignType() {
		return designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}

	/**
	 * @hibernate.property type="string" column="randomize"
	 * @return
	 */

	public String getRandomize() {
		return randomize;
	}

	public void setRandomize(String randomize) {
		this.randomize = randomize;
	}

	/**
	 * @hibernate.property type="string" column="blind_method"
	 * @return
	 */
	public String getBlindMethod() {
		return blindMethod;
	}

	public void setBlindMethod(String blindMethod) {
		this.blindMethod = blindMethod;
	}

	/**
	 * @hibernate.property type="string" column="subject_conditionIn"
	 * @return
	 */

	public String getSubjectConditionIn() {
		return subjectConditionIn;
	}

	public void setSubjectConditionIn(String subjectConditionIn) {
		this.subjectConditionIn = subjectConditionIn;
	}

	/**
	 * @hibernate.property type="string" column="subject_conditionout"
	 * @return
	 */

	public String getSubjectConditionout() {
		return subjectConditionout;
	}

	public void setSubjectConditionout(String subjectConditionout) {
		this.subjectConditionout = subjectConditionout;
	}

	/**
	 * @hibernate.property type="string" column="groupcompared_medicine"
	 * @return
	 */

	public String getGroupComparedMedicine() {
		return groupComparedMedicine;
	}

	public void setGroupComparedMedicine(String groupComparedMedicine) {
		this.groupComparedMedicine = groupComparedMedicine;
	}

	/**
	 * @hibernate.property type="string" column="is_jsure"
	 * @return
	 */

	public String getIsJsure() {
		return isJsure;
	}

	public void setIsJsure(String isJsure) {
		this.isJsure = isJsure;
	}
	/**
	 * @hibernate.property type="string" column="dic_disease_name"
	 * @return
	 */
	public String getDicDiseaseName() {
		return dicDiseaseName;
	}

	public void setDicDiseaseName(String dicDiseaseName) {
		this.dicDiseaseName = dicDiseaseName;
	}
	/**
	 * @hibernate.property type="string" column="dic_disease_profile"
	 * @return
	 */
	public String getDicDiseaseProfile() {
		return dicDiseaseProfile;
	}

	public void setDicDiseaseProfile(String dicDiseaseProfile) {
		this.dicDiseaseProfile = dicDiseaseProfile;
	}
	/**
	 * @hibernate.property type="string" column="dic_disease_introduction"
	 * @return
	 */

	public String getDicDiseaseIntroduction() {
		return dicDiseaseIntroduction;
	}

	public void setDicDiseaseIntroduction(String dicDiseaseIntroduction) {
		this.dicDiseaseIntroduction = dicDiseaseIntroduction;
	}
	/**
	 * @hibernate.property type="string" column="sponsorinfo"
	 * @return
	 */
	
	public String getSponsorinfo() {
		return sponsorinfo;
	}

	public void setSponsorinfo(String sponsorinfo) {
		this.sponsorinfo = sponsorinfo;
	}
	
	/**
	 * @hibernate.property type="int" column="state"
	 * @return
	 */

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

}

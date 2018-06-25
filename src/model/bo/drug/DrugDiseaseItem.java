package model.bo.drug;

import java.io.Serializable;
import java.util.Date;

/**
 * 病例项目类
 * 主键	疾病(项目)名称	         疾病概要	                   疾病简介                            		疾病大分类id
   id	disease_name	disease_profile	disease_introduction    classification_id
 * @author 
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="drug_disease_item" lazy="false"
 */
public class DrugDiseaseItem implements Serializable {
	
	private Long id;
	private String diseaseName;
	private String medicineName;
	private String diseaseProfile;
	private String diseaseIntroduction;
	private String classificationId;//疾病大分类id
	private String classificationName; //疾病大分类名称
	private Integer isshow;//是否显示
	private String imgpath;//图片路径
	private Date updateTime;//操作时间
	private String chosenCondition;//入选条件
	private String searchKey;//搜索关键字
	private Integer projectId;//映射数据中心的projectId
	private String projectName;//搜索关键字
	

	/**
	 * hibernate中的主键
	 * 
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property type="string" column="disease_name"
	 * @return
	 */
	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	
	/**
	 * @hibernate.property type="string" column="medicine_name"
	 * @return
	 */
	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	/**
	 * @hibernate.property type="string" column="disease_profile"
	 * @return
	 */
	public String getDiseaseProfile() {
		return diseaseProfile;
	}

	public void setDiseaseProfile(String diseaseProfile) {
		this.diseaseProfile = diseaseProfile;
	}

	/**
	 * @hibernate.property type="string" column="disease_introduction"
	 * @return
	 */
	public String getDiseaseIntroduction() {
		return diseaseIntroduction;
	}

	public void setDiseaseIntroduction(String diseaseIntroduction) {
		this.diseaseIntroduction = diseaseIntroduction;
	}

	/**
	 * @hibernate.property type="string" column="classification_id"
	 * @return
	 */
	public String getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(String classificationId) {
		this.classificationId = classificationId;
	}

	public String getClassificationName() {
		return classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}
	/**
	 * @hibernate.property type="int" column="isshow"
	 * @return
	 */
	public Integer getIsshow() {
		return isshow;
	}

	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}
	/**
	 * @hibernate.property type="string" column="imgpath"
	 * @return
	 */
	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	/**
	 * @hibernate.property type="timestamp" column="update_time"
	 * @return
	 */
		
		public Date getUpdateTime() {
			return updateTime;
		}
	
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		
		/**
		 * @hibernate.property type="string" column="chosen_condition"
		 * @return
		 */

		public String getChosenCondition() {
			return chosenCondition;
		}

		public void setChosenCondition(String chosenCondition) {
			this.chosenCondition = chosenCondition;
		}

		public String getSearchKey() {
			return searchKey;
		}

		public void setSearchKey(String searchKey) {
			this.searchKey = searchKey;
		}
		
		public Integer getProjectId() {
			return projectId;
		}

		public void setProjectId(Integer projectId) {
			this.projectId = projectId;
		}
		
		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

}

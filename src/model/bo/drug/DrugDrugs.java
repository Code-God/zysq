package model.bo.drug;

import java.io.Serializable;

/**
 * 药物表类
 * 主键	疾病项目表主键ID	对症药物名称
	id	disease_id	medicine_name
 * @author 
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="drug_drugs" lazy="false"
 */
public class DrugDrugs  implements Serializable {
	
	private Long id;
	private Long diseaseId;
	private String diseaseName;//对症疾病名称（显示使用）
	private String medicineName;

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
	 * @hibernate.property type="long" column="disease_id"
	 * @return
	 */
	public Long getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(Long diseaseId) {
		this.diseaseId = diseaseId;
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

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}
	
}


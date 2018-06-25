package model.bo.drug;

import java.io.Serializable;

/**
 * 病例类
 * 主键	用户表主键ID	病历图片路径
   id	user_id	medical_history_pic

 * @author 
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="drug_medical_history" lazy="false"
 */
public class DrugMedicalHistory  implements Serializable {
	
	private Long id;
	private Long userId;
	private String medicalHistoryPic;

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
	 * @hibernate.property type="long" column="user_id"
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @hibernate.property type="string" column="medical_history_pic"
	 * @return
	 */
	public String getMedicalHistoryPic() {
		return medicalHistoryPic;
	}

	public void setMedicalHistoryPic(String medicalHistoryPic) {
		this.medicalHistoryPic = medicalHistoryPic;
	}
	
}

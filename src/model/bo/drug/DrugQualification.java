package model.bo.drug;

import java.io.Serializable;

/**
 * 医疗专业认证类
 * 主键	用户表主键ID	医院	科室	职称	资质认证图片路径
   id	user_id	hospital	office	professional_title	qualification_pic
 * @author 
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="drug_qualification" lazy="false"
 */
public class DrugQualification implements Serializable {
	
	private Long id;
	private Long userId;
	private String hospital;
	private String office;
	private String professionalTitle;
	private String qualificationPic;
	private Integer reviewState;//待审核0，1，审核通过，2，审核未通过

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
	 * @hibernate.property type="string" column="hospital"
	 * @return
	 */
	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	/**
	 * @hibernate.property type="string" column="office"
	 * @return
	 */
	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	/**
	 * @hibernate.property type="string" column="professional_title"
	 * @return
	 */
	public String getProfessionalTitle() {
		return professionalTitle;
	}

	public void setProfessionalTitle(String professionalTitle) {
		this.professionalTitle = professionalTitle;
	}

	/**
	 * @hibernate.property type="string" column="qualification_pic"
	 * @return
	 */
	public String getQualificationPic() {
		return qualificationPic;
	}

	public void setQualificationPic(String qualificationPic) {
		this.qualificationPic = qualificationPic;
	}

	/**
	 * @hibernate.property type="int" column="review_state"
	 * @return
	 */
	public Integer getReviewState() {
		return reviewState;
	}

	public void setReviewState(Integer reviewState) {
		this.reviewState = reviewState;
	}
	
	
}

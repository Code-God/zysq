package model.bo.drug;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户与疾病项目关系表类
 * 主键	用户表主键ID	疾病项目表主键ID	推荐人用户id（为空则是主动报名产生的记录，反之为推荐记录） 报名时间
	id	user_id	disease_id	referrer_id                                 signup_date
 * @author 
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="drug_u_d_relation" lazy="false"
 */
public class DrugUDRelation implements Serializable {
	
	private Long id;
	private Long userId;
	private Long diseaseId;
	private Long referrerId;
	private Date signupDate;

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
	 * @hibernate.property type="long" column="referrer_id"
	 * @return
	 */
	public Long getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(Long referrerId) {
		this.referrerId = referrerId;
	}
	/**
	 * @return
	 * @hibernate.property type="timestamp" column="signup_date" not-null="true"
	 */

	public Date getSignupDate() {
		return signupDate;
	}

	public void setSignupDate(Date signupDate) {
		this.signupDate = signupDate;
	}
}

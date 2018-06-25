package model.bo.drug;

/**
 * 机构信息表
 * 
 * @author Administrator
 * @hibernate.class dynamic-insert="true" dynamic-update="true"
 *                  table="drug_research_center" lazy="false"
 */

public class DrugResearchCenter implements java.io.Serializable {

	private Long id;//主键
	private String ctrId;//登记号
	private String institutionName;//机构名称
	private String resercher;//主要研究者
	private String ctContry;//国家
	private String ctProvince;//省
	private String ctCity;//城市

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
	 * @hibernate.property type="string" column="institution_name"
	 * @return
	 */
	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	/**
	 * @hibernate.property type="string" column="resercher"
	 * @return
	 */
	public String getResercher() {
		return resercher;
	}

	public void setResercher(String resercher) {
		this.resercher = resercher;
	}

	/**
	 * @hibernate.property type="string" column="ct_contry"
	 * @return
	 */
	public String getCtContry() {
		return ctContry;
	}

	public void setCtContry(String ctContry) {
		this.ctContry = ctContry;
	}

	/**
	 * @hibernate.property type="string" column="ct_province"
	 * @return
	 */
	public String getCtProvince() {
		return ctProvince;
	}

	public void setCtProvince(String ctProvince) {
		this.ctProvince = ctProvince;
	}

	/**
	 * @hibernate.property type="string" column="ct_city"
	 * @return
	 */
	public String getCtCity() {
		return ctCity;
	}

	public void setCtCity(String ctCity) {
		this.ctCity = ctCity;
	}

}

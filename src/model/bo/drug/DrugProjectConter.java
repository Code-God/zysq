package model.bo.drug;

/**
 * 项目中心
 * 
 * @author Administrator *
 * @author Administrator
 * @hibernate.class dynamic-insert="true" dynamic-update="true"
 *                  table="drug_project_conter" lazy="false"
 */
public class DrugProjectConter implements java.io.Serializable {
	private Long id;// 主键
	private String diseaseId;// 疾病id
	private String diseaseName;// 疾病名称
	private String organizationName;// 机构名称
	private String researcher;// 研究者
	private String country;// 国家
	private String province;// 省份
	private String city;// 城市

	/**
	 * hibernate中的主键
	 * 
	 * @hibernate.id column="id" generator-class="native" type="long"
	 *               unsaved-value="0"
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property type="string" column="diseaseid"
	 * @return
	 */
	public String getDiseaseId() {
		return diseaseId;
	}

	public void setDiseaseId(String diseaseId) {
		this.diseaseId = diseaseId;
	}

	/**
	 * @hibernate.property type="string" column="organization_name"
	 * @return
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * @hibernate.property type="string" column="researcher"
	 * @return
	 */
	public String getResearcher() {
		return researcher;
	}

	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}

	/**
	 * @hibernate.property type="string" column="country"
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @hibernate.property type="string" column="province"
	 * @return
	 */
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @hibernate.property type="string" column="city"
	 * @return
	 */
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

}

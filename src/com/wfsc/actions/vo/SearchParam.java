package com.wfsc.actions.vo;


public class SearchParam {
	/** 关键字搜索，索索字段：姓名，昵称，电话，QQ */
	private String keyword;

	private String sex;

	private Integer ageFrom;

	private Integer ageTo;

	private String province;

	private String city;
	
	/** 针对上海地区的区域 */
	private String area;

	private Integer heightFrom;

	private Integer heightTo;

	private String degree;

	/** 诚信等级 */
	private String level;
	/** 会员等级 */
	private String mlevel;
	
	private String hasPhoto;
	/** 学历 */
	private String diploma;
	
	//----------------------------------------------------------------
	
	/** 薪资 */
	private String income;
	/** 学历 */
	private String education;
	/** 住房 */
	private String house;
	
	/** 是否审核通过 */
	private String pass = "N";
	/** 是否被屏蔽 */
	private String masked = "N";
	/** 1 ：启用；２ ：禁用； 0 - 未激活, 9-已成功 */
	private Integer status;
	
	public SearchParam() {
		this.setAgeFrom(20);
		this.setAgeTo(60);
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getHeightFrom() {
		return heightFrom;
	}

	public void setHeightFrom(Integer heightFrom) {
		this.heightFrom = heightFrom;
	}

	public Integer getHeightTo() {
		return heightTo;
	}

	public void setHeightTo(Integer heightTo) {
		this.heightTo = heightTo;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getHasPhoto() {
		return hasPhoto;
	}

	public void setHasPhoto(String hasPhoto) {
		this.hasPhoto = hasPhoto;
	}

	public Integer getAgeFrom() {
		return ageFrom;
	}

	public void setAgeFrom(Integer ageFrom) {
		this.ageFrom = ageFrom;
	}

	public Integer getAgeTo() {
		return ageTo;
	}

	public void setAgeTo(Integer ageTo) {
		this.ageTo = ageTo;
	}

	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	
	public String getIncome() {
		return income;
	}

	
	public void setIncome(String income) {
		this.income = income;
	}

	
	public String getEducation() {
		return education;
	}

	
	public void setEducation(String education) {
		this.education = education;
	}

	
	public String getHouse() {
		return house;
	}

	
	public void setHouse(String house) {
		this.house = house;
	}

	
	public String getMlevel() {
		return mlevel;
	}

	
	public void setMlevel(String mlevel) {
		this.mlevel = mlevel;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	
	public String getPass() {
		return pass;
	}

	
	public String getMasked() {
		return masked;
	}

	
	public void setMasked(String masked) {
		this.masked = masked;
	}

	
	public Integer getStatus() {
		return status;
	}

	
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public String getArea() {
		return area;
	}

	
	public void setArea(String area) {
		this.area = area;
	}

	
	public String getKeyword() {
		return keyword;
	}

	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}

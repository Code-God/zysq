package com.wfsc.actions.vo;

import java.io.Serializable;

/**
 * 周边用户记录返回
 * 
 * @author jacky
 * @version 1.0
 * @since cupid 1.0
 */
public class AroundUser implements Serializable {

	private static final long serialVersionUID = 591311015341158172L;

	private String userName;

	/** 年龄 */
	private int age;
	
	/** 头像URL */
	private String logoUrl;
	
	private String diploma;
	
	private String openId;
	
	/** 性别 */
	private String sex;
//	tr += "婚况："+ data[i].marr +"<br>";
// 	tr += "收入："+ data[i].income +"<br>";
// 	tr += "身高："+ data[i].height +"<br>";
// 	tr += "体重："+ data[i].weight +"<br>";
// 	tr += "学历："+ data[i].diploma +"</td>";
	private String marital;
	private String height;
	private String income;
	private String weight;

	/** 职业 */
	private String ocupation;
	
	/** 地点 */
	private String addr;
	
	/** 距离 */
	private double distance;

	
	public String getUserName() {
		return userName;
	}

	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public int getAge() {
		return age;
	}

	
	public void setAge(int age) {
		this.age = age;
	}

	
	public String getSex() {
		return sex;
	}

	
	public void setSex(String sex) {
		this.sex = sex;
	}

	
	public String getOcupation() {
		return ocupation;
	}

	
	public void setOcupation(String ocupation) {
		this.ocupation = ocupation;
	}

	
	public String getAddr() {
		return addr;
	}

	
	public void setAddr(String addr) {
		this.addr = addr;
	}


	
	public double getDistance() {
		return distance;
	}


	
	public void setDistance(double distance) {
		this.distance = distance;
	}


	
	public String getLogoUrl() {
		return logoUrl;
	}


	
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}


	
	public String getDiploma() {
		return diploma;
	}


	
	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}


	
	public String getMarital() {
		return marital;
	}


	
	public void setMarital(String marital) {
		this.marital = marital;
	}


	
	public String getIncome() {
		return income;
	}


	
	public void setIncome(String income) {
		this.income = income;
	}


	
	public String getWeight() {
		return weight;
	}


	
	public void setWeight(String weight) {
		this.weight = weight;
	}


	
	public String getHeight() {
		return height;
	}


	
	public void setHeight(String height) {
		this.height = height;
	}


	
	public String getOpenId() {
		return openId;
	}


	
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
}

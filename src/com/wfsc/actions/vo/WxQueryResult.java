package com.wfsc.actions.vo;

import java.io.Serializable;

public class WxQueryResult implements Serializable{

	private static final long serialVersionUID = -5132092107639454336L;

	private String id;
	
	private String sex;

	private String nickname;

	private String age;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	
	public String getSex() {
		return sex;
	}

	
	public void setSex(String sex) {
		this.sex = sex;
	}
}

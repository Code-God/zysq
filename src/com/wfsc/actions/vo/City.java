package com.wfsc.actions.vo;

import java.io.Serializable;

public class City implements Serializable {

	/**
	 */
	private static final long serialVersionUID = -5011819604611870227L;

	private Integer id;

	private String cityName;

	public City(Integer id, String cityName) {
		this.id = id;
		this.cityName = cityName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof City))
			return false;
		final City cat = (City) obj;
		if (!getCityName().equals(cat.getCityName()))
			return false;
		return true;
	}

	@Override
	public int hashCode() { // hashCode主要是用来提高hash系统的查询效率。当hashCode中不进行任何操作时，可以直接让其返回 一常数，或者不进行重写。
		int result = getId().hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return this.getCityName();
	}
}

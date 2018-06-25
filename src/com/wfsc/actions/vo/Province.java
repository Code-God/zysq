package com.wfsc.actions.vo;

import java.io.Serializable;
import java.util.List;

public class Province implements Serializable {

	/**
	 */
	private static final long serialVersionUID = -5268757728279825247L;

	private Integer id;

	private String name;
	
	private List<City> cities;

	public void addCity(City city){
		if(!this.cities.contains(city)){
			this.cities.add(city);
		}
	}
	
	public void removeCity(City city){
		this.cities.remove(city);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Province(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	
	public List<City> getCities() {
		return cities;
	}

	
	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return this.getName();
	}
	
	
}

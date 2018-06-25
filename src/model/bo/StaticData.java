package model.bo;

import java.io.Serializable;

/**
 * 统计数据
 * @author jacky
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="staticData" lazy="false"
 */
public class StaticData implements Serializable {

	private static final long serialVersionUID = 5013198130913188346L;

	private Long id;

	/** 菜单名或title */
	private String menuName;

	/** 统计数据 */
	private int total = 0;

	/**
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	/**
	 * @hibernate.property type="string"
	 */
	public String getMenuName() {
		return menuName;
	}

	
	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	
	/**
	 * @hibernate.property type="int"
	 */
	public int getTotal() {
		return total;
	}

	
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	

}

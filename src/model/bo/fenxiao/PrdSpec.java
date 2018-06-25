package model.bo.fenxiao;

import java.io.Serializable;

/**
 * 
 * 产品规格， 与产品分类表关联 一个产品分类，可对应1个或多个规格
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="prdSpec" lazy="false"
 */
public class PrdSpec implements Serializable {

	private static final long serialVersionUID = 2191132254693940814L;

	private Long id;

	private String specName;

	private Long prdCatId;

	/**
	 * @return
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
	 * @return
	 */
	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getPrdCatId() {
		return prdCatId;
	}

	public void setPrdCatId(Long prdCatId) {
		this.prdCatId = prdCatId;
	}
}

package model.bo;

import java.io.Serializable;

/**
 * 栏目表
 * 
 * @author jacky
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="Ycolumns" lazy="false"
 */
public class Columns implements Serializable {

	private static final long serialVersionUID = 2365256792787921245L;

	private Long id;

	/** 所属模块 */
	private String module;

	/** 栏目标题 */
	private String title;

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
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
}

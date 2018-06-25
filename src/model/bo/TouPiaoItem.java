package model.bo;

import java.io.Serializable;
/**
 * 投票选项 
 *
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="tpitem" lazy="false"
 */
public class TouPiaoItem implements Serializable{

	private static final long serialVersionUID = 8379120569267574635L;

	private Long id;

	private TP tp;

	private String content;
	
	/** 被投票次数 */
	private int count;

	/**
	 * hibernate中的主键
	 * 
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.many-to-one  class="model.bo.TP" column="pid"
	 * @return
	 */
	public TP getTp() {
		return tp;
	}

	
	public void setTp(TP tp) {
		this.tp = tp;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getCount() {
		return count;
	}

	
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
}

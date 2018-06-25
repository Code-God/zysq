package model.bo.food;

import java.io.Serializable;

/**
 * 推广人（庄家）管理
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="spreader" lazy="false"
 */
public class Spreader implements Serializable {

	private static final long serialVersionUID = 3202109998778681619L;

	private Long id;

	/** wx大号微信号 */
	private String wxName;

	/** 推广获得积分 */
	private int score;

	/** 自动生成的推广链接 */
	private String spUrl;

	/**
	 * hibernate中的主键
	 * 
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
	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getSpUrl() {
		return spUrl;
	}

	public void setSpUrl(String spUrl) {
		this.spUrl = spUrl;
	}
}

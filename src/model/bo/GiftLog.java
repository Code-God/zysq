package model.bo;

import java.io.Serializable;

/**
 * 
 * 礼品兑换记录
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="giftsLog" lazy="false"
 */
public class GiftLog implements Serializable {

	private static final long serialVersionUID = 1819572831675747758L;

	private Long id;

	/** 礼品名称 */
	private String giftName;

	/** 兑换人 */
	private String operator;

	private String opdate;
	
	/** 管理员处理时间 */
	private String markdate;

	/** 花掉的积分 */
	private int score;

	/** 0-未兑换 1-已兑换 */
	private int status = 0;

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
	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
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
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getOpdate() {
		return opdate;
	}

	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getMarkdate() {
		return markdate;
	}

	
	public void setMarkdate(String markdate) {
		this.markdate = markdate;
	}
}

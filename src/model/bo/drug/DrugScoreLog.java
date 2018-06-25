package model.bo.drug;

import java.io.Serializable;
import java.util.Date;

/**
 * 积分表
 * @author Administrator
 *	@hibernate.class dynamic-insert="true" dynamic-update="true" table="drug_score_log" lazy="false"
 */
public class DrugScoreLog implements Serializable {

	private long id; //主键
	private String openId;//openid
	private int score;//积分
	private int action;//操作类型;0:减小，1：增加
	private Date opdate;//时间
	private String source;//来源(基本分0;完成推荐加分1;	完成报名加分2;	医生认证结束加分3;	完善个人信息加分4;人工审核通过加分5;用户兑换积分6)

	/**
	 * hibernate中的主键
	 * 
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @hibernate.property type="string" column="openid"
	 * @return
	 */
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * @hibernate.property type="int" column="score"
	 * @return
	 */
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @hibernate.property type="int" column="action"
	 * @return
	 */
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	/**
	 * @hibernate.property type="timestamp" column="opdate"
	 * @return
	 */
	public Date getOpdate() {
		return opdate;
	}
	public void setOpdate(Date opdate) {
		this.opdate = opdate;
	}
	
	/**
	 * @hibernate.property type="string" column="source"
	 * @return
	 */
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
}

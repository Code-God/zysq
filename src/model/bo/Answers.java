package model.bo;

import java.io.Serializable;

/**
 * 考试（包括在线辅导和在线考试）记录表 idbigint(20) NOT NULL uidvarchar(20) NULL answervarchar(10) NULL qidbigint(20) NULL answerDate
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="answer" lazy="false"
 */
public class Answers implements Serializable {

	private static final long serialVersionUID = 1160291793084561497L;

	private Long id;

	/** 回答的用户uID */
	private String openId;

	/** 回答用户姓名 */
	private String userName;

	/** 他的答案 */
	private String answer;

	/** 关联的问题ID */
	private Long qid;

	/** 回答日期： YYYY-MM-DD */
	private String answerDate;
	
	/** 答题时间（秒） */
	private int durtime;
	
	/** 序列号：每次答题时都会生成唯一的序列号 */
	private String snum;
	
	/** 用来区分是回答在线考试（T）还是在线辅导（S） */
	private String flag = "S";

	/**
	 * @hibernate.property type="string"
	 */
	public String getAnswerDate() {
		return answerDate;
	}

	public void setAnswerDate(String answerDate) {
		this.answerDate = answerDate;
	}

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
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @hibernate.property type="long"
	 */
	public Long getQid() {
		return qid;
	}

	public void setQid(Long qid) {
		this.qid = qid;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	/**
	 * @hibernate.property type="string"
	 */
	public String getFlag() {
		return flag;
	}

	
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getSnum() {
		return snum;
	}

	
	public void setSnum(String snum) {
		this.snum = snum;
	}


	/**
	 * @hibernate.property type="int"
	 */
	public int getDurtime() {
		return durtime;
	}

	
	public void setDurtime(int durtime) {
		this.durtime = durtime;
	}
}

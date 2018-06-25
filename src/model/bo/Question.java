package model.bo;

import java.io.Serializable;

/**
 * idbigint(20) NOT NULL qtitlevarchar(100) NULL option1varchar(200) NULL option2varchar(200) NULL option3varchar(200) NULL
 * option4varchar(200) NULL option5varchar(200) NULL oiption6varchar(200) NULL answer
 * 
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="questions" lazy="false"
 */
public class Question implements Serializable {

	private static final long serialVersionUID = -6261979061565447279L;
	/** 单选 */
	public static final int FLAG_S = 0;
	/** 多选 */
	public static final int FLAG_M = 1;
	
	private Long id;
	/** 题目类型：0-单选  1-多选 2-主观*/
	private int flag;
	
	private String flagStr;

	/** 问题标题 */
	private String qtitle;
	
	/** 所属话题ID */
	private Long topicId;
	

	private String option1;

	private String option2;

	private String option3;

	private String option4;

	private String option5;

	private String option6;
	
	/** 正确答案，对于调研类的问题，由于没有正确答案，这个可以为空 */
	private String answer;

	/** yyyy-mm */
	private String dateym;
	
	/** 参与人数，不存到数据库，显示用 */
	private int ascount;
	/** 问题选项toString */
	private String qstr;
	

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
	public String getQtitle() {
		return qtitle;
	}

	public void setQtitle(String qtitle) {
		this.qtitle = qtitle;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOption1() {
		return option1;
	}

	
	public void setOption1(String option1) {
		this.option1 = option1;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOption2() {
		return option2;
	}

	
	public void setOption2(String option2) {
		this.option2 = option2;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOption3() {
		return option3;
	}

	
	public void setOption3(String option3) {
		this.option3 = option3;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOption4() {
		return option4;
	}

	
	public void setOption4(String option4) {
		this.option4 = option4;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOption5() {
		return option5;
	}

	
	public void setOption5(String option5) {
		this.option5 = option5;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getOption6() {
		return option6;
	}

	
	public void setOption6(String option6) {
		this.option6 = option6;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("");
		sb.append( this.getOption1() + "<br>");
		sb.append(  this.getOption2() + "<br>");
		if(this.getOption3() != null){
			sb.append(  this.getOption3() + "<br>");
		}
		if(this.getOption4() != null){
			sb.append(  this.getOption4() + "<br>");
		}
		if(this.getOption5() != null){
			sb.append(  this.getOption5() + "<br>");
		}
		if(this.getOption6() != null){
			sb.append(  this.getOption6() + "<br>");
		}
		return sb.toString();
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getAnswer() {
		return answer;
	}

	
	public int getAscount() {
		return ascount;
	}

	
	/**
	 * @hibernate.property type="string"
	 */
	public String getDateym() {
		return dateym;
	}

	
	public void setDateym(String dateym) {
		this.dateym = dateym;
	}

	public void setAscount(int ascount) {
		this.ascount = ascount;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getFlag() {
		return flag;
	}

	
	public void setFlag(int flag) {
		this.flag = flag;
	}

	
	public String getQstr() {
		return this.toString();
	}

	
	public void setQstr(String qstr) {
		this.qstr = qstr;
	}

	
	public String getFlagStr() {
		//0-单选  1-多选 2-主观
		if(this.flag == 0){
			return "单选";
		}else if(this.flag == 1){
			return "多选";
		}else{
			return "主观";
		}
	}

	
	public void setFlagStr(String flagStr) {
		this.flagStr = flagStr;
	}

	/**
	 * @hibernate.property type="long"
	 */
	public Long getTopicId() {
		return topicId;
	}

	
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	
	
	
}

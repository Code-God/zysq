package model.bo.games;

import java.io.Serializable;

import com.wfsc.util.DateUtil;


/**
 * 
 * 抽奖活动管理
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="drawActMgt" lazy="false"
 */
public class ActivityMgt implements Serializable {

	private static final long serialVersionUID = -8120791709435549685L;

	private Long id;

	/** 活动名称 */
	private String actName;

	private String createTime;

	/** 开始时间 */
	private String startTime;

	/** 结束时间 */
	private String endTime;

	/** 总体出奖几率, 单位是百分比， 从1-100 */
	private int luckyrate;
	
	/** 必中奖次数限制， 如果为0则表示无限制 */
	private int must = 0;
	
	/** 每天抽奖限制次数 */
	private int daylimit = 3;

	/** 抽奖活动状态： 是否结束， 非持久化属性 */
	private String status;

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getLuckyrate() {
		return luckyrate;
	}

	public void setLuckyrate(int luckyrate) {
		this.luckyrate = luckyrate;
	}

	public ActivityMgt() {
	}

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
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		long t1 = DateUtil.getLongDate(this.startTime);
		long t2 = DateUtil.getLongDate(this.endTime);
		long current = System.currentTimeMillis();
		if (current > t2) {
			return "<font color=red>已结束</font>";
		} else if (current < t1) {
			return "尚未开始";
		} else {
			return "<font color='purple'>火热进行中</font>";
		}
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getMust() {
		return must;
	}

	
	public void setMust(int must) {
		this.must = must;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getDaylimit() {
		return daylimit;
	}

	
	public void setDaylimit(int daylimit) {
		this.daylimit = daylimit;
	}
}

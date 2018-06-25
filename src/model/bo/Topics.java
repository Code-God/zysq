package model.bo;

import java.io.Serializable;

import com.wfsc.util.DateUtil;

/**
 * 
 * 话题表
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="topics" lazy="false"
 */
public class Topics implements Serializable {

	private static final long serialVersionUID = 3699063079150871952L;

	private Long id;

	/** 名称 */
	private String topic;

	private String dateym;
	
	private String monthStr;
	
	/** 话题的状态文字的颜色 */
	private String color;

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
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getDateym() {
		return dateym;
	}

	public void setDateym(String dateym) {
		this.dateym = dateym;
	}

	
	public String getMonthStr() {
		if(dateym != null){
			String m = this.dateym.split("-")[1];
			return m;
		}else{
			return DateUtil.getShortCurrentDate().split("-")[1];
		}
	}

	
	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}

	
	public String getColor() {
		//<span style="color:#999" class="ui-li-count ui-body-inherit">未开启</span>
		//<span style="color:#00f" class="ui-li-count ui-body-inherit">进行中</span>
		//<span style="color:#F00" class="ui-li-count ui-body-inherit">已过期</span>
		int year = Integer.valueOf(this.getDateym().split("-")[0]);
		int month = Integer.valueOf(this.getDateym().split("-")[1]);
//		当前年月
		int year0 = Integer.valueOf(DateUtil.getShortCurrentDate().split("-")[0]);
		int month0 = Integer.valueOf(DateUtil.getShortCurrentDate().split("-")[1]);
		
		
		//如果年月份相等
		if(year == year0 && month == month0){
			return "<span style=\"color:#00f\" class=\"ui-li-count ui-body-inherit\">进行中</span>";//进行中
		}else if(year == year0 && month > month0){
			return "<span style=\"color:#999\" class=\"ui-li-count ui-body-inherit\">未开启</span>";//未开启
		}else{
			return "<span style=\"color:#F00\" class=\"ui-li-count ui-body-inherit\">已过期</span>";//已过期
		}
	}

	
	public void setColor(String color) {
		this.color = color;
	}
}

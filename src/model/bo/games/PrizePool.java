package model.bo.games;

import java.io.Serializable;

/**
 * 
 * 奖池管理 这个表里记录各种奖品的名称，总数， 出奖概率
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="prizePool" lazy="false"
 */
public class PrizePool implements Serializable {

	private static final long serialVersionUID = -2371460674815047161L;

	private Long id;

	/** 这些奖品属于哪个活动 */
	private ActivityMgt act;

	/** name */
	private String prizeName;

	/** 奖品规格等描述信息 */
	private String prizedesc;
	
	/** 奖品图片 */
	private String prizeImage;

	/** 总数 */
	private int total;

	/** 二级出奖几率, 单位是百分比， 从1-100 */
	private int luckyrate;
	//已中奖
	private int luckynum= 0;
	
	/** 剩余库存 */
	private int leftct = 0;
	
	/** 当天限制出奖 */
	private int todaylimit = 0;

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

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

	public PrizePool() {
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
	public String getPrizedesc() {
		return prizedesc;
	}

	public void setPrizedesc(String prizedesc) {
		this.prizedesc = prizedesc;
	}

	/**
	 * @hibernate.many-to-one class="model.bo.games.ActivityMgt" column="actId" not-null="false"
	 */
	public ActivityMgt getAct() {
		return act;
	}

	public void setAct(ActivityMgt act) {
		this.act = act;
	}

	
	public int getLuckynum() {
		return this.total - this.leftct;
	}

	
	public void setLuckynum(int luckynum) {
		this.luckynum = luckynum;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getLeftct() {
		return leftct;
	}

	
	public void setLeftct(int leftct) {
		this.leftct = leftct;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPrizeImage() {
		return prizeImage;
	}

	
	public void setPrizeImage(String prizeImage) {
		this.prizeImage = prizeImage;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getTodaylimit() {
		return todaylimit;
	}

	
	public void setTodaylimit(int todaylimit) {
		this.todaylimit = todaylimit;
	}
}

package model.bo.fenxiao;

import java.io.Serializable;

/**
 * 
 * 分销商/客申请相关设置
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="fxApplyConfig" lazy="false"
 */
public class FxApplyConfig implements Serializable {

	private static final long serialVersionUID = -5033387406083397389L;

	private Long id;

	/** 当前分销商 */
	private String orgCode;

	/** 分销商申请文字 */
	private String fxsApplyTxt = "申请成为分销商";

	/** 分销客申请文字 */
	private String fxkApplyTxt = "申请成为分销客";;

	/** 分销商描述配图 */
	private String fxsDescPic = "";

	/** 分销客描述配图 */
	private String fxkDescPic = "";

	/** 分销客申请条件：0 - 无须门槛  1-需要至少消费一笔 */
	private int fxkApplyCondition = 0;
	
	//非持久，用来传递参数
	private int clearFxsPic;
	private int clearFxkPic;
	
	/**
	 * @return
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getOrgCode() {
		return orgCode;
	}

	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getFxsApplyTxt() {
		return fxsApplyTxt;
	}

	public void setFxsApplyTxt(String fxsApplyTxt) {
		this.fxsApplyTxt = fxsApplyTxt;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getFxkApplyTxt() {
		return fxkApplyTxt;
	}

	public void setFxkApplyTxt(String fxkApplyTxt) {
		this.fxkApplyTxt = fxkApplyTxt;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getFxsDescPic() {
		return fxsDescPic;
	}

	public void setFxsDescPic(String fxsDescPic) {
		this.fxsDescPic = fxsDescPic;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getFxkDescPic() {
		return fxkDescPic;
	}

	public void setFxkDescPic(String fxkDescPic) {
		this.fxkDescPic = fxkDescPic;
	}

	
	public int getClearFxsPic() {
		return clearFxsPic;
	}

	
	public void setClearFxsPic(int clearFxsPic) {
		this.clearFxsPic = clearFxsPic;
	}

	
	public int getClearFxkPic() {
		return clearFxkPic;
	}

	
	public void setClearFxkPic(int clearFxkPic) {
		this.clearFxkPic = clearFxkPic;
	}

	/**
	 * @return
	 * @hibernate.property type="int"
	 */
	public int getFxkApplyCondition() {
		return fxkApplyCondition;
	}

	
	public void setFxkApplyCondition(int fxkApplyCondition) {
		this.fxkApplyCondition = fxkApplyCondition;
	}
}

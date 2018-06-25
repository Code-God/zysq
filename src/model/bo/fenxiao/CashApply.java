package model.bo.fenxiao;

import java.io.Serializable;

/**
 * 
 * 分销商/客提现申请
 * 分销商在后台点击提现，此表会出现一条记录
 * 分销客在前台点击提现，同上
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="cashapply" lazy="false"
 */
public class CashApply implements Serializable {

	private static final long serialVersionUID = -8245079018096215990L;

	private Long id;

	/** 总销商code */
	private String topOrgCode;
	
	/** 当前分销商code  */
	private String orgCode;
	
	/** 提现人 */
	private String nickName;
	
	/** 提现申请人ID */
	private Long userId;

	/** 提现时间 */
	private String applyDate;
	
	/** 提现金额，分 */
	private Long applyFee;

	/** 状态：0-申请中  1-已处理  2-已拒绝 */
	private int flag;
	private String flagStr;
	
	/** 提现类型： 0 - 分销客， 1-分销商 */
	private int atype; 

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
	public String getNickName() {
		return nickName;
	}

	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return
	 * @hibernate.property type="string" 
	 */
	public String getApplyDate() {
		return applyDate;
	}

	
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * @return
	 * @hibernate.property type="long" 
	 */
	public Long getApplyFee() {
		return applyFee;
	}

	
	public void setApplyFee(Long applyFee) {
		this.applyFee = applyFee;
	}

	/**
	 * @return
	 * @hibernate.property type="int" 
	 */
	public int getFlag() {
		return flag;
	}

	
	public void setFlag(int flag) {
		this.flag = flag;
	}


	/**
	 * @return
	 * @hibernate.property type="long" 
	 */
	public Long getUserId() {
		return userId;
	}


	
	public void setUserId(Long userId) {
		this.userId = userId;
	}


	/**
	 * @return
	 * @hibernate.property type="string" 
	 */
	public String getTopOrgCode() {
		return topOrgCode;
	}


	
	public void setTopOrgCode(String topOrgCode) {
		this.topOrgCode = topOrgCode;
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
	 * @hibernate.property type="int" 
	 */
	public int getAtype() {
		return atype;
	}


	
	public void setAtype(int atype) {
		this.atype = atype;
	}

	
	public String getFlagStr() {
//		0-申请中  1-已处理  2-已拒绝
		if(this.flag == 0){
			return "处理中";
		}else if(flag == 1){
			return "已处理";
		}else if(flag == 2){
			return "已拒绝";
		}
		return "";
	}


	
	public void setFlagStr(String flagStr) {
		this.flagStr = flagStr;
	}
	
	 
}

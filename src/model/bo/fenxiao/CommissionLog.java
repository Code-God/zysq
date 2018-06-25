package model.bo.fenxiao;

import java.io.Serializable;

/**
 * 
 * 分销商/客佣金日志表
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="commissionLog" lazy="false"
 */
public class CommissionLog implements Serializable {

	private static final long serialVersionUID = 4018462245511123320L;

	private Long id;
	
	/** 分销客ID */
	private Long agentId;
	
	/** 分销商编码 */
	private String orgCode;
	
	/** 佣金 */
	private Float yj;
	
	/** 产生时间 */
	private String createTime;
	 
	/** 所属订单 */
	private String orderNo;;
	
	/** 支付者ID */
	private Long payerId;

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
	 * @hibernate.property type="long" 
	 */
	public Long getAgentId() {
		return agentId;
	}


	
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}



	/**
	 * @return
	 * @hibernate.property type="float" 
	 */
	public Float getYj() {
		return yj;
	}


	
	public void setYj(Float yj) {
		this.yj = yj;
	}



	/**
	 * @return
	 * @hibernate.property type="string" 
	 */
	public String getCreateTime() {
		return createTime;
	}


	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	/**
	 * @return
	 * @hibernate.property type="string" 
	 */
	public String getOrderNo() {
		return orderNo;
	}


	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}



	/**
	 * @return
	 * @hibernate.property type="long" 
	 */
	public Long getPayerId() {
		return payerId;
	}


	
	public void setPayerId(Long payerId) {
		this.payerId = payerId;
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
	
	 
}

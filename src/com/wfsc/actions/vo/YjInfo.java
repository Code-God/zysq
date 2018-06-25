package com.wfsc.actions.vo;

import java.io.Serializable;

/**
 * 佣金相关信息
 * 
 * @author jacky
 * @version 1.0
 * @since Resint 1.0
 */
public class YjInfo implements Serializable {

	private static final long serialVersionUID = -5397153585476481835L;

	/** 佣金比例 */
	private int commission;

	/** 分销订单数 */
	private int orderSize;

	/** 累计佣金费用，单位： 分 */
	private Long yjfee;
	
	/** 可提佣金 */
	private Long avfee;
	

	public int getCommission() {
		return commission;
	}

	public void setCommission(int commission) {
		this.commission = commission;
	}

	public int getOrderSize() {
		return orderSize;
	}

	public void setOrderSize(int orderSize) {
		this.orderSize = orderSize;
	}

	public Long getYjfee() {
		return yjfee;
	}

	public void setYjfee(Long yjfee) {
		this.yjfee = yjfee;
	}

	
	public Long getAvfee() {
		return avfee;
	}

	
	public void setAvfee(Long avfee) {
		this.avfee = avfee;
	}
}

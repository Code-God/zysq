package model.bo.hotel;

import java.io.Serializable;

/**
 * 房间时间记录表
-- 2015-12-27 酒店房间记录表  Id | prdId | prdCode | rdate | orderId
DROP TABLE IF EXISTS `roomTimeLine`;
CREATE TABLE `roomTimeLine` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `prdId` bigint(20) ,
  `prdCode` varchar(20),
  `rdate` datetime DEFAULT NULL,
  `price` float DEFAULT NULL,
  `orderId` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;,
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="roomTimeLine" lazy="false"
 */
public class RoomTimeline implements Serializable {

	private static final long serialVersionUID = 4524431908042141111L;
//	0-空闲， 1-被占用  2-被预定（通过预定表查看保留时间，超过时间，状态自动回归为0）， 9 – 被保留
	public static final int RSTATUS_IDLE = 0;
	public static final int RSTATUS_USED = 1;
	public static final int RSTATUS_BOOKED = 2;
	public static final int RSTATUS_RESERVED = 9;
	
	private Long id;
	
	/** 产品ID，即房间ID */
	private Long prdid;
	
	/** 房间序号 */
	private int roomSn;
	
	/** 产品编码 */
	private String prdCode;
	
	/** 日期 */
	private String rdate;
	
	/** 价格 */
	private Float price;
	
	/** 状态  状态， 0-空闲， 1-被占用  2-被预定（通过预定表查看保留时间，超过时间，状态自动回归为0）， 9 – 被保留*/
	private int rstatus;

	/** 所属订单ID,仅当rstatus为1或2时有值 */
	private Long orderId;
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
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getPrdid() {
		return prdid;
	}

	public void setPrdid(Long prdid) {
		this.prdid = prdid;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPrdCode() {
		return prdCode;
	}

	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}
	/**
	 * @hibernate.property type="float"
	 * @return
	 */
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getRstatus() {
		return rstatus;
	}

	public void setRstatus(int rstatus) {
		this.rstatus = rstatus;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getRoomSn() {
		return roomSn;
	}

	public void setRoomSn(int roomSn) {
		this.roomSn = roomSn;
	}
	 

}

package model.bo.food;

import java.io.Serializable;

/**
 * 通用配置表
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="configParam" lazy="false"
 */
public class ConfigParam implements Serializable {

	private static final long serialVersionUID = 1011099406511114191L;

	/** 虚拟货币汇率 */
	public static final String RMB_EXCHANGE_RATE = "RMB_EXCHANGE_RATE";

	/** 虚拟货币有效期 */
	public static final String FB_EXPIRE_DAYS = "FB_EXPIRE_DAYS";

	/** 午餐下单限制, 固定模式： 前一天几点之后 && 当天几点之前 */
	public static final String LUNCH_TIME = "LUNCH_TIME";

	/** 晚餐下单限制,比如：固定模式： 前一天几点之后 && 当天几点之前 */
	public static final String DINNER_TIME = "DINNER_TIME";

	/** 虚拟货币的单位： 默认为 福币 */
	public static final String VIRTUAL_FEE_NAME = "VIRTUAL_FEE_NAME";
	
	/** 庄家的消费门槛 */
	public static final String DISPATCHER_PRICE_THRESHOLD = "DISPATCHER_PRICE_THRESHOLD";
	/** 大V推广 返点比例 */
	public static final String VIP_DISCOUNT = "VIP_DISCOUNT";
	/** 运费 */
	public static final String YUN_FEE = "YUN_FEE";
	/** 微信服务器的上下文 */
	public static final String WX_SERVER_CTX = "WX_SERVER_CTX";

	public static final String APPID = "APPID";
	public static final String APP_SECRET = "APP_SECRET";
	public static final String WX_ID = "WX_ID";
	public static final String WX_KF = "WX_KF";
	
	
	private Long id;

	/** 该参数的key */
	private String ckey;

	/** 该参数的说明 */
	private String cdesc;

	/** 该参数的值：按约定的格式存储， 不同类型的参数对应的值存储格式可能不同 */
	private String cvalue;

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
	public String getCkey() {
		return ckey;
	}

	public void setCkey(String key) {
		this.ckey = key;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCdesc() {
		return cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCvalue() {
		return cvalue;
	}

	public void setCvalue(String cvalue) {
		this.cvalue = cvalue;
	}
}

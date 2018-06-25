package model.vo;

import java.io.Serializable;

/**
 * 存储微信参数的对象，主要是包含了一个时间戳，表示该参数生成的时间。 
 *
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public class WxParamObj implements Serializable{

	private static final long serialVersionUID = -4308677634236918372L;

	private String paramvalue;
	
	private Long timestamp;

	
	public WxParamObj(String v, Long t){
		this.paramvalue = v;
		this.timestamp = t;
	}
	
	public WxParamObj() {
	}

	public String getParamvalue() {
		return paramvalue;
	}

	
	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}

	
	public Long getTimestamp() {
		return timestamp;
	}

	
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
package model.vo;

/**
 * 
 * 数据对象
 * 
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public class DataObject {

	// 柱状图的分类名称
	private String key;

	// 对应的值
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
package com.base;


/**
 * 这个对象是将JS对象转换为DaoCriteria对象的桥梁，原因是DaoCriteria对象中有一个Object属性无法直接从js转换为java
 * 
 * @author Administrator
 * 
 */
public class DwrDaoCriteria {

	private String propertyName;

	private String operator;

	private String value;

	private int startIndex;

	private int maxCount;

	private ConditionValueType valueType;// 属性值是所属类别

	private String separator = "，";// 分割符

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public ConditionValueType getValueType() {
		return valueType;
	}

	public void setValueType(ConditionValueType valueType) {
		this.valueType = valueType;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}
}

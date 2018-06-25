package util;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * dao层进行数据过滤的条件集合
 * 
 */
public class DaoCriteria implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -6456190924441768808L;

	private String propertyName;

	/**
	 * 使用QueryOperatorEnum的操作符
	 */
	private String operator;

	private Object value;

	private int startIndex;

	private int maxCount;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public DaoCriteria() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param property
	 * @param operator
	 * @param value
	 */
	public DaoCriteria(String property, String operator, Object value) {
		this.propertyName = property;
		this.operator = operator;
		this.value = value;
	}

	public String toString() {
		return new ToStringBuilder(this).append("Property", propertyName).append(operator).append("Value", value).toString();
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the maxCount
	 */
	public int getMaxCount() {
		return maxCount;
	}

	/**
	 * @param maxCount the maxCount to set
	 */
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
}

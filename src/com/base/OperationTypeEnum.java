/*
 * Copyright 2007 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Ocean
 * Filename : OperationTypeEnum.java
 * Create   : rick@tekview.com, Mar 1, 2007/12:45:57 PM
 */
package com.base;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 操作类型的枚举量, 针对每个模块均有对应的枚举量
 * 
 * @author Rick
 * 
 */
public class OperationTypeEnum extends ValuedEnum {

	public static final int BASIC_OPERATION_VALUE = 0;

	public static final int SECURITY_OPERATION_VALUE = 1;

	public static final int LICENSE_OPERATION_VALUE = 2;

	public static final int VIEW_OPERATION_VALUE = 3;

	public static final int DISCOVERY_OPERATION_VALUE = 4;

	public static final int RESOURCE_OPERATION_VALUE = 5;

	public static final int ALARM_OPERATION_VALUE = 6;

	public static final int PERFORMANCE_OPERATION_VALUE = 7;

	public static final int REPORT_OPERATION_VALUE = 8;

	public static final OperationTypeEnum SECURITY_OPERATION = new OperationTypeEnum("SECURITY_OPERATION",
			SECURITY_OPERATION_VALUE);

	public static final OperationTypeEnum BASIC_OPERATION = new OperationTypeEnum("BASIC_OPERATION", BASIC_OPERATION_VALUE);

	public static final OperationTypeEnum LICENSE_OPERATION = new OperationTypeEnum("LICENSE_OPERATION",
			LICENSE_OPERATION_VALUE);

	public static final OperationTypeEnum VIEW_OPERATION = new OperationTypeEnum("VIEW_OPERATION", VIEW_OPERATION_VALUE);

	public static final OperationTypeEnum DISCOVERY_OPERATION = new OperationTypeEnum("DISCOVERY_OPERATION",
			DISCOVERY_OPERATION_VALUE);

	public static final OperationTypeEnum RESOURCE_OPERATION = new OperationTypeEnum("RESOURCE_OPERATION",
			RESOURCE_OPERATION_VALUE);

	public static final OperationTypeEnum ALARM_OPERATION = new OperationTypeEnum("ALARM_OPERATION", ALARM_OPERATION_VALUE);

	public static final OperationTypeEnum PERFORMANCE_OPERATION = new OperationTypeEnum("PERFORMANCE_OPERATION",
			PERFORMANCE_OPERATION_VALUE);

	public static final OperationTypeEnum REPORT_OPERATION = new OperationTypeEnum("REPORT_OPERATION",
			REPORT_OPERATION_VALUE);

	/**
	 * @param name
	 * @param value
	 */
	protected OperationTypeEnum(String name, int value) {
		super(name, value);
	}

	private static final long serialVersionUID = 6271681715061933170L;

	public static OperationTypeEnum getEnum(String value) {
		return (OperationTypeEnum) getEnum(OperationTypeEnum.class, value);
	}

	public static OperationTypeEnum getEnum(int value) {
		return (OperationTypeEnum) getEnum(OperationTypeEnum.class, value);
	}

	@SuppressWarnings("unchecked")
	public static Map getEnumMap() {
		return getEnumMap(OperationTypeEnum.class);
	}

	@SuppressWarnings("unchecked")
	public static List getEnumList() {
		return getEnumList(OperationTypeEnum.class);
	}

	@SuppressWarnings("unchecked")
	public static Iterator iterator() {
		return iterator(OperationTypeEnum.class);
	}
}

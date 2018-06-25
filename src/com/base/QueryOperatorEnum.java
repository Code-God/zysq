/**
 * Copyright ? 2006 Tekview Co. Ltd.
 * All right reserved. 
 * FileName :QueryFilterCampareType.java
 * Create :yujie.xu@tekview.com 
 * Time :2006-10-21 12:16:24
 */
package com.base;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * 查询的比较类型
 * 
 * @author yujie
 * 
 */
public final class QueryOperatorEnum extends ValuedEnum {

	private static final long serialVersionUID = 8355531254179577545L;

	protected QueryOperatorEnum(String arg0, int arg1) {
		super(arg0, arg1);
	}

	public static final int LESS_VALUE = 1; // <

	public static final int BIGGER_VALUE = 2; // >

	public static final int EQUAL_VALUE = 3;// =

	public static final int LESS_EQUAL_VALUE = 4; // <=

	public static final int BIGGER_EQUAL_VALUE = 5;// >=

	public static final int NOT_EQUAL_VALUE = 6; // !=

	public static final int IN_VALUE = 7;// in

	public static final int LIKE_VALUE = 8;// like '%value%'

	public static final int OR_VALUE = 9; // 需要数据库构造多重匹配条件

	// aaron add
	public static final int ORDER_VALUE = 9;

	public static final int COUNT_VALUE = 10;

	public static final int IS_NULL_VALUE = 11;

	public static final int IS_NOT_NULL_VALUE = 12;

	public static final int PAGE_VALUE = 13;

	public static final int RESULT_SIZE = 14;

	public static final QueryOperatorEnum LESS = new QueryOperatorEnum("LESS", LESS_VALUE);

	public static final QueryOperatorEnum BIGGER = new QueryOperatorEnum("BIGGER", BIGGER_VALUE);

	public static final QueryOperatorEnum EQUAL = new QueryOperatorEnum("EQUAL", EQUAL_VALUE);

	public static final QueryOperatorEnum LESS_EQUAL = new QueryOperatorEnum("LESS_EQUAL", LESS_EQUAL_VALUE);

	public static final QueryOperatorEnum BIGGER_EQUAL = new QueryOperatorEnum("BIGGER_EQUAL", BIGGER_EQUAL_VALUE);

	public static final QueryOperatorEnum NOT_EQUAL = new QueryOperatorEnum("NOT_EQUAL", NOT_EQUAL_VALUE);

	public static final QueryOperatorEnum IN = new QueryOperatorEnum("IN", IN_VALUE);

	public static final QueryOperatorEnum LIKE = new QueryOperatorEnum("LIKE", LIKE_VALUE);

	public static final QueryOperatorEnum OR = new QueryOperatorEnum("OR", OR_VALUE);

	public static final QueryOperatorEnum MAX_SIZE = new QueryOperatorEnum("MAX_SIZE", RESULT_SIZE);

	// aaron add
	public static final OperationTypeEnum ORDER = new OperationTypeEnum("ORDER", ORDER_VALUE);

	public static final OperationTypeEnum COUNT = new OperationTypeEnum("COUNT", COUNT_VALUE);

	public static final QueryOperatorEnum ISNULL = new QueryOperatorEnum("IS_NULL", IS_NULL_VALUE);

	public static final QueryOperatorEnum ISNOTNULL = new QueryOperatorEnum("IS_NOT_NULL", IS_NOT_NULL_VALUE);

	public static final QueryOperatorEnum PAGE = new QueryOperatorEnum("PAGE", PAGE_VALUE);

	public static QueryOperatorEnum getEnum(String name) {
		return (QueryOperatorEnum) getEnum(QueryOperatorEnum.class, name);
	}

	public static QueryOperatorEnum getEnum(int name) {
		return (QueryOperatorEnum) getEnum(QueryOperatorEnum.class, name);
	}

	@SuppressWarnings("unchecked")
	public static Map getMap() {
		return getEnumMap(QueryOperatorEnum.class);
	}

	@SuppressWarnings("unchecked")
	public static List getList() {
		return getEnumList(QueryOperatorEnum.class);
	}

	@SuppressWarnings("unchecked")
	public static Iterator iterator() {
		return iterator(QueryOperatorEnum.class);
	}
}

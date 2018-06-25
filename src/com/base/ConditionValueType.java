package com.base;

public enum ConditionValueType {
	STRING_ONLY, BOOLEAN_ONLY, LONG_ONLY, STRING_COLLECTION, INTEGER_COLLECTION, LONG_COLLECTION;
	public boolean isStringOnly() {
		return this == STRING_ONLY;
	}

	public boolean isBooleanOnly() {
		return this == BOOLEAN_ONLY;
	}

	public boolean isLongOnly() {
		return this == LONG_ONLY;
	}

	public boolean isStringCollection() {
		return this == STRING_COLLECTION;
	}

	public boolean isIntegerCollection() {
		return this == INTEGER_COLLECTION;
	}

	public boolean isLongCollection() {
		return this == LONG_COLLECTION;
	}
}

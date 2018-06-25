package com.wfsc.consts;

public enum AdvConfigAdType {
	
	PTGG(1, "普通广告"), HDPGG(2, "幻灯片广告");

	private int value;

	public String displayName;

	private AdvConfigAdType(int value, String displayName) {
		this.value = value;
		this.displayName = displayName;
	}

	public static AdvConfigAdType getDbSpaceType(int value) {
		for (AdvConfigAdType dbspaceType : AdvConfigAdType.values()) {
			if (dbspaceType.value == value) {
				return dbspaceType;
			}
		}
		return null;
	}

	public int getValue() {
		return this.value;
	}

	public String getDisplayName() {
		return this.displayName;
	}

}

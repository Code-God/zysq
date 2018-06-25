package util;

public enum TypeName {
	apexNews, mediaReport, activityRecord, tradeClassify, enterpriseScale;

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		if (this == apexNews) {
			return "APEX新闻";
		} else if (this == mediaReport) {
			return "媒体报道";
		} else if (this == activityRecord) {
			return "活动纪实";
		} else if (this == tradeClassify) {
			return "行业分类";
		} else if (this == enterpriseScale) {
			return "企业规模";
		} else {
			return null;
		}
	}
}

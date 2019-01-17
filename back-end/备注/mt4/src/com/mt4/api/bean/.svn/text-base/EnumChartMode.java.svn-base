package com.mt4.api.bean;

public enum EnumChartMode {
	CHART_RANGE_IN(0), // Data from the range of ChartInfo::from - ChartInfo::to.
	CHART_RANGE_OUT(1), // Data from the ranges "the beginning of history" - ChartInfo::from and ChartInfo::to - the end of history.
	CHART_RANGE_LAST(2); // Data from the range ChartInfo::from - the end of history.
	
	private int value;

	private EnumChartMode(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	public int value() {
		return this.value;
	}
}

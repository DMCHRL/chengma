package com.mt4.api.bean;

public enum EnumChartPeriods {
	PERIOD_M1(1), // 1-minute.
	PERIOD_M5(5), // 5-minute.
	PERIOD_M15(15), // 15-minute.
	PERIOD_M30(30), // 30-minute.
	PERIOD_H1(60), // One-hour.
	PERIOD_H4(240), // 4-hour.
	// ---
	PERIOD_D1(1440), // Daily.
	PERIOD_W1(10080), // Weekly
	PERIOD_MN(43200); // Monthly
	
	private int value;

	private EnumChartPeriods(int value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	public int value() {
		return this.value;
	}
}

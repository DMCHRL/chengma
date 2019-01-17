package com.mt4.api.bean;

public enum TranscCmd {
	OP_BUY((short)0), //买入
	OP_SELL((short)1), //卖出
	OP_BUY_LIMIT((short)2), //买入最高价
	OP_SELL_LIMIT((short)3), //卖出最高价
	OP_BUY_STOP((short)4), //买入的最低价
	OP_SELL_STOP((short)5),//卖出的最低价
	OP_BALANCE((short)6),//平衡
	OP_CREDIT((short)7);//信用

	private short value = 0;

	private TranscCmd(short value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	public short value() {
		return this.value;
	}

}

package com.mt4.api.bean;

public enum TransType {
	TT_PRICES_GET((char)0), // prices requets
	TT_PRICES_REQUOTE((char)1), // requote
	// --- client trade transaction
	TT_ORDER_IE_OPEN((char)64), // open order (Instant Execution)
	TT_ORDER_REQ_OPEN((char)65), // open order (Request Execution)
	TT_ORDER_MK_OPEN((char)66), // open order (Market Execution)
	TT_ORDER_PENDING_OPEN((char)67), // open pending order
	// ---
	TT_ORDER_IE_CLOSE((char)68), // close order (Instant Execution)
	TT_ORDER_REQ_CLOSE((char)69), // close order (Request Execution)
	TT_ORDER_MK_CLOSE((char)70), // close order (Market Execution)
	// ---
	TT_ORDER_MODIFY((char)71), // modify pending order
	TT_ORDER_DELETE((char)72), // delete pending order
	TT_ORDER_CLOSE_BY((char)73), // close order by order
	TT_ORDER_CLOSE_ALL((char)74), // close all orders by symbol
	// --- broker trade transactions
	TT_BR_ORDER_OPEN((char)75), // open order
	TT_BR_ORDER_CLOSE((char)76), // close order
	TT_BR_ORDER_DELETE((char)77), // delete order (ANY OPEN ORDER!!!)
	TT_BR_ORDER_CLOSE_BY((char)78), // close order by order
	TT_BR_ORDER_CLOSE_ALL((char)79), // close all orders by symbol
	TT_BR_ORDER_MODIFY((char)80), // modify open price, stoploss, takeprofit etc. of
							// order
	TT_BR_ORDER_ACTIVATE((char)81), // activate pending order
	TT_BR_ORDER_COMMENT((char)82), // modify comment of order
	TT_BR_BALANCE((char)83); // balance/credit

	private char value = 0;

	private TransType(char value) { // 必须是private的，否则编译错误
		this.value = value;
	}

	public char value() {
		return this.value;
	}
}

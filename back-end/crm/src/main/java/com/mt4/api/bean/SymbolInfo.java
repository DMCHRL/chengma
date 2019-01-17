package com.mt4.api.bean;

public class SymbolInfo {
	private String symbol; // symbol name
	private int digits; // floating point digits
	private int count; // symbol counter
	private int visible; // visibility

	private int type; // symbol type (symbols group index)
	private double point; // symbol point=1/pow(10,digits)
	private int spread; // symbol spread
	private int spread_balance; // spread balance
	
	private int direction; // direction
	private int updateflag; // update flag
	private long lasttime; // last tick time
	private double bid;
	private double ask; // bid, ask
	private double high;
	private double low; // high, low
	private double commission; // commission
	private int comm_type; // commission type

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getDigits() {
		return digits;
	}

	public void setDigits(int digits) {
		this.digits = digits;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public int getSpread() {
		return spread;
	}

	public void setSpread(int spread) {
		this.spread = spread;
	}

	public int getSpread_balance() {
		return spread_balance;
	}

	public void setSpread_balance(int spread_balance) {
		this.spread_balance = spread_balance;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getUpdateflag() {
		return updateflag;
	}

	public void setUpdateflag(int updateflag) {
		this.updateflag = updateflag;
	}

	public long getLasttime() {
		return lasttime;
	}

	public void setLasttime(long lasttime) {
		this.lasttime = lasttime;
	}

	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public double getAsk() {
		return ask;
	}

	public void setAsk(double ask) {
		this.ask = ask;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public int getComm_type() {
		return comm_type;
	}

	public void setComm_type(int comm_type) {
		this.comm_type = comm_type;
	}

	@Override
	public String toString() {
		return "SymbolInfo [symbol=" + symbol + ", digits=" + digits
				+ ", count=" + count + ", visible=" + visible + ", type="
				+ type + ", point=" + point + ", spread=" + spread
				+ ", spread_balance=" + spread_balance + ", direction="
				+ direction + ", updateflag=" + updateflag + ", lasttime="
				+ lasttime + ", bid=" + bid + ", ask=" + ask + ", high=" + high
				+ ", low=" + low + ", commission=" + commission
				+ ", comm_type=" + comm_type + "]";
	}

}

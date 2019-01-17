package com.mt4.api.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RateInfo {

	private long  ctm;                       // Bar time
	private int   open;                      // Open price
	private int   high;
	private int  low;
	private int  close;            // High, Low and Close prices (distance from the Open price)
	private double vol;                       // Tick volume
	
	
	public long getCtm() {
		return ctm;
	}
	public void setCtm(long ctm) {
		this.ctm = ctm;
	}
	public int getOpen() {
		return open;
	}
	public void setOpen(int open) {
		this.open = open;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}
	public int getClose() {
		return close;
	}
	public void setClose(int close) {
		this.close = close;
	}
	public double getVol() {
		return vol;
	}
	public void setVol(double vol) {
		this.vol = vol;
	}
	
	
	@Override
	public String toString() {
		
		 Date nowTime = new Date(ctm*1000);
	      SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      String date = sdFormatter.format(nowTime);
	      
		return "RateInfo [ctm=" + date + ", open=" + open + ", high=" + high
				+ ", low=" + low + ", close=" + close + ", vol=" + vol + "]";
	}
	
	
	
}

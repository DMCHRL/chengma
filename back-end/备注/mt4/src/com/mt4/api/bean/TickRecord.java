package com.mt4.api.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TickRecord {
	private String time;
	private  long  ctm;                        // Time
	private  double  bid;
	private  double ask;                    // The Bid and Ask prices
	private  int    datafeed;                   // The data feed
	private  int  flags;                      // Flags
	 
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public long getCtm() {
		return ctm;
	}
	public void setCtm(long ctm) {
		this.ctm = ctm;
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
	public int getDatafeed() {
		return datafeed;
	}
	public void setDatafeed(int datafeed) {
		this.datafeed = datafeed;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}
	@Override
	public String toString() {
		
		 Date nowTime = new Date(ctm*1000);
	     SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String date = sdFormatter.format(nowTime);
		
		return "TickRecord [ctm=" + date + ", bid=" + bid + ", ask=" + ask
				+ ", datafeed=" + datafeed + ", flags=" + flags + "]";
	}
	 
	 
}

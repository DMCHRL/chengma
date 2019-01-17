package com.mt4.api.bean;

public class TradeTransInfo {

	private char type; // Trading transaction type ：交易类型
	private String flags; // Flags   旗帜
	private short cmd; // Trade command type   贸易指令型
	private int order; //订单？
	private int orderby; // Ticket of the order and ticket of the opposite order   订购单和相反顺序的票
	private String symbol; // Financial instrument   金融工具
	private int volume; // Volume ：体积
	private double price; // Price ：价格
	private double sl, tp; // Stop Loss and Take Profit   止损止盈
	private int ieDeviation; // Deviation in the Instant Execution mode
	private String comment; // Comment ：评论
	private long expiration; // Expiration ：到期
	private int crc; // Check sum ：校验和
	private char reserved;
	
	
	
	public char getReserved() {
		return reserved;
	}

	public void setReserved(char reserved) {
		this.reserved = reserved;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrderby() {
		return orderby;
	}

	public void setOrderby(int orderby) {
		this.orderby = orderby;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSl() {
		return sl;
	}

	public void setSl(double sl) {
		this.sl = sl;
	}

	public double getTp() {
		return tp;
	}

	public void setTp(double tp) {
		this.tp = tp;
	}

	public int getIeDeviation() {
		return ieDeviation;
	}

	public void setIeDeviation(int ieDeviation) {
		this.ieDeviation = ieDeviation;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getExpiration() {
		return expiration;
	}

	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

	public int getCrc() {
		return crc;
	}

	public void setCrc(int crc) {
		this.crc = crc;
	}

}

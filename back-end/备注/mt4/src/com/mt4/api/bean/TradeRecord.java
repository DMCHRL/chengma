package com.mt4.api.bean;

/**
 * 交易记录
 * @author opsun
 * @since 1.0, 2015-1-2
 */
public class TradeRecord {
	
	/**
	 * 订单编号
	 */
	private int order;
	
	/**
	 * 用户id
	 */
	private int login;
	
	/**
	 * 交易货币对
	 */
	private String symbol;
	
	/**
	 * 精度
	 */
	private int digits;
	
	/**
	 * 交易指令，OP_BUY,OP_SELL等  0 buy 1 sell
	 */
	private int cmd;
	
	/**
	 * 交易数量
	 */
	private int volume;

	/**
	 * 下单时间
	 */
	private long openTime;
	
	/**
	 * 订单状态
	 */
	private int state;
	
	/**
	 * 下单价格
	 */
	private double openPrice;
	
	/**
	 * 止损
	 */
	private double sl;
	
	/**
	 * 止盈
	 */
	private double tp;
	
	/**
	 * 订单关闭时间
	 */
	private long closeTime;
	
	/**
	 * 网关上的订单数量
	 */
	private int gwVolume;
	
	/**
	 * pending order's expiration time
	 * 失效时间
	 */
	private long expiration;
	
	private char reason;
	
	/**
	 * reserved fields
	 */
	private String convReserv;
	
	/**
	 *  convertation rates from profit currency to group deposit currency
	 * (first element-for open time, second element-for close time)
	 */
	private double[] convRates = new double[2];
	
	/**
	 * 佣金
	 */
	private double commission;
	
	/**
	 * agent commission
	 */
	private double commissionAgent;
	
	/**
	 * order swaps
	 */
	private double storage;
	
	/**
	 * close price
	 */
	private double closePrice;
	
	/**
	 * profit
	 */
	private double profit;
	
	/**
	 * taxes
	 */
	private double taxes;
	
	/**
	 * special value used by client experts
	 * 订单magic number
	 */
	private int magic;
	
	/**
	 * 订单注释
	 */
	private String comment;
	
	/**
	 * gateway order ticket
	 */
	private int gwOrder;
	
	/**
	 * used by MT Manager
	 */
	private int activation;
	
	/**
	 * gateway order price deviation (pips) from order open price
	 */
	private short gwOpenPrice;
	
	/**
	 * gateway order price deviation (pips) from order close price
	 */
	private short gwClosePrice;
	
	/**
	 * margin convertation rate (rate of convertation from margin currency to deposit one)
	 */
	private double marginRate;
	
	/**
	 * 时间戳
	 */
	private long timestamp;
	
	/**
	 * for api usage
	 */
	private int[] apiData = new int[4];

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

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

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public long getOpenTime() {
		return openTime;
	}

	public void setOpenTime(long openTime) {
		this.openTime = openTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
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

	public long getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(long closeTime) {
		this.closeTime = closeTime;
	}

	public int getGwVolume() {
		return gwVolume;
	}

	public void setGwVolume(int gwVolume) {
		this.gwVolume = gwVolume;
	}

	public long getExpiration() {
		return expiration;
	}

	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}

	public char getReason() {
		return reason;
	}

	public void setReason(char reason) {
		this.reason = reason;
	}

	public String getConvReserv() {
		return convReserv;
	}

	public void setConvReserv(String convReserv) {
		this.convReserv = convReserv;
	}

	public double[] getConvRates() {
		return convRates;
	}

	public void setConvRates(double[] convRates) {
		this.convRates = convRates;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public double getCommissionAgent() {
		return commissionAgent;
	}

	public void setCommissionAgent(double commissionAgent) {
		this.commissionAgent = commissionAgent;
	}

	public double getStorage() {
		return storage;
	}

	public void setStorage(double storage) {
		this.storage = storage;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getTaxes() {
		return taxes;
	}

	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}

	public int getMagic() {
		return magic;
	}

	public void setMagic(int magic) {
		this.magic = magic;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getGwOrder() {
		return gwOrder;
	}

	public void setGwOrder(int gwOrder) {
		this.gwOrder = gwOrder;
	}

	public int getActivation() {
		return activation;
	}

	public void setActivation(int activation) {
		this.activation = activation;
	}

	public short getGwOpenPrice() {
		return gwOpenPrice;
	}

	public void setGwOpenPrice(short gwOpenPrice) {
		this.gwOpenPrice = gwOpenPrice;
	}

	public short getGwClosePrice() {
		return gwClosePrice;
	}

	public void setGwClosePrice(short gwClosePrice) {
		this.gwClosePrice = gwClosePrice;
	}

	public double getMarginRate() {
		return marginRate;
	}

	public void setMarginRate(double marginRate) {
		this.marginRate = marginRate;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int[] getApiData() {
		return apiData;
	}

	public void setApiData(int[] apiData) {
		this.apiData = apiData;
	}
	
}

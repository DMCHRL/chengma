package com.mt4.api;

import com.mt4.api.bean.*;

public class MT4 implements ConnectorAPI {
	
	//manager的指针地址
	private int ptr;
	
	public MT4() {
		ptr = init();
		if(ptr<0){
		  throw new RuntimeException();
		}
	}
	
	/**
	 * 初始化
	 * @return 返回CManagerInterface实例的地址
	 */
	private native int init();
	
	/**
	 * 释放manager实例
	 * @param ptr
	 * @return
	 */
	private native int Release(int ptr);
	
	/**
	 * 获取错误信息
	 * @param ptr
	 * @param code
	 * @return
	 */
	private native String ErrorDescription(int ptr,int code);
	
	/**
	 * 连接到服务端
	 * @param ptr
	 * @param server
	 * @return
	 */
	private native int Connect(int ptr,String server);
	
	/**
	 * 断开链接
	 * @param ptr
	 * @return
	 */
	private native int Disconnect(int ptr);
	
	/**
	 * 是否连接
	 * @param ptr
	 * @return
	 */
	private native int IsConnected(int ptr);
	/**
	 * 心跳
	 * @param ptr
	 * @return
	 */
	private native int Ping(int ptr);
	
	/**
	 * 登陆
	 * @param ptr
	 * @param uid
	 * @param password
	 * @return
	 */
	private native int Login(int ptr,int uid,String password);
	
	/**
	 * 加载socket
	 */
	private static native void WinsockStartup();
	
	private native long ServerTime(int ptr);
	
	//orders
	
	/**
	 * 查询交易信息
	 * @param ptr
	 * @return
	 */
	private native TradeRecord[] TradesRequest(int ptr);
	
	/**
	 * 查询用户交易信息
	 * @param ptr
	 * @return
	 */
	private native TradeRecord[] TradesUserHistory(int ptr);
	/**
	 * 查询指定用户交易信息
	 * @param ptr
	 * @param login
	 * @param from
	 * @param to
	 * @return
	 */
	private native TradeRecord[] getTradesUserHistory(int ptr, int login, long from, long to);
	
	/**
	 * 查询交易信息
	 * @param ptr
	 * @param login
	 * @param from
	 * @param to
	 * @return
	 */
	private native TradeRecord[] TradesGet(int ptr);
	
	/**
	 * 用户创建
	 * @param ptr
	 * @return
	 */
	private native int UserRecordNew(int ptr,UserRecord user);
	/**
	 * 更新用户
	 * @param ptr
	 * @return
	 */
	private native int UserRecordUpdate(int ptr,UserRecord user);

	
	/**
	 * 查询用户列表
	 * @param ptr
	 * @return
	 */
	private native UserRecord[] UserRecordsRequest(int ptr, int[] login);
	
	
	/**
	 * 查询用户列表
	 * @param ptr
	 * @return
	 */
	private native UserRecord[] UsersRequest(int ptr);
	
	/**
	 * 查询组列表
	 * @param ptr
	 * @return
	 */
	private native ConGroup[] GroupsRequest(int ptr);
	/**
	 * 增加分组
	 * @param group
	 * @return
	 */
	private native int  CfgUpdateGroup(int ptr,ConGroup group);
	

	/**
	 * 账户余额操作
	 * @return
	 */
	private native int  TradeTransaction(int ptr,TradeTransInfo info);
	
	private native MarginLevel[]  MarginsGet(int ptr);
	
	private native MarginLevel MarginLevelRequest(int ptr, int login);
	
	
	private native TradeRecord[] ReportsRequest(int ptr, ReportGroupRequest req, int[] logins);

	private native TickRecord[] TicksRequest(int ptr, String symbol, long from, long to , char flag);
	
	private native TickRecord[] TickInfoLast(int ptr, String symbol);
	
	private native RateInfo[] ChartRequest(int ptr,String symbol,int period,long start,long end,long timesign,int mode);
	
	private native SymbolInfo SymbolInfoGet(int ptr, String symbol);
	
	private native  int SymbolAdd(int ptr,String symbol);
	
	static{
		System.out.println(System.getProperty("java.library.path"));
		System.loadLibrary("MTConnector64");
		WinsockStartup();
	}

	
	@Override
	public int release() {
		return Release(ptr);
	}

	@Override
	public String getErrorDescription(int code) {
		return ErrorDescription(ptr,code);
	}

	@Override
	public int connect(String server) {
		return Connect(ptr, server);
	}

	@Override
	public int disconnect() {
		return Disconnect(ptr);
	}

	@Override
	public boolean isConnected() {
		return IsConnected(ptr)==1;
	}
	@Override
	public  int ping(){
		return Ping(ptr);
	}
	
	@Override
	public int login(int uid, String password) {
		return Login(ptr, uid, password);
	}
	@Override
	public long serverTime(){
		return ServerTime(ptr);
	}
	@Override
	public TradeRecord[] tradesRequest() {
		return TradesRequest(ptr);
	}

	
	@Override
	public int userRecordNew(UserRecord user){
		return UserRecordNew(ptr, user);
	}
	
	@Override
	public UserRecord[] usersRequest(){
		return UsersRequest(ptr);
	}
	
	@Override
	public UserRecord[] userRecordsRequest(int[] login){
		return UserRecordsRequest(ptr,login);
	}
	
	@Override
	public ConGroup[] groupsRequest(){
		return GroupsRequest(ptr);
	}
	@Override
	public int cfgUpdateGroup(ConGroup group){
		return CfgUpdateGroup(ptr,group);
	}
	@Override
	public TradeRecord[] getTradesUserHistory(int login, long from, long to ){
		return getTradesUserHistory(ptr, login, from, to);
	}

	@Override
	public int  userRecordUpdate(UserRecord user){
		return UserRecordUpdate(ptr, user);
	}
	@Override
	public TradeRecord[]  tradesGet(){
		return TradesGet(ptr);
	}
	
	@Override
	public int tradeTransaction(TradeTransInfo info){
		return TradeTransaction(ptr,info);
	}
	@Override
	public MarginLevel[] marginsGet(){
		return MarginsGet(ptr);
	}
	@Override
	public MarginLevel marginLevelRequest(int login){
		return MarginLevelRequest(ptr,login);
	}
	
	@Override
	public TradeRecord[] reportsRequest(ReportGroupRequest req, int[] logins){
		return ReportsRequest(ptr,req,logins);
	}


	/**
	 * 历史价格查询
	 * @return
	 */
	@Override
	public TickRecord[] ticksRequest(String symbol, long from, long to , char flag){
		return TicksRequest(ptr,symbol,from,to,flag);
	}
	@Override
	public TickRecord[] tickInfoLast(String symbol){
		return TickInfoLast(ptr,symbol);
	}
	@Override
	public RateInfo[] chartRequest(String symbol,int period,long start,long end,long timesign,int mode){
		return ChartRequest(ptr, symbol, period, start, end, timesign, mode);
	}
	@Override
	public SymbolInfo symbolInfoGet(String symbol){
		return SymbolInfoGet(ptr, symbol);
	}
	
	@Override
	public int symbolAdd(String symbol){
		return SymbolAdd(ptr, symbol);
	}
	
}

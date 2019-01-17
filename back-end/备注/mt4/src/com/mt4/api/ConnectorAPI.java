package com.mt4.api;

import com.mt4.api.bean.ConGroup;
import com.mt4.api.bean.MarginLevel;
import com.mt4.api.bean.RateInfo;
import com.mt4.api.bean.ReportGroupRequest;
import com.mt4.api.bean.SymbolInfo;
import com.mt4.api.bean.TickRecord;
import com.mt4.api.bean.TradeRecord;
import com.mt4.api.bean.TradeTransInfo;
import com.mt4.api.bean.UserRecord;

public interface ConnectorAPI {
	
	public int release();
	
	public String getErrorDescription(int code);
	
	public int connect(String server);
	
	public int disconnect();
	
	public boolean isConnected();
	
	public  int ping();
	
	public int login(int uid,String password);
	/**
	 * 获取服务器时间
	 * @return
	 */
	public long serverTime();
	/**
	 * 查询总交易记录
	 * @return
	 */
	public TradeRecord[] tradesRequest();//总交易记录是指一级账户？
	
	/**
	 * 创建用户
	 * @param user
	 * @return
	 */
	public int userRecordNew(UserRecord user);//二级账户？
	
	/**
	 * 查询所有账号列表  
	 * @return
	 */
	public UserRecord[] usersRequest();//一级账户下的所有的二级账户？
	
	/**
	 * 查询某个用户的账号列表  
	 * @return
	 */
	public UserRecord[] userRecordsRequest(int[] login);//一级账户的还是二级账户的？
	/**
	 * 分组列表查询
	 * @return
	 */
	public ConGroup[] groupsRequest();//哪个分组？
	/**
	 * 分组添加
	 * @param group
	 * @return
	 */
	public int  cfgUpdateGroup(ConGroup group);//是哪级账户下的分组
	
	/**
	 * 查询个人交易记录
	 * @param login
	 * @param from
	 * @param to
	 * @return
	 */
	public TradeRecord[] getTradesUserHistory(int login,long from,long to);//一级账户下的还是二级账户的
	
	
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int  userRecordUpdate(UserRecord user);
	/**
	 * 查询交易记录
	 * @param login
	 * @param from
	 * @param to
	 * @return
	 */
	public TradeRecord[]  tradesGet();//查询的是哪级账户
	
	
	/**
	 * 账户余额操作
	 * @return
	 */
	public int tradeTransaction(TradeTransInfo info);//返回的是什么，哪个是必须填的
	
	
	public MarginLevel[] marginsGet();
	
	public MarginLevel marginLevelRequest(int login);
	
	/**
	 * 查询用户列表中的交易记录
	 * @param req
	 * @param logins
	 * @return
	 */
	public TradeRecord[] reportsRequest(ReportGroupRequest req,int[] logins);//哪级的账户

	/**
	 * 历史价格查询
	 * @return
	 */
	public TickRecord[] ticksRequest(String symbol,long from,long to ,char flag);
	/**
	 * 查询最新价格
	 * @return
	 */
	public TickRecord[] tickInfoLast(String symbol);
	/**
	 * 查询行情
	 * @param symbol//边缘
	 * @param period//时期
	 * @param start//开始
	 * @param end//结束
	 * @param timesign//时间安排
	 * @param mode//模式
	 * @return
	 */
	public RateInfo[] chartRequest(String symbol,int period,long start,long end,long timesign,int mode);
	/**
	 * 查询外汇信息
	 * @param symbol
	 * @return
	 */
	public SymbolInfo symbolInfoGet(String symbol);
	
	public int symbolAdd(String symbol);
	
}

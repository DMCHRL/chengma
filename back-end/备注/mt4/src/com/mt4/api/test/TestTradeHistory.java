package com.mt4.api.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.TradeRecord;
import com.mt4.api.bean.UserRecord;

public class TestTradeHistory {
	public static void main(String[] args) {
	ConnectorAPI mt4 = new MT4();
	mt4.connect("mt3.yhmt4.com:1118");
	if(mt4.isConnected()){
		System.out.println("connected");
	}

	mt4.login(108, "abc123");

	long from = mt4.serverTime()- 60 * 60*24*80;//每80天
	System.out.println("from="+from);
	long to = mt4.serverTime() ;//服务器时间

	System.out.println("to="+to);
	UserRecord[] users = mt4.usersRequest();//查看交易账户列表
	System.out.println("--->"+users.length);//获取长度
	for(UserRecord user: users){
		TradeRecord[] records =  mt4.getTradesUserHistory(user.getLogin(), from, to);//账户的交易记录

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式
		if(records != null && records.length > 0){
			for(TradeRecord record : records){//每一条记录
				Calendar cal2 = Calendar.getInstance();
				Date date2 = cal2.getTime();//当前时间
				System.out.println(record.getOrder()+","+  //订单号
						record.getLogin()+","+             //登录号
						record.getSymbol()+","+				//交易类型
						record.getCmd()+","+				//交易指令 0 buy 1 sell
						record.getVolume()*1.0/100+","+         //交易手数
						record.getOpenPrice()+",======="+		//开仓价格
						record.getClosePrice()+","+			//平仓价格
						sdf.format(record.getOpenTime()*1000-1000*60*60*8)+","+
						sdf.format(record.getCloseTime()*1000-1000*60*60*8)+","+             	//
						record.getStorage()+","+			//存储费
						record.getProfit()+","+
						record.getState());				//获利
			}
		}

	}
		/*TradeRecord[] records =  mt4.getTradesUserHistory(7900005, from, to);//账户的交易记录

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//时间格式
		for(TradeRecord record : records){//每一条记录
			Calendar cal2 = Calendar.getInstance();
			cal2.setTimeInMillis(record.getTimestamp()*1000);//时间戳
			Date date2 = cal2.getTime();//当前时间
			System.out.println(record.getOrder()+","+
					record.getLogin()+","+
					record.getSymbol()+","+
					record.getCmd()+","+
					record.getVolume()/100+","+
					record.getClosePrice()+","+
					sdf.format(date2)+","+
					record.getStorage()+","+
					record.getProfit());
		}*/

}
}

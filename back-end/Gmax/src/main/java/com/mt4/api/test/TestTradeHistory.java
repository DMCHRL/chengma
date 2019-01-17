package com.mt4.api.test;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.TradeRecord;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestTradeHistory {
	public static void main(String[] args) {
		ConnectorAPI mt4 = new MT4();
		mt4.connect("mt3.yhmt4.com:1118");
		if(mt4.isConnected()){
			System.out.println("connected");
		}
		
		mt4.login(108, "abc123");



		/*long from = mt4.serverTime()- 60 * 60 * 24 *80;//服务器时间-80天
		System.out.println("from="+from);
		long to = mt4.serverTime() ;//服务器时间
		
		System.out.println("to="+to);
		TradeRecord[] records =  mt4.getTradesUserHistory(20181203, from, to);//账户的交易记录
		
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
			
		}
		System.out.println("records="+records.length);//多少条记录*/
	}
}

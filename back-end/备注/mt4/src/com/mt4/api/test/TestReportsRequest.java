package com.mt4.api.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.ReportGroupRequest;
import com.mt4.api.bean.TradeRecord;

public class TestReportsRequest {
	public static void main(String[] args) {
		ConnectorAPI mt4 = new MT4();
		mt4.connect("IP:443");
		if(mt4.isConnected()){
			System.out.println("connected");
		}
		//System.out.println(new Date().getTime()/1000 - 12 * 24 * 60 * 60);
		mt4.login(112233, "abcd");
		
		long from = (new Date().getTime())/1000 - 12 * 24 * 60 * 60 ;
		//根据服务器时区，设置截止时间
		long to = (new Date().getTime())/1000 + 24 * 60 * 60 ;
		ReportGroupRequest req = new ReportGroupRequest();//这个类是什么？
		req.setName("ss");  
		req.setFrom(from);
		req.setTo(to);
		req.setTotal(1);
		int[] logins = {65600063};
		TradeRecord[] records =  mt4.reportsRequest(req, logins);//交易记录
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(TradeRecord record : records){
			
			
			Calendar cal2 = Calendar.getInstance();//日历
			cal2.setTimeInMillis(record.getTimestamp()*1000);
			Date date2 = cal2.getTime();
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
		System.out.println("records="+records.length);
	}
}

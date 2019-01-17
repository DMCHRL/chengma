package com.mt4.api.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;

public class TestServerTime {

	public static void main(String[] args) {
		ConnectorAPI mt4 = new MT4();
		mt4.connect("mt4live.shjhkj.com:1996");
		if(mt4.isConnected()){
			System.out.println("connected");
		}
		mt4.login(8097, "tx123456");
		
		long time = mt4.serverTime();//服务器时间
		
		System.out.println("time="+time);
		
		String aa=TestServerTime.timestamp2Date(time+"","yyyy-MM-dd HH:mm:ss");
		
		System.out.println(aa);

	}

	public static String timestamp2Date(String str_num,String format ) {
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    if (str_num.length() == 13) {//Long类型和Integer类型的区别？？？
	        String date = sdf.format(new Date(Long.parseLong(str_num)));
	      //  LogUtil.debug("timestamp2Date"+ "将13位时间戳:" + str_num + "转化为字符串:", date);
	        return date;
	    } else {
	        String date = sdf.format(new Date(Integer.parseInt(str_num) * 1000L));
	       // LogUtil.debug("timestamp2Date" + "将10位时间戳:" + str_num + "转化为字符串:", date);
	        return date;
	    }
	}
	
	
	
	
}

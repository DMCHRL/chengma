package com.mt4.api.test;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.MarginLevel;

public class TestMargin {

	public static void main(String[] args) {
		ConnectorAPI mt4 = new MT4();
		mt4.connect("mt4live.shjhkj.com:1996");
		if(mt4.isConnected()){
			System.out.println("connected");
		}
		
		mt4.login(8097, "tx8097");
		/*MarginLevel[] margins =  mt4.getMargins();
		System.out.println(margins);
		for(int i=0;i<margins.length;i++){
			
			System.out.println(margins[i].getLogin()+","+margins[i].getUpdated()+","+margins[i].getLeverage()+","+margins[i].getBalance()+","+margins[i].getEquity()+","+margins[i].getMargin());
		}*/
		MarginLevel margins =  mt4.marginLevelRequest(1890015);//MarginLevel什么用的
		System.out.println("Login="+margins.getLogin()+",Updated="+margins.getUpdated()+",Leverage="+margins.getLeverage()+",Balance="+margins.getBalance()+",Equity="+margins.getEquity()+",Margin="+margins.getMargin());
		

	}

}

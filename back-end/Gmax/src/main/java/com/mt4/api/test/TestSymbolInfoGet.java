package com.mt4.api.test;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.SymbolInfo;

public class TestSymbolInfoGet {

	public static void main(String[] args) {
		ConnectorAPI mt4 = new MT4();
		mt4.connect("mt4live.shjhkj.com:1996");
		if(mt4.isConnected()){
			System.out.println("connected");
		}
		
		
		
		int res = mt4.login(8090, "tx123456");
		System.out.println(res+"->"+mt4.getErrorDescription(res));
		//int sres = mt4.symbolAdd("EURAUD");

		//System.out.println("symbolAdd ->"+sres+"->"+mt4.getErrorDescription(sres));
		SymbolInfo symbol = mt4.symbolInfoGet("USDJPY");//查看外汇信息
		
		
		System.out.println("symbol--->"+symbol);//symbol：什么东西？
		

	}

}

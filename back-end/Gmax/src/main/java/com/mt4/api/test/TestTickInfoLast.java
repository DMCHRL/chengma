package com.mt4.api.test;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.TickRecord;

public class TestTickInfoLast {

	public static void main(String[] args) {
		ConnectorAPI mt4 = new MT4();
		mt4.connect("mt4live.shjhkj.com:1996");
		if(mt4.isConnected()){
			System.out.println("connected");
		}
		
		mt4.login(8090, "tx123456");
		
		TickRecord[] ticks = mt4.tickInfoLast("USDJPY");//最新价格
		
		for(TickRecord tick:ticks){
			System.out.println(tick);
		}


	}

}

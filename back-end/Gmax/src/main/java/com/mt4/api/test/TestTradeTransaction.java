package com.mt4.api.test;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.TradeTransInfo;
import com.mt4.api.bean.TransType;
import com.mt4.api.bean.TranscCmd;

public class TestTradeTransaction {
public static void main(String[] args) {
	ConnectorAPI mt4 = new MT4();
	mt4.connect("mt4live.shjhkj.com:1996");
	if(mt4.isConnected()){
		System.out.println("connected");
	}
	
	mt4.login(8097, "tx8097");
	
	TradeTransInfo info = new TradeTransInfo();//交易信息
	info.setCmd(TranscCmd.OP_BALANCE.value());//这个什么操作？？？
	info.setComment("ctest");
	info.setType(TransType.TT_BR_BALANCE.value());
	info.setOrderby(1890099);
	info.setPrice(1.01);
	info.setCrc(1);
	
	int res = mt4.tradeTransaction(info);//账户余额
	System.out.println("res="+res);
	System.out.println(mt4.getErrorDescription(res));//获取异常信息
}
}

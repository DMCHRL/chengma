package com.mt4.api.test;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.UserRecord;

public class TestUpdateUser {

	public static void main(String[] args) {
		ConnectorAPI mt4 = new MT4();
		mt4.connect("mt4live.shjhkj.com:1996");
		if(mt4.isConnected()){
			System.out.println("connected");
		}
		
		mt4.login(8097, "tx123456");
		
		UserRecord user = new UserRecord();
		user.setLogin(1890099);
		user.setName("修改1");
		int res= mt4.userRecordUpdate(user);//更新用户信息
		System.out.println("result="+res);
		System.out.println(mt4.getErrorDescription(res));//错误信息
	
	}
}

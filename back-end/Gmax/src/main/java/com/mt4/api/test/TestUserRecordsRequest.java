package com.mt4.api.test;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.UserRecord;

public class TestUserRecordsRequest {
	public static void main(String[] args) {
		ConnectorAPI mt4 = new MT4();//创建初始化
		mt4.connect("mt4live.shjhkj.com:1996");//服务器地址
		if(mt4.isConnected()){
			System.out.println("connected");
		}
		
		mt4.login(8097, "tx8097");//登录密码
	
		int[] logins = {1890002};//账户号
		UserRecord[]  users =  mt4.userRecordsRequest(logins);//获取账户下的交易账户
		
		UserRecord user=users[0];
		System.out.println(user.getPassword()+"111111111111");

		/*//mt4账户
		UserRecord user = new UserRecord();
		user.setLogin(Integer.parseInt("111"));
		user.setName("aaa");
		user.setPassword("abc");  //必填(注：注册必须有英文加数字)
		user.setPasswordInvestor("abc");
		user.setLeverage(100);//杠杆
		int res= mt4.userRecordUpdate(user);//更新用户信息
		System.out.println("result="+res);
		System.out.println(mt4.getErrorDescription(res));//错误信息*/
		//System.out.println(user.getPassword());//交易账户的密码
	}
}

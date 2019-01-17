package com.mt4.api.test;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.UserRecord;


public class TestUserNew {
	public static void main(String[] args) throws Exception{
		ConnectorAPI mt4 = new MT4();//初始化
		mt4.connect("mt3.yhmt4.com:1118");//服务器地址
		if(mt4.isConnected()){
			System.out.println("connected");
		}
		
		mt4.login(108, "abc123");//登录密码
	
		UserRecord user  = new UserRecord();//账户1890099
		user.setName("测试111111");
		user.setLogin(189123);  //唯一约束
		user.setGroup("GMAX1");//分组
		user.setEnable(1);
		
		user.setAddress("我的地址");
		user.setCity("广州");
		user.setCountry("China");
		user.setEmail("59229617@qq.com");//必填
		user.setId("371521198910143444");
		user.setPassword("abc123");  //必填
		user.setPhone("+8613659981859"); 
		user.setState("Asia");

		user.setStatus("resident");
		user.setLeverage(100);//杠杆
		user.setBalance(0);//平衡
		
		//user.setPasswordInvestor("123");
		//user.setPasswordPhone("123456phone");
		//user.setState("Asia");
		int res = mt4.userRecordNew(user);//创建交易账户
		
		System.out.println(res+"->"+mt4.getErrorDescription(res));//获取错误信息
		
		
	}
}

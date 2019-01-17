package com.mt4.api.test;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.UserRecord;

public class TestUserList {

	public static void main(String[] args) {
		ConnectorAPI mt4 = new MT4();//初始化
		/*mt4.connect("mt4live.shjhkj.com:1996");//服务器*/
		int ptr = mt4.connect("mt3.yhmt4.com:1118");
		if(mt4.isConnected()){
			System.out.println("connected");
		}
		
	/*	mt4.login(8097, "tx8097");//登录密码*/
		/*mt4.login(1111,"2333");*/
		int i =mt4.login(108, "abc123");  //返回为零 说明连接成功
		System.out.print(i);

		UserRecord[] users = mt4.usersRequest();//查看交易账户列表
		System.out.println("--->"+users.length);//获取长度
		for(UserRecord user: users){
			System.out.println("user.login="+user.getLogin());//账户登录密码
			System.out.println("user.name="+user.getName());//用户名
			System.out.println("user.getBalance="+user.getBalance());//平衡
			System.out.println("user.getBalance="+user.getPrevbalance());//
			//System.out.println("user.getPassword="+user.getPassword());
			System.out.println("user.getLeverage="+user.getGroup());//分组
		}
		

	}

}

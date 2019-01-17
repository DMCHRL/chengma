package com.mt4.api.test;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.ConGroup;

public class TestGroupList {

	public static void main(String[] args) {
		ConnectorAPI mt4 = new MT4();
		mt4.connect("mt3.yhmt4.com:1118");
		if(mt4.isConnected()){
			System.out.println("connected");
		}
		
		mt4.login(108, "abc123");
		
		//System.out.println(mt4.getErrorDescription(rs));
		
		
		ConGroup[] groups = mt4.groupsRequest();//分组列表
		System.out.println("groups.length--->"+groups.length);//长度
		
		for(int i=0;i<groups.length;i++){//
			ConGroup gro=groups[i];
			
			System.out.println("groups.gro--->"+gro.getGroup());//获得分组的信息

		}
		
		/*
		ConGroup group = new ConGroup();
		group.setGroup("testyjb");
		group.setEnable(1);
		group.setTimeout(1000);
		group.setOtpMode(1);
		
		int res = mt4.cfgUpdateGroup(group);
		System.out.println("add--->"+res);
		System.out.println("add--->"+mt4.getErrorDescription(res));
		
		
		ConGroup[] groups1 = mt4.groupsRequest();
		System.out.println("groups.length--->"+groups1.length);
		
		*/

	}

}

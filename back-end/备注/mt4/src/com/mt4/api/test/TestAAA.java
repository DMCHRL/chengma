package com.mt4.api.test;

import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.UserRecord;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class TestAAA {
    public static void main(String[] args) {
        ConnectorAPI mt4 = new MT4();
        mt4.connect("mt4live.shjhkj.com:1996");
        if(mt4.isConnected()){
            System.out.println("connected");
        }

        //mt4.login(8090, "tx123456");
        UserRecord userRecord = new UserRecord();
        userRecord.setAddress("中国");
        userRecord.setCity("广东");
        userRecord.setEmail("aaa@163.com");
        userRecord.setEnable(123);
        userRecord.setPassword("2333");
        userRecord.setId("1111");
        int i = mt4.userRecordNew(userRecord);
        System.out.println(i);
        mt4.login(1111,"2333");
        long l = mt4.serverTime();
        String time=timestamp2Date(l+"","yyyy-MM-dd HH:mm:ss");
        System.out.println(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date(Long.parseLong(l+"")*1000L)));
        System.out.println(mt4.getErrorDescription(i));
        Calendar now = Calendar.getInstance();
        int i1 = now.get(Calendar.YEAR);
        StringBuilder randomcode1 = new StringBuilder(100);
        String model = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] m1 = model.toCharArray();
        for (int j = 0; j <3; j++) {
            randomcode1.append(m1[(int) (Math.random() * 26)]);
        }

        String s =""+i1+System.currentTimeMillis();
        System.out.println(randomcode1.toString()+s);
        System.out.println(UUID.randomUUID().toString());


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

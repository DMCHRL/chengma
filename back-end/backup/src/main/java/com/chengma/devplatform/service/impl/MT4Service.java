package com.chengma.devplatform.service.impl;

/*import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.TradeTransInfo;
import com.mt4.api.bean.TransType;
import com.mt4.api.bean.TranscCmd;
import com.mt4.api.bean.UserRecord;*/
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/7/5.
 */
@Service
public class MT4Service {


   /* @Autowired
    private MyWebSocket webSocket;

    public void createMT4Acount(TlbAccountDTO tlbAccountDTO, HppUser hppUser) {

        ConnectorAPI mt4 = new MT4();
        mt4.connect("mt4demo.shjhkj.com:1995");
        if(mt4.isConnected()){
            System.out.println("connected");
        }
        mt4.login(8090, "tx123456");

        UserRecord user  = new UserRecord();//账户1890099
        user.setName(tlbAccountDTO.getAccountName());
        user.setLogin(Integer.valueOf(tlbAccountDTO.getAccount()));  //唯一约束
        user.setGroup(tlbAccountDTO.getGroup());//分组

        user.setAddress(hppUser.getAddress());
        user.setCity("广州");
        user.setCountry("China");
        user.setEmail(hppUser.getEmail());//必填
        user.setPassword(tlbAccountDTO.getMt4Password());  //必填
        user.setPhone("+86" + hppUser.getPhone());
        user.setState("Asia");

        user.setStatus("resident");
        user.setLeverage(10);//杠杆
        user.setBalance(0);//平衡
        int res = 0;//mt4.userRecordNew(user);//创建交易账户
        System.out.println(res+"->"+mt4.getErrorDescription(res));//获取错误信息
    }

    public void updateAccountMargin(String login, double margin) {
        ConnectorAPI mt4 = new MT4();
        mt4.connect("mt4demo.shjhkj.com:1995");
        if(mt4.isConnected()){
            System.out.println("connected");
        }
        mt4.login(8090, "tx123456");
        TradeTransInfo info = new TradeTransInfo();//交易信息
        info.setCmd(TranscCmd.OP_BALANCE.value());//这个什么操作？？？
        info.setComment("");
        info.setType(TransType.TT_BR_BALANCE.value());
        info.setOrderby(Integer.valueOf(login));
        info.setPrice(margin);
        //info.setCrc(1);
        int res = 0;//mt4.tradeTransaction(info);//账户余额
        System.out.println("res="+res);
    }

    public void sendSymbolInfo(String symbol){
        try {
            webSocket.sendInfo(" I am come to you !" + new Date());
        }catch (Exception e){

        }
    }*/

}
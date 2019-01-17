package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.domain.Mt4Configure;
import com.chengma.devplatform.domain.TlbTrade;
import com.chengma.devplatform.domain.TlbUser;
import com.chengma.devplatform.service.Mt4ConfigureService;
import com.chengma.devplatform.service.TlbTradeService;
import com.chengma.devplatform.service.dto.Mt4ConfigureDTO;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.TlbTradeDTO;
import com.chengma.devplatform.service.dto.TlbUserDTO;
import com.chengma.devplatform.websocket.MyWebSocket;
import com.mt4.api.ConnectorAPI;
import com.mt4.api.MT4;
import com.mt4.api.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/7/5.
 */
@Service
public class MT4Service {

    @Autowired
    private Mt4ConfigureService mt4ConfigureService;

    @Autowired
    private MyWebSocket webSocket;

    @Autowired
    private TlbTradeService tlbTradeService;



    /**
     * 0 为创建成功
     * @param tlbAccountDTO
     * @param tlbUser
     * @return
     */
    public int createMT4Acount(TlbAccountDTO tlbAccountDTO, TlbUser tlbUser) {
        Mt4ConfigureDTO mt4ConfigureDTO =getMt4Configure();
        ConnectorAPI mt4 = new MT4();
        mt4.connect(mt4ConfigureDTO.getServer());
        mt4.login(mt4ConfigureDTO.getUid(), mt4ConfigureDTO.getPassword());

        UserRecord user  = new UserRecord();//账户1890099
        user.setName(tlbAccountDTO.getAccountName());
        user.setLogin(Integer.valueOf(tlbAccountDTO.getAccount()));  //唯一约束
        user.setGroup("GMAX1");//分组

        user.setAddress(tlbUser.getAddress());
        user.setCity("广州");
        user.setCountry("China");
        user.setEnable(1);
        user.setEmail(tlbUser.getEmail());//必填
        user.setPassword(tlbAccountDTO.getMt4Password());  //必填
        user.setPhone("+86" + tlbUser.getPhone());
        user.setState("Asia");

        user.setStatus("resident");
        user.setLeverage(10);//杠杆
        user.setBalance(0);//平衡
        int result =mt4.userRecordNew(user);
        System.out.println(result+"->"+mt4.getErrorDescription(result));//获取错误信息
        return result;//创建交易账户
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
    }

    public int login(Mt4ConfigureDTO mt4ConfigureDTO){
        ConnectorAPI mt4 = new MT4();//初始化
		/*mt4.connect("mt4live.shjhkj.com:1996");//服务器*/
        int ptr = mt4.connect(mt4ConfigureDTO.getServer());
        if(!mt4.isConnected()){
            return -1;
        }
        return mt4.login(mt4ConfigureDTO.getUid(), mt4ConfigureDTO.getPassword());  //返回为零 说明连接成功
    }

    private Mt4ConfigureDTO getMt4Configure(){
        return mt4ConfigureService.findAll().get(0);
    }


    public void findTradeHistory(){
        Mt4ConfigureDTO mt4ConfigureDTO =getMt4Configure();
        ConnectorAPI mt4 = new MT4();
        mt4.connect(mt4ConfigureDTO.getServer());
        mt4.login(mt4ConfigureDTO.getUid(), mt4ConfigureDTO.getPassword());

        long from = mt4.serverTime()- 60 * 60 * 24 *80;//服务器时间-80天
        System.out.println("from="+from);
        long to = mt4.serverTime() ;//服务器时间

        System.out.println("to="+to);
        UserRecord[] users = mt4.usersRequest();//查看交易账户列表
        System.out.println("--->"+users.length);//获取长度
        List<TlbTradeDTO> newList = new ArrayList<>();
        List<TlbTradeDTO> oldList =tlbTradeService.findAll();
        //把旧历史记录转为map，并已订单号作为key
        Map<Long, TlbTradeDTO> maps = oldList.stream().collect(Collectors.toMap(TlbTradeDTO::getOrderNo, tlbTradeDTO -> tlbTradeDTO));
        for(UserRecord user: users){
            TradeRecord[] records =  mt4.getTradesUserHistory(user.getLogin(), from, to);//账户的交易记录
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式
            if(records != null && records.length > 0){
                for(TradeRecord record : records){//每一条记录
                    //map中不存在订单号的key，则新增到数据库
                    if( !maps.containsKey((long)record.getOrder())){
                        TlbTradeDTO tlbTradeDTO = new TlbTradeDTO();
                        tlbTradeDTO.setOrderNo(Long.parseLong(record.getOrder()+""));
                        tlbTradeDTO.setAccount( record.getLogin()+"");
                        if("".equals(record.getSymbol())){
                            if(record.getProfit() < 0){
                                record.setSymbol("出金");
                            }else if(record.getProfit() > 0){
                                record.setSymbol("入金");
                            }
                        }
                        tlbTradeDTO.setSymbol( record.getSymbol());
                        String type = "";
                        if(record.getCmd() == 1){
                            type = "sell";
                        }else if(record.getCmd() == 0){
                            type = "buy";
                        }


                        tlbTradeDTO.setOrderType(type);
                        tlbTradeDTO.setLots( record.getVolume()*1.0/100);
                        tlbTradeDTO.setOpenPrice(record.getOpenPrice());
                        tlbTradeDTO.setClosePrice(record.getClosePrice());
                        tlbTradeDTO.setOpenTime(new Date(record.getOpenTime()*1000-1000*60*60*8));
                        tlbTradeDTO.setCloseTime(new Date(record.getCloseTime()*1000-1000*60*60*8));
                        tlbTradeDTO.setStorage( record.getStorage());
                        tlbTradeDTO.setGainAmount( record.getProfit());
                        newList.add(tlbTradeDTO);
                    }

                }
            }
        }
        tlbTradeService.saveList(newList);
    }

     public static void main(String[] args){

         }

}
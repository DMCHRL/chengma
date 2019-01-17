package com.chengma.devplatform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.config.llpay.config.PartnerConfig;
import com.chengma.devplatform.config.llpay.config.ServerURLConfig;
import com.chengma.devplatform.config.llpay.conn.HttpRequestSimple;
import com.chengma.devplatform.config.llpay.utils.LLPayUtil;
import com.chengma.devplatform.config.llpay.vo.PaymentInfo;
import com.chengma.devplatform.config.usdt.HttpRequest;
import com.chengma.devplatform.config.usdt.USDTConfig;
import com.chengma.devplatform.domain.Order;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.OrderRepository;
import com.chengma.devplatform.repository.UsdtOrderRepository;
import com.chengma.devplatform.service.OrderService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.OrderDTO;
import com.chengma.devplatform.service.dto.UsdtOrderDTO;
import com.chengma.devplatform.service.mapper.OrderMapper;
import com.chengma.devplatform.service.mapper.UsdtOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private UsdtOrderRepository usdtOrderRepository;

    @Autowired
    private UsdtOrderMapper usdtOrderMapper;


    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper){
        this.orderRepository=orderRepository;
        this.orderMapper=orderMapper;
    }

    @Override
    public Page<OrderDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        return orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDTO)));
    }

    @Override
    public HashMap<String,Object> createOrderDTO(OrderDTO orderDTO) {
        HashMap<String,Object> result = new HashMap<>();

        User user = userService.getUserWithAuthorities();
        orderDTO.setNoOrder(LLPayUtil.getCurrentDateTimeStr());
        orderDTO.setDtOrder(LLPayUtil.getCurrentDateTimeStr());
        orderDTO.setNameGoods("充值");
        orderDTO.setFlagPayProduct("2"); //网银
        orderDTO.setUserId(user.getId());
        orderDTO.setPayStatus("0"); //未付款
        orderDTO.setCreateAt(new Date());


        // 构造支付请求对象
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setApi_version(PartnerConfig.VERSION);
        paymentInfo.setOid_partner(PartnerConfig.OID_PARTNER);  //商户号
        paymentInfo.setSign_type(PartnerConfig.SIGN_TYPE);
        paymentInfo.setBusi_partner(PartnerConfig.BUSI_PARTNER);
        paymentInfo.setNo_order(orderDTO.getNoOrder());
        paymentInfo.setDt_order(orderDTO.getDtOrder());
        paymentInfo.setName_goods(orderDTO.getNameGoods());
           /* paymentInfo.setInfo_order(order.getInfo_order());*/
        paymentInfo.setMoney_order(orderDTO.getMoneyOrder());
        paymentInfo.setNotify_url(PartnerConfig.NOTIFY_URL);   //异步通知
        paymentInfo.setUrl_return(PartnerConfig.URL_RETURN);   //同步通知
        paymentInfo.setValid_order("30");// 单位分钟，可以为空，默认30分钟
        paymentInfo.setTime_stamp(LLPayUtil.getCurrentDateTimeStr());
        paymentInfo.setRisk_item(createRiskItem());
        paymentInfo.setFlag_chnl("3");//2 web 、3 wap
        paymentInfo.setFlag_pay_product(orderDTO.getFlagPayProduct());//0 快捷、1 认证、2 网银、5新认证支付

        paymentInfo.setUser_id(orderDTO.getUserId());
      /*  paymentInfo.setCard_no(orderDTO.getCardNo());   //银行卡号
        paymentInfo.setAcct_name(orderDTO.getAcctName());  //银行卡持有人姓名
        paymentInfo.setId_type("0");   //可选 身份证
        paymentInfo.setId_no(orderDTO.getIdNo()); //可选*/
        //paymentInfo.setBind_mob(req.getParameter("bind_mob"));
        //paymentInfo.setBank_code(req.getParameter("bank_code"));  //bank_code card_type二选一
        paymentInfo.setCard_type(orderDTO.getCardType());


        // 加签名
        String sign = LLPayUtil.addSign(JSON.parseObject(JSON
                        .toJSONString(paymentInfo)), PartnerConfig.TRADER_PRI_KEY,
                "");
        paymentInfo.setSign(sign);

        String resJSON = HttpRequestSimple.getInstance().postSendHttp(ServerURLConfig.BILL_CREATE_URL, JSON.toJSONString(paymentInfo));
        System.out.println("创单请求响应报文[" + resJSON + "]");

        JSONObject payDataBean = JSON.parseObject(resJSON);
        if(!"0000".equals(payDataBean.getString("ret_code"))){
            result.put("statusCode", ResponseResult.FAIL_CODE);
            result.put("msg", "创单失败!");
            return result;
        }

        if (!LLPayUtil.checkSign(resJSON, PartnerConfig.YT_PUB_KEY, "")) {
            result.put("statusCode", ResponseResult.FAIL_CODE);
            result.put("msg", "签名验证失败!");
            return result;
        }

        OrderDTO orderDTO1 = orderMapper.toDto(orderRepository.save(orderMapper.toEntity(orderDTO)));
       /* orderDTO1.setGatewayUrl(payDataBean.getString("gateway_url"));*/
        result.put("statusCode", ResponseResult.SUCCESS_CODE);
        result.put("data", orderDTO1);
        return result;


    }

    @Override
    public HashMap<String, Object> createUsdtOrderDTO(UsdtOrderDTO usdtOrderDTO) {
        HashMap<String,Object> result = new HashMap<>();
        usdtOrderDTO.setOrderId(LLPayUtil.getCurrentDateTimeStr());

        String P_Result_url = USDTConfig.P_Result_url;//充值状态通知地址
        String P_Notify_url = USDTConfig.P_Notify_url;//充值后网页跳转地址
        String signParam = USDTConfig.P_UserId+"|"+usdtOrderDTO.getOrderId()+"|"+usdtOrderDTO.getCardId()+"|"+usdtOrderDTO.getCardPass()+"|"+usdtOrderDTO.getFaceValue()+"|"+usdtOrderDTO.getChannelId()+"|"+USDTConfig.channelKey;
        String P_PostKey = encryption(signParam);
        usdtOrderRepository.save(usdtOrderMapper.toEntity(usdtOrderDTO));


        String url = USDTConfig.payUrl+"?P_UserId="+USDTConfig.P_UserId+"&P_OrderId="+usdtOrderDTO.getOrderId()+"&P_CardId="+usdtOrderDTO.getCardId()+
        "&P_CardPass="+usdtOrderDTO.getCardPass()+"&P_FaceValue="+usdtOrderDTO.getFaceValue()+"&P_ChannelId="+usdtOrderDTO.getChannelId()+"&P_Subject="+usdtOrderDTO.getSubject()+"&P_Price="+usdtOrderDTO.getPrice()+
                "&P_Quantity="+usdtOrderDTO.getQuantity()+"&P_Description="+usdtOrderDTO.getDescription()+"&P_Notic="+usdtOrderDTO.getNotic()+"&P_Result_url="+P_Notify_url+"&P_Notify_url="+P_Result_url+"&P_PostKey="+P_PostKey;
        result.put("statusCode", ResponseResult.SUCCESS_CODE);
        result.put("data", url);
        return result;
    }

    public String encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

    /**
     * 根据连连支付风控部门要求的参数进行构造风控参数
     * @return
     */
    private String createRiskItem()
    {
        JSONObject riskItemObj = new JSONObject();
        riskItemObj.put("user_info_full_name", "你好");
        riskItemObj.put("frms_ware_category", "1999");
        return riskItemObj.toString();
    }


    @Override
    public HashMap<String, Object> checkCreateOrderDTO(OrderDTO orderDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public OrderDTO findOne(String id) {
        return orderMapper.toDto(orderRepository.findOne(id));
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderMapper.toDto(orderRepository.findAll());
    }

    @Override
    public void delete(String id) {
        orderRepository.delete(id);
    }

    @Override
    public void updateTranInfo(String noOrder) {
        Order llPayOrder = orderRepository.findByNoOrderEquals(noOrder);

        if( null != llPayOrder){
            //更新资金申请表
          /*  HashMap<String, Object> paramsFundApply = new HashMap<>();
            paramsFundApply.put("account", tranInfoDTO.getObj());
            paramsFundApply.put("usd", Long.valueOf(tranInfoDTO.getTotalFeeUsd().toString()));
            paramsFundApply.put("amount", Long.valueOf(tranInfoDTO.getTotalFee().toString()) / 100);
            paramsFundApply.put("transactionId", params.get("payOrderId"));
            fundApplyService.recharge(paramsFundApply);*/

            llPayOrder.setPayStatus("1");
            orderRepository.save(llPayOrder);
        }
    }

    @Override
    public List<OrderDTO> loadMyOrder() {
        User user = userService.getUserWithAuthorities();
        String sql="select o.*,p.c_name,p.i_price,p.c_img1 from t_order o join jhi_user u on o.c_user_id=u.id join t_product p on p.c_id = o.c_product_id where u.id='"+user.getId()+"'";
        return baseDao.findListBySql(sql,OrderDTO.class);
    }
}

package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.HppStrategy;
import com.chengma.devplatform.domain.HppStrategyOrder;
import com.chengma.devplatform.domain.TlbAccount;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppStrategyOrderRepository;
import com.chengma.devplatform.repository.UserRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.HppStrategyOrderMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class HppStrategyOrderServiceImpl implements HppStrategyOrderService {

    private final HppStrategyOrderRepository hppStrategyOrderRepository;

    private final HppStrategyOrderMapper  hppStrategyOrderMapper;

    @Autowired
    private HppStrategyService hppStrategyService;

    @Autowired
    private HppMobileUserService hppMobileUserService;

    @Autowired
    private HppMobileValidateService hppMobileValidateService;

    @Autowired
    private HppNoticeService hppNoticeService;

    @Autowired
    private TlbAccountService tlbAccountService;

    @Autowired
    private UserService userService ;

    @Autowired
    private HppIntegralDetailService hppIntegralDetailService ;

    @Autowired
    private HppIntegralService hppIntegralService ;

    @Autowired
    private HppStrategyDataService hppStrategyDataService;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private PageCommon pageCommon;

    public HppStrategyOrderServiceImpl(HppStrategyOrderRepository hppStrategyOrderRepository,HppStrategyOrderMapper hppStrategyOrderMapper){
        this.hppStrategyOrderRepository=hppStrategyOrderRepository;
        this.hppStrategyOrderMapper=hppStrategyOrderMapper;
    }

    @Override
    public HppStrategyOrderDTO follow(HppStrategyOrderDTO hppStrategyOrderDTO) {
        hppStrategyOrderDTO.setCreateAt(new Date());
        hppStrategyOrderDTO.setType(DevplatformConstants.STRATEGY_IN);
        hppStrategyOrderDTO.setStatus(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_APPLYING);
        hppStrategyOrderDTO.setState(DevplatformConstants.STRATEGY_EFFECTIVE);

        User user = userService.findByDepartment(EnumRole.MT4.value());
        //发送任务通知
        if(user != null){
            noticeToCharge(user.getMobile());
        }
        //发送用户通知
        HppStrategyOrder hppStrategyOrder =  hppStrategyOrderMapper.toEntity(hppStrategyOrderDTO);
        HppStrategyDTO hppStrategyDTO = hppStrategyService.findOne(hppStrategyOrderDTO.getStrategyId());
        notice(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_PASSED,DevplatformConstants.STRATEGY_IN,hppStrategyOrder,hppStrategyDTO);

        return hppStrategyOrderMapper.toDto(hppStrategyOrderRepository.save(hppStrategyOrder));
    }

    /**
     * 发送通知
     * @param mobile
     */
    public void noticeToCharge(String mobile){
        HashMap<String,Object> param = new HashMap<>();
        param.put("mobileNum",mobile);
        param.put("sendType","task");
        //param.put("content",hppStrategyOrder.getAccount()+","+hppStrategyDTO.getStrategyName());
        hppMobileValidateService.notice(param);
    }

    @Override
    public HppStrategyOrderDTO relieve(HppStrategyOrderDTO hppStrategyOrderDTO) {
        hppStrategyOrderDTO.setCreateAt(new Date());
        hppStrategyOrderDTO.setType(DevplatformConstants.STRATEGY_OUT);
        hppStrategyOrderDTO.setStatus(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_APPLYING);
        hppStrategyOrderDTO.setState(DevplatformConstants.STRATEGY_EFFECTIVE);
        hppStrategyOrderDTO = hppStrategyOrderMapper.toDto(hppStrategyOrderRepository.save(hppStrategyOrderMapper.toEntity(hppStrategyOrderDTO)));

        User user = userService.findByDepartment(EnumRole.MT4.value());
        if(user != null){
            noticeToCharge(user.getMobile());
        }

        //自动解绑
        HashMap<String,Object> params =new HashMap<>();
        params.put("strategyOrderId",hppStrategyOrderDTO.getId());
        params.put("status","PASSED");
        hppStrategyOrderDTO=this.approve(params);
        return hppStrategyOrderDTO;
    }

    @Override
    public HashMap<String, Object> checkApply(HppStrategyOrderDTO hppStrategyOrderDTO,String type) {
        HashMap<String,Object> retMap =new HashMap<>();
        if(StringUtils.isBlank(hppStrategyOrderDTO.getAccount())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入交易账号");
            return retMap;
        }
        if(StringUtils.isBlank(hppStrategyOrderDTO.getPassword())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入交易密码");
            return retMap;
        }


        /*if(tlbAccountService.findByAccountAndPassword(hppStrategyOrderDTO.getAccount(),hppStrategyOrderDTO.getPassword()) == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "交易账号或密码错误");
            return retMap;
        }*/

        if(hppMobileUserService.findByMobile(hppStrategyOrderDTO.getMobileNum())==null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入有效联系方式");
            return retMap;
        }
        if(type.equals(DevplatformConstants.STRATEGY_IN)) {
            if (StringUtils.isBlank(hppStrategyOrderDTO.getRisk())) {
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "请选择风险参数");
                return retMap;
            }
        }
        HppStrategyDTO hppStrategyDTO = hppStrategyService.findOne(hppStrategyOrderDTO.getStrategyId());
        if(hppStrategyDTO == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择有效的策略");
            return retMap;
        }

        if(type.equals(DevplatformConstants.STRATEGY_IN)) {
            if (tlbAccountService.findByAccount(hppStrategyOrderDTO.getAccount()) != null) {
                if (tlbAccountService.findOneByMobileAndAccount(hppStrategyOrderDTO.getMobileNum(), hppStrategyOrderDTO.getAccount()) == null) {
                    retMap.put("statusCode", ResponseResult.FAIL_CODE);
                    retMap.put("msg", hppStrategyOrderDTO.getAccount() + "账号首次捆绑非本机号码，请联系客服");
                    return retMap;
                }
            }
        }

        if(hppStrategyDTO.getActivityFlag().equals("N")){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", hppStrategyDTO.getActivityText());
            return retMap;
        }

        //防止重复提交
        if(type.equals(DevplatformConstants.STRATEGY_IN)){
            if(this.exist(hppStrategyOrderDTO.getAccount(),type,null,true)){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "申请中或已有记录");
                return retMap;
            }
        }else if(type.equals(DevplatformConstants.STRATEGY_OUT)){
            if(this.exist(hppStrategyOrderDTO.getAccount(),type,null,true)){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "申请中");
                return retMap;
            }
            if(!this.exist(hppStrategyOrderDTO.getAccount(),DevplatformConstants.STRATEGY_IN,hppStrategyOrderDTO.getStrategyId(),false)){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "未跟单,无法解绑");
                return retMap;
            }

        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    /**
     * 判断账号是否已有记录
     * @param account
     * @param type
     * @param strategyId
     * @param flag true(passed or applying),false(passed)
     * @return
     */
    private boolean exist(String account, String type,String strategyId,boolean flag) {
        StringBuffer sb=new StringBuffer("SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\tt_hpp_strategy_order\n" +
                "WHERE\n" +
                "\tc_account = '"+account+"'\n" +
                "AND c_type = '"+type+"'\n" +
                "AND (\n" +
                "\tc_status = '"+DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_PASSED+"'\n" +
                "\tOR c_status = '"+DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_APPLYING+"'\n" +
                ") "+
                "AND c_state = '"+DevplatformConstants.STRATEGY_EFFECTIVE+"'\n");
        String sql=null;
        if(!flag){
            sb.append(" AND c_strategy_id = '"+strategyId+"'");
            sql=sb.toString().replace("OR c_status = 'APPLYING'","");
        }else{
            sql=sb.toString();
        }
        List list=baseDao.findListBySql(sql,HppStrategyOrderDTO.class);
        if(list!=null&&list.size()>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public HppStrategyOrderDTO approve(HashMap<String, Object> params) {
        String strategyOrderId = params.get("strategyOrderId") == null ? "" : params.get("strategyOrderId").toString();
        String status = params.get("status") == null ? "" : params.get("status").toString();
        User currentUser=userService.getUserWithAuthorities();
        HppStrategyOrder hppStrategyOrder=hppStrategyOrderRepository.findOne(strategyOrderId);

        //具体策略
        HppStrategyDTO hppStrategyDTO = hppStrategyService.findOne(hppStrategyOrder.getStrategyId());

        if(status.equals(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_PASSED)){
            //通过
            if(hppStrategyOrder.getType().equals(DevplatformConstants.STRATEGY_OUT)){
                //解绑
                //找到跟单记录,将其无效化
                String sql="SELECT * from t_hpp_strategy_order where c_type='IN' and c_state='EFFECTIVE' and c_status='PASSED' and c_account='"+hppStrategyOrder.getAccount()+"'";
                List<HppStrategyOrderDTO> list= baseDao.findListBySql(sql,HppStrategyOrderDTO.class);
                HppStrategyOrderDTO followHppStrategyOrderDTO=list.get(0);
                followHppStrategyOrderDTO.setState(DevplatformConstants.STRATEGY_INVALID);
                hppStrategyOrderRepository.save(hppStrategyOrderMapper.toEntity(followHppStrategyOrderDTO));

                hppStrategyOrder.setState(DevplatformConstants.STRATEGY_INVALID);

                //发通知
                notice(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_PASSED,DevplatformConstants.STRATEGY_OUT,hppStrategyOrder,hppStrategyDTO);

                //更改account交易状态
                tlbAccountService.changeEnableTrade(hppStrategyOrder.getAccount(),"N");
                if(!this.existFollowByMobile(hppStrategyOrder.getMobileNum())){
                    hppMobileUserService.followFlagN(hppStrategyOrder.getMobileNum()); //标记为不跟单用户
                }
            }else{

                if(tlbAccountService.findByAccount(hppStrategyOrder.getAccount()) == null){
                    createAccount(hppStrategyOrder);
                }

                //首次使用，加1000积分
                List<HppStrategyOrder> hppStrategyOrders = hppStrategyOrderRepository.findByMobileNumEquals(hppStrategyOrder.getMobileNum());
                if(hppStrategyOrders != null && hppStrategyOrders.size() ==1 ){
                    hppIntegralService.addIntegral(hppStrategyOrder.getMobileNum(),1000.0);
                    HppIntegralDetailDTO hppIntegralDetailDTO = new HppIntegralDetailDTO(hppStrategyOrder.getMobileNum(),new Date(), DevplatformConstants.INTEGRAL_IN,DevplatformConstants.INTEGRAL_DETAIL_TYPE_STRATEGY,1000.0,null);
                    hppIntegralDetailService.save(hppIntegralDetailDTO);
                }

                hppStrategyOrder.setMt4Status(DevplatformConstants.STRATEGY_ORDER_MT4_STATUS_NOLINE); //默认标记为未上线
                tlbAccountService.changeEnableTrade(hppStrategyOrder.getAccount(),"Y");
                hppMobileUserService.followFlagY(hppStrategyOrder.getMobileNum());
            }

            hppStrategyOrder.setStatus(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_PASSED);
        }else{
            //拒绝
            hppStrategyOrder.setStatus(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_REJECT);
            hppStrategyOrder.setState(DevplatformConstants.STRATEGY_INVALID);

            if(hppStrategyOrder.getType().equals(DevplatformConstants.STRATEGY_OUT)){
                notice(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_REJECT,DevplatformConstants.STRATEGY_OUT,hppStrategyOrder,hppStrategyDTO);
            }else{
                notice(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_REJECT,DevplatformConstants.STRATEGY_IN,hppStrategyOrder,hppStrategyDTO);
            }
        }

        Date now = new Date();

        hppStrategyOrder.setApproveId(currentUser.getId());
        hppStrategyOrder.setApproveAt(now);
        return hppStrategyOrderMapper.toDto(hppStrategyOrderRepository.save(hppStrategyOrder));
    }

    private void createAccount(HppStrategyOrder hppStrategyOrder){
        TlbAccountDTO tlbAccountDTO = new TlbAccountDTO();
        tlbAccountDTO.setAccount(hppStrategyOrder.getAccount());
        tlbAccountDTO.setMt4Password(hppStrategyOrder.getPassword());
        tlbAccountDTO.setCreateAt(new Date());
        tlbAccountDTO.setMobileNum(hppStrategyOrder.getMobileNum());
        tlbAccountDTO.setEnableTrade("Y"); //跟单
        tlbAccountService.saveAccount(tlbAccountDTO);

        HppStrategyDataDTO hppStrategyDataDTO =new HppStrategyDataDTO();
        hppStrategyDataDTO.setAccount(hppStrategyOrder.getAccount());
        hppStrategyDataDTO.setFundBack(0.0);
        hppStrategyDataDTO.setMonthProfit(0.0);
        hppStrategyDataDTO.setTotalProfit(0.0);
        hppStrategyDataDTO.setLots(0.0);
        hppStrategyDataDTO.setFundOut(0.0);
        hppStrategyDataDTO.setFundIn(0.0);
        hppStrategyDataDTO.setBalance(0.0);
        hppStrategyDataDTO.setTopNetWorth(0.0);
        hppStrategyDataDTO.setNetWorth(0.0);
        hppStrategyDataDTO.setMargin(0.0);
        /**
         * ....
         */
        hppStrategyDataService.save(hppStrategyDataDTO);
    }

    /**
     * 通知用户与审核者
     */
    private void notice(String status,String type,HppStrategyOrder hppStrategyOrder,HppStrategyDTO hppStrategyDTO){
        HppNoticeDTO hppNoticeDTO =new HppNoticeDTO();
        if(status.equals(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_PASSED)) {
            if (type.equals(DevplatformConstants.STRATEGY_OUT)) {
                //消息通知
                hppNoticeDTO.setType(DevplatformConstants.MSG_TYPE_RELIEVE_ORDER);
                hppNoticeDTO.setContext("您的"+hppStrategyOrder.getAccount()+"交易帐户，已与"+hppStrategyDTO.getStrategyName()+"交易帐号策略解除绑定。请检查您的交易帐户是否存有遗留未处理单，若有请及时处理。");

                //短信通知用户
                HashMap<String,Object> param = new HashMap<>();
                param.put("mobileNum",hppStrategyOrder.getMobileNum());
                param.put("sendType","strategyOut");
                param.put("content",hppStrategyOrder.getAccount()+","+hppStrategyDTO.getStrategyName());
                hppMobileValidateService.notice(param);
            }else{
                hppNoticeDTO.setType(DevplatformConstants.MSG_TYPE_FOLLOW_ORDER);
                hppNoticeDTO.setContext("您的"+hppStrategyOrder.getAccount()+"交易帐户申请跟随"+hppStrategyDTO.getStrategyName()+"交易帐号策略，已递交系统审核，请稍后关注。（审核时间：工作日9：30----18：30。过18：30次日审核）");

                HashMap<String,Object> param = new HashMap<>();
                param.put("mobileNum",hppStrategyOrder.getMobileNum());
                param.put("sendType","strategyIn");
                param.put("content",hppStrategyOrder.getAccount()+","+hppStrategyDTO.getStrategyName());
                hppMobileValidateService.notice(param);
            }
        }else{
            if(hppStrategyOrder.getType().equals(DevplatformConstants.STRATEGY_OUT)){
                hppNoticeDTO.setType(DevplatformConstants.MSG_TYPE_RELIEVE_ORDER);
                hppNoticeDTO.setContext(DevplatformConstants.MSG_RELIEVE_ORDER_FAIL);
            }else{
                hppNoticeDTO.setType(DevplatformConstants.MSG_TYPE_FOLLOW_ORDER);
                hppNoticeDTO.setContext("您的帐户与策略帐户服务器不匹配，请联系客服，我们会快速解决您的需求。");

                HashMap<String,Object> param = new HashMap<>();
                param.put("mobileNum",hppStrategyOrder.getMobileNum());
                param.put("sendType","strategyInReject");
                //param.put("content",hppStrategyOrder.getAccount()+","+hppStrategyDTO.getStrategyName());
                hppMobileValidateService.notice(param);
            }
        }
        hppNoticeDTO.setCreateTime(new Date());
        hppNoticeDTO.setSendFlag("N");
        hppNoticeDTO.setDelFlag("N");
        hppNoticeDTO=hppNoticeService.save(hppNoticeDTO);

        //发送通知栏
        HppSendNoticeDTO hppSendNoticeDTO =new HppSendNoticeDTO();
        hppSendNoticeDTO.setNoticeId(hppNoticeDTO.getId());
        hppSendNoticeDTO.setType(DevplatformConstants.DEFAULT_NOTICE);

        //添加通知对象
        User user=userService.findUserByMobile(hppStrategyOrder.getMobileNum());
        List<User> userList = new ArrayList<>();
        userList.add(user);
        hppSendNoticeDTO.setUserList(userList);
        hppNoticeService.sendNotice(hppSendNoticeDTO);
    }



    @Override
    public HashMap<String, Object> checkApprove(HashMap<String, Object> params) {
        HashMap<String,Object> retMap =new HashMap<>();
        User currentUser=userService.getUserWithAuthorities();
        String strategyOrderId = params.get("strategyOrderId") == null ? "" : params.get("strategyOrderId").toString();
        String status = params.get("status") == null ? "" : params.get("status").toString();

        /*if(currentUser==null||!(currentUser.getDepartment().equals(EnumRole.ADMIN.value())||currentUser.getDepartment().equals(EnumRole.SERVICE.value()))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录有效账号");
            return retMap;
        }*/
        HppStrategyOrder hppStrategyOrder=hppStrategyOrderRepository.findOne(strategyOrderId);
        if(hppStrategyOrder==null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择处理单据");
            return retMap;
        }else{
            String currentStatus=hppStrategyOrder.getStatus();
            if(currentStatus.equals(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_PASSED)||currentStatus.equals(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_REJECT)){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "已被处理");
                return retMap;
            }
        }
        if(!status.equals(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_PASSED)&&!status.equals(DevplatformConstants.STRATEGY_ORDER_APPLY_STATUS_REJECT)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择处理类型");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public Long findByStrategyId(String StrategyId) {
        return hppStrategyOrderRepository.findByStrategyId(StrategyId);
    }

    @Override
    public Page<HppStrategyOrderDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String strategyName = formParams.get("strategyName") == null ? "" : formParams.get("strategyName").toString();
        String type = formParams.get("type") == null ? "" : formParams.get("type").toString();
        String mobileNum = formParams.get("mobileNum") == null ? "" : formParams.get("mobileNum").toString();
        String account = formParams.get("account") == null ? "" : formParams.get("account").toString();
        String status = formParams.get("status") == null ? "" : formParams.get("status").toString();
        String startTime = formParams.get("startTime") == null ? "" : formParams.get("startTime").toString();
        String endTime = formParams.get("endTime") == null ? "" : formParams.get("endTime").toString();

        User currentUser=userService.getUserWithAuthorities();
        if(currentUser==null)return null;

        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" SELECT o.*,IFNULL(u.first_name,'') approve_name,s.c_strategy_name strategy_name   ");

        StringBuilder cond = new StringBuilder(" from t_hpp_strategy_order o LEFT JOIN jhi_user u on o.c_approve_id =u.id join t_hpp_strategy s on s.c_id=o.c_strategy_id where 1=1 ");

        //非超级管理员或客服
        /*if(!(currentUser.getDepartment().equals(EnumRole.ADMIN.value())||currentUser.getDepartment().equals(EnumRole.SERVICE.value()))){
            cond.append(" and s.c_user_id='" + currentUser.getId() + "'");
        }*/

        if (StringUtils.isNotBlank(strategyName)) {
            cond.append(" and s.c_strategy_name like '%"+strategyName+"%' ");
        }

        if (StringUtils.isNotBlank(type)) {
            cond.append(" and o.c_type like '%"+type+"%' ");
        }

        if (StringUtils.isNotBlank(mobileNum)) {
            cond.append(" and o.c_mobile_num like '%"+mobileNum+"%' ");
        }

        if (StringUtils.isNotBlank(account)) {
            cond.append(" and o.c_account like '%"+account+"%' ");
        }

        if (StringUtils.isNotBlank(status)) {
            cond.append(" and o.c_status like '%"+status+"%' ");
        }

        if(StringUtils.isNotBlank(startTime)){
            cond.append(" and TO_DAYS(o.d_create_at)>=TO_DAYS('"+startTime+"') ");
        }

        if(StringUtils.isNotBlank(endTime)){
            cond.append(" and TO_DAYS(o.d_create_at)<=TO_DAYS('"+endTime+"') ");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= "o."+ ReflectUtils.getColumnName(HppStrategy.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<HppStrategyOrderDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy, page_number, page_size, HppStrategyOrderDTO.class);
        return page;
    }

    @Override
    public boolean existFollow(String account, String password) {
        return hppStrategyOrderRepository.existFollow(account,password) != null;
    }

    @Override
    public Page<HppStrategyOrderDTO> findList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String strategyId = formParams.get("strategyId") == null ? "" : formParams.get("strategyId").toString();
        String startTime = formParams.get("startTime") == null ? "" : formParams.get("startTime").toString();
        String endTime = formParams.get("endTime") == null ? "" : formParams.get("endTime").toString();
        String mobileNum = formParams.get("mobileNum") == null ? "" : formParams.get("mobileNum").toString();
        /*String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();*/

        StringBuilder column = new StringBuilder(" SELECT\n" +
                "\to.*,\n" +
                "\tIFNULL(ROUND(sum(f.i_lots),2),0) lots");

        StringBuilder cond = new StringBuilder(" FROM\n" +
                "\tt_hpp_strategy_order o\n" +
                "left join t_hpp_strategy_trade f on o.c_account= f.c_account ");
        if(StringUtils.isNotBlank(startTime)){
            cond.append(" and TO_DAYS(f.d_close_time)>=TO_DAYS('"+startTime+"') ");
        }

        if(StringUtils.isNotBlank(endTime)){
            cond.append(" and TO_DAYS(f.d_close_time)<=TO_DAYS('"+endTime+"') ");
        }
        cond.append("WHERE\n" +
                "\to.c_type = 'IN'\n" +
                "AND o.c_status = 'PASSED'\n" +
                "AND o.c_state = 'EFFECTIVE'\n" +
                "AND o.c_strategy_id = '"+strategyId+"'\n" +
                "AND (f.c_order_type='SELL' or f.c_order_type='BUY' or ISNULL(f.c_order_type))");

        if(StringUtils.isNotBlank(mobileNum)){
            cond.append(" and  o.c_mobile_num like '%"+mobileNum+"%' ");
        }
        cond.append(" GROUP BY o.c_account");

        Page<HppStrategyOrderDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, HppStrategyOrderDTO.class);
        return page;
    }

    @Override
    public boolean existFollowByMobile(String mobile) {
        List<HppStrategyOrder> list = hppStrategyOrderRepository.findByMobileNumEqualsAndTypeEqualsAndStatusEqualsAndStateEquals(mobile,DevplatformConstants.STRATEGY_IN,DevplatformConstants.STRATEGY_APPLY_STATUS_PASSED,DevplatformConstants.STRATEGY_EFFECTIVE);
       if(list != null && list.size()>0){
           return true;
       }else{
           return false;
       }
    }

    @Override
    public void setMt4Status(HashMap<String, Object> params) {
        String id = params.get("id") == null ? "" : params.get("id").toString();
        String mt4Status = params.get("mt4Status") == null ? "" : params.get("mt4Status").toString();

        HppStrategyOrder hppStrategyOrder = hppStrategyOrderRepository.findOne(id);
        if(hppStrategyOrder != null){
            if(mt4Status.equals(DevplatformConstants.STRATEGY_ORDER_MT4_STATUS_ONLINE)){
                hppStrategyOrder.setMt4Status(mt4Status);
            }else if(mt4Status.equals(DevplatformConstants.STRATEGY_ORDER_MT4_STATUS_DOWNLINE)){
                hppStrategyOrder.setMt4Status(mt4Status);
            }else{
                hppStrategyOrder.setMt4Status(DevplatformConstants.STRATEGY_ORDER_MT4_STATUS_NOLINE);
            }
        }
        hppStrategyOrderRepository.save(hppStrategyOrder);
    }
}

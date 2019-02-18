package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.*;
import com.chengma.devplatform.repository.HppStrategyRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.HppStrategyMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class HppStrategyServiceImpl implements HppStrategyService {


    private final HppStrategyRepository hppStrategyRepository;

    private final HppStrategyMapper hppStrategyMapper;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private UserService userService;

    @Autowired
    private TlbAccountService tlbAccountService;

    @Autowired
    private HppStrategyDataService hppStrategyDataService;

    @Autowired
    private HppStrategyOrderService hppStrategyOrderService;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private AppPushService appPushService;

    @Autowired
    private HppNoticeService hppNoticeService;

    @Autowired
    private HppNoticeSignService hppNoticeSignService;

    @Autowired
    private HppMobileValidateService hppMobileValidateService;

    public HppStrategyServiceImpl(HppStrategyRepository hppStrategyRepository, HppStrategyMapper hppStrategyMapper){
        this.hppStrategyRepository=hppStrategyRepository;
        this.hppStrategyMapper=hppStrategyMapper;
    }

    @Override
    public HppStrategyDTO findByMobileNum(String mobileNum) {
        StringBuffer sql= new StringBuffer("SELECT\n" +
                "\ts.c_id,\n" +
                "\ts.c_strategy_name,\n" +
                "\ts.c_strategy_name2,\n" +
                "\tROUND((IFNULL(sd.i_total_profit,0)/IFNULL(sd.i_fund_in,1))*100,2) as i_income_rate,\n" +
                "\ts.c_strategy_text,\n" +
                "\ts.d_create_at,\n" +
                "\ts.d_update_at,\n" +
                "\ts.c_account\n" +
                "FROM\n" +
                "\tt_hpp_strategy s\n" +
                "JOIN t_hpp_strategy_order so ON s.c_id = so.c_strategy_id\n" +
                "JOIN t_hpp_strategy_data sd ON sd.c_account=s.c_account\n" +
                "AND so.c_type = 'IN'\n" +
                "AND so.c_status = 'PASSED'\n" +
                "AND c_mobile_num = '"+mobileNum+"' and so.c_state ='"+ DevplatformConstants.STRATEGY_EFFECTIVE+" '");
         List<HppStrategyDTO> list=baseDao.findListBySql(sql.toString(),HppStrategyDTO.class);
         if(list!=null&&list.size()>0){
             return list.get(0);
         }else{
             return null;
         }
    }

    @Override
    public HppStrategyDTO findOne(String id) {
        return hppStrategyMapper.toDto(hppStrategyRepository.findOne(id));
    }

    @Override
    public List<HppStrategyDTO> findAll() {
        StringBuffer sql = new StringBuffer("SELECT\n" +
                "\ts.c_id,\n" +
                "\ts.c_strategy_name,\n" +
                "\ts.c_strategy_name2,\n" +
                "\ts.c_strategy_text,\n" +
                "\ts.d_create_at,\n" +
                "\ts.d_update_at,\n" +
                "\ts.c_account,\n" +
                "\tsd.i_month_profit as i_income_rate\n" +
                "FROM\n" +
                "\tt_hpp_strategy s\n" +
                "JOIN t_hpp_strategy_data sd ON sd.c_account=s.c_account\n" +
                "WHERE\n" +
                "\tc_status = '"+DevplatformConstants.STRATEGY_APPLY_STATUS_PASSED+"'"+
                "\tAND s.c_line_flag='Y'\n" );
        String order=" ORDER BY i_income_rate desc";
        return baseDao.findListBySql(sql.toString(),order,HppStrategyDTO.class);
    }

    @Override
    public Page<HppStrategyDTO> findPage(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String strategyName = formParams.get("strategyName") == null ? "" : formParams.get("strategyName").toString();
        String status = formParams.get("status") == null ? "" : formParams.get("status").toString();
        String account = formParams.get("account") == null ? "" : formParams.get("account").toString();
        String mobile = formParams.get("mobile") == null ? "" : formParams.get("mobile").toString();

        User currentUser=userService.getUserWithAuthorities();
        if(currentUser==null)return null;

        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" SELECT\n" +
                "\ts.c_id,\n" +
                "\ts.c_strategy_name,\n" +
                "\ts.c_strategy_name2,\n" +
                "\ts.c_strategy_text,\n" +
                "\ts.d_create_at,\n" +
                "\ts.d_update_at,\n" +
                "\ts.c_account,\n" +
                "\ts.c_password,\n" +
                "\ts.c_mobile,\n" +
                "\ts.c_platform,\n" +
                "\ts.c_server,\n" +
                "\ts.c_trade_type,\n" +
                "\ts.c_line_flag,\n" +
                "\ts.c_activity_flag,\n" +
                "\ts.c_activity_title,\n" +
                "\ts.c_activity_text,\n" +
                "\tIFNULL(sd.i_month_profit, 0) AS i_income_rate,\n" +
                "\tIFNULL(count(o.c_strategy_id), 0) AS follow_num");

        StringBuilder cond = new StringBuilder(" from\n" +
                "\tt_hpp_strategy s\n" +
                "JOIN t_hpp_strategy_data sd ON sd.c_account = s.c_account\n" +
                "LEFT JOIN t_hpp_strategy_order o ON s.c_id = o.c_strategy_id\n" +
                "AND o.c_type = 'IN'\n" +
                "AND o.c_status = 'PASSED'\n" +
                "AND o.c_state = 'EFFECTIVE'\n" +
                "WHERE\n" +
                "\ts.c_status = '"+DevplatformConstants.STRATEGY_APPLY_STATUS_PASSED+"'\n" );

        //非(超级管理员或客服)
        /*if(!(currentUser.getDepartment().equals(EnumRole.ADMIN.value())||currentUser.getDepartment().equals(EnumRole.SERVICE.value()))){
            cond.append(" and s.c_user_id='" + currentUser.getId() + "'");
        }*/

        if (StringUtils.isNotBlank(strategyName)) {
            cond.append(" and s.c_strategy_name like '%"+strategyName+"%' ");
        }

        if (StringUtils.isNotBlank(status)) {
            cond.append(" and s.c_status like '%"+status+"%' ");
        }

        if (StringUtils.isNotBlank(account)) {
            cond.append(" and s.c_account like '%"+account+"%' ");
        }

        if (StringUtils.isNotBlank(mobile)) {
            cond.append(" and s.c_mobile like '%"+mobile+"%' ");
        }

        cond.append("group by s.c_id");

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= "s."+ ReflectUtils.getColumnName(HppStrategy.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<HppStrategyDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy, page_number, page_size, HppStrategyDTO.class);
        return page;
    }

    @Override
    public HppStrategyDTO save(HppStrategyDTO hppStrategyDTO) {
        if(StringUtils.isBlank(hppStrategyDTO.getId())){
            //添加
            Date now = new Date();
            hppStrategyDTO.setCreateAt(now);
            hppStrategyDTO.setUpdateAt(now);
            User currentUser=userService.getUserWithAuthorities();
            hppStrategyDTO.setUserId(currentUser.getId());
            hppStrategyDTO.setMobile(currentUser.getMobile());

            User user = userService.findByDepartment(EnumRole.STRATEGY.value());
            //发送任务通知
            if(user != null){
                noticeToCharge(user.getMobile());
            }
        }else{
            // 修改
            HppStrategy currentHppStrategy=hppStrategyRepository.findOne(hppStrategyDTO.getId());
            hppStrategyDTO.setCreateAt(currentHppStrategy.getCreateAt());
            hppStrategyDTO.setUpdateAt(new Date());
            hppStrategyDTO.setUserId(currentHppStrategy.getUserId());
            hppStrategyDTO.setMobile(currentHppStrategy.getMobile());
            hppStrategyDTO.setStatus(currentHppStrategy.getStatus() == null ? DevplatformConstants.STRATEGY_APPLY_STATUS_APPLYING : currentHppStrategy.getStatus());
        }
        hppStrategyDTO.setStatus(DevplatformConstants.STRATEGY_APPLY_STATUS_APPLYING);

        return hppStrategyMapper.toDto(hppStrategyRepository.save(hppStrategyMapper.toEntity(hppStrategyDTO)));
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
    public HppStrategyDTO edit(HppStrategyDTO hppStrategyDTO) {
        HppStrategy hppStrategy = hppStrategyRepository.findOne(hppStrategyDTO.getId());
        hppStrategy.setStrategyName(hppStrategyDTO.getStrategyName());
        hppStrategy.setStrategyName2(hppStrategyDTO.getStrategyName2());
        hppStrategy.setStrategyText(hppStrategyDTO.getStrategyText());
        hppStrategy.setPlatform(hppStrategyDTO.getPlatform());
        hppStrategy.setServer(hppStrategyDTO.getServer());
        hppStrategy.setAccount(hppStrategyDTO.getAccount());
        hppStrategy.setPassword(hppStrategyDTO.getPassword());
        hppStrategy.setTradeType(hppStrategyDTO.getTradeType());
        return hppStrategyMapper.toDto(hppStrategyRepository.save(hppStrategy));
    }

    @Override
    public HashMap<String, Object> checkHppStrategyDTO(HppStrategyDTO hppStrategyDTO) {
        HashMap<String,Object> retMap =new HashMap<>();

        if(StringUtils.isBlank(hppStrategyDTO.getStrategyName())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入策略名");
            return retMap;
        }
        if(hppStrategyRepository.findByStrategyNameEqualsAndStatusEquals(hppStrategyDTO.getStrategyName(), DevplatformConstants.STRATEGY_APPLY_STATUS_PASSED)!=null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "策略名已存在");
            return retMap;
        }
        if(StringUtils.isBlank(hppStrategyDTO.getAccount())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入账号");
            return retMap;
        }

        if(StringUtils.isBlank(hppStrategyDTO.getTradeType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入交易方式");
            return retMap;
        }

        if(StringUtils.isBlank(hppStrategyDTO.getPlatform())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入交易平台");
            return retMap;
        }

        if(StringUtils.isBlank(hppStrategyDTO.getServer())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入服务器");
            return retMap;
        }

        if(StringUtils.isBlank(hppStrategyDTO.getPassword())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入密码");
            return retMap;
        }

        if(hppStrategyOrderService.existFollow(hppStrategyDTO.getAccount(),hppStrategyDTO.getPassword())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "该账号跟单或申请中,不能入驻!");
            return retMap;
        }

        if(exist(hppStrategyDTO.getAccount())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "策略已被申请");
            return retMap;
        }
        User currentUser=userService.getUserWithAuthorities();
        if(currentUser==null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录...");
            return retMap;
        }
       /* if(userService.findOne(userId)==null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录...");
            return retMap;
        }*/
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    /**
     * 判断账号是否已有记录
     * @param account
     * @return
     */
    private boolean exist(String account) {
        StringBuffer sql=new StringBuffer("SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\tt_hpp_strategy\n" +
                "WHERE\n" +
                "\tc_account = '"+account+"'\n" +
                "AND (\n" +
                "\tc_status = '"+ DevplatformConstants.STRATEGY_APPLY_STATUS_PASSED+"'\n" +
                "\tOR c_status = '"+ DevplatformConstants.STRATEGY_APPLY_STATUS_APPLYING+"'\n" +
                ")");
        List list=baseDao.findListBySql(sql.toString(),HppStrategyDTO.class);
        if(list!=null&&list.size()>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public HppStrategyDTO approve(HashMap<String, Object> params) {
        String strategyId = params.get("strategyId") == null ? "" : params.get("strategyId").toString();
        String status = params.get("status") == null ? "" : params.get("status").toString();

        HppStrategy hppStrategy=hppStrategyRepository.findOne(strategyId);

        if(status.equals(DevplatformConstants.STRATEGY_APPLY_STATUS_PASSED)){
            //通过
            hppStrategy.setStatus(DevplatformConstants.STRATEGY_APPLY_STATUS_PASSED);
            hppStrategy.setLineFlag("Y"); //上线
            hppStrategy.setActivityFlag("Y");//允许跟单
            //保存策略具体数据.(mt4做这一步)..
            if(hppStrategyDataService.findByAccount(hppStrategy.getAccount())==null){
                HppStrategyDataDTO hppStrategyDataDTO =new HppStrategyDataDTO();
                hppStrategyDataDTO.setAccount(hppStrategy.getAccount());
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
                hppStrategyDataService.save(hppStrategyDataDTO);
            }

        }else{
            //拒绝
            hppStrategy.setStatus(DevplatformConstants.STRATEGY_APPLY_STATUS_REJECT);
            hppStrategy.setLineFlag("N"); //下线
            hppStrategy.setActivityFlag("N");//不允许跟单
        }
        User currentUser=userService.getUserWithAuthorities();
        hppStrategy.setApproveId(currentUser.getId());
        hppStrategy.setApproveAt(new Date());
        return hppStrategyMapper.toDto(hppStrategyRepository.save(hppStrategy));
    }

    @Override
    public HashMap<String, Object> checkApprove(HashMap<String, Object> params) {
        HashMap<String,Object> retMap =new HashMap<>();

        String strategyId = params.get("strategyId") == null ? "" : params.get("strategyId").toString();
        String status = params.get("status") == null ? "" : params.get("status").toString();
        User currentUser=userService.getUserWithAuthorities();
        if(currentUser==null||!(currentUser.getDepartment().equals(EnumRole.ADMIN.value())||currentUser.getDepartment().equals(EnumRole.SERVICE.value()))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录有效账号");
            return retMap;
        }
        HppStrategy hppStrategy=hppStrategyRepository.findOne(strategyId);
        if(hppStrategy==null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择处理单据");
            return retMap;
        }else{
            String currentStatus=hppStrategy.getStatus();
            if(currentStatus.equals(DevplatformConstants.STRATEGY_APPLY_STATUS_PASSED)||currentStatus.equals(DevplatformConstants.STRATEGY_APPLY_STATUS_REJECT)){
                retMap.put("statusCode", ResponseResult.FAIL_CODE);
                retMap.put("msg", "已被处理");
                return retMap;
            }
        }
        if(!status.equals(DevplatformConstants.STRATEGY_APPLY_STATUS_PASSED)&&!status.equals(DevplatformConstants.STRATEGY_APPLY_STATUS_REJECT)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择处理类型");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public void delete(String id) {
        HppStrategy hppStrategy = hppStrategyRepository.findOne(id);
        hppStrategy.setStatus(DevplatformConstants.STRATEGY_APPLY_STATUS_APPLYING);
        hppStrategy.setUpdateAt(new Date());
        hppStrategy.setApproveId(userService.getUserWithAuthorities().getId());
        hppStrategyRepository.save(hppStrategy);
        /*HppStrategyDataDTO hppStrategyDataDTO = hppStrategyDataService.findByAccount(hppStrategy.getAccount());
        hppStrategyDataService.delete(hppStrategyDataDTO.getId());
        hppStrategyRepository.delete(id);*/
    }

    @Override
    public void deleteAll(String id) {
        HppStrategy hppStrategy = hppStrategyRepository.findOne(id);
        HppStrategyDataDTO hppStrategyDataDTO = hppStrategyDataService.findByAccount(hppStrategy.getAccount());
        if(hppStrategyDataDTO != null){
            hppStrategyDataService.delete(hppStrategyDataDTO.getId());
        }
        hppStrategyRepository.delete(id);
    }

    @Override
    public Long findOrderCountById(String id) {
        return hppStrategyOrderService.findByStrategyId(id);
    }

    @Override
    public void downOrUpLine(String id) {
        HppStrategy hppStrategy = hppStrategyRepository.findOne(id);
        String delFlag = hppStrategy.getLineFlag()==null ? "N":hppStrategy.getLineFlag();
        if(delFlag.equals("Y")){
            hppStrategy.setLineFlag("N");
        }else{
            hppStrategy.setLineFlag("Y");
        }
        hppStrategyRepository.save(hppStrategy);
    }

    @Override
    public Page<HppStrategyDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String strategyName = formParams.get("strategyName") == null ? "" : formParams.get("strategyName").toString();
        String status = formParams.get("status") == null ? "" : formParams.get("status").toString();
        String account = formParams.get("account") == null ? "" : formParams.get("account").toString();
        String mobile = formParams.get("mobile") == null ? "" : formParams.get("mobile").toString();

        User currentUser=userService.getUserWithAuthorities();
        if(currentUser==null)return null;

        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" SELECT s.*,IFNULL(u.first_name,'') approve_name,IFNULL(u2.first_name,'') user_name   ");

        StringBuilder cond = new StringBuilder(" from t_hpp_strategy s left join jhi_user u on s.c_approve_id=u.id LEFT JOIN jhi_user u2 on u2.id=s.c_user_id where 1=1 ");

        //非(超级管理员或客服)
        if(!(currentUser.getDepartment().equals(EnumRole.ADMIN.value())||currentUser.getDepartment().equals(EnumRole.SERVICE.value()))){
            cond.append(" and s.c_user_id='" + currentUser.getId() + "'");
        }


        if (StringUtils.isNotBlank(strategyName)) {
            cond.append(" and s.c_strategy_name like '%"+strategyName+"%' ");
        }

        if (StringUtils.isNotBlank(status)) {
            cond.append(" and s.c_status like '%"+status+"%' ");
        }

        if (StringUtils.isNotBlank(account)) {
            cond.append(" and s.c_account like '%"+account+"%' ");
        }

        if (StringUtils.isNotBlank(mobile)) {
            cond.append(" and s.c_mobile like '%"+mobile+"%' ");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= "s."+ ReflectUtils.getColumnName(HppStrategy.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<HppStrategyDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy, page_number, page_size, HppStrategyDTO.class);
        return page;
    }

    @Override
    public void outStrategy(HashMap<String,Object> params) {
        String id = (params.get("id") == null ? "" : params.get("id").toString());
        String activityTitle = (params.get("activityTitle") == null ? "" : params.get("activityTitle").toString());
        String activityText = (params.get("activityText") == null ? "" : params.get("activityText").toString());

        HppStrategy hppStrategy = hppStrategyRepository.findOne(id);
        if(hppStrategy != null) {
            if(hppStrategy.getActivityFlag() == null || hppStrategy.getActivityFlag().equals("N")){
                hppStrategy.setActivityFlag("Y");
                hppStrategy.setActivityTitle(null);
                hppStrategy.setActivityText(null);
            }else{
                hppStrategy.setActivityFlag("N");
                hppStrategy.setActivityTitle(activityTitle);
                hppStrategy.setActivityText(activityText);
                pushNotice(id,activityTitle,activityText);
            }
            hppStrategyRepository.save(hppStrategy);
        }
    }

    private void pushNotice(String strategyId,String activityTitle,String activityText){
        String sql = "  SELECT\n" +
                "\tu.c_cid,u.c_phone\n" +
                " FROM\n" +
                "\tt_hpp_strategy_order o\n" +
                "join t_hpp_mobile_user u on o.c_mobile_num=u.c_phone\n" +
                "WHERE\n" +
                "\to.c_type = 'IN'\n" +
                "AND o.c_status = 'PASSED'\n" +
                "AND o.c_state = 'EFFECTIVE'\n" +
                "AND o.c_strategy_id = '" + strategyId + "'\n" +
                "GROUP BY u.c_cid ";
        List<User> list = baseDao.findListBySql(sql, User.class);


        if (list != null && list.size() > 0) {
            HppNoticeDTO noticeDTO = new HppNoticeDTO();
            noticeDTO.setType(activityTitle);
            noticeDTO.setContext(activityText);
            noticeDTO = hppNoticeService.createHppNoticeDTO(noticeDTO);

            HppSendNoticeDTO hppSendNoticeDTO = new HppSendNoticeDTO();
            hppSendNoticeDTO.setNoticeId(noticeDTO.getId());
            hppSendNoticeDTO.setUserList(list);
            hppSendNoticeDTO.setType(DevplatformConstants.DEFAULT_NOTICE);
            hppNoticeService.sendNotice(hppSendNoticeDTO);
        }
    }
}

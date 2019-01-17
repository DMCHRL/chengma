package com.chengma.devplatform.service.impl;

import com.alibaba.fastjson.JSON;
import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.PayCommonUtil;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.config.WxPayProperties;
import com.chengma.devplatform.domain.HppCourse;
import com.chengma.devplatform.domain.HppNews;
import com.chengma.devplatform.domain.HppTrainApply;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppTrainApplyRepository;
import com.chengma.devplatform.repository.WxOrderRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.HppTrainApplyMapper;
import com.chengma.devplatform.service.mapper.WxOrderMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.SortedMap;


@Service
@Transactional
public class HppTrainApplyServiceImpl implements HppTrainApplyService {


    private final HppTrainApplyRepository hppTrainApplyRepository;

    private final HppTrainApplyMapper hppTrainApplyMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private HppTrainService hppTrainService;

    @Autowired
    private UserService userService;

    @Autowired
    private HppCourseMealService hppCourseMealService;

    @Autowired
    private HppCourseService hppCourseService;

    @Autowired
    private CourseMealService courseMealService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private HppMobileValidateService hppMobileValidateService;



    public HppTrainApplyServiceImpl( HppTrainApplyRepository hppTrainApplyRepository,HppTrainApplyMapper hppTrainApplyMapper){
        this.hppTrainApplyRepository=hppTrainApplyRepository;
        this.hppTrainApplyMapper=hppTrainApplyMapper;
    }
    
    @Override
    public Page<HppTrainApplyDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String trainName = formParams.get("trainName") == null ? "" : formParams.get("trainName").toString();
        String phone = formParams.get("phone") == null ? "" : formParams.get("phone").toString();
        String username = formParams.get("username") == null ? "" : formParams.get("username").toString();
        String status = formParams.get("status") == null ? "" : formParams.get("status").toString();
        String email = formParams.get("email") == null ? "" : formParams.get("email").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();
        String startTime = formParams.get("startTime") == null ? "" : formParams.get("startTime").toString();
        String endTime = formParams.get("endTime") == null ? "" : formParams.get("endTime").toString();
        String payType = formParams.get("payType") == null ? "" : formParams.get("payType").toString();

        StringBuilder column = new StringBuilder("select t.* ");

        StringBuilder cond = new StringBuilder(" from t_hpp_train_apply t where 1=1");

        //非(超级管理员或客服)
       /* User currentUser=userService.getUserWithAuthorities();
        if(!(currentUser.getDepartment().equals(EnumRole.ADMIN.value())||currentUser.getDepartment().equals(EnumRole.SERVICE.value()))){
            cond.append(" and c_user_id='"+currentUser.getId()+"'");
        }*/
        if(StringUtils.isNotBlank(trainName)){
            cond.append(" and c_train_name like '%"+trainName+"%'");
        }

        if(StringUtils.isNotBlank(phone)){
            cond.append(" and c_phone like '%"+phone+"%'");
        }

        if(StringUtils.isNotBlank(username)){
            cond.append(" and c_username like '%"+username+"%'");
        }

        if(StringUtils.isNotBlank(status)){
            cond.append(" and c_status ='"+status+"'");
        }

        if(StringUtils.isNotBlank(email)){
            cond.append(" and c_email like '%"+email+"%'");
        }

        if(StringUtils.isNotBlank(payType)){
            cond.append(" and t.c_pay_type ='"+payType+"'");
        }

        if(StringUtils.isNotBlank(startTime)){
            cond.append(" and TO_DAYS(t.d_create_at)>=TO_DAYS('"+startTime+"') ");
        }

        if(StringUtils.isNotBlank(endTime)){
            cond.append(" and TO_DAYS(t.d_create_at)<=TO_DAYS('"+endTime+"') ");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= "t."+ReflectUtils.getColumnName(HppTrainApply.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<HppTrainApplyDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, HppTrainApplyDTO.class);
        return page;
    }

    @Override
    public HppTrainApplyDTO save(HppTrainApplyDTO hppTrainApplyDTO) {
        return hppTrainApplyMapper.toDto(hppTrainApplyRepository.save(hppTrainApplyMapper.toEntity(hppTrainApplyDTO)));
    }

    @Override
    public  HashMap<String,Object> createHppTrainApplyDTO(HttpServletRequest request , HppTrainApplyDTO hppTrainApplyDTO) {

        String body =DevplatformConstants.BODY_TYPE_COURSE;
        String id = hppTrainApplyDTO.getCourseMealId();
        //培训课程报名表
        hppTrainApplyDTO.setCreateAt(new Date());
        if(hppTrainApplyDTO.getPayType().equals(DevplatformConstants.PAY_TYPE_INTEGRAL)){
            hppTrainApplyDTO.setPayType(DevplatformConstants.PAY_TYPE_MIX);
        }
        hppTrainApplyDTO.setStatus(DevplatformConstants.PAY_STATUS_N); //未支付
        HppCourseDTO hppCourseDTO = hppCourseService.findByCourseMealId(id);
        if(hppCourseDTO != null){
            hppTrainApplyDTO.setTrainName(hppCourseDTO.getTrainName());
        }
        HppCourseMealDTO hppCourseMealDTO = hppCourseMealService.findOne(id);
        if(hppCourseMealDTO != null){
            hppTrainApplyDTO.setMealName(hppCourseMealDTO.getName());
            hppTrainApplyDTO.setMealInclude(hppCourseMealDTO.getInclude());
            hppTrainApplyDTO.setMealPrice(hppCourseMealDTO.getPrice());
        }

        HashMap<String,Object> params = new HashMap<>();
        params.put("id",id);
        params.put("body",body);
        params.put("payType",hppTrainApplyDTO.getPayType());
        HashMap<String,Object> result = wxPayService.checkPay(params);
        if(!ResponseResult.SUCCESS_CODE.equals(result.get("statusCode"))){
            return result;
        }
        User user = userService.findByDepartment(EnumRole.TEACH.value());
        if(user != null){
            noticeToCharge(user.getMobile());
        }
        result =  wxPayService.getWXPay(request,params);
        WxOrderDTO order =(WxOrderDTO)result.get("data");
        hppTrainApplyDTO.setOrderNum(order.getOutTradeNo());
        this.save(hppTrainApplyDTO);
        return result;
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
    public HppTrainApplyDTO findOne(String id) {
        return hppTrainApplyMapper.toDto(hppTrainApplyRepository.findOne(id));
    }

    @Override
    public void delete(String id) {
        hppTrainApplyRepository.delete(id);
    }

    @Override
    public HashMap<String, Object> checkHppTrainApply(HppTrainApplyDTO hppTrainApplyDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(hppTrainApplyDTO.getPhone())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入联系方式");
            return retMap;
        }
        if(StringUtils.isBlank(hppTrainApplyDTO.getEmail())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入邮箱");
            return retMap;
        }
        if(StringUtils.isBlank(hppTrainApplyDTO.getUsername())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入姓名");
            return retMap;
        }

        CourseMealDTO courseMealDTO = courseMealService.findByCourseMealId(hppTrainApplyDTO.getCourseMealId());
        HppCourseDTO hppCourseDTO = null;
        if(courseMealDTO != null){
            hppCourseDTO = hppCourseService.findOne(courseMealDTO.getCourseId());
        }

        if(StringUtils.isBlank(hppTrainApplyDTO.getCourseMealId())||hppCourseDTO == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择培训");
            return retMap;
        }
       /* if(this.findByPhoneAndCourseMealId(hppTrainApplyDTO.getPhone(),hppTrainApplyDTO.getCourseMealId())!=null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "该手机号已报名");
            return retMap;
        }*/

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public Long countByCourseMealId(String courseMealId) {
        return hppTrainApplyRepository.countByCourseMealId(courseMealId);
    }

    @Override
    public HppTrainApplyDTO findByOutTradeNo(String outTradeNo) {
        return hppTrainApplyMapper.toDto(hppTrainApplyRepository.findByOrderNumEquals(outTradeNo));
    }

    @Override
    public void deleteByOutTradeNo(String outTradeNo) {
        hppTrainApplyRepository.deleteByOrderNumEquals(outTradeNo);
    }
}

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
import com.chengma.devplatform.domain.*;
import com.chengma.devplatform.repository.HppExamApplyRepository;
import com.chengma.devplatform.repository.WxOrderRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.HppExamApplyMapper;
import com.chengma.devplatform.service.mapper.WxOrderMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.SortedMap;


@Service
@Transactional
public class HppExamApplyServiceImpl implements HppExamApplyService {


    private final HppExamApplyRepository hppExamApplyRepository;

    private final HppExamApplyMapper hppExamApplyMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private HppExamService hppExamService;

    @Autowired
    private UserService userService;

    @Autowired
    private PayCommonUtil payCommonUtil;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private HppCourseMealService hppCourseMealService;

    @Autowired
    private HppCourseService hppCourseService;

    @Autowired
    private CourseMealService courseMealService;

    @Autowired
    private HppMobileValidateService hppMobileValidateService;


    public HppExamApplyServiceImpl(HppExamApplyRepository hppExamApplyRepository, HppExamApplyMapper hppExamApplyMapper){
        this.hppExamApplyRepository=hppExamApplyRepository;
        this.hppExamApplyMapper=hppExamApplyMapper;
    }
    
    @Override
    public Page<HppExamApplyDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String examName = formParams.get("examName") == null ? "" : formParams.get("examName").toString();
        String phone = formParams.get("phone") == null ? "" : formParams.get("phone").toString();
        String username = formParams.get("username") == null ? "" : formParams.get("username").toString();
        String email = formParams.get("email") == null ? "" : formParams.get("email").toString();
        String status = formParams.get("status") == null ? "" : formParams.get("status").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();
        String startTime = formParams.get("startTime") == null ? "" : formParams.get("startTime").toString();
        String endTime = formParams.get("endTime") == null ? "" : formParams.get("endTime").toString();
        String payType = formParams.get("payType") == null ? "" : formParams.get("payType").toString();

        StringBuilder column = new StringBuilder("select e.* ");

        StringBuilder cond = new StringBuilder(" from t_hpp_exam_apply e where 1=1 ");

        //非(超级管理员或客服)
       /* User currentUser=userService.getUserWithAuthorities();
        if(!(currentUser.getDepartment().equals(EnumRole.ADMIN.value())||currentUser.getDepartment().equals(EnumRole.SERVICE.value()))){
            cond.append(" and c_user_id='"+currentUser.getId()+"'");
        }*/
        if(StringUtils.isNotBlank(examName)){
            cond.append(" and e.c_exam_name like '%"+examName+"%'");
        }

        if(StringUtils.isNotBlank(phone)){
            cond.append(" and e.c_phone like '%"+phone+"%'");
        }

        if(StringUtils.isNotBlank(username)){
            cond.append(" and e.c_username like '%"+username+"%'");
        }

        if(StringUtils.isNotBlank(email)){
            cond.append(" and e.c_email like '%"+email+"%'");
        }

        if(StringUtils.isNotBlank(status)){
            cond.append(" and e.c_status ='"+status+"'");
        }

        if(StringUtils.isNotBlank(payType)){
            cond.append(" and e.c_pay_type ='"+payType+"'");
        }

        if(StringUtils.isNotBlank(startTime)){
            cond.append(" and TO_DAYS(e.d_create_at)>=TO_DAYS('"+startTime+"') ");
        }

        if(StringUtils.isNotBlank(endTime)){
            cond.append(" and TO_DAYS(e.d_create_at)<=TO_DAYS('"+endTime+"') ");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= "e."+ReflectUtils.getColumnName(HppExamApply.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<HppExamApplyDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, HppExamApplyDTO.class);
        return page;
    }

    @Override
    public HppExamApplyDTO save(HppExamApplyDTO hppExamApplyDTO) {
        return hppExamApplyMapper.toDto(hppExamApplyRepository.save(hppExamApplyMapper.toEntity(hppExamApplyDTO)));
    }

    @Override
    public  HashMap<String,Object> createHppExamApplyDTO(HttpServletRequest request , HppExamApplyDTO hppExamApplyDTO) {

        String body = DevplatformConstants.BODY_TYPE_EXAM;
        String id = hppExamApplyDTO.getCourseMealId();
        //考证报名表
        hppExamApplyDTO.setCreateAt(new Date());
        if(hppExamApplyDTO.getPayType().equals(DevplatformConstants.PAY_TYPE_INTEGRAL)){
            hppExamApplyDTO.setPayType(DevplatformConstants.PAY_TYPE_MIX);
        }
        hppExamApplyDTO.setStatus(DevplatformConstants.PAY_STATUS_N); //未支付
        HppExamDTO hppExamDTO = hppExamService.findByCourseMealId(id);
        if(hppExamDTO != null){
            hppExamApplyDTO.setExamName(hppExamDTO.getTrainName());
        }
        HppCourseMealDTO hppCourseMealDTO = hppCourseMealService.findOne(id);
        if(hppCourseMealDTO != null){
            hppExamApplyDTO.setMealName(hppCourseMealDTO.getName());
            hppExamApplyDTO.setMealInclude(hppCourseMealDTO.getInclude());
            hppExamApplyDTO.setMealPrice(hppCourseMealDTO.getPrice());
        }

        HashMap<String,Object> params = new HashMap<>();
        params.put("id",id);
        params.put("body",body);
        params.put("payType",hppExamApplyDTO.getPayType());
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
        hppExamApplyDTO.setOrderNum(order.getOutTradeNo());
        this.save(hppExamApplyDTO);
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
    public HppExamApplyDTO findOne(String id) {
        return hppExamApplyMapper.toDto(hppExamApplyRepository.findOne(id));
    }

    @Override
    public void delete(String id) {
        hppExamApplyRepository.delete(id);
    }

    @Override
    public HashMap<String, Object> checkHppExamApply(HppExamApplyDTO hppExamApplyDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(hppExamApplyDTO.getPhone())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入联系方式");
            return retMap;
        }
        if(StringUtils.isBlank(hppExamApplyDTO.getEmail())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入邮箱");
            return retMap;
        }
        if(StringUtils.isBlank(hppExamApplyDTO.getUsername())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入姓名");
            return retMap;
        }

        CourseMealDTO courseMealDTO = courseMealService.findByCourseMealId(hppExamApplyDTO.getCourseMealId());
        HppExamDTO hppExamDTO = null;
        if(courseMealDTO != null){
            hppExamDTO = hppExamService.findOne(courseMealDTO.getCourseId());
        }

        if(StringUtils.isBlank(hppExamApplyDTO.getCourseMealId())||hppExamDTO == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择考试");
            return retMap;
        }
       /* if(this.findByPhoneAndCourseMealId(hppExamApplyDTO.getPhone(),hppExamApplyDTO.getCourseMealId())!=null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "该手机号已报名");
            return retMap;
        }*/

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public Long countByCourseMealId(String courseMealId) {
        return hppExamApplyRepository.countByCourseMealId(courseMealId);
    }

    @Override
    public HppExamApplyDTO findByOutTradeNo(String outTradeNo) {
        return hppExamApplyMapper.toDto(hppExamApplyRepository.findByOrderNumEquals(outTradeNo));
    }

    @Override
    public void deleteByOutTradeNo(String outTradeNo) {
        hppExamApplyRepository.deleteByOrderNumEquals(outTradeNo);
    }
}

package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.*;
import com.chengma.devplatform.repository.HppNoticeRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.HppNoticeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/18.
 */
@Service
@Transactional
public class HppNoticeServiceImpl implements HppNoticeService {

    private final HppNoticeRepository hppNoticeRepository;

    private final HppNoticeMapper hppNoticeMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private HppMobileUserService hppMobileUserService;

    @Autowired
    private HppNoticeSignService hppNoticeSignService;

    @Autowired
    private AppPushService appPushService;



    public HppNoticeServiceImpl(HppNoticeRepository hppNoticeRepository, HppNoticeMapper hppNoticeMapper){
        this.hppNoticeRepository=hppNoticeRepository;
        this.hppNoticeMapper=hppNoticeMapper;
    }

    @Override
    public Page<HppNoticeDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String type = formParams.get("type") == null ? "" : formParams.get("type").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select *  ");

        StringBuilder cond = new StringBuilder(" from t_hpp_notice where 1=1 and c_del_flag ='N'and (ISNULL(c_object) or c_object !='"+DevplatformConstants.DEFAULT_NOTICE+"')");

        //非超级管理员
        /*User currentUser=userService.getUserWithAuthorities();
        if(!currentUser.getDepartment().equals(EnumRole.ADMIN.value())){
            cond.append(" and c_create_by='"+currentUser.getId()+"'");
        }*/

        if(StringUtils.isNotBlank(type)){
            cond.append(" and c_type like '%"+type+"%' ");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(HppNotice.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<HppNoticeDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy,page_number, page_size, HppNoticeDTO.class);
        return page;
    }

    @Override
    public HppNoticeDTO save(HppNoticeDTO hppNoticeDTO) {
        return hppNoticeMapper.toDto(hppNoticeRepository.save(hppNoticeMapper.toEntity(hppNoticeDTO)));
    }

    @Override
    public HppNoticeDTO createHppNoticeDTO(HppNoticeDTO hppNoticeDTO) {
        //添加
        if(StringUtils.isBlank(hppNoticeDTO.getId())){
            Date now = new Date();
            hppNoticeDTO.setCreateTime(now);
            hppNoticeDTO.setUpdateTime(now);
        }else{
            //修改
           HppNotice hppNotice = hppNoticeRepository.findOne(hppNoticeDTO.getId());
            hppNoticeDTO.setCreateTime(hppNotice.getCreateTime());
            hppNoticeDTO.setUpdateTime(new Date());
        }
        User user = userService.getUserWithAuthorities();
        hppNoticeDTO.setCreateBy(user.getId());
        hppNoticeDTO.setDelFlag("N");
        hppNoticeDTO.setRevokeFlag("N");
        hppNoticeDTO.setSendFlag("N");

        return hppNoticeMapper.toDto(hppNoticeRepository.save(hppNoticeMapper.toEntity(hppNoticeDTO)));
    }

    @Override
    public HashMap<String, Object> sendNotice(HppSendNoticeDTO hppSendNoticeDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(hppSendNoticeDTO == null||hppSendNoticeDTO.getNoticeId() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择通知");
            return retMap;
        }
        HppNotice hppNotice = hppNoticeRepository.findOne(hppSendNoticeDTO.getNoticeId());
        if(hppNotice == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择通知");
            return retMap;
        }
        if(StringUtils.isBlank(hppSendNoticeDTO.getType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择通知对象");
            return retMap;
        }

        List<User> list =null;
        //通知全体
        if(hppSendNoticeDTO.getType().equals(DevplatformConstants.WHOLE_NOTICE)){
            hppNotice.setObject(DevplatformConstants.WHOLE_NOTICE);
            list = userService.findList();
        }/*else if(hppSendNoticeDTO.getType().equals(DevplatformConstants.FOLLOW_NOTICE)){
            //跟单用户
            hppNotice.setObject(DevplatformConstants.FOLLOW_NOTICE);
            list =hppMobileUserService.findWithFollowFlag();
        }else if(hppSendNoticeDTO.getType().equals(DevplatformConstants.BUY_NOTICE)){
            //消费用户
            hppNotice.setObject(DevplatformConstants.BUY_NOTICE);
            list =hppMobileUserService.findWithBuyFlag();
        }*/else if(hppSendNoticeDTO.getType().equals(DevplatformConstants.MULTIPLE_NOTICE)){
            //自选用户
            hppNotice.setObject(DevplatformConstants.MULTIPLE_NOTICE);
            list =hppSendNoticeDTO.getUserList();
        }else if(hppSendNoticeDTO.getType().equals(DevplatformConstants.DEFAULT_NOTICE)){
            //自动通知
            hppNotice.setObject(DevplatformConstants.DEFAULT_NOTICE);
            list =hppSendNoticeDTO.getUserList();
        }

        if(list == null || list.size()==0){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "通知对象为空");
            return retMap;
        }

        hppNotice.setSendFlag("Y");
        hppNotice.setSendTime(new Date());

        //添加提醒
        for(User user : list){
            HppNoticeSignDTO hppNoticeSignDTO = new HppNoticeSignDTO();
            hppNoticeSignDTO.setMail(user.getLogin());
            hppNoticeSignDTO.setNoticeId(hppSendNoticeDTO.getNoticeId());
            hppNoticeSignDTO.setStatus("N");
            hppNoticeSignService.save(hppNoticeSignDTO);
            hppNoticeRepository.save(hppNotice);
        }

        /*//发送到通知栏
        if(hppSendNoticeDTO.getType().equals(DevplatformConstants.WHOLE_NOTICE)){
            sendToField(hppSendNoticeDTO,hppNotice,null);
        }else{
            sendToField(hppSendNoticeDTO,hppNotice,list);
        }*/

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    private void sendToField(HppSendNoticeDTO hppSendNoticeDTO,HppNotice hppNotice,List<HppMobileUserDTO> list){
        //发送
        if(hppSendNoticeDTO.getType().equals(DevplatformConstants.WHOLE_NOTICE)){
            if(StringUtils.isNotBlank(hppNotice.getUrl())){
                appPushService.sendAllWithUrl(hppNotice.getType(),hppNotice.getContext(),hppNotice.getUrl());
            }else{
                appPushService.sendAllNoUrl(hppNotice.getType(),hppNotice.getContext());
            }
        }else{
            for(HppMobileUserDTO hppMobileUserDTO : list) {
                if (StringUtils.isNotBlank(hppMobileUserDTO.getCid())) {
                    //发送到通知栏
                    if (StringUtils.isNotBlank(hppNotice.getUrl())) {
                        appPushService.sentToOneWithUrl(hppNotice.getType(), hppNotice.getContext(), hppMobileUserDTO.getCid(), hppNotice.getUrl());
                    } else {
                        appPushService.sentToOneNoUrl(hppNotice.getType(), hppNotice.getContext(), hppMobileUserDTO.getCid());
                    }
                }
            }
        }
    }

    @Override
    public void revokeNotice(String id) {
        HppNotice hppNotice = hppNoticeRepository.findOne(id);
        hppNotice.setSendFlag("N");
        hppNotice.setRevokeFlag("Y");
        hppNotice.setRevokeTime(new Date());
        hppNoticeSignService.deleteByNoticeId(id);
    }

    @Override
    public HashMap<String, Object> checkCreateHppNoticeDTO(HppNoticeDTO hppNoticeDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        if(hppNoticeDTO == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "消息不能为空");
            return retMap;
        }

        //已发送的不能修改
        if(hppNoticeDTO.getId() != null && hppNoticeRepository.findByIdEqualsAndSendFlagEquals(hppNoticeDTO.getId(),"Y")!=null ){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "消息已发送不能修改");
            return retMap;
        }

        if(StringUtils.isBlank(hppNoticeDTO.getType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入消息类型");
            return retMap;
        }

        if(StringUtils.isBlank(hppNoticeDTO.getContext())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入消息内容");
            return retMap;
        }



        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HppNoticeDTO findOne(String id) {
        return hppNoticeMapper.toDto(hppNoticeRepository.findOne(id));
    }

    @Override
    public List<HppNoticeDTO> findAll() {
        return hppNoticeMapper.toDto(hppNoticeRepository.findAll());
    }

    @Override
    public void delete(String id) {
        hppNoticeRepository.delete(id);
    }

    @Override
    public void deleteFlag(String id) {
        HppNotice hppNotice = hppNoticeRepository.findOne(id);
        if(hppNotice == null)return;
        hppNotice.setDelFlag("Y");
        hppNoticeRepository.save(hppNotice);
    }
}
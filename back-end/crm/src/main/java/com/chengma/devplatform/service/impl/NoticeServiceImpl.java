package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.Notice;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.domain.UserCid;
import com.chengma.devplatform.repository.NoticeRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.NoticeDTO;
import com.chengma.devplatform.service.dto.NoticeSignDTO;
import com.chengma.devplatform.service.dto.SendNoticeDTO;
import com.chengma.devplatform.service.dto.UserCidDTO;
import com.chengma.devplatform.service.mapper.NoticeMapper;
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
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository hppNoticeRepository;

    private final NoticeMapper hppNoticeMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCidService userCidService;


    @Autowired
    private NoticeSignService hppNoticeSignService;

    @Autowired
    private AppPushService appPushService;



    public NoticeServiceImpl(NoticeRepository hppNoticeRepository, NoticeMapper hppNoticeMapper){
        this.hppNoticeRepository=hppNoticeRepository;
        this.hppNoticeMapper=hppNoticeMapper;
    }

    @Override
    public Page<NoticeDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String type = formParams.get("type") == null ? "" : formParams.get("type").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select *  ");

        StringBuilder cond = new StringBuilder(" from t_notice where 1=1 and c_del_flag ='N'");

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
            String columnName= ReflectUtils.getColumnName(Notice.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<NoticeDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy,page_number, page_size, NoticeDTO.class);
        return page;
    }

    @Override
    public NoticeDTO save(NoticeDTO hppNoticeDTO) {
        return hppNoticeMapper.toDto(hppNoticeRepository.save(hppNoticeMapper.toEntity(hppNoticeDTO)));
    }

    @Override
    public NoticeDTO createNoticeDTO(NoticeDTO hppNoticeDTO) {
        //添加
        if(StringUtils.isBlank(hppNoticeDTO.getId())){
            Date now = new Date();
            hppNoticeDTO.setCreateTime(now);
            hppNoticeDTO.setUpdateTime(now);
        }else{
            //修改
           Notice hppNotice = hppNoticeRepository.findOne(hppNoticeDTO.getId());
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
    public HashMap<String, Object> sendNotice(SendNoticeDTO sendNoticeDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(sendNoticeDTO == null||sendNoticeDTO.getNoticeId() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择通知");
            return retMap;
        }
        Notice hppNotice = hppNoticeRepository.findOne(sendNoticeDTO.getNoticeId());
        if(hppNotice == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择通知");
            return retMap;
        }
        if(StringUtils.isBlank(sendNoticeDTO.getType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择通知对象");
            return retMap;
        }

        List<UserCidDTO> list =null;
        //通知全体
        if(sendNoticeDTO.getType().equals(DevplatformConstants.WHOLE_NOTICE)){
            hppNotice.setObject(DevplatformConstants.WHOLE_NOTICE);
            list = userCidService.findAll();
        }/*else if(sendNoticeDTO.getType().equals(DevplatformConstants.FOLLOW_NOTICE)){
            //跟单用户
            hppNotice.setObject(DevplatformConstants.FOLLOW_NOTICE);
            list =hppMobileUserService.findWithFollowFlag();
        }else if(sendNoticeDTO.getType().equals(DevplatformConstants.BUY_NOTICE)){
            //消费用户
            hppNotice.setObject(DevplatformConstants.BUY_NOTICE);
            list =hppMobileUserService.findWithBuyFlag();
        }else if(sendNoticeDTO.getType().equals(DevplatformConstants.MULTIPLE_NOTICE)){
            //自选用户
            hppNotice.setObject(DevplatformConstants.MULTIPLE_NOTICE);
            list =sendNoticeDTO.getUserCidDTOList();
        }else if(sendNoticeDTO.getType().equals(DevplatformConstants.DEFAULT_NOTICE)){
            //自动通知
            hppNotice.setObject(DevplatformConstants.DEFAULT_NOTICE);
            list =sendNoticeDTO.getUserCidDTOList();
        }*/

        if(list == null || list.size()==0){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "通知对象为空");
            return retMap;
        }

        hppNotice.setSendFlag("Y");
        hppNotice.setSendTime(new Date());

        //添加提醒
        for(UserCidDTO userCidDTO : list){
            NoticeSignDTO hppNoticeSignDTO = new NoticeSignDTO();
            hppNoticeSignDTO.setMobile(userCidDTO.getMobile());
            hppNoticeSignDTO.setNoticeId(sendNoticeDTO.getNoticeId());
            hppNoticeSignDTO.setStatus("N");
            hppNoticeSignService.save(hppNoticeSignDTO);
            hppNoticeRepository.save(hppNotice);
        }

        //发送到通知栏
        if(sendNoticeDTO.getType().equals(DevplatformConstants.WHOLE_NOTICE)){
            sendToField(sendNoticeDTO,hppNotice,null);
        }else{
            sendToField(sendNoticeDTO,hppNotice,list);
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    private void sendToField(SendNoticeDTO sendNoticeDTO,Notice hppNotice,List<UserCidDTO> list){
        //发送
        if(sendNoticeDTO.getType().equals(DevplatformConstants.WHOLE_NOTICE)){
            if(StringUtils.isNotBlank(hppNotice.getUrl())){
                appPushService.sendAllWithUrl(hppNotice.getType(),hppNotice.getContext(),hppNotice.getUrl());
            }else{
                appPushService.sendAllNoUrl(hppNotice.getType(),hppNotice.getContext());
            }
        }else{
            for(UserCidDTO userCidDTO : list) {
                if (StringUtils.isNotBlank(userCidDTO.getCid())) {
                    //发送到通知栏
                    if (StringUtils.isNotBlank(hppNotice.getUrl())) {
                        appPushService.sentToOneWithUrl(hppNotice.getType(), hppNotice.getContext(), userCidDTO.getCid(), hppNotice.getUrl());
                    } else {
                        appPushService.sentToOneNoUrl(hppNotice.getType(), hppNotice.getContext(), userCidDTO.getCid());
                    }
                }
            }
        }
    }

    @Override
    public void revokeNotice(String id) {
        Notice hppNotice = hppNoticeRepository.findOne(id);
        hppNotice.setSendFlag("N");
        hppNotice.setRevokeFlag("Y");
        hppNotice.setRevokeTime(new Date());
        hppNoticeSignService.deleteByNoticeId(id);
    }

    @Override
    public HashMap<String, Object> checkCreateNoticeDTO(NoticeDTO hppNoticeDTO) {
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
    public NoticeDTO findOne(String id) {
        return hppNoticeMapper.toDto(hppNoticeRepository.findOne(id));
    }

    @Override
    public List<NoticeDTO> findAll() {
        return hppNoticeMapper.toDto(hppNoticeRepository.findAll());
    }

    @Override
    public void delete(String id) {
        hppNoticeRepository.delete(id);
    }

    @Override
    public void deleteFlag(String id) {
        Notice hppNotice = hppNoticeRepository.findOne(id);
        if(hppNotice == null)return;
        hppNotice.setDelFlag("Y");
        hppNoticeRepository.save(hppNotice);
    }
}
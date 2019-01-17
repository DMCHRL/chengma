package com.chengma.devplatform.service;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.dto.SysOperateLogDTO;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/24.
 *
 * @author administrator
 */
public interface SysOperateLogService {

    /**
     * 保存日志
     *
     * @param sysOperateLogDTO 日志信息
     */
    void save(SysOperateLogDTO sysOperateLogDTO);

    /**
     * 添加操作日志
     *
     * @param account      账号
     * @param userName     用户名
     * @param type         操作类型 1登入登出，2业务
     * @param content      内容
     * @param opertateDate 操作时间
     */
    void addLog(String account, String userName, String type, Date opertateDate, String content);

    /**
     * 获取日志page
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{account:"",userName:"",type:"",operateContent:"",startTime:"",endTime:""}}
     * @return ResponseResult
     */
    ResponseResult findAll(HashMap<String, Object> params);

}

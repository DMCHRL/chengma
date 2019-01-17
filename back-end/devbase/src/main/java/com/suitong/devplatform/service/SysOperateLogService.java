package com.suitong.devplatform.service;

import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.service.dto.SysOperateLogDTO;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/24.
 */
public interface SysOperateLogService {

    /**
     * Save a sysOperateLog.
     *
     * @param sysOperateLogDTO the entity to save
     */
    void save(SysOperateLogDTO sysOperateLogDTO);

    /**
     * add a log
     * @param account
     * @param userName
     * @param content
     * @param opertateDate
     */
    void addLog(String account, String userName, String type, Date opertateDate, String content);

    /**
     *  Get all the sysOperateLog.
     *
     *  @return the list of entities
     */
    ResponseResult findAll(HashMap<String,Object> params);

}

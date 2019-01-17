package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.DateUtil;
import com.chengma.devplatform.common.util.IpKit;
import com.chengma.devplatform.domain.SysOperateLog;
import com.chengma.devplatform.repository.SysOperateLogRepository;
import com.chengma.devplatform.service.SysOperateLogService;
import com.chengma.devplatform.service.dto.SysOperateLogDTO;
import com.chengma.devplatform.service.mapper.SysOperateLogMapper;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/24.
 *
 * @author administrator
 */
@Service
@Transactional
public class SysOperateLogServiceImpl implements SysOperateLogService {

    private final Logger log = LoggerFactory.getLogger(SysOperateLogServiceImpl.class);

    private final SysOperateLogRepository sysOperateLogRepository;

    private final SysOperateLogMapper sysOperateLogMapper;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private DBService dbService;

    public SysOperateLogServiceImpl(SysOperateLogRepository sysOperateLogRepository, SysOperateLogMapper sysOperateLogMapper) {
        this.sysOperateLogRepository = sysOperateLogRepository;
        this.sysOperateLogMapper = sysOperateLogMapper;
    }

    /**
     * 保存日志
     *
     * @param sysOperateLogDTO 日志信息
     */
    @Override
    public void save(SysOperateLogDTO sysOperateLogDTO) {
        log.debug("Request to save SysOperateLogDTO : {}", sysOperateLogDTO);
        SysOperateLog sysOperateLog = sysOperateLogMapper.toEntity(sysOperateLogDTO);
        sysOperateLogRepository.save(sysOperateLog);
    }

    /**
     * 添加操作日志
     *
     * @param account      账号
     * @param userName     用户名
     * @param type         操作类型 1登入登出，2业务
     * @param content      内容
     * @param opertateDate 操作时间
     */
    @Override
    public void addLog(String account, String userName, String type, Date opertateDate, String content) {
        String ip = IpKit.getRealIp(request);
        SysOperateLogDTO sysOperateLogDTO = new SysOperateLogDTO();
        sysOperateLogDTO.setAccount(account);
        sysOperateLogDTO.setUserName(userName);
        sysOperateLogDTO.setAddress(ip);
        sysOperateLogDTO.setType(type);
        sysOperateLogDTO.setOperateContent(content);
        sysOperateLogDTO.setCreateTime(new Date());
        sysOperateLogDTO.setLogTime(opertateDate);
        this.save(sysOperateLogDTO);
    }

    /**
     * 获取日志page
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{account:"",userName:"",type:"",operateContent:"",startTime:"",endTime:""}}
     * @return ResponseResult
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseResult findAll(HashMap<String, Object> params) {
        log.debug("Request to get all SysOperateLog");
        int pageNumber = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int pageSize = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");
        String account = formParams.get("account") == null ? "" : formParams.get("account").toString();
        String userName = formParams.get("userName") == null ? "" : formParams.get("userName").toString();
        String type = formParams.get("type") == null ? "" : formParams.get("type").toString();
        String operateContent = formParams.get("operateContent") == null ? "" : formParams.get("operateContent").toString();
        Date startTime = formParams.get("startTime") == null ? null : DateUtil.toGMT(formParams.get("startTime").toString(), "yyyy-MM-dd");
        Date endTime = formParams.get("endTime") == null ? null : DateUtil.toGMT(formParams.get("endTime").toString(), "yyyy-MM-dd");

        String column = "select sol.c_id as id,sol.c_account as account,sol.c_user_name as user_name,sol.d_create_time as create_time," +
                "sol.c_type as type,sol.c_address as address,sol.d_log_time as log_time,sol.c_operate_content as operate_content";
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_operate_log sol ");
        cond.append(" where 1=1 ");
        if (StringUtils.isNotBlank(account)) {
            cond.append(" and sol.c_account like ");
            cond.append("'%" + account + "%'");
        }
        if (StringUtils.isNotBlank(userName)) {
            cond.append(" and sol.c_user_name like ");
            cond.append("'%" + userName + "%'");
        }
        if (StringUtils.isNotBlank(type)) {
            cond.append(" and sol.c_type = ");
            cond.append("'" + type + "'");
        }
        if (StringUtils.isNotBlank(operateContent)) {
            cond.append(" and sol.c_operate_content like ");
            cond.append("'%" + operateContent + "%'");
        }
        if (startTime != null) {
            String date = DateUtil.formatSimpleDate(startTime);
            cond.append(" and " + dbService.date2char("sol.d_log_time") + " >= ");
            cond.append("'" + date + "'");
        }
        if (endTime != null) {
            String date = DateUtil.formatSimpleDate(endTime);
            cond.append(" and " + dbService.date2char("sol.d_log_time") + " <= ");
            cond.append("'" + date + "'");
        }
        cond.append(" order by sol.d_log_time desc");
        HashMap<String, Object> response = new HashMap<>();
        ResponseResult json = new ResponseResult();
        try {
            Page<SysOperateLogDTO> page = pageCommon.execPage(column, cond.toString(), pageNumber, pageSize, SysOperateLogDTO.class);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys-operateLog/getAllSysOperateLogs");
            response.put("list", page.getContent());
            response.put("total_count", headers.get("X-Total-Count").get(0));
            response.put("link", headers.get(HttpHeaders.LINK).get(0));
            response.put("page_number", (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString())));
            response.put("page_size", (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString())));
            json.setData(response);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        } catch (Exception e) {
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return json;
    }


}

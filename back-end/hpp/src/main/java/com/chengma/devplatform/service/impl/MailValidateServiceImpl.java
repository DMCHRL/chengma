package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumGroup;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.util.SysRestUtil;
import com.chengma.devplatform.domain.HppMobileValidate;
import com.chengma.devplatform.domain.MailValidate;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.MailValidateRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.dto.MailValidateDTO;
import com.chengma.devplatform.service.mapper.MailValidateMapper;
import com.chengma.devplatform.service.mapper.MailValidateMapper;
import liquibase.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Service Implementation for managing HppMobileValidate.
 *
 * @author administrator
 */
@Service
@Transactional
public class MailValidateServiceImpl implements MailValidateService {

    private final Logger log = LoggerFactory.getLogger(MailValidateServiceImpl.class);

    private final MailValidateRepository mailValidateRepository;

    private final MailValidateMapper mailValidateMapper;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SysOperateLogService sysOperateLogService;

    @Autowired
    private DBService dbService;

    @Autowired
    private MailService mailService;

    public MailValidateServiceImpl(MailValidateRepository mailValidateRepository, MailValidateMapper mailValidateMapper) {
        this.mailValidateRepository = mailValidateRepository;
        this.mailValidateMapper = mailValidateMapper;
    }

    @Override
    public MailValidateDTO save(MailValidateDTO mailValidateDTO) {
        log.debug("Request to save mailValidateDTO : {}", mailValidateDTO);
        MailValidate mailValidate = mailValidateMapper.toEntity(mailValidateDTO);
        mailValidate = mailValidateRepository.save(mailValidate);
        return mailValidateMapper.toDto(mailValidate);
    }

    @Override
    public MailValidateDTO findByMobile(HashMap<String, Object> params) {
        String mail = params.get("mail") == null ? "" : params.get("mail").toString();

        StringBuilder column = new StringBuilder(" select c_id ");
        column.append(" ,c_mail mobile_num,c_validate_code validate_code,d_failure_time failure_time ");
        column.append(" ,c_create_by create_by,d_create_at create_at ");
        column.append(" ,c_last_update_by last_update_by,d_last_update_at last_update_at ");

        StringBuilder cond = new StringBuilder(" from t_mail_validate where 1 = 1 ");
        cond.append(" and c_mail = '" + mail + "'");
        cond.append(" and d_failure_time >= " + dbService.sysdate() + " ");
        cond.append(" order by d_create_at desc ");

        List<MailValidateDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), MailValidateDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public HashMap<String, Object> verification(HashMap<String, Object> params) {
        MailValidateDTO dto = findByMobile(params);//查找验证码
        //ResponseResult response = new ResponseResult();
        HashMap<String, Object> retMap  = new HashMap<>();

        if (null != dto) {
            String validateCode = params.get("validateCode") == null ? "" : params.get("validateCode").toString();
            if (validateCode.equals(dto.getValidateCode())) {
                dto.setFailureTime(dto.getCreateAt());//使之失效
                dto.setLastUpdateAt(new Date());
                save(dto);
                retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
                //response.setStatusCode(ResponseResult.SUCCESS_CODE);
            } else {
                //response.setMsgCode("invalidCode");
                retMap.put("statusCode", "invalidCode");
            }
        } else {
            //response.setMsgCode("invalidCode");
            retMap.put("statusCode", "invalidCode");
        }

        return retMap;
    }

    @Override
    public HashMap<String, Object> sendCode(HashMap<String, Object> params) {
        String mail = params.get("mail") == null ? "" : params.get("mail").toString();

        String code = SysRestUtil.getCode();
        User temp = new User();
        temp.setEmail(mail);
        temp.setLangKey("zh-cn");
        temp.setCharNo(code);
        mailService.sendMailValidate(temp);
        HashMap<String, Object> retMap  = new HashMap<>();
        MailValidateDTO mailValidateDTO = new MailValidateDTO();
        mailValidateDTO.setCreateAt(new Date());
        mailValidateDTO.setValidateCode(code);
        mailValidateDTO.setMail(mail);
        mailValidateDTO.setFailureTime(new Date(System.currentTimeMillis() + 60000 * SysRestUtil.getExpiryTime()));//失效时间10分钟
        save(mailValidateDTO);
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HashMap<String, Object> notice(HashMap<String, Object> params) {
        return null;
    }
}

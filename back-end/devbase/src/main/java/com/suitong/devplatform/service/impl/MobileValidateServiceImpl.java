package com.suitong.devplatform.service.impl;


import com.suitong.devplatform.common.dao.BaseDao;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.common.util.SysRestUtil;
import com.suitong.devplatform.domain.MobileValidate;
import com.suitong.devplatform.domain.User;
import com.suitong.devplatform.repository.MobileValidateRepository;
import com.suitong.devplatform.service.MobileValidateService;
import com.suitong.devplatform.service.SysOperateLogService;
import com.suitong.devplatform.service.UserService;
import com.suitong.devplatform.service.dto.MobileValidateDTO;
import com.suitong.devplatform.service.mapper.MobileValidateMapper;
import liquibase.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Service Implementation for managing MobileValidate.
 */
@Service
@Transactional
public class MobileValidateServiceImpl implements MobileValidateService {

    private final Logger log = LoggerFactory.getLogger(MobileValidateServiceImpl.class);

    private final MobileValidateRepository mobileValidateRepository;

    private final MobileValidateMapper mobileValidateMapper;

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private UserService userService;
    @Autowired
    private SysOperateLogService sysOperateLogService;

    public MobileValidateServiceImpl(MobileValidateRepository mobileValidateRepository, MobileValidateMapper mobileValidateMapper) {
        this.mobileValidateRepository = mobileValidateRepository;
        this.mobileValidateMapper = mobileValidateMapper;
    }

    /**
     * Save a mobileValidate.
     */
    @Override
    public MobileValidateDTO save(MobileValidateDTO mobileValidateDTO) {
        log.debug("Request to save MobileValidate : {}", mobileValidateDTO);
        MobileValidate mobileValidate = mobileValidateMapper.toEntity(mobileValidateDTO);
        mobileValidate = mobileValidateRepository.save(mobileValidate);
        return mobileValidateMapper.toDto(mobileValidate);
    }

    /**
     * 根据手机号查询
     */
    @Override
    @Transactional(readOnly = true)
    public MobileValidateDTO findByMobile(HashMap<String, Object> params) {
        String mobileNum = params.get("mobileNum") == null ? "" : params.get("mobileNum").toString();
        String mobile = params.get("mobile") == null ? "" : params.get("mobile").toString();
        if (StringUtils.isEmpty(mobileNum)) {
            mobileNum = mobile;
        }

        StringBuilder column = new StringBuilder(" select id ");
        column.append(" ,c_mobile_num mobile_num,c_validate_code validate_code,d_failure_time failure_time ");
        column.append(" ,i_create_by create_by,d_create_at create_at ");
        column.append(" ,i_last_update_by last_update_by,d_last_update_at last_update_at ");

        StringBuilder cond = new StringBuilder(" from t_mobile_validate where 1 = 1 ");
        cond.append(" and c_mobile_num = '" + mobileNum + "'");
        cond.append(" and d_failure_time >= now() ");
        cond.append(" order by d_create_at desc ");

        List<MobileValidateDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), MobileValidateDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 发送验证码
     */
    @Override
    public ResponseResult sendCode(HashMap<String, Object> params) {

        String mobileNum = params.get("mobileNum") == null ? "" : params.get("mobileNum").toString();

        String mobile = params.get("mobile") == null ? "" : params.get("mobile").toString();
        if (StringUtils.isEmpty(mobileNum)) {
            mobileNum = mobile;
        }

        String code = SysRestUtil.getCode();
        String sendParams = code + ",10";//拼凑参数，参数1是验证码，“,”后面是参数2表示10分钟
        String respCode = SysRestUtil.sendCode(mobileNum, sendParams);//发送验证码
        ResponseResult response = new ResponseResult();
        String successCode = "000000";
        if (successCode.equals(respCode)) {
            User user = userService.getUserWithAuthorities();
            MobileValidateDTO mobileValidateDTO = new MobileValidateDTO();
            mobileValidateDTO.setCreateBy(user.getId());
            mobileValidateDTO.setCreateAt(new Date());
            mobileValidateDTO.setValidateCode(code);
            mobileValidateDTO.setMobileNum(mobileNum);
            mobileValidateDTO.setFailureTime(new Date(System.currentTimeMillis() + 600000));//失效时间10分钟
            save(mobileValidateDTO);

            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "此手机号请求了验证码：" + mobileNum);

            response.setStatusCode(ResponseResult.SUCCESS_CODE);
        }
        return response;
    }

    /**
     * 验证随机码
     */
    @Override
    public ResponseResult verification(HashMap<String, Object> params) {

        MobileValidateDTO dto = findByMobile(params);//查找验证码
        ResponseResult response = new ResponseResult();

        if (null != dto) {
            String validateCode = params.get("validateCode") == null ? "" : params.get("validateCode").toString();
            if (validateCode.equals(dto.getValidateCode())) {
                dto.setFailureTime(dto.getCreateAt());//使之失效
                dto.setLastUpdateAt(new Date());
                save(dto);
                response.setStatusCode(ResponseResult.SUCCESS_CODE);
            } else {
                response.setMsgCode("invalidCode");
            }
        } else {
            response.setMsgCode("invalidCode");
        }

        return response;
    }

    /**
     * 发送报名成功通知（示例）
     */
    @Override
    public HashMap<String, Object> notice(HashMap<String, Object> params) {
        String content = params.get("content") == null ? "" : params.get("content").toString();
        //设计问题，验证中手机号出现两种写法，然开发中没有及时更改
        String mobileNum = params.get("mobileNum") == null ? "" : params.get("mobileNum").toString();
        String mobile = params.get("mobile") == null ? "" : params.get("mobile").toString();
        if (StringUtils.isEmpty(mobileNum)) {
            mobileNum = mobile;
        }

        content = "8.09读书沙龙报名 | 创业维艰：怎样才能避免创业失败？";
        content += ",2017-08-31 09:00";
        content += ",广州市天河区珠江西路17号 广晟国际大厦3812";

        String respCode = SysRestUtil.noticeForSignUp(mobileNum, content);//发送通知
        HashMap<String, Object> response = new HashMap();
        String successCode = "000000";
        if (successCode.equals(respCode)) {
            response.put("statusCode", HttpStatus.OK);
        } else {
            response.put("msg", respCode);
        }

        return response;
    }
}

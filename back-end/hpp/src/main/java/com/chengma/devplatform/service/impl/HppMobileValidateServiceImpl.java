package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumGroup;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.util.StringUtil;
import com.chengma.devplatform.common.util.SysRestUtil;
import com.chengma.devplatform.domain.HppIntegral;
import com.chengma.devplatform.domain.HppMobileValidate;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppMobileValidateRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.HppIntegralDTO;
import com.chengma.devplatform.service.dto.HppMobileUserDTO;
import com.chengma.devplatform.service.dto.HppMobileValidateDTO;
import com.chengma.devplatform.service.mapper.HppMobileValidateMapper;
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
public class HppMobileValidateServiceImpl implements HppMobileValidateService {

    private final Logger log = LoggerFactory.getLogger(HppMobileValidateServiceImpl.class);

    private final HppMobileValidateRepository hppMobileValidateRepository;

    private final HppMobileValidateMapper hppMobileValidateMapper;

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private UserService userService;
    @Autowired
    private SysOperateLogService sysOperateLogService;
    @Autowired
    private DBService dbService;

    @Autowired
    private HppMobileUserService hppMobileUserService;

    @Autowired
    private HppIntegralService hppIntegralService;

    @Autowired
    private SerialNoService serialNoService;

    public HppMobileValidateServiceImpl(HppMobileValidateRepository hppMobileValidateRepository, HppMobileValidateMapper hppMobileValidateMapper) {
        this.hppMobileValidateRepository = hppMobileValidateRepository;
        this.hppMobileValidateMapper = hppMobileValidateMapper;
    }

    /**
     * 保存
     *
     * @param hppMobileValidateDTO 短信验证码信息
     * @return HppMobileValidateDTO
     */
    @Override
    public HppMobileValidateDTO save(HppMobileValidateDTO hppMobileValidateDTO) {
        log.debug("Request to save HppMobileValidate : {}", hppMobileValidateDTO);
        HppMobileValidate hppMobileValidate = hppMobileValidateMapper.toEntity(hppMobileValidateDTO);
        hppMobileValidate = hppMobileValidateRepository.save(hppMobileValidate);
        return hppMobileValidateMapper.toDto(hppMobileValidate);
    }

    /**
     * 根据手机号查询
     *
     * @param params 参数：{mobileNum:""}
     * @return HppMobileValidateDTO
     */
    @Override
    @Transactional(readOnly = true)
    public HppMobileValidateDTO findByMobile(HashMap<String, Object> params) {
        String mobileNum = params.get("mobileNum") == null ? "" : params.get("mobileNum").toString();
        String mobile = params.get("mobile") == null ? "" : params.get("mobile").toString();
        if (StringUtils.isEmpty(mobileNum)) {
            mobileNum = mobile;
        }

        StringBuilder column = new StringBuilder(" select c_id ");
        column.append(" ,c_mobile_num mobile_num,c_validate_code validate_code,d_failure_time failure_time ");
        column.append(" ,c_create_by create_by,d_create_at create_at ");
        column.append(" ,c_last_update_by last_update_by,d_last_update_at last_update_at ");

        StringBuilder cond = new StringBuilder(" from t_hpp_mobile_validate where 1 = 1 ");
        cond.append(" and c_mobile_num = '" + mobileNum + "'");
        cond.append(" and d_failure_time >= " + dbService.sysdate() + " ");
        cond.append(" order by d_create_at desc ");

        List<HppMobileValidateDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), HppMobileValidateDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 发送验证码
     *
     * @param params 参数：{mobileNum:""}
     * @return ResponseResult
     */
    @Override
    public HashMap<String, Object> sendCode(HashMap<String, Object> params) {

        String mobileNum = params.get("mobileNum") == null ? "" : params.get("mobileNum").toString();

        String mobile = params.get("mobile") == null ? "" : params.get("mobile").toString();
        if (StringUtils.isEmpty(mobileNum)) {
            mobileNum = mobile;
        }

        String code = SysRestUtil.getCode();
        String sendParams = code + "," + SysRestUtil.getExpiryTime();//拼凑参数，参数1是验证码，“,”后面是参数2表示的时间
        String sendType = "sendCode";
        String respCode = SysRestUtil.sendMessage(mobileNum, sendParams, sendType);//发送验证码
        ResponseResult response = new ResponseResult();
        String successCode = "000000";
        HashMap<String, Object> retMap  = new HashMap<>();
        if (successCode.equals(respCode)) {
            User user = userService.getUserWithAuthorities();
            HppMobileValidateDTO hppMobileValidateDTO = new HppMobileValidateDTO();
            hppMobileValidateDTO.setCreateBy(user.getId());
            hppMobileValidateDTO.setCreateAt(new Date());
            hppMobileValidateDTO.setValidateCode(code);
            hppMobileValidateDTO.setMobileNum(mobileNum);
            hppMobileValidateDTO.setFailureTime(new Date(System.currentTimeMillis() + 60000 * SysRestUtil.getExpiryTime()));//失效时间10分钟
            save(hppMobileValidateDTO);


            //save mobile user
            HppMobileUserDTO mobileDTO = hppMobileUserService.findByMobile(mobileNum);
            if(mobileDTO == null || mobileDTO.getId() == null){ //
                mobileDTO = new HppMobileUserDTO();
                Date now=new Date();
                mobileDTO.setPhone(mobileNum);
                mobileDTO.setCreateAt(now);
                mobileDTO.setUpdateAt(now);
                mobileDTO.setUserName(DevplatformConstants.DEFAULT_NAME);
                mobileDTO.setHeadImg(DevplatformConstants.DEFAULT_HEAD_IMG);
                mobileDTO.setFlag(DevplatformConstants.MOBILE_FLAG_NO);
                mobileDTO.setFollowFlag("N");
                mobileDTO.setBuyFlag("N");
                mobileDTO.setOpenFlag("N");
                //生成推荐码
                String recommendationNo = serialNoService.getAccountNo(EnumGroup.valueOf("recommendation").value());
                while(hppMobileUserService.findByRecommendation(recommendationNo) != null && userService.findByCharNo(recommendationNo) != null){
                    recommendationNo = serialNoService.getAccountNo(EnumGroup.valueOf("recommendation").value());
                }
                mobileDTO.setRecommendation(recommendationNo);
                hppMobileUserService.save(mobileDTO);

                //初始化用户积分
                HppIntegralDTO hppIntegralDTO=new HppIntegralDTO();
                hppIntegralDTO.setMobileNum(mobileNum);
                hppIntegralDTO.setTotal(DevplatformConstants.INTEGRAL);
                hppIntegralDTO.setBalance(DevplatformConstants.INTEGRAL);
                hppIntegralDTO.setUsed(0.0);
                hppIntegralDTO.setCreateAt(now);
                hppIntegralDTO.setUpdateAt(now);
                hppIntegralService.save(hppIntegralDTO);
            }

            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "此手机号请求了验证码：" + mobileNum);

            //response.setStatusCode(ResponseResult.SUCCESS_CODE);
            retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        }
        return retMap;
    }

    /**
     * 验证随机码
     *
     * @param params 参数：{mobileNum:"",validateCode:""}
     * @return ResponseResult
     */
    @Override
    public HashMap<String, Object> verification(HashMap<String, Object> params) {

        HppMobileValidateDTO dto = findByMobile(params);//查找验证码
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

    /**
     * 发送通知（示例）
     *
     * @param params 参数：{mobileNum:""}
     * @return ResponseResult
     */
    @Override
    public HashMap<String, Object> notice(HashMap<String, Object> params) {
        String content = params.get("content") == null ? "" : params.get("content").toString();
        //设计问题，验证中手机号出现两种写法，然开发中没有及时更改
        String mobileNum = params.get("mobileNum") == null ? "" : params.get("mobileNum").toString();
        String mobile = params.get("mobile") == null ? "" : params.get("mobile").toString();
        String sendType = params.get("sendType") == null ? "" : params.get("sendType").toString();
        HashMap<String, Object> retMap  = new HashMap<>();
        if (StringUtils.isEmpty(mobileNum)) {
            mobileNum = mobile;
        }

       /* content = "8.09读书沙龙报名 | 创业维艰：怎样才能避免创业失败？";
        content += ",2017-08-31 09:00";
        content += ",广州市天河区珠江西路17号 广晟国际大厦3812";*/

        String respCode = SysRestUtil.sendMessage(mobileNum, content, sendType);//发送通知
        //ResponseResult response = new ResponseResult();
        String successCode = "000000";
        if (successCode.equals(respCode)) {
            retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
            //response.setStatusCode(ResponseResult.SUCCESS_CODE);
        } else {
            //response.setMsgCode(respCode);
            retMap.put("statusCode", respCode);
        }

        return retMap;
    }

     public static void main(String[] args){

         }
}

package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumGroup;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.util.SysRestUtil;
import com.chengma.devplatform.domain. MobileValidate;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository. MobileValidateRepository;
import com.chengma.devplatform.service.MobileUserService;
import com.chengma.devplatform.service.MobileValidateService;
import com.chengma.devplatform.service.SysOperateLogService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto. MobileUserDTO;
import com.chengma.devplatform.service.dto. MobileValidateDTO;
import com.chengma.devplatform.service.mapper. MobileValidateMapper;
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
 * Service Implementation for managing  MobileValidate.
 *
 * @author administrator
 */
@Service
@Transactional
public class MobileValidateServiceImpl implements MobileValidateService {

    private final Logger log = LoggerFactory.getLogger( MobileValidateServiceImpl.class);

    private final  MobileValidateRepository  MobileValidateRepository;

    private final  MobileValidateMapper  MobileValidateMapper;

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private UserService userService;
    @Autowired
    private SysOperateLogService sysOperateLogService;
    @Autowired
    private DBService dbService;

    @Autowired
    private MobileUserService mobileUserService;

    @Autowired
    private SerialNoService serialNoService;

    public MobileValidateServiceImpl( MobileValidateRepository  MobileValidateRepository,  MobileValidateMapper  MobileValidateMapper) {
        this. MobileValidateRepository =  MobileValidateRepository;
        this. MobileValidateMapper =  MobileValidateMapper;
    }

    /**
     * 保存
     *
     * @param  MobileValidateDTO 短信验证码信息
     * @return  MobileValidateDTO
     */
    @Override
    public  MobileValidateDTO save( MobileValidateDTO  MobileValidateDTO) {
        log.debug("Request to save  MobileValidate : {}",  MobileValidateDTO);
         MobileValidate  MobileValidate =  MobileValidateMapper.toEntity( MobileValidateDTO);
         MobileValidate =  MobileValidateRepository.save( MobileValidate);
        return  MobileValidateMapper.toDto( MobileValidate);
    }

    /**
     * 根据手机号查询
     *
     * @param params 参数：{mobileNum:""}
     * @return  MobileValidateDTO
     */
    @Override
    @Transactional(readOnly = true)
    public  MobileValidateDTO findByMobile(HashMap<String, Object> params) {
        String mobileNum = params.get("mobileNum") == null ? "" : params.get("mobileNum").toString();
        String mobile = params.get("mobile") == null ? "" : params.get("mobile").toString();
        if (StringUtils.isEmpty(mobileNum)) {
            mobileNum = mobile;
        }

        StringBuilder column = new StringBuilder(" select c_id ");
        column.append(" ,c_mobile_num mobile_num,c_validate_code validate_code,d_failure_time failure_time ");
        column.append(" ,c_create_by create_by,d_create_at create_at ");
        column.append(" ,c_last_update_by last_update_by,d_last_update_at last_update_at ");

        StringBuilder cond = new StringBuilder(" from t_lhfz_mobile_validate where 1 = 1 ");
        cond.append(" and c_mobile_num = '" + mobileNum + "'");
        cond.append(" and d_failure_time >= " + dbService.sysdate() + " ");
        cond.append(" order by d_create_at desc ");

        List< MobileValidateDTO> list = baseDao.findListBySql(column.toString(), cond.toString(),  MobileValidateDTO.class);
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
             MobileValidateDTO  MobileValidateDTO = new  MobileValidateDTO();
             MobileValidateDTO.setCreateBy(user.getId());
             MobileValidateDTO.setCreateAt(new Date());
             MobileValidateDTO.setValidateCode(code);
             MobileValidateDTO.setMobileNum(mobileNum);
             MobileValidateDTO.setFailureTime(new Date(System.currentTimeMillis() + 60000 * SysRestUtil.getExpiryTime()));//失效时间10分钟
            save( MobileValidateDTO);

            //save mobile user
             MobileUserDTO mobileDTO =  mobileUserService.findByMobile(mobileNum);
            if(mobileDTO == null || mobileDTO.getId() == null){ //
                mobileDTO = new  MobileUserDTO();
                Date now=new Date();
                mobileDTO.setMobile(mobileNum);
                mobileDTO.setCreateAt(now);
                mobileDTO.setUpdateAt(now);
                mobileDTO.setName(DevplatformConstants.DEFAULT_NAME);
                mobileDTO.setHeadImg(DevplatformConstants.DEFAULT_HEAD_IMG);
                mobileDTO.setStatus(DevplatformConstants.MOBILE_USER_UNAUTHORIZED);
                mobileDTO.setLevel(DevplatformConstants.USER_LEVEL);
               /* //生成推荐码
                String recommendationNo = serialNoService.getAccountNo(EnumGroup.valueOf("recommendation").value());
                while( MobileUserService.findByRecommendation(recommendationNo) != null && userService.findByCharNo(recommendationNo) != null){
                    recommendationNo = serialNoService.getAccountNo(EnumGroup.valueOf("recommendation").value());
                }
                mobileDTO.setRecommendation(recommendationNo);*/
                mobileUserService.save(mobileDTO);
            }

            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "此手机号请求了验证码：" + code);

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

         MobileValidateDTO dto = findByMobile(params);//查找验证码
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

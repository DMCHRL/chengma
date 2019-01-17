package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.domain.Asset;
import com.chengma.devplatform.domain.MobileUser;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.service.AssetService;
import com.chengma.devplatform.service.MobileUserService;
import com.chengma.devplatform.service.MobileValidateService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.MobileUserDTO;
import com.chengma.devplatform.service.mapper.UserMapper;
import com.chengma.devplatform.web.rest.vm.LoginVM;
import com.codahale.metrics.annotation.Timed;
import com.chengma.devplatform.common.dao.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * REST controller for managing MobileValidate.
 *
 * @author administrator
 */
@RestController
@RequestMapping("/api")
public class MobileValidateResource {


    @Autowired
    private UserService userService;

    @Autowired
    private MobileUserService mobileUserService;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AssetService assetService;

    private final MobileValidateService mobileValidateService;

    private final UserJWTController userJWTController;

    public MobileValidateResource(MobileValidateService mobileValidateService, UserJWTController userJWTController) {
        this.mobileValidateService = mobileValidateService;
        this.userJWTController = userJWTController;
    }

    /**
     * post /mobileValidate/sendCode ：发送短信验证码
     *
     * @param params 参数：{mobileNum:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/mobileValidate/sendCode")
    @Timed
    public ResponseEntity<ResponseResult> sendCode(@RequestBody HashMap<String, Object> params) {
        ResponseResult response = new ResponseResult();
        HashMap<String, Object> retMap =  mobileValidateService.sendCode(params);
        if(ResponseResult.SUCCESS_CODE.equals(retMap.get("statusCode"))){
            response.setStatusCode(ResponseResult.SUCCESS_CODE);
        }else{
            String msgCode = retMap.get("statusCode") == null ? "" : retMap.get("statusCode").toString();
            response.setMsgCode(msgCode);
        }
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    /**
     * post /mobileValidate/verification ：校验短信验证码
     *
     * @param params 参数：{mobileNum:"",validateCode:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/mobileValidate/verification")
    @Timed
    public ResponseEntity<ResponseResult> verification(@Valid @RequestBody HashMap<String, Object> params, HttpServletResponse response, HttpServletRequest request) {
        ResponseResult returnResponse = new ResponseResult();
        HashMap<String, Object> retMap =  mobileValidateService.verification(params);
        if(ResponseResult.SUCCESS_CODE.equals(retMap.get("statusCode"))){
            //处理手机认证登录
            String mobile = params.get("mobileNum").toString();
            MobileUserDTO mobileUserDTO = mobileUserService.findByMobile(mobile);

            User dbUser = userService.findUserByMobile(mobile); //findMobile
            //查询是否已经进行登记
            //登记
            if (dbUser == null) {
                dbUser=new User();
                dbUser.setLogin(mobile);
                dbUser.setMobile(mobile);
                dbUser.setPassword(DevplatformConstants.USER_DEFAULT_PASSWORD);
                dbUser.setDepartment("partner");  //标记为合伙人
                dbUser.setFirstName(mobileUserDTO.getName());
                dbUser=userService.createUser(userMapper.userToUserDTO(dbUser));

            }
            //初始化资金
            if(null == assetService.findByUserId(dbUser.getId())){
                assetService.initAsset(dbUser.getId());
            }

            //app用户绑定jhi_user_id
            mobileUserDTO.setUserId(dbUser.getId());
            mobileUserService.save(mobileUserDTO);

            // 直接跳转到后台
            LoginVM loginVM = new LoginVM();
            loginVM.setUsername(dbUser.getLogin());
            loginVM.setPassword(dbUser.getPasswordRemark());
            return userJWTController.inAuthorize(request, loginVM, response, true);
        }else{
            String msgCode = retMap.get("statusCode") == null ? "" : retMap.get("statusCode").toString();
            returnResponse.setMsgCode(msgCode);
        }
        return new ResponseEntity<>(returnResponse, null, HttpStatus.OK);
    }

    /**
     * post /mobileValidate/notice ：发送通知短信（示例）
     *
     * @param params 参数：{mobileNum:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/mobileValidate/notice")
    @Timed
    public ResponseEntity<ResponseResult> notice(@RequestBody HashMap<String, Object> params) {
        ResponseResult response = new ResponseResult();
        HashMap<String, Object> retMap =  mobileValidateService.notice(params);
        if(ResponseResult.SUCCESS_CODE.equals(retMap.get("statusCode"))){
            response.setStatusCode(ResponseResult.SUCCESS_CODE);
        }else{
            String msgCode = retMap.get("statusCode") == null ? "" : retMap.get("statusCode").toString();
            response.setMsgCode(msgCode);
        }
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }
}

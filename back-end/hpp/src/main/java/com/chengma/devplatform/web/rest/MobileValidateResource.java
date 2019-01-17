package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.domain.HppMobileUser;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.service.HppMobileUserService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.HppMobileUserDTO;
import com.chengma.devplatform.service.mapper.UserMapper;
import com.chengma.devplatform.web.rest.vm.LoginVM;
import com.codahale.metrics.annotation.Timed;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.HppMobileValidateService;
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
import java.util.Map;

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
    private HppMobileUserService hppMobileUserService;


    @Autowired
    private UserMapper userMapper;

    private final HppMobileValidateService hppMobileValidateService;

    private final UserJWTController userJWTController;

    public MobileValidateResource(HppMobileValidateService hppMobileValidateService, UserJWTController userJWTController) {
        this.hppMobileValidateService = hppMobileValidateService;
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
        HashMap<String, Object> retMap =  hppMobileValidateService.sendCode(params);
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
        HashMap<String, Object> retMap = hppMobileValidateService.verification(params);
        if(ResponseResult.SUCCESS_CODE.equals(retMap.get("statusCode"))){
            //处理手机认证登录
            String mobile = params.get("mobileNum").toString();

            String recommendationEd = params.get("recommendationEd") == null? "3" : params.get("recommendationEd").toString();
            HppMobileUserDTO hppMobileUserDTO = hppMobileUserService.findByMobile(mobile);
            hppMobileUserDTO.setRecommendationEd(recommendationEd);
            hppMobileUserService.save(hppMobileUserDTO);

            User dbUser = userService.findUserByMobileAndDepartment(mobile,"mobile"); //带上身份查询
            //查询是否已经进行登记
            //登记
            if (dbUser == null) {
                dbUser=new User();
                dbUser.setLogin(mobile);
                dbUser.setMobile(mobile);
                dbUser.setPassword(DevplatformConstants.USER_DEFAULT_PASSWORD);
                dbUser.setDepartment("mobile");  //标记为手机用户
                dbUser=userService.createUser(userMapper.userToUserDTO(dbUser));
            }
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
        HashMap<String, Object> retMap = hppMobileValidateService.notice(params);
        if(ResponseResult.SUCCESS_CODE.equals(retMap.get("statusCode"))){
            response.setStatusCode(ResponseResult.SUCCESS_CODE);
        }else{
            String msgCode = retMap.get("statusCode") == null ? "" : retMap.get("statusCode").toString();
            response.setMsgCode(msgCode);
        }
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }
}

package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.service.SysOperateLogService;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.util.EnvUtil;
import com.chengma.devplatform.common.util.VerifyCodeUtils;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.security.jwt.JWTConfigurer;
import com.chengma.devplatform.security.jwt.TokenProvider;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.UserDTO;
import com.chengma.devplatform.service.mapper.UserMapper;
import com.chengma.devplatform.web.rest.vm.LoginVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller to authenticate users.
 *
 * @author administrator
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private SysOperateLogService sysOperateLogService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpSession session;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    /**
     * post /authenticate ：登陆验证
     *
     * @param request  HTTP请求
     * @param loginVM  登陆参数模型
     * @param response HTTP响应
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<ResponseResult> authorize(HttpServletRequest request, @Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {
        boolean skip = "true".equals(EnvUtil.getProperty("loginControl.skipVerify"));
        return inAuthorize(request, loginVM, response, skip);
    }

    /**
     * 登陆验证
     *
     * @param request  HTTP请求
     * @param loginVM  登陆参数模型
     * @param response HTTP响应
     * @param skip     是否跳过验证码
     * @return statusCode:成功0000，失败0100。
     */
    public ResponseEntity<ResponseResult> inAuthorize(HttpServletRequest request, @Valid @RequestBody LoginVM loginVM, HttpServletResponse response, boolean skip) {
        //先默认验证码成功
        ResponseResult verifyResult = new ResponseResult();
        verifyResult.setStatusCode(ResponseResult.SUCCESS_CODE);
        if (!skip) {
            String inputCode = loginVM.getVerifyCode();
            //校验验证码
            boolean verifySucceed = VerifyCodeUtils.checkVerifyCode(request, inputCode, VerifyCodeUtils.TYPE_EQUALS_IGNORE_CASE);
            if (!verifySucceed) {
                verifyResult.setStatusCode(ResponseResult.FAIL_CODE);
                verifyResult.setMsgCode(DevplatformConstants.ERROR_VERIFYCODE);
            }
        }
        //校验登陆信息
        ResponseResult responseResult = userService.loginVerification(loginVM);
        if (ResponseResult.FAIL_CODE.equals(verifyResult.getStatusCode())) {
            return new ResponseEntity<>(verifyResult, null, HttpStatus.OK);
        } else if (ResponseResult.FAIL_CODE.equals(responseResult.getStatusCode())) {
            return new ResponseEntity<>(responseResult, null, HttpStatus.OK);
        } else {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

            try {
                Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
                String jwt = tokenProvider.createToken(authentication, rememberMe);
                response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);

                responseResult.setStatusCode(ResponseResult.SUCCESS_CODE);
                HashMap<String, Object> retMap = new HashMap<>();
                //把当前登录用户头像，昵称存到数据库
                Map userInfo = (Map) request.getAttribute("userInfo");
                User user = userService.getUserWithAuthorities();
                UserDTO userDTO = userMapper.userToUserDTO(user);
                retMap.put("id_token", jwt);
                retMap.put("user", userDTO);
                responseResult.setData(retMap);
                if (userInfo != null) {
                    String imageUrl = userInfo.get("headimgurl") == null ? "" : userInfo.get("headimgurl").toString();
                    String nickname = userInfo.get("nickname") == null ? "" : userInfo.get("nickname").toString();
                    userDTO.setImageUrl(imageUrl);
                    userDTO.setLastName(nickname);
                    userDTO = userService.encodeBase64(userDTO);
                    if (!userDTO.getImageUrl().equals(user.getImageUrl()) || !userDTO.getLastName().equals(user.getLastName())) {
                        userService.save(userDTO);
                    }
                }

                sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "1", new Date(), "用户：" + user.getLogin() + " 执行登陆操作");
                return new ResponseEntity<>(responseResult, null, HttpStatus.OK);
            } catch (AuthenticationException ae) {
                log.trace("Authentication exception trace: {}", ae);
                responseResult.setMsgCode("default");
                return new ResponseEntity<>(responseResult, null, HttpStatus.OK);
            }
        }
    }

    /**
     * get /generateVerifyCode ：生成图形验证码
     *
     * @param request  HTTP请求
     * @param response HTTP响应
     */
    @GetMapping("/generateVerifyCode")
    @Timed
    public void generateVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        try {
            //生成随机字串，可自定义字符源，支持大小写字母、数字
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            //存入会话session
            WebUtils.setSessionAttribute(request, VerifyCodeUtils.CODE_SAVED, verifyCode);
            //生成图片
            int w = 100, h = 40;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}

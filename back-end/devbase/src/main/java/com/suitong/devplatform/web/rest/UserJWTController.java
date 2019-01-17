package com.suitong.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.common.util.VerifyCodeUtils;
import com.suitong.devplatform.domain.User;
import com.suitong.devplatform.security.jwt.JWTConfigurer;
import com.suitong.devplatform.security.jwt.TokenProvider;
import com.suitong.devplatform.service.SysOperateLogService;
import com.suitong.devplatform.service.UserService;
import com.suitong.devplatform.service.dto.UserDTO;
import com.suitong.devplatform.service.mapper.UserMapper;
import com.suitong.devplatform.web.rest.vm.LoginVM;
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
import java.util.Date;
import java.util.Map;

/**
 * Controller to authenticate users.
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

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<ResponseResult> authorize(HttpServletRequest request, @Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {
        return inAuthorize(request, loginVM, response, true);
    }

    /**
     * 登陆验证方法
     * @param request
     * @param loginVM
     * @param response
     * @param skip 是否跳过验证码
     * @return
     */
    public ResponseEntity<ResponseResult> inAuthorize(HttpServletRequest request, @Valid @RequestBody LoginVM loginVM, HttpServletResponse response, boolean skip) {
        //先默认验证码成功
        ResponseResult checkResult = new ResponseResult();
        checkResult.setStatusCode(ResponseResult.SUCCESS_CODE);
        if (!skip) {
            String inputCode = loginVM.getVerifyCode();
            //校验验证码
            checkResult = VerifyCodeUtils.checkVerifyCode(request, inputCode, VerifyCodeUtils.TYPE_EQUALS_IGNORE_CASE);
        }
        //校验登陆信息
        ResponseResult responseResult = userService.loginVerification(loginVM);
        if (ResponseResult.FAIL_CODE.equals(checkResult.getStatusCode())) {
            return new ResponseEntity<>(checkResult, null, HttpStatus.OK);
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
                responseResult.setData(new JWTToken(jwt));
                //把当前登录用户头像，昵称存到数据库
                Map userInfo = (Map) request.getAttribute("userInfo");
                User user = userService.getUserWithAuthorities();
                UserDTO userDTO = userMapper.userToUserDTO(user);
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

    @GetMapping("/generateVerifyCode")
    @Timed
    public void generateVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串，可自定义字符源，支持大小写字母、数字
        //String sources = "abcdef23456789";
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //String verifyCode = VerifyCodeUtils.generateVerifyCode(4,sources);
        //存入会话session
        WebUtils.setSessionAttribute(request, VerifyCodeUtils.CODE_SAVED, verifyCode);
        //生成图片
        int w = 100, h = 40;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
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

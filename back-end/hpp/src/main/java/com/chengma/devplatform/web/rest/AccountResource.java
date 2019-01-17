package com.chengma.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.security.SecurityUtils;
import com.chengma.devplatform.service.MailService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.UserDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import com.chengma.devplatform.web.rest.vm.KeyAndPasswordVM;
import com.chengma.devplatform.web.rest.vm.ManagedUserVM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * REST controller for managing the current user's account.
 *
 * @author administrator
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

   /* private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    public AccountResource(UserRepository userRepository, UserService userService,
                           MailService mailService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    *//**
     * POST  /register : 注册用户。
     *
     * @param managedUserVM 系统用户模型
     * @return 状态：201：用户已注册；400：登录或邮箱已被使用
     *//*
    @PostMapping(path = "/register",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    public ResponseEntity registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

        User u = userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase());
        User u2 = userRepository.findOneByEmail(managedUserVM.getEmail());
        if (null != u) {
            return new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST);
        } else if (null != u2) {
            return new ResponseEntity<>("email address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST);
        } else {
            User user = userService
                    .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
                            managedUserVM.getFirstName(), managedUserVM.getLastName(),
                            managedUserVM.getEmail().toLowerCase(), managedUserVM.getImageUrl(),
                            managedUserVM.getLangKey());
            mailService.sendActivationEmail(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    *//**
     * GET  /activate : 激活已注册用户。
     *
     * @param key 激活密钥
     * @return 状态：200：请求成功并返回用户信息；500：服务器错误，用户无法激活。
     *//*
    @GetMapping("/activate")
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        User user = userService.activateRegistration(key);
        if (null != user) {
            return new ResponseEntity<String>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    *//**
     * GET  /authenticate : 检查用户是否经过身份验证，并返回其登录。
     *
     * @param request HTTP请求
     * @return 如果用户已验证，则登录。
     *//*
    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    *//**
     * GET  /account : 获取当前用户。
     *
     * @return 状态：200：请求成功并返回用户信息；500：服务器错误，不能返回用户信息。
     *//*
    @GetMapping("/account")
    @Timed
    public ResponseEntity<UserDTO> getAccount() {
        User loginUser = userService.getUserWithAuthorities();
        loginUser = userService.decodeBase64(loginUser);
        if (null != loginUser) {
            return new ResponseEntity<>(new UserDTO(loginUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    *//**
     * POST  /account : 更新当前用户的信息。
     *
     * @param userDTO 当前用户信息
     * @return 状态：200：请求成功；400：错误请求；500：服务器错误，用户不能被更新。
     *//*
    @PostMapping("/account")
    @Timed
    public ResponseEntity saveAccount(@Valid @RequestBody UserDTO userDTO) {
        final String userLogin = SecurityUtils.getCurrentUserLogin();
        User existingUser = userRepository.findOneByEmail(userDTO.getEmail());
        if (null != existingUser && (!existingUser.getLogin().equalsIgnoreCase(userLogin))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "Email already in use")).body(null);
        }

        User u = userRepository.findOneByLogin(userLogin);
        if (null != u) {
            userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                    userDTO.getLangKey(), userDTO.getImageUrl());
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    *//**
     * POST  /account/change_password : 更改当前用户的密码
     *
     * @param password 新的密码
     * @return 状态：200：请求成功；400：错误请求，新密码不够强。
     *//*
    @PostMapping(path = "/account/change_password",
            produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    *//**
     * POST   /account/reset_password/init : 发送电子邮件重置用户密码
     *
     * @param mail 用户的邮箱
     * @return 状态：200:请求成功，发送电子邮件；400：错误请求，电子邮件地址没有注册。
     *//*
    @PostMapping(path = "/account/reset_password/init",
            produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity requestPasswordReset(@RequestBody String mail) {
        User user = userService.requestPasswordReset(mail);
        if (null != user) {
            mailService.sendPasswordResetMail(user);
            return new ResponseEntity<>("email was sent", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("email address not registered", HttpStatus.BAD_REQUEST);
        }
    }

    *//**
     * POST   /account/reset_password/finish : 完成重新设置用户的密码。
     *
     * @param keyAndPassword 生成的密钥和新密码
     * @return 状态：200：请求成功，密码被重置；400：错误请求；500：服务器错误，密码不能重置。
     *//*
    @PostMapping(path = "/account/reset_password/finish",
            produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        User user = userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());
        if (null != user) {
            return new ResponseEntity<String>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
                password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }*/
}
